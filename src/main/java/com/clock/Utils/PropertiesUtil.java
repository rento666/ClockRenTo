package com.clock.Utils;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {
    // D:\ProgrammingProjects\JavaProjects
    private static final String userDir = System.getProperty("user.dir");
    private static final String conf = "/conf";
    private static final String cup = "/conf/user.properties";

    /**
     * 创建Properties文件
     */
    public static void createProperties(){

        Properties properties = new Properties();
        OutputStream output = null;
        File file = new File(userDir + conf);
        if(file.mkdir()){
            try {
                output = new FileOutputStream(userDir + cup);
                properties.setProperty("hourSystem","12");
                properties.setProperty("timeColor","black");
                properties.setProperty("textSize","32");
                properties.setProperty("location","center");

                properties.store(output,"User's config");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("create finish");
                if (output != null){
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 读配置文件 ，返回配置文件对象
     * @return properties
     */
    public static Properties getProperties(){

        Properties properties = new Properties();
        InputStream input = null;
        try{
            input = new FileInputStream(userDir + cup);
            properties.load(input);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (null != input){
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    /**
     * 根据key查询value值
     * @param key 键
     * @return value 值
     */
    public static String getValue(String key){
        Properties properties = getProperties();
        return properties.getProperty(key);
    }

    public static void setValue(String key,String value){
        Properties properties = getProperties();
        properties.setProperty(key, value);

        String path = userDir + cup;
        FileOutputStream fis = null;
        try{
            fis = new FileOutputStream(path);
            properties.store(fis,"update config");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
