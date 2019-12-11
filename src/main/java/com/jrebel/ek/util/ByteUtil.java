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
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
import java.util.Random;

/**
 * *********************************************************
 *
 * @Author <a href="mailto:xyqierkang@163.com">qierkang</a>
 * Blog: https://blog.csdn.net/qierkang
 * @Title ByteUtil.java
 * @Date Created in 2019-12-11 18:18
 * <p>Description: [ TODO ] </p>
 * <p>Copyright:    </p>
 * <p>Company:      </p>
 * <p>Department:   </p>
 * *********************************************************
 */
public class ByteUtil {
    private static final Random a;

    public static String a(final byte[] binaryData) {
        if (binaryData == null) {
            return null;
        }
        return new String(Base64.encodeBase64(binaryData), Charset.forName("UTF-8"));
    }

    public static byte[] a(final String s) {
        if (s == null) {
            return null;
        }
        return Base64.decodeBase64(s.getBytes(Charset.forName("UTF-8")));
    }

    public static byte[] a(final int n) {
        final byte[] array = new byte[n];
        ByteUtil.a.nextBytes(array);
        return array;
    }

    static {
        a = new Random();
    }
}
