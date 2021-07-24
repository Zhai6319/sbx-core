package com.sbx.core.test;

import com.sbx.core.launch.service.LauncherService;
import org.junit.runners.model.InitializationError;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/1/21
 */
public class SbxSpringRunner extends SpringJUnit4ClassRunner {

    public SbxSpringRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
        setUpTestClass(clazz);
    }

    private void setUpTestClass(Class<?> clazz) {
        SbxBootTest sbxBootTest = AnnotationUtils.getAnnotation(clazz, SbxBootTest.class);
        if (sbxBootTest == null) {
            throw new SbxBootTestException(String.format("%s must be @SbxBootTest .", clazz));
        } else {
            String appName = sbxBootTest.appName();
            String profile = sbxBootTest.profile();
            Properties props = System.getProperties();
            props.setProperty("sbx.env",profile);
            props.setProperty("spring.application.name", appName);
            props.setProperty("spring.profiles.active",profile);
            props.setProperty("spring.main.allow-bean-definition-overriding", "true");
            props.setProperty("spring.cloud.nacos.config.file-extension","yaml");
            props.setProperty("spring.cloud.nacos.discovery.register-enable","true");
            props.setProperty("spring.cloud.nacos.discovery.enable","true");
            props.setProperty("spring.cloud.nacos.config.shared-dataids", "sbx.yaml,sbx-"+profile+".yaml,"+appName+"-secrecy.yaml");

            if (sbxBootTest.enableLoader()) {
                SpringApplicationBuilder builder = new SpringApplicationBuilder(new Class[]{clazz});
                List<LauncherService> launcherList = new ArrayList();
                ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
                launcherList.stream().sorted(Comparator.comparing(LauncherService::getOrder)).collect(Collectors.toList())
                        .forEach(launcherService -> launcherService.launcher(builder,appName , profile, false));
            }

            System.err.println(String.format("---[junit.test]:[%s]---启动中，读取到的环境变量:[%s]", appName, profile));
        }
    }

}
