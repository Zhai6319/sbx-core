package org.sbx.core.launch;

import org.sbx.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.*;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>Application class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/3/22
 */
public class Application {

    public static ConfigurableApplicationContext run(String appName, Class source, String... args){
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(source);
        ConfigurableEnvironment environment = new StandardEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new SimpleCommandLinePropertySource(args));
        propertySources.addLast(new MapPropertySource(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, environment.getSystemProperties()));
        propertySources.addLast(new SystemEnvironmentPropertySource(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, environment.getSystemEnvironment()));
        // 获取配置的环境变量
        String[] activeProfiles = environment.getActiveProfiles();
        // 判断环境:dev、test、prod
        List<String> profiles = Arrays.asList(activeProfiles);
        // 预设的环境
        List<String> presetProfiles = new ArrayList<>(Arrays.asList("dev", "test", "prod"));
        // 交集
        presetProfiles.retainAll(profiles);
        // 当前使用
        List<String> activeProfileList = new ArrayList<>(profiles);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(source);
        String profile;
        if (activeProfileList.isEmpty()) {
            // 默认dev开发
            profile = "dev";
            activeProfileList.add(profile);
            builder.profiles(profile);
        } else if (activeProfileList.size() == 1) {
            profile = activeProfileList.get(0);
        } else {
            // 同时存在dev、test、prod环境时
            throw new RuntimeException("同时存在环境变量:[" + StringUtils.arrayToCommaDelimitedString(activeProfiles) + "]");
        }
        String startJarPath = Application.class.getResource("/").getPath().split("!")[0];
        Function<Object[], String> joinFun = StringUtils::arrayToCommaDelimitedString;
        String activePros = joinFun.apply(activeProfileList.toArray());
        System.out.println(String.format("----启动中，读取到的环境变量:[%s]，jar地址:[%s]----", activePros, startJarPath));
        Properties props = System.getProperties();
        props.setProperty("sbx.env",profile);
        props.setProperty("spring.application.name", appName);
        props.setProperty("spring.profiles.active",profile);
        props.setProperty("spring.cloud.nacos.config.file-extension","yaml");
        props.setProperty("spring.cloud.nacos.discovery.register-enable","true");
        props.setProperty("spring.cloud.nacos.discovery.enable","true");
        props.setProperty("spring.cloud.nacos.config.shared-dataids", "sbx.yaml,sbx-"+profile+".yaml");
        // 加载自定义组件
        List<LauncherService> launcherList = new ArrayList<>();
        ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
        launcherList.stream().sorted(Comparator.comparing(LauncherService::getOrder)).collect(Collectors.toList())
                .forEach(launcherService -> launcherService.launcher(builder,appName , profile, false));
        return springApplicationBuilder.run(args);
    }

}
