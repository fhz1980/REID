package com.ffait.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class BlackImg {
	//生成黑色底图
	public static BufferedImage pureColorPictures(int width,int height ) {
		//width 生成图宽度
		// height 生成图高度
		//创建一个width xheight ，RGB高彩图，类型可自定
		BufferedImage img=new BufferedImage(width, height , BufferedImage.TYPE_INT_RGB);
		//取得图形
		Graphics g=img.getGraphics();
		//设置颜色
		g.setColor(Color.BLACK);
		//填充
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		
        return img;
	}

}
