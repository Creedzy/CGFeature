package org.cg.util;

import java.nio.charset.Charset;
import java.util.Random;

public class StringUtils {
    
    
    public static String randomString() {
        Random random = new Random();
        byte[] size = new byte[12];
        random.nextBytes(size);
        return new String(size,Charset.forName("UTF-8"));
    }
    
}
