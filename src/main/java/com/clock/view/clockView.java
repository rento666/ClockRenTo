package com.clock.view;

import com.clock.Utils.PropertiesUtil;
import com.clock.component.clockPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URI;
import java.net.URL;


public class clockView extends JFrame{

    // 系统托盘
    SystemTray systemTray;
    TrayIcon trayIcon;
    Timer timer;
    clockPanel cp = new clockPanel();
    PopupMenu menu = new PopupMenu(); // 创建弹出式菜单
    MenuItem exitItem = new MenuItem("退出"); // 创建菜单项
    CheckboxMenuItem time12Item = new CheckboxMenuItem("设为12小时制");
    CheckboxMenuItem time24Item = new CheckboxMenuItem("设为24小时制");
    Menu cCMenu = new Menu("更改颜色");
    CheckboxMenuItem whiteItem = new CheckboxMenuItem("白色");
    CheckboxMenuItem blackItem = new CheckboxMenuItem("黑色");
    CheckboxMenuItem lightGrayItem = new CheckboxMenuItem("浅灰色");
    CheckboxMenuItem grayItem = new CheckboxMenuItem("灰色");
    CheckboxMenuItem darkGrayItem = new CheckboxMenuItem("深灰色");
    CheckboxMenuItem redItem = new CheckboxMenuItem("红色");
    CheckboxMenuItem pinkItem = new CheckboxMenuItem("粉红色");
    CheckboxMenuItem orangeItem = new CheckboxMenuItem("橙色");
    CheckboxMenuItem yellowItem = new CheckboxMenuItem("黄色");
    CheckboxMenuItem greenItem = new CheckboxMenuItem("绿色");
    CheckboxMenuItem magentaItem = new CheckboxMenuItem("紫红色");
    CheckboxMenuItem cyanItem = new CheckboxMenuItem("深蓝色");
    CheckboxMenuItem blueItem = new CheckboxMenuItem("蓝色");
    String[] colorS = {whiteItem.getLabel(),blackItem.getLabel(),lightGrayItem.getLabel(),grayItem.getLabel(),
            darkGrayItem.getLabel(),redItem.getLabel(),pinkItem.getLabel(),orangeItem.getLabel(), yellowItem.getLabel(),
            greenItem.getLabel(),magentaItem.getLabel(),cyanItem.getLabel(),blueItem.getLabel()};
    Menu cSMenu = new Menu("更改大小");
    CheckboxMenuItem littleItem = new CheckboxMenuItem("小号");   // 24
    CheckboxMenuItem mediumItem = new CheckboxMenuItem("中号");   // 32
    CheckboxMenuItem bigItem = new CheckboxMenuItem("大号");      // 40
    String[] sizeS = {littleItem.getLabel(),mediumItem.getLabel(),bigItem.getLabel()};
    Menu cLMenu = new Menu("更改位置");
    CheckboxMenuItem leftItem = new CheckboxMenuItem("左边位置");
    CheckboxMenuItem middleItem = new CheckboxMenuItem("中间位置");
    CheckboxMenuItem rightItem = new CheckboxMenuItem("右边位置");
    String[] locationS = {leftItem.getLabel(),middleItem.getLabel(),rightItem.getLabel()};
    MenuItem aboutItem = new MenuItem("关于");
    MenuItem wordItem = new MenuItem("介绍文档");

    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int screenWidth = (int) screenSize.getWidth();
    public static final int screenHeight = (int) screenSize.getHeight();
    private static final int jfWidth = 800;
    private static final int jfHeight = 70;
    private static final int jfx = (screenWidth - jfWidth) / 2;
    private static final int jfy = (screenHeight - jfWidth) / 2;


    public clockView(){
        super();   // 窗体对象

        setSuper(); // 设置窗体的一些基础配置

        init(); // 初始化其他内容

        setContentPane(cp);   // 设置窗口要显示的内容，此处为一个面板

        trayInit(); // 系统托盘初始化

        setVisible(true);    // 可视
    }

    private void init() {
        // [初始化] 选中状态与配置项有关
        CheckboxMenuItem hourToItem = hourToItem(); // 时间进制
        hourToItem.setState(true);
        CheckboxMenuItem sizeToItem = sizeToItem(); // 文字大小
        sizeToItem.setState(true);
        CheckboxMenuItem colorToItem = colorToItem();   // 文字颜色
        colorToItem.setState(true);
        CheckboxMenuItem locationToItem = locationToItem(); // 位置
        locationToItem.setState(true);

        PropertiesUtil.createProperties(); // 创建配置文件
        cp.setNum(Integer.parseInt(PropertiesUtil.getValue("hourSystem"))); // 设置进制
        setTimeColor(PropertiesUtil.getValue("timeColor")); // 设置颜色
        cp.setFontSize(Integer.parseInt(PropertiesUtil.getValue("textSize"))); // 设置字体大小

        // 定时器，每1秒执行刷新时间标签，实现时钟效果
        ActionListener actionListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.setLab_clock_Text(cp.getNum());
            }
        };
        timer = new Timer(1000,actionListener);
        timer.start();
    }
    private void setTimeColor(String color){
        String str = charToStr(color);
        setColor(str);
    }

    private void trayInit() {
        // 如果系统支持托盘
        if(SystemTray.isSupported()){
            try {
                String title = "桌面时钟组件";
                String content = "启动成功，软件由Rento独立开发";
                systemTray = SystemTray.getSystemTray();
                URL url = clockView.class.getClassLoader().getResource("time.png");
                assert url != null;
                trayIcon = new TrayIcon(new ImageIcon(url).getImage(),title,createMenu());
                // 设置托盘图片大小自动缩放，必设置！
                trayIcon.setImageAutoSize(true);

                systemTray.add(trayIcon);
                trayIcon.displayMessage(title,content, TrayIcon.MessageType.INFO);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    private void setSuper() {
        setSize(jfWidth,jfHeight); // 大小
        //setLocation(jfx,jfy); // 居中
        setViewLocation(PropertiesUtil.getValue("location"));   // 位置
        setUndecorated(true);   // 设置窗口无边缘
        //AWTUtilities.setWindowOpaque(this,false);   // 设置窗口完全透明
        setBackground(new Color(0,0,0,0));   // 设置窗口完全透明
        setType(Type.UTILITY); // 隐藏任务栏图标
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);     // 关闭程序
    }

    private PopupMenu createMenu() {

        // 退出选项
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        // 12小时制选项
        time12Item.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    PropertiesUtil.setValue("hourSystem","12");
                    cp.setNum(12);
                    time24Item.setState(false);
                }
            }
        });
        // 24小时制选项
        time24Item.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    PropertiesUtil.setValue("hourSystem","24");
                    cp.setNum(24);
                    time12Item.setState(false);
                }
            }
        });
        // 颜色监听
        setCCsListener();
        // 大小监听
        setCssListener();
        // 位置监听
        setClsListener();
        // 文档 + 关于 按钮监听
        setWordAndAboutListener();
        // 将组件添加到面板
        menuAdd();

        return menu;
    }

    private void setWordAndAboutListener() {
        wordItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });
    }

    private void setClsListener(){
        setChooseLocationListener(leftItem,"left");
        setChooseLocationListener(middleItem,"center");
        setChooseLocationListener(rightItem,"right");
    }

    private void setChooseLocationListener(CheckboxMenuItem locationItem,String location){
        locationItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    PropertiesUtil.setValue("location",location);
                    //改变位置
                    setViewLocation(location);
                    setIsViewLocation(locationItem);
                }
            }
        });
    }

    private void setCssListener() {
        setChooseSizeListener(littleItem,"24");
        setChooseSizeListener(mediumItem,"32");
        setChooseSizeListener(bigItem,"40");
    }

    private void setChooseSizeListener(CheckboxMenuItem sizeItem,String size) {
        sizeItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    PropertiesUtil.setValue("textSize",size);   // 保存到配置文件
                    cp.setFontSize(Integer.parseInt(size)); // 改变大小
                    setIsSize(sizeItem);// 设置相应的选项为选中状态
                }
            }
        });
    }

    private void setCCsListener() {
        setChooseColorListener(whiteItem,whiteItem.getLabel());
        setChooseColorListener(blackItem,blackItem.getLabel());
        setChooseColorListener(lightGrayItem,lightGrayItem.getLabel());
        setChooseColorListener(grayItem,grayItem.getLabel());
        setChooseColorListener(darkGrayItem,darkGrayItem.getLabel());
        setChooseColorListener(redItem,redItem.getLabel());
        setChooseColorListener(pinkItem,pinkItem.getLabel());
        setChooseColorListener(orangeItem,orangeItem.getLabel());
        setChooseColorListener(yellowItem,yellowItem.getLabel());
        setChooseColorListener(greenItem,greenItem.getLabel());
        setChooseColorListener(magentaItem,magentaItem.getLabel());
        setChooseColorListener(cyanItem,cyanItem.getLabel());
        setChooseColorListener(blackItem,blackItem.getLabel());
        setChooseColorListener(blueItem,blueItem.getLabel());


    }

    private void menuAdd() {

        cCMenu.add(whiteItem);
        cCMenu.add(blackItem);
        cCMenu.add(lightGrayItem);
        cCMenu.add(grayItem);
        cCMenu.add(darkGrayItem);
        cCMenu.add(redItem);
        cCMenu.add(pinkItem);
        cCMenu.add(orangeItem);
        cCMenu.add(yellowItem);
        cCMenu.add(greenItem);
        cCMenu.add(magentaItem);
        cCMenu.add(cyanItem);
        cCMenu.add(blackItem);
        cCMenu.add(blueItem);

        cSMenu.add(littleItem);
        cSMenu.add(mediumItem);
        cSMenu.add(bigItem);

        cLMenu.add(leftItem);
        cLMenu.add(middleItem);
        cLMenu.add(rightItem);

        menu.add(time12Item);
        menu.add(time24Item);
        menu.addSeparator();
        menu.add(cCMenu);
        menu.addSeparator();
        menu.add(cSMenu);
        menu.addSeparator();
        menu.add(cLMenu);
        menu.addSeparator();
        menu.add(wordItem);
        menu.addSeparator();
        menu.add(aboutItem);
        menu.addSeparator();
        menu.add(exitItem);
    }

    // 选中某一颜色时，监听事件
    private void setChooseColorListener(CheckboxMenuItem chooseColor,String color) {
        chooseColor.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    PropertiesUtil.setValue("timeColor",strToChar(color));
                    //System.out.println(strToChar(color));
                    String timeColor = PropertiesUtil.getValue("timeColor");
                    //System.out.println(timeColor);
                    setTimeColor(timeColor);
                    setIsColor(chooseColor);
                }
            }
        });
    }

    /**
     * 例如： black -> 黑色
     */
    private String charToStr(String c){
        switch (c){
            case "while":
                return "白色";
            case "lightGray":
                return "浅灰色";
            case "gray":
                return "灰色";
            case "darkGray":
                return "深灰色";
            case "red":
                return "红色";
            case "pink":
                return "粉红色";
            case "orange":
                return "橙色";
            case "yellow":
                return "黄色";
            case "green":
                return "绿色";
            case "magenta":
                return "紫红色";
            case "cyan":
                return "深蓝色";
            case "blue":
                return "蓝色";
            default:
                return "黑色";
        }
    }

    private String strToChar(String s){
        switch (s){
            case "白色":
                return "white";
            case "浅灰色":
                return "lightGray";
            case "灰色":
                return "gray";
            case "深灰色":
                return "darkGray";
            case "红色":
                return "red";
            case "粉红色":
                return "pink";
            case "橙色":
                return "orange";
            case "黄色":
                return "yellow";
            case "绿色":
                return "green";
            case "紫红色":
                return "magenta";
            case "深蓝色":
                return "cyan";
            case "蓝色":
                return "blue";
            default:
                return "黑色";
        }
    }


    // 设置颜色，参数是汉字 例如：黑色
    public void setColor(String color){
        switch (color){
            case "白色":
                cp.setColor(Color.white);
                break;
            case "黑色":
                cp.setColor(Color.black);
                break;
            case "浅灰色":
                cp.setColor(Color.lightGray);
                break;
            case "灰色":
                cp.setColor(Color.gray);
                break;
            case "深灰色":
                cp.setColor(Color.darkGray);
                break;
            case "红色":
                cp.setColor(Color.red);
                break;
            case "粉红色":
                cp.setColor(Color.pink);
                break;
            case "橙色":
                cp.setColor(Color.orange);
                break;
            case "黄色":
                cp.setColor(Color.yellow);
                break;
            case "绿色":
                cp.setColor(Color.green);
                break;
            case "紫红色":
                cp.setColor(Color.magenta);
                break;
            case "深蓝色":
                cp.setColor(Color.cyan);
                break;
            case "蓝色":
                cp.setColor(Color.blue);
                break;
        }
    }

    // 设置哪个为选中状态，其余为未选中
    public void setIsColor(CheckboxMenuItem choseItem){
        setColorFalse();
        for (String color : colorS) {
            if (choseItem.getLabel().equals(color)) {
                choseItem.setState(true);
            }
        }
    }

    public void setIsSize(CheckboxMenuItem choseItem){
        setSizeFalse();
        for (String size : sizeS){
            if (choseItem.getLabel().equals(size)){
                choseItem.setState(true);
            }
        }
    }

    private void setViewLocation(String location){
        switch (location){
            case "left":
                setLocation(jfx / 7,jfy);
                break;
            case "right":
                setLocation(jfx * 2,jfy);
                break;
            default:
                setLocation(jfx,jfy);
        }
    }
    private void setIsViewLocation(CheckboxMenuItem choseItem){
        String itemLabel = choseItem.getLabel();    // 左边位置，中间位置，右边位置
        setLocationFalse();
        for (String location : locationS){
             if (location.equals(itemLabel)){
                 choseItem.setState(true);
             }
        }
    }

    // 通过颜色得到该谁选中，初始化时使用
    private CheckboxMenuItem colorToItem(){
        String c = PropertiesUtil.getValue("timeColor");
        switch (c){
            case "while":
                return whiteItem;
            case "lightGray":
                return lightGrayItem;
            case "gray":
                return grayItem;
            case "darkGray":
                return darkGrayItem;
            case "red":
                return redItem;
            case "pink":
                return pinkItem;
            case "orange":
                return orangeItem;
            case "yellow":
                return yellowItem;
            case "green":
                return greenItem;
            case "magenta":
                return magentaItem;
            case "cyan":
                return cyanItem;
            case "blue":
                return blueItem;
            default:
                return blackItem;
        }
    }

    private CheckboxMenuItem hourToItem(){
        String s = PropertiesUtil.getValue("hourSystem");
        if (s.equals("24")){
            return time24Item;
        }else {
            return time12Item;
        }
    }

    private CheckboxMenuItem sizeToItem(){
        String s = PropertiesUtil.getValue("textSize");
        if (s.equals("24")){
            return littleItem;
        } else if (s.equals("40")){
            return bigItem;
        } else {
            return mediumItem;
        }
    }

    private CheckboxMenuItem locationToItem(){
        String l = PropertiesUtil.getValue("location");
        if (l.equals("left")){
            return leftItem;
        } else if (l.equals("right")){
            return rightItem;
        } else {
            return middleItem;
        }
    }

    // 全部设为未选中
    public void setColorFalse(){
        whiteItem.setState(false);
        blackItem.setState(false);
        lightGrayItem.setState(false);
        grayItem.setState(false);
        darkGrayItem.setState(false);
        redItem.setState(false);
        pinkItem.setState(false);
        orangeItem.setState(false);
        yellowItem.setState(false);
        greenItem.setState(false);
        magentaItem.setState(false);
        cyanItem.setState(false);
        blueItem.setState(false);
    }

    public void setSizeFalse(){
        littleItem.setState(false);
        mediumItem.setState(false);
        bigItem.setState(false);
    }

    public void setLocationFalse(){
        leftItem.setState(false);
        middleItem.setState(false);
        rightItem.setState(false);
    }

}
