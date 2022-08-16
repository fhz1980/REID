package com.ffait.reid;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class MainFrame2 extends JPanel {
    private BufferedImage mImg;
    public void paintComponent(Graphics g){
        if(mImg!=null){
//        	System.out.println("mwidth:"+mImg.getWidth()+"mheight:"+mImg.getHeight());
            g.drawImage(mImg, 0, 0, mImg.getWidth(),mImg.getHeight(),this);
        }
    }
    public static void main(String[] args) {
    	long pretime=System.currentTimeMillis();
    	FaceService fs=new FaceService();
        try{
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat capImg=new Mat();
            VideoCapture capture=new VideoCapture(0);
            capture.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 1024);//宽度 
            capture.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 768);//高度           
            int height = (int)capture.get(Videoio.CAP_PROP_FRAME_HEIGHT);
            int width = (int)capture.get(Videoio.CAP_PROP_FRAME_WIDTH);
            System.out.println("height:"+height+"  width:"+width);
            if(height==0||width==0){
                throw new Exception("camera not found!");
            }

            JFrame frame=new JFrame("身份认证");
            ActionListener actionListener =new ActionListener(){
         	   public void actionPerformed(ActionEvent e) {
         	    frame.dispose();
         	   }
         	  };
            frame.getRootPane().registerKeyboardAction(actionListener, "command",KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),JComponent.WHEN_IN_FOCUSED_WINDOW);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            MainFrame2 panel=new MainFrame2();
            frame.setLayout(new FlowLayout());
            frame.setContentPane(panel);
            frame.setUndecorated(true); 
            frame.setVisible(true);
            frame.setBackground(Color.LIGHT_GRAY);
            frame.setSize(960,800);
            Label name=new Label("姓名:");
           
            frame.add(name);
            double swidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            double sheight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            frame.setLocation( (int) (swidth - frame.getWidth()) / 2,(int) (sheight - frame.getHeight()) / 2);
            while(frame.isShowing()){
                capture.read(capImg);
                BufferedImage bi=fs.mat2BI(capImg);
                panel.mImg=bi;
                long currenttime=System.currentTimeMillis();
                if(currenttime-pretime>8000) {
                	new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							long a=System.currentTimeMillis();
							System.out.println(fs.findResult(bi));
		                	
						}
                		
                	}).start();
                	pretime=currenttime;
                }
               panel.repaint();
            }
            capture.release();
            frame.dispose();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            System.out.println("--done--");
        }

    }
}