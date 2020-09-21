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

import com.jrebel.ek.util.JrebelSign;
import com.jrebel.ek.util.rsasign;
import net.sf.json.JSONObject;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * *********************************************************
 *
 * @Author <a href="mailto:xyqierkang@163.com">qierkang</a>
 * Blog: https://blog.csdn.net/qierkang
 * @Title RunServer$.java
 * @Date Created in $ $
 * <p>Description: [ 服务启动类 ] </p>
 * <p>Copyright:    </p>
 * <p>Company:      </p>
 * <p>Department:   </p>
 * *********************************************************
 */
public class RunServer extends AbstractHandler {


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
            port = "9001";
        }
        Server server = new Server(Integer.parseInt(port));
        server.setHandler(new RunServer());
        server.start();
        System.out.println("是，程序员设计了程序，还是，程序造就了程序员？程序，程序员——你的名字，我的姓氏");
        System.out.println("@Author xyqierkang@163.com\n" + "Blog https://blog.csdn.net/qierkang Home https://www.qekang.com");
        System.out.println("License Server started at http://localhost:" + port);
        System.out.println("JetBrains Activation address was: http://localhost:" + port + "/");
        System.out.println("JRebel address was: http://localhost:" + port + "/{tokenname}, with any email.");
        System.out.println("JRebel address was: http://localhost:" + port + "/{guid}(eg:http://localhost:" + port + "/" + UUID.randomUUID().toString() + "), with any email.");
        server.join();
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        System.out.println(target);
        if (target.equals("/")) {
            indexHandler(target, baseRequest, request, response);
        } else if (target.equals("/jrebel/leases")) {
            jrebelLeasesHandler(target, baseRequest, request, response);
        } else if (target.equals("/jrebel/leases/1")) {
            jrebelLeases1Handler(target, baseRequest, request, response);
        } else if (target.equals("/agent/leases")) {
            jrebelLeasesHandler(target, baseRequest, request, response);
        } else if (target.equals("/agent/leases/1")) {
            jrebelLeases1Handler(target, baseRequest, request, response);
        } else if (target.equals("/jrebel/validate-connection")) {
            jrebelValidateHandler(target, baseRequest, request, response);
        } else if (target.equals("/rpc/ping.action")) {
            pingHandler(target, baseRequest, request, response);
        } else if (target.equals("/rpc/obtainTicket.action")) {
            obtainTicketHandler(target, baseRequest, request, response);
        } else if (target.equals("/rpc/releaseTicket.action")) {
            releaseTicketHandler(target, baseRequest, request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private void jrebelValidateHandler(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        String jsonStr = "{\n" +
                "    \"serverVersion\": \"3.2.4\",\n" +
                "    \"serverProtocolVersion\": \"1.1\",\n" +
                "    \"serverGuid\": \"a1b4aea8-b031-4302-b602-670a990272cb\",\n" +
                "    \"groupType\": \"managed\",\n" +
                "    \"statusCode\": \"SUCCESS\",\n" +
                "    \"company\": \"Administrator\",\n" +
                "    \"canGetLease\": true,\n" +
                "    \"licenseType\": 1,\n" +
                "    \"evaluationLicense\": false,\n" +
                "    \"seatPoolType\": \"standalone\"\n" +
                "}\n";
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        String body = jsonObject.toString();
        response.getWriter().print(body);
    }

    private void jrebelLeases1Handler(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String username = request.getParameter("username");
        baseRequest.setHandled(true);
        String jsonStr = "{\n" +
                "    \"serverVersion\": \"3.2.4\",\n" +
                "    \"serverProtocolVersion\": \"1.1\",\n" +
                "    \"serverGuid\": \"a1b4aea8-b031-4302-b602-670a990272cb\",\n" +
                "    \"groupType\": \"managed\",\n" +
                "    \"statusCode\": \"SUCCESS\",\n" +
                "    \"msg\": null,\n" +
                "    \"statusMessage\": null\n" +
                "}\n";
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        if (username != null) {
            jsonObject.put("company", username);
        }
        String body = jsonObject.toString();
        response.getWriter().print(body);

    }

    private void jrebelLeasesHandler(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String clientRandomness = request.getParameter("randomness");
        String username = request.getParameter("username");
        String guid = request.getParameter("guid");
        System.out.println(((Request) request).getParameters());
        boolean offline = Boolean.parseBoolean(request.getParameter("offline"));
        String validFrom = "null";
        String validUntil = "null";
        if (offline) {
            String clientTime = request.getParameter("clientTime");
            String offlineDays = request.getParameter("offlineDays");
            //long clinetTimeUntil = Long.parseLong(clientTime) + Long.parseLong(offlineDays)  * 24 * 60 * 60 * 1000;
            long clinetTimeUntil = Long.parseLong(clientTime) + 180L * 24 * 60 * 60 * 1000;
            validFrom = clientTime;
            validUntil = String.valueOf(clinetTimeUntil);
        }
        baseRequest.setHandled(true);
        String jsonStr = "{\n" +
                "    \"serverVersion\": \"3.2.4\",\n" +
                "    \"serverProtocolVersion\": \"1.1\",\n" +
                "    \"serverGuid\": \"a1b4aea8-b031-4302-b602-670a990272cb\",\n" +
                "    \"groupType\": \"managed\",\n" +
                "    \"id\": 1,\n" +
                "    \"licenseType\": 1,\n" +
                "    \"evaluationLicense\": false,\n" +
                "    \"signature\": \"OJE9wGg2xncSb+VgnYT+9HGCFaLOk28tneMFhCbpVMKoC/Iq4LuaDKPirBjG4o394/UjCDGgTBpIrzcXNPdVxVr8PnQzpy7ZSToGO8wv/KIWZT9/ba7bDbA8/RZ4B37YkCeXhjaixpmoyz/CIZMnei4q7oWR7DYUOlOcEWDQhiY=\",\n" +
                "    \"serverRandomness\": \"H2ulzLlh7E0=\",\n" +
                "    \"seatPoolType\": \"standalone\",\n" +
                "    \"statusCode\": \"SUCCESS\",\n" +
                "    \"offline\": " + String.valueOf(offline) + ",\n" +
                "    \"validFrom\": " + validFrom + ",\n" +
                "    \"validUntil\": " + validUntil + ",\n" +
                "    \"company\": \"Administrator\",\n" +
                "    \"orderId\": \"\",\n" +
                "    \"zeroIds\": [\n" +
                "        \n" +
                "    ],\n" +
                "    \"licenseValidFrom\": 1490544001000,\n" +
                "    \"licenseValidUntil\": 1691839999000\n" +
                "}";

        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        if (clientRandomness == null || username == null || guid == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            JrebelSign jrebelSign = new JrebelSign();
            jrebelSign.toLeaseCreateJson(clientRandomness, guid, offline, validFrom, validUntil);
            String signature = jrebelSign.getSignature();
            jsonObject.put("signature", signature);
            jsonObject.put("company", username);
            String body = jsonObject.toString();
            response.getWriter().print(body);
        }
    }

    private void releaseTicketHandler(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String salt = request.getParameter("salt");
        baseRequest.setHandled(true);
        if (salt == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            String xmlContent = "<ReleaseTicketResponse><message></message><responseCode>OK</responseCode><salt>" + salt + "</salt></ReleaseTicketResponse>";
            String xmlSignature = rsasign.Sign(xmlContent);
            String body = "<!-- " + xmlSignature + " -->\n" + xmlContent;
            response.getWriter().print(body);
        }
    }

    private void obtainTicketHandler(String target, Request baseRequest, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        SimpleDateFormat fm = new SimpleDateFormat("EEE,d MMM yyyy hh:mm:ss Z", Locale.ENGLISH);
        String date = fm.format(new Date()) + " GMT";
        //response.setHeader("Date", date);
        //response.setHeader("Server", "fasthttp");
        String salt = request.getParameter("salt");
        String username = request.getParameter("userName");
        String prolongationPeriod = "607875500";
        baseRequest.setHandled(true);
        if (salt == null || username == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            String xmlContent = "<ObtainTicketResponse><message></message><prolongationPeriod>" + prolongationPeriod + "</prolongationPeriod><responseCode>OK</responseCode><salt>" + salt + "</salt><ticketId>1</ticketId><ticketProperties>licensee=" + username + "\tlicenseType=0\t</ticketProperties></ObtainTicketResponse>";
            String xmlSignature = rsasign.Sign(xmlContent);
            String body = "<!-- " + xmlSignature + " -->\n" + xmlContent;
            response.getWriter().print(body);
        }
    }

    private void pingHandler(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String salt = request.getParameter("salt");
        baseRequest.setHandled(true);
        if (salt == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            String xmlContent = "<PingResponse><message></message><responseCode>OK</responseCode><salt>" + salt + "</salt></PingResponse>";
            String xmlSignature = rsasign.Sign(xmlContent);
            String body = "<!-- " + xmlSignature + " -->\n" + xmlContent;
            response.getWriter().print(body);
        }

    }

    private void indexHandler(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        // 拼接服务器地址
        String licenseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        StringBuffer html = new StringBuffer();
        html.append(
                "<meta charset='UTF-8'>\n"
                        + "<link rel='canonical' href='https://blog.csdn.net/qierkang'>\n"
                        + "<meta http-equiv='content-type' content='text/html; charset=utf-8'>\n"
                        + "<meta name='renderer' content='webkit'>\n"
                        + "<meta name='force-rendering' content='webkit'>\n"
                        + "<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'>\n"
                        + "<meta name='viewport' content='width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no'>\n"
                        + "<meta name='apple-mobile-web-app-status-bar-style' content='black'>\n"
                        + "<meta name='referrer' content='always'>\n"
                        + "<meta http-equiv='Cache-Control' content='no-siteapp'>\n"
                        + "<meta name='csdn-baidu-search' content='戚尔康的博客-薯条的博客-尔康博客-xyqierkang-xyqierkang@163.com-383888666-qierkang- CSDN博客'>\n"
                        + "<meta name='description' content='戚尔康的博客-薯条的博客-尔康博客-xyqierkang-xyqierkang@163.com-383888666-qierkang-是一名计算机软件行业的博主,他一直在热衷于分享后台技术栈,服务器领域的技术知识。他主要关注安全\n"
                        + "架构方面的内容。'>");
        html.append("<hr/>");
        html.append("<title>Welcome to JetBrains License Server!:)</title>\n" +
                "<script type='text/javascript'>" +
                "    setInterval(on,1000);  //这里是使时间动起来的地方\n" +
                "    function on() {\n" +
                "        var date1 =new Date;\n" +
                "        var  year=date1.getFullYear();\n" +
                "        var  month=date1.getMonth();\n" +
                "        var day=date1.getDate();\n" +
                "        var xinqi=date1.getDay();\n" +
                "        var hh=date1.getHours();\n" +
                "        var mm=date1.getMinutes();\n" +
                "        var ss=date1.getSeconds();\n" +
                "        var time=year+\"-\"+(month+1)+\"-\"+day+\" \"+hh+\":\"+mm+\":\"+ss;\n" +
                "        document.getElementById(\"text\").innerHTML = time;\n" +
                "    }" +
                "" +
                "</script>\n" +
                "<style>\n" +
                "    body {\n" +
                "        width: 60em;\n" +
                "        margin: 0 auto;\n" +
                "        font-family: Tahoma, Verdana, Arial, sans-serif;\n" +
                "    }" +
                "img.xk{\n" +
                "            position: absolute;\n" +
                "            background-color: black;\n" +
                "            width: 300px;\n" +
                "            height: 200px;\n" +
                "            border: 6px solid white;\n" +
                "            box-shadow: 1px 1px 5px #333333;\n" +
                "        }\n" +
                "img.code{\n" +
                "            background-color: black;\n" +
                "            width: 70%;\n" +
                "            border: 6px solid white;\n" +
                "            box-shadow: 1px 1px 5px #333333;\n" +
                "            float: left;\n" +
                "        }\n" +
                "        div{position: absolute;\n" +
                "    left: 200px;\n" +
                "    top: 100px;\n" +
                "    text-align: center;\n" +
                "    font-size: 14px;\n" +
                "    padding: 10px;\n" +
                "    border: 1px solid #990;\n" +
                "    background-color: white;\n" +
                "    box-shadow: 1px 1px 5px #333333;\n" +
                "    width: 150px;\n" +
                "    transform: rotate(30deg)" +
                "   }\n" +
                "        img{\n" +
                "            width: 100%;\n" +
                "        }" +
                "</style>");
        html.append("<h1><a href='https://blog.csdn.net/qierkang/article/details/95095954' target='_blank' style='text-decoration:none;' >Hello,This is a Jrebel & JetBrains License Server!</a></h1>");
        html.append("<p>JetBrains Activation address was: <span style='color:red'>").append(licenseUrl).append("/{guid}");
        html.append("<p><h3>GUID address: <a href='https://www.guidgen.com/' target='_blank' style='text-decoration:none;'>GUID</a></h3></p>");
        html.append("<p>Demo JRebel address was: 【<span style='color:red'>").append(licenseUrl).append("/").append(UUID.randomUUID().toString()).append("</span>】, with any email.");
        html.append("<p>一键地址直接使用: 【<span style='color:red'>").append("https://jrebel.qekang.com").append("/").append(UUID.randomUUID().toString()).append("</span>】");
        html.append("<hr/>");
        html.append("<p><em>Builder:qierkang E-mail:xyqierkang@163.com  Wechat:qekang <span id=\"text\"></span></em></p>");
        html.append("<p><h3><a href='https://www.qekang.com' target='_blank' style='text-decoration:none;'>Home:)</a>&nbsp;&nbsp;&nbsp;<a href='https://blog.csdn.net/qierkang' target='_blank' style='text-decoration:none;'>CSDN:)</a></h3></p>");
        html.append("<div>服务将到期,凝天下力量!<img src='https://www.qekang.com/wechatPay.jpeg'/><img src='https://www.qekang.com/aliPay.jpeg'/></div>");
        html.append("<img src='https://www.qekang.com/code.jpg' class='code'/>");
        html.append("<br/>");
        html.append("<img class='code' src='https://img-blog.csdnimg.cn/20191127182857244.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FpZXJrYW5n,size_16,color_FFFFFF,t_70' width='800' height='550'/>");
        html.append("<br/><br/><br/>");
        response.getWriter().println(html);
    }
}
