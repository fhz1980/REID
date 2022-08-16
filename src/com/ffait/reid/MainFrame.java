package com.ffait.reid;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import com.ffait.tts.JacobDemo;

public class MainFrame {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	static FaceService fs=new FaceService();
	private JFrame frame;
	static JLabel cameralable;
	static JTextArea name;
	static JTextArea idnum;
	static JTextArea completeP;
	static JTextArea uncompleteP;
	static int flag = 0;
	public MainFrame() {
		initialize();
	}
	private void initialize() {
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame = new JFrame("尼古拉AI培训系统");
		try {
			frame.setIconImage(ImageIO.read(new File("C:\\REID\\ncola.jpg")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setBounds(0,0, 1024, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		name = new JTextArea("");
		name.setFont(new Font("monospaced", Font.PLAIN, 16) );
		name.setEditable(false);//模仿JLabel 禁止编辑文字
		name.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		name.setBounds(20, 10, 900, 100);
		
		idnum = new JTextArea("");
		idnum.setEditable(false);//模仿JLabel 禁止编辑文字
		idnum.setFont(new Font("monospaced", Font.PLAIN, 16) );
		idnum.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		idnum.setBounds(20, 110, 900, 100);
		
		completeP = new JTextArea("");
		completeP.setLineWrap(true);
		completeP.setFont(new Font("monospaced", Font.PLAIN, 16) );
		completeP.setEditable(false);//模仿JLabel 禁止编辑文字
		completeP.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		completeP.setBounds(20, 210, 900, 100);
		
		uncompleteP = new JTextArea("");
		uncompleteP.setLineWrap(true);
		uncompleteP.setFont(new Font("monospaced", Font.PLAIN, 16) );
		uncompleteP.setEditable(false);//模仿JLabel 禁止编辑文字
		uncompleteP.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		uncompleteP.setBounds(20, 310, 900, 100);		
		frame.getContentPane().add(name);
		frame.getContentPane().add(idnum);
		frame.getContentPane().add(completeP);
		frame.getContentPane().add(uncompleteP);
		cameralable = new JLabel("");
		cameralable.setBounds(32, 440, 960, 540);
		frame.getContentPane().add(cameralable);
	}	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		long pretime=System.currentTimeMillis();
		VideoCapture camera = new VideoCapture(0);
		if (!camera.isOpened()) {
			System.out.println("Camera Error");
		} else {
			Mat frame = new Mat();
			while (flag == 0) {
				camera.read(frame);
                BufferedImage bi=fs.mat2BI(frame);
                //panel.mImg=bi;
                long currenttime=System.currentTimeMillis();
                if(currenttime-pretime>8000) {
                	new Thread(new Runnable() {
						@Override
						public void run() {
							TraingResult  tr=fs.findResult(bi);
							if(tr!=null) {
								name.setText(tr.getName());
								idnum.setText(tr.getFzk());
								completeP.setText(tr.getCompleteProject());
								uncompleteP.setText(tr.getUncompleteProject());
								//System.out.println(fs.findResult(bi));
								if(tr.isUncomplete()) {
									JacobDemo.readString("您有未完成的项目请继续培训");
								}
							}else{
								name.setText("");
								idnum.setText("");
								completeP.setText("");
								uncompleteP.setText("");
							}
	
						}
                		
                	}).start();
                	pretime=currenttime;
                }
                cameralable.setIcon(new ImageIcon(bi));
			}
		}
	}


}