/*
 * @(#)com.jrebel.ek.service 2019-12-11 18:15
 * @Author <a href="mailto:xyqierkang@163.com">qierkang</a>
 * @Blog：https://blog.csdn.net/qierkang
 * Copyright (c) 2019-2019 Shanghai
 * All rights reserved.

 * This software is the confidential and proprietary information of
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 */
package com.jrebel.ek;

import org.eclipse.jetty.server.Server;

import java.util.*;

/**
 * *********************************************************
 *
 * @Author <a href="mailto:xyqierkang@163.com">qierkang</a>
 * Blog: https://blog.csdn.net/qierkang
 * @Title RunServer$.java
 * @Date Created in $ $
 * <p>Description: [ TODO ] </p>
 * <p>Copyright:    </p>
 * <p>Company:      </p>
 * <p>Department:   </p>
 * *********************************************************
 */
public class RunServerTest {

    private static Map<String, String> parseArguments(String[] args) {
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("Error in argument's length ");
        }
        Map<String, String> params = new HashMap<String, String>();
        for (int i = 0, len = args.length; i < len; ) {
            String argName = args[i++];

            if (argName.charAt(0) == '-') {
                if (argName.length() < 2) {
                    throw new IllegalArgumentException("Error at argument " + argName);
                }
                argName = argName.substring(1);
            }
            params.put(argName, args[i++]);
        }
        return params;
    }


    public static void main(String[] args) throws Exception {
        Map<String, String> arguments = parseArguments(args);
        String port = arguments.get("p");
        if (port == null || !port.matches("\\d+")) {
            port = "8081";
        }
        Server server = new Server(Integer.parseInt(port));
        server.start();
        System.out.println("License Server started at http://localhost:" + port);
        System.out.println("JetBrains Activation address was: http://localhost:" + port + "/");
        System.out.println("JRebel 7.1 and earlier version Activation address was: http://localhost:" + port + "/{tokenname}, with any email.");
        System.out.println("JRebel 2018.1 and later version Activation address was: http://localhost:" + port + "/{guid}(eg:http://localhost:" + port + "/" + UUID.randomUUID().toString() + "), with any email.");
        server.join();
    }

}
