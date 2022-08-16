package com.ffait.reid;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class PutText2Img {
 	/**
     * 画换行文案
     *
     * @param base 图片
     * @param text 文案
     * @param x 横坐标
     * @param y 纵坐标
     * @param rowWidth 每行宽度
     * @param height 行高
     * @param widthRate 每个字符所占的长度
     * @param font 字体
     * @param color 颜色
     * @return
     */
    public static BufferedImage drawTextWithFontStyleLineFeed(BufferedImage base, String text, int x, int y,
                                                              int rowWidth, int height, Double widthRate, Font font, Color color) {
        Graphics2D g = (Graphics2D) base.getGraphics();
        g.setFont(font);
        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawTextLineFeed(g, base, text, rowWidth, height, x, y, widthRate);
        return base;
    }

   /**
	*  通过递归的方式由上到下画文案
	*/
    private static void drawTextLineFeed(Graphics2D g, BufferedImage base, String text, int rowWidth, int height, int loc_X, int loc_Y, Double widthRate) {
        int width = getStrWidth(base, text, widthRate);
        if (width <= rowWidth) {
            g.drawString(text, loc_X, loc_Y);
            return;
        }
        for (int i = 1; ; i++) {
            String str = text.substring(0, i);
            int localWidth = getStrWidth(base, str, widthRate);
            // 当字体宽度超过目标行宽，则输出当前文本
            if (localWidth >= rowWidth) {
                g.drawString(str, loc_X, loc_Y);
                int length = text.length();
                // 保存剩余的文本
                text = text.substring(i, length);
                break;
            }
        }
        // 画下一行
        drawTextLineFeed(g, base, text, rowWidth, height, loc_X,loc_Y + height, widthRate);
    }
	
   /**
	* 计算当前文案所占宽度
	*/
    private static int getStrWidth(BufferedImage base, String str, Double widthRate) {
        char[] c = str.toCharArray();
        Double width = base.getGraphics().getFontMetrics().charsWidth(c, 0, str.length()) * widthRate;
        return width.intValue();
    }

}
