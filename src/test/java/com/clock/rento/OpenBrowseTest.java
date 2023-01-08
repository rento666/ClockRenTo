package com.clock.rento;

import java.awt.*;
import java.net.URI;

public class OpenBrowseTest {
    public static void open(){
        if (Desktop.isDesktopSupported()){
            try{
                String url = "www.baidu.com";
                URI uri = URI.create(url);
                Desktop dp = Desktop.getDesktop();
                if (dp.isSupported(Desktop.Action.BROWSE)){ // 判断系统桌面是否支持要执行的功能
                    dp.browse(uri);
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        open();
    }
}
