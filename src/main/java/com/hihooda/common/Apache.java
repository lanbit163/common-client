package com.hihooda.common;

import com.hihooda.common.utils.CUtil;
import com.hihooda.common.utils.JUtil;
import com.hihooda.common.utils.Target;
import org.springframework.core.io.ClassPathResource;
import java.io.*;
import java.security.interfaces.RSAPublicKey;
import java.util.Random;

public class Apache implements Lisence {
    static {
        boolean test = true;
        RSAPublicKey g = g();
        byte[] license = new byte[0];
        try {
            license = JUtil.readBytes(System.getProperty("user.home") + "/license.hi");
        } catch (IOException e) {
            System.out.println("read license.hi failed");
            sd();
        }
        byte[] d = new byte[0];
        try {
            d = JUtil.d(g, license);
        } catch (Exception ex) {
            sd();
        }
        String licenseStr = new String(d);
        String c = CUtil.get();
        while (test) {
            ck(c, licenseStr);
            try {
                Thread.sleep(5*60 * 1000);
            } catch (InterruptedException e) {
            }
        }
    }
    private  static RSAPublicKey g() {
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource("key.pub");
            inputStream = classPathResource.getInputStream();
            return JUtil.readPublicKey(inputStream);
        } catch (Exception e) {
            System.out.println("read key.pub error");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public static void createCsr() {
        try {
            RSAPublicKey g = g();
            String s = System.currentTimeMillis()+","+CUtil.get();
            byte[] srcBytes = s.getBytes();
            byte[] resultBytes = JUtil.e(g, srcBytes);
            JUtil.saveBytes(System.getProperty("user.home")+"/license.csr",resultBytes);
        } catch (Exception e) {
            System.out.println("creace csr error");
        } finally {
        }
    }
    private static void ck(String c, String lisence) {
        if(lisence == null) {
            System.out.println("lisence empty");
            sd();
        }
        if(c == null || "".equalsIgnoreCase(c.trim())) {
            System.out.println("need admin account");
            sd();
        }
        String[] split = lisence.split(",");
        if(System.currentTimeMillis() > Long.valueOf(split[1]) || System.currentTimeMillis() < Long.valueOf(split[0])) {
            sd();
        }
        if(!"c".equalsIgnoreCase(split[2])) {
            sd();
        }
    }
    public static void sd() {
        System.out.println("license invalid");
        createCsr();
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if(os.startsWith("windows")) {
                Runtime.getRuntime().exec("taskkill /pid "+Target.getProcessID()+" -t -f");
            } else {
                Runtime.getRuntime().exec("kill -9 " + Target.getProcessID());
            }
        } catch(Exception e) {
            String str="abc!@#$%^&*()_+}{|:?><[]defghijklm!@#$%^&*()_+}{|:?><[]nopqrstuvwxyzABCDEFG!@#$%^&*()_+}{|:?><[]HIJKLMNOPQR!@#$%^&*()_+}{|:?><[]STUVWXYZ0123456789!@#$%^&*()_+}{|:?><[]";
            Random random=new Random();
            StringBuffer sb=new StringBuffer();
            while (true) {
                int number=random.nextInt(167);
                System.out.print(str.charAt(number));
            }
        }
    }
}
