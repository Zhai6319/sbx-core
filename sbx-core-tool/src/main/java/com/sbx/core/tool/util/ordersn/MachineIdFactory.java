package com.sbx.core.tool.util.ordersn;

import com.sbx.core.tool.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MachineIdFactory {

    private static final Logger logger = LoggerFactory.getLogger(MachineIdFactory.class);

    private static int id = 0;


    public static int getId() {
        if (id == 0) {
            init();
        }
        return id;
    }

    public static void init() {
        try {
            String localIp = getLocalIp();
            if (!StringUtil.isEmpty(localIp) && localIp.contains(".")) {
                String[] ipArray = localIp.split("\\.");
                String ipLast = ipArray[ipArray.length - 1];
                id = Integer.valueOf(ipLast);
            }
        } catch (Exception e) {
            logger.error("获取机器id失败");
        }
    }

    public static String getLocalIp() throws UnknownHostException {
        String ip = null;
        InetAddress inetAddress = InetAddress.getLocalHost();
        ip = inetAddress.getHostAddress();
        return StringUtil.isBlank(ip) ? null : ip.trim();
    }

}
