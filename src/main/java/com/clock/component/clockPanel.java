package com.clock.component;

import com.clock.Utils.getNowTime;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class clockPanel extends JPanel {
    // 所用组件
    JLabel lab_clock;
    JToggleButton toggleBtn;

    private String fontType;    // 文字类型，例如：宋体，楷体等
    private final int fontStyle = Font.PLAIN;   // 文字风格：例如：加粗 斜体等，此处是正常
    private int fontSize;   // 文字大小
    private int num;
    private Color color;
    
    public clockPanel(){
        super();
        setBackground(new Color(0,0,0,0));   // 设置窗口完全透明
        setOpaque(false);

        lab_clock = new JLabel();
        fontType = "宋体";
        lab_clock.setFont(new Font(fontType, fontStyle, fontSize));
        lab_clock.setForeground(color);   // 设置文字颜色
        setLab_clock_Text(num);

        add(lab_clock);
    }

    // 设置时间标签内容
    public void setLab_clock_Text(int num){
        if (24 == num){
            lab_clock.setText(getNowTime.nowTime24());
        }else{
            lab_clock.setText(getNowTime.nowTime12());
        }
    }



    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        lab_clock.setFont(new Font(fontType, fontStyle, fontSize));
    }

    public void setColor(Color color) {
        this.color = color;
        lab_clock.setForeground(color);
    }
}
