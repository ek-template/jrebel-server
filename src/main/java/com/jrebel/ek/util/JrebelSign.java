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
package com.jrebel.ek.util;

import org.apache.commons.lang3.StringUtils;

/**
 * *********************************************************
 *
 * @Author <a href="mailto:xyqierkang@163.com">qierkang</a>
 * Blog: https://blog.csdn.net/qierkang
 * @Title JrebelSign.java
 * @Date Created in 2019-12-11 18:19
 * <p>Description: [ TODO ] </p>
 * <p>Copyright:    </p>
 * <p>Company:      </p>
 * <p>Department:   </p>
 * *********************************************************
 */
public class JrebelSign {
    private String signature;

    public void toLeaseCreateJson(String clientRandomness, String guid, boolean offline, String validFrom, String validUntil) {
        //String serverRandomness = ByteUtil.a(ByteUtil.a(8));
        String serverRandomness =  "H2ulzLlh7E0="; //服务端随机数,如果要自己生成，务必将其写到json的serverRandomness中
        String installationGuidString = guid;
        //String value = String.valueOf("false");
        String s2= "";
        if(offline){
            s2 = StringUtils.join((Object[]) new String[]{clientRandomness, serverRandomness, installationGuidString , String.valueOf(offline), validFrom, validUntil}, ';');
        }else{
            s2 = StringUtils.join((Object[]) new String[]{clientRandomness, serverRandomness, installationGuidString , String.valueOf(offline)}, ';');
        }
        System.out.println(s2);
        final byte[] a2 =LicenseServer2ToJRebelPrivateKey.a(s2.getBytes());
        this.signature = ByteUtil.a(a2);
    }

    public String getSignature() {
        return signature;
    }

}
