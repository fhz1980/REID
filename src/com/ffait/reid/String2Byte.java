package com.ffait.reid;
public class String2Byte {
    public static byte[] generateByte(String feature){
       String newfeature= feature.substring(1,feature.length()-1);
       String sb[]=newfeature.split(",");
       byte[] fb=new byte[sb.length];
        for (int i=0;i<sb.length;i++) {
//            System.out.println(sb[i]);
            fb[i]=Byte.parseByte(sb[i].trim());
        }
        return fb;
    }
}
