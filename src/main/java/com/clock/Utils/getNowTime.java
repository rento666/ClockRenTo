package com.clock.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class getNowTime {
    /**
     * 例如：2023年1月7日 下午8时17分50秒
     * @return String -> currentTime -> 12小时制
     */
    public static String nowTime12(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年M月d日E ah时m分s秒");
        return formatter.format(currentTime);
    }

    /**
     * 例如：2023年1月7日 20时18分52秒
     * @return String -> currentTime -> 24小时制
     */
    public static String nowTime24(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年M月d日E H时m分s秒");
        return formatter.format(currentTime);
    }

    public static void main(String[] args) {
        String nowTime12 = nowTime12();
        String nowTime24 = nowTime24();
        System.out.println(nowTime12 + "\n" + nowTime24);
    }
}
