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
import com.ffait.util.ParameterOperate;
import com.ffait.util.UCS2UTF;

import zkteco.id100com.jni.id100sdk;

public class CardMainFrame {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	static FaceService fs=new FaceService();
	private JFrame frame;
	static JLabel cameralable;
	static JLabel photolable;
	static JTextArea name;
	static JTextArea idnum;
	static JTextArea sex;
	static JTextArea nation;
	static JTextArea birthday;
	static JTextArea address;
	static JTextArea org;
	static JTextArea validateTime;
	static JTextArea trainProject;
	static int flag = 0;
	public CardMainFrame() {
		initialize();
	}
	private void initialize() {
		frame = new JFrame("AI培训系统");
		try {
			frame.setIconImage(ImageIO.read(new File("C:\\REID\\ncola.jpg")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setBounds(0,0, 1024, 960);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		name = new JTextArea("姓名：");
		name.setFont(new Font("monospaced", Font.PLAIN, 20) );
		name.setEditable(false);//模仿JLabel 禁止编辑文字
		name.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		name.setBounds(20, 10, 600, 40);
		
		nation = new JTextArea("民族：");
		nation.setFont(new Font("monospaced", Font.PLAIN, 20) );
		nation.setEditable(false);//模仿JLabel 禁止编辑文字
		nation.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		nation.setBounds(20, 50, 600, 40);
		
		sex = new JTextArea("性别：");
		sex.setFont(new Font("monospaced", Font.PLAIN, 20) );
		sex.setEditable(false);//模仿JLabel 禁止编辑文字
		sex.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		sex.setBounds(20, 90, 600, 40);
		
		birthday = new JTextArea("生日：");
		birthday.setFont(new Font("monospaced", Font.PLAIN, 20) );
		birthday.setEditable(false);//模仿JLabel 禁止编辑文字
		birthday.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		birthday.setBounds(20, 130, 600, 40);
		
		idnum = new JTextArea("身份证号：");
		idnum.setFont(new Font("monospaced", Font.PLAIN, 20) );
		idnum.setEditable(false);//模仿JLabel 禁止编辑文字
		idnum.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		idnum.setBounds(20, 170, 600, 40);
		
		org = new JTextArea("机构：");
		org.setFont(new Font("monospaced", Font.PLAIN, 20) );
		org.setEditable(false);//模仿JLabel 禁止编辑文字
		org.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		org.setBounds(20, 210, 600, 40);
		
		address = new JTextArea("地址：");
		address.setEditable(false);//模仿JLabel 禁止编辑文字
		address.setFont(new Font("monospaced", Font.PLAIN, 20) );
		address.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		address.setBounds(20, 250, 900, 40);
		
		validateTime = new JTextArea("有效日期：");
		validateTime.setLineWrap(true);
		validateTime.setFont(new Font("monospaced", Font.PLAIN, 20) );
		validateTime.setEditable(false);//模仿JLabel 禁止编辑文字
		validateTime.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		validateTime.setBounds(20, 290, 900, 40);
		
		trainProject = new JTextArea("培训项目：");
		trainProject.setLineWrap(true);
		trainProject.setFont(new Font("monospaced", Font.PLAIN, 20) );
		trainProject.setEditable(false);//模仿JLabel 禁止编辑文字
		trainProject.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		trainProject.setBounds(20, 330, 900, 50);		
		
		cameralable = new JLabel("");
		cameralable.setBounds(32, 380, 960, 540);
		
		photolable = new JLabel();
		photolable.setBounds(650, 30, 204, 252);
		
		frame.getContentPane().add(name);
		frame.getContentPane().add(nation);
		frame.getContentPane().add(sex);
		frame.getContentPane().add(birthday);
		frame.getContentPane().add(idnum);
		frame.getContentPane().add(org);
		frame.getContentPane().add(address);
		frame.getContentPane().add(validateTime);
		frame.getContentPane().add(trainProject);
		frame.getContentPane().add(cameralable);		
		frame.getContentPane().add(photolable);
        int x = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-frame.getWidth())/2;

        int y = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-frame.getHeight())/2;

		frame.setLocation(x, y);
	}	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardMainFrame window = new CardMainFrame();
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

                long currenttime=System.currentTimeMillis();
                if(currenttime-pretime>5000) {
                	new Thread(new Runnable() {
						@Override
						public void run() {
							//long a=System.currentTimeMillis();
							if("OK".equals(id100sdk.readCard())){
								IDCard cardinfo=UCS2UTF.readCard(ParameterOperate.extract("idCardLib")+"/wz.txt");
								//System.out.println(cardinfo);
								name.setText("姓名："+cardinfo.getName());
								idnum.setText("身份证号："+cardinfo.getIdnum());
								nation.setText("民族："+cardinfo.getNation());
								sex.setText("性别："+cardinfo.getSex());
								address.setText("地址："+cardinfo.getAddress());
								birthday.setText("生日："+cardinfo.getBirthday());
								org.setText("机构："+cardinfo.getOrg());
								validateTime.setText("有效期："+cardinfo.getValidateTime());
								try {
									BufferedImage photo=ImageIO.read(new File(ParameterOperate.extract("idCardLib")+"/zp.jpg"));
									photolable.setIcon(new ImageIcon(photo));
									float sim=fs.personCardAuth(bi, photo);
									//System.out.println(sim);
									if(sim>0.50f) {
										String project =fs.allocateDuty(ParameterOperate.extract("idCardLib")+"/zp.jpg",cardinfo.getIdnum(),cardinfo.getName(),cardinfo.getSex(),cardinfo.getNation());
										
										if(project==null) {
											JacobDemo.readString("今日没有您的培新信息，请联系管理员！");
											trainProject.setText("你的培训过程为：");
										}else {
											JacobDemo.readString("人脸认证成功，请查看您的培训项目！");
											trainProject.setText("你的培训过程为："+project);
										}
									}else {
										JacobDemo.readString("人脸认证不成功，请重新认证！");
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							//System.out.println(System.currentTimeMillis()-a);
						}
                		
                	}).start();
                	pretime=currenttime;
                }
                
                
                
                cameralable.setIcon(new ImageIcon(bi));
			}
		}
	}


}