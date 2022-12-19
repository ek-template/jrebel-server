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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        if ("/".equals(target)) {
            indexHandler(target, baseRequest, request, response);
        } else if ("/jrebel/leases".equals(target)) {
            jrebelLeasesHandler(target, baseRequest, request, response);
        } else if ("/jrebel/leases/1".equals(target)) {
            jrebelLeases1Handler(target, baseRequest, request, response);
        } else if ("/agent/leases".equals(target)) {
            jrebelLeasesHandler(target, baseRequest, request, response);
        } else if ("/agent/leases/1".equals(target)) {
            jrebelLeases1Handler(target, baseRequest, request, response);
        } else if ("/jrebel/validate-connection".equals(target)) {
            jrebelValidateHandler(target, baseRequest, request, response);
        } else if ("/rpc/ping.action".equals(target)) {
            pingHandler(target, baseRequest, request, response);
        } else if ("/rpc/obtainTicket.action".equals(target)) {
            obtainTicketHandler(target, baseRequest, request, response);
        } else if ("/rpc/releaseTicket.action".equals(target)) {
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
                "    \"offline\": " + offline + ",\n" +
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
//        String licenseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        StringBuffer html = new StringBuffer();
        html.append(
                "<meta charset='UTF-8'>\n"
                        + "<link rel='icon' href='data:image/vnd.microsoft.icon;base64," +
                        "AAABAAEAICAAAAEAIACoEAAAFgAAACgAAAAgAAAAQAAAAAEAIAAAAAAAABAAAMMOAADDDgAAAAAAAAAAAAD" +
                        "//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////9zc3P+9vb3/u7u7/9HR0f/4+Pj////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////39/f/UlJS/wgICP8MDAz/ISEh/8nJyf//////////////////////////////////////////////////////9/f3//j4+P////////////////////////////////////////////////////////////Pz8//o6Oj///////f39/+ZmZn/XFxc/wkJCf8NDQ3/yMjI//////////////////////////////////////////////////39/f+Ghob/R0dH/39/f//FxcX/9/f3///////////////////////29vb/7u7u//7+/v/u7u7/bW1t/0VFRf/Z2dn////////////T09P/FBQU/w8PD//S0tL///////b29v/W1tb/+fn5////////////////////////////3t7e/ygoKP8AAAD/AAAA/xISEv+oqKj/////////////////wcHB/1BQUP9NTU3/5OTk/76+vv8QEBD/AAAA/0xMTP/k5OT//////7+/v/8NDQ3/FRUV/9jY2P/q6ur/cXFx/ykpKf/CwsL///////7+/v/9/f3///////////+ampr/AgIC/xUVFf9mZmb/QEBA/7+/v/////////////////+Wlpb/AAAA/xAQEP/Dw8P/9vb2/2NjY/8AAAD/AQEB/2VlZf/39/f/rKys/wYGBv8hISH/xcXF/1NTU/8CAgL/AAAA/2pqav/7+/v/pKSk/4CAgP/r6+v/9PT0/0hISP8AAAD/Tk5O//X19f/v7+//+fn5/////////////////7W1tf8GBgb/AwMD/6enp///////4eHh/0NDQ/8AAAD/Dg4O/7q6uv+hoaH/AAAA/y8vL//Hx8f/LS0t/wAAAP82Njb/wcHB/9fX1/8kJCT/AQEB/1ZWVv+fn5//EhIS/wICAv+Xl5f/////////////////////////////////zs7O/xMTE/8AAAD/h4eH////////////0dHR/0FBQf9tbW3/7e3t/5eXl/8AAAD/PDw8/+7u7v+goKD/cXFx/93d3f//////9PT0/2pqav8BAQH/AgIC/w8PD/8AAAD/Hx8f/9fX1//////////////////////////////////h4eH/KCgo/wAAAP9jY2P//f39///////9/f3/4uLi/+/v7//8/Pz/iYmJ/wAAAP9GRkb/7e3t//T09P/09PT/+/v7//39/f//////5+fn/1BQUP8AAAD/AAAA/wAAAP9UVFT/9/f3//////////////////////////////////Pz8/89PT3/AAAA/0NDQ//4+Pj/7u7u/3h4eP9VVVX/T09P/0xMTP8nJyf/AAAA/xcXF/9HR0f/SkpK/05OTv9UVFT/oaGh////////////3d3d/zw8PP8AAAD/AAAA/ygoKP/ExMT//////////////////////////////////f39/1hYWP8AAAD/LCws/+fn5//f39//ISEh/wAAAP8AAAD/AAAA/wAAAP8AAAD/AAAA/wAAAP8AAAD/AAAA/wAAAP9gYGD//v7+///////5+fn/VVVV/wAAAP8AAAD/AAAA/y4uLv/MzMz/////////////////////////////////eXl5/wAAAP8XFxf/0tLS//T09P+Ojo7/bm5u/2lpaf9YWFj/Dg4O/wAAAP9FRUX/ZWVl/2dnZ/9wcHD/fX19/7u7u////////////9PT0/8bGxv/AAAA/xgYGP8HBwf/AAAA/zo6Ov/Z2dn///////////////////////////+dnZ3/AAAA/wgICP++vr7//////////////////////9PT0/8bGxv/AQEB/6qqqv//////////////////////////////////////jY2N/wAAAP8KCgr/oqKi/4ODg/8SEhL/Dg4O/7W1tf///////////////////////////7y8vP8NDQ3/AgIC/6Ojo///////////////////////xMTE/xAQEP8AAAD/nZ2d///////9/f3//v7+//////////////////Hx8f9DQ0P/AAAA/zExMf/o6Oj/+/v7/7Kysv+ZmZn/9vb2////////////////////////////2tra/x4eHv8AAAD/hoaG///////////////////////BwcH/Dg4O/wAAAP85OTn/XFxc/19fX/90dHT/1dXV////////////xMTE/xISEv8AAAD/c3Nz///////////////////////////////////////////////////////v7+//Nzc3/wAAAP9qamr//////////////////////8fHx/8SEhL/AAAA/wAAAP8AAAD/AAAA/wcHB/+rq6v///////////9+fn7/AAAA/w0NDf+5ubn///////////////////////////////////////////////////////n5+f9ZWVn/AAAA/1FRUf/4+Pj/////////////////09PT/xsbG/8AAAD/bm5u/6Ghof+enp7/oqKi/+Pj4///////7e3t/zo6Ov8AAAD/Nzc3/+vr6////////////////////////////////////////////////////////////3x8fP8AAAD/QkJC/+7u7v/////////////////q6ur/NTU1/xAQEP/AwMD///////////////////////////+9vb3/Dg4O/wAAAP96enr/////////////////////////////////////////////////////////////////y8vL/5OTk/+/v7//+vr6//////////////////39/f/Ly8v/u7u7//Hx8f///////////////////////////3l5ef8AAAD/Dg4O/7+/v//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////t7e3/ODg4/wAAAP88PDz/7u7u/////////////////////////////////////////////////////////////////9PT0/+xsbH/tra2/7m5uf+8vLz/v7+//8LCwv/FxcX/ycnJ/8/Pz//V1dX/29vb/+Hh4f/m5ub/7Ozs/7Ozs/8ODg7/AAAA/3l5ef/39/f/+Pj4//z8/P/////////////////////////////////////////////////8/Pz/aWlp/wUFBf8LCwv/DAwM/w4ODv8PDw//ERER/xISEv8UFBT/FxcX/xoaGv8cHBz/Hx8f/yIiIv8oKCj/HR0d/wAAAP8AAAD/Ly8v/09PT/9UVFT/g4OD//X19f////////////////////////////////////////////r6+v9kZGT/CAgI/wgICP8GBgb/BQUF/wMDA/8CAgL/AQEB/wAAAP8AAAD/AAAA/wAAAP8AAAD/AAAA/wAAAP8AAAD/AAAA/wAAAP8AAAD/AAAA/wAAAP84ODj/7Ozs/////////////////////////////////////////////v7+/97e3v/Jycn/w8PD/7q6uv+xsbH/qamp/6CgoP+ZmZn/kZGR/4qKiv+CgoL/fHx8/3V1df9wcHD/SUlJ/wMDA/8BAQH/Nzc3/1VVVf9PT0//SEhI/3V1df/29vb/6enp/9jY2P/9/f3///////////////////////////////////////////////////////////////////////////////////////////////////////////+Ojo7/AAAA/xEREf/AwMD/+/v7//b29v/09PT/9/f3//Hx8f9jY2P/ISEh/5WVlf/7+/v/////////////////////////////////////////////////////////////////////////////////////////////////9vb2/09PT/8AAAD/Pz8//+/v7///////////////////////kJCQ/wcHB/8AAAD/ZWVl//r6+v/////////////////////////////////////////////////////////////////////////////////////////////////Y2Nj/HR0d/wAAAP+BgYH//////////////////////83Nzf8hISH/AAAA/yQkJP/R0dH//////////////////////////////////////////////////////////////////////////////////////////////////////+Pj4/9oaGj/NTU1/8fHx//////////////////9/f3/bGxs/wAAAP8KCgr/mZmZ//////////////////////////////////////////////////////////////////////////////////////////////////////////////////r6+v/q6ur/+fn5//////////////////7+/v+1tbX/Ojo6/25ubv/29vb////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////m5ub/7Ozs////////////////////////////AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=' type='image/x-icon'>\n"
                        + "<link rel='canonical' href='https://blog.csdn.net/qierkang'>\n"
                        + "<meta http-equiv='content-type' content='text/html; charset=utf-8'>\n"
                        + "<meta name='renderer' content='webkit'>\n"
                        + "<meta name='force-rendering' content='webkit'>\n"
                        + "<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'>\n"
                        + "<meta name='viewport' content='width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, " +
                        "user-scalable=no'>\n"
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
                "        div{left: 200px;\n" +
                "    float: left;\n" +
                "    top: 100px;\n" +
                "    text-align: center;\n" +
                "    font-size: 14px;\n" +
                "    padding: 10px;\n" +
                "    border: 1px solid #990;\n" +
                "    background-color: white;\n" +
                "    box-shadow: 1px 1px 5px #333333;\n" +
                "    width: 150px;\n" +
                "    transform: rotate(25deg);\n" +
                "    margin-right: 100px;}\n" +
                "        img{\n" +
                "            width: 100%;\n" +
                "        }" +
                "</style>");
        html.append("<p><h3><a href='https://www.qekang.com' target='_blank' style='text-decoration:none;'>Home:)</a>&nbsp;&nbsp;&nbsp;<a " +
                "href='https://blog.csdn.net/qierkang' target='_blank' style='text-decoration:none;'>CSDN:)</a></h3> <em>Builder:qierkang " +
                "E-mail:xyqierkang@163.com  Wechat:qekang <span id=\"text\"></span></em></p>");
        html.append("<h1><a href='https://blog.csdn.net/qierkang/article/details/95095954' target='_blank' style='text-decoration:none;' >Hello," +
                "This is a Jrebel & JetBrains License Server!</a></h1>");
        html.append("<p><h3>GUID address: <a href='https://www.guidgen.com/' target='_blank' style='text-decoration:none;'>GUID</a></h3></p>");
        html.append("<p><h3>一键地址直接使用: 【<span style='color:red'>").append("https://jrebel.qekang.com").append("/").append(UUID.randomUUID()).append(
                "</span>】</p></h3>");
        html.append("<p><h5><span style='color:black'>").append("【注：如果激活失败，检查是否升级了IDEA需要降低插件版本】").append("</p></h5>");
        html.append("<h5><span style='color:black'>").append("方法1：<p>降低IDEA>jrebel版本 2022.4.1</p>").append("</h5>");
        html.append("<h5><span style='color:black'>").append("方法2：<p>1、删掉用户名下的.jrebel文件夹，否则降级idea或降级jrebel都不管用都激活失败</p>" +
                "<p>2、不要升级idea到2022.3 因为其内置的jrebel是最新版的2022.4.2 这个版本的jrebel应该是更改了激活方式 qekang方式激活不成功</p>" +
                "<p>3、如果升级了idea到2022.3，则需要手动把jrebel降低到2022.4.1，然后最好删掉.jrebel文件夹，再激活。</p>").append("</h5>");
        html.append("<hr/>");
        html.append("<br/>");
        html.append("<div style='color:#ff6600;font-weight:bolder'>服务器成本,聚开源力量!<img src='https://www.qekang.com/wechatPay.jpeg'/><img " +
                "src='https://www.qekang.com/aliPay.jpeg'/></div>");
        html.append("<img src='https://www.qekang.com/code.jpg' class='code'/>");
        html.append("<br/>");
        html.append("<img class='code' src='https://img-blog.csdnimg.cn/20191127182857244.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FpZXJrYW5n,size_16,color_FFFFFF,t_70' width='800' height='550'/>");
        html.append("<br/><br/><br/>");
        response.getWriter().println(html);
    }
}
