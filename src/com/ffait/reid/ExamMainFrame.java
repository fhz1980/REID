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

public class ExamMainFrame {
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
	public ExamMainFrame() {
		initialize();
	}
	private void initialize() {
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame = new JFrame("尼古拉AI培训系统");
		
		
		try {
			frame.setIconImage(ImageIO.read(new File("C:\\EXAM\\ncola.jpg")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setBounds(0,0, 780, 780);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		photolable = new JLabel();
		photolable.setBounds(500, 20, 102, 126);
		photolable.setBackground(new Color(255,255,255));//设置背景色和 窗体的背景色一样
		
		name = new JTextArea("姓名：");
		name.setFont(new Font("monospaced", Font.PLAIN, 20) );
		name.setEditable(false);//模仿JLabel 禁止编辑文字
		name.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		name.setBounds(20, 10, 400, 30);
		

		
		nation = new JTextArea("民族：");
		nation.setFont(new Font("monospaced", Font.PLAIN, 20) );
		nation.setEditable(false);//模仿JLabel 禁止编辑文字
		nation.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		nation.setBounds(20, 40, 400, 30);
		
		sex = new JTextArea("性别：");
		sex.setFont(new Font("monospaced", Font.PLAIN, 20) );
		sex.setEditable(false);//模仿JLabel 禁止编辑文字
		sex.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		sex.setBounds(20, 70, 400, 30);
		
		birthday = new JTextArea("生日：");
		birthday.setFont(new Font("monospaced", Font.PLAIN, 20) );
		birthday.setEditable(false);//模仿JLabel 禁止编辑文字
		birthday.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		birthday.setBounds(20, 100, 400, 30);
		
		idnum = new JTextArea("身份证号：");
		idnum.setFont(new Font("monospaced", Font.PLAIN, 20) );
		idnum.setEditable(false);//模仿JLabel 禁止编辑文字
		idnum.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		idnum.setBounds(20, 130, 400, 30);
		
		org = new JTextArea("机构：");
		org.setFont(new Font("monospaced", Font.PLAIN, 20) );
		org.setEditable(false);//模仿JLabel 禁止编辑文字
		org.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		org.setBounds(20, 160, 400, 30);
		

		
		validateTime = new JTextArea("有效日期：");
		validateTime.setLineWrap(true);
		validateTime.setFont(new Font("monospaced", Font.PLAIN, 20) );
		validateTime.setEditable(false);//模仿JLabel 禁止编辑文字
		validateTime.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		validateTime.setBounds(20, 190, 780, 30);
		
		
		address = new JTextArea("地址：");
		address.setEditable(false);//模仿JLabel 禁止编辑文字
		address.setFont(new Font("monospaced", Font.PLAIN, 20) );
		address.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		address.setBounds(20, 220, 780, 30);
		
		trainProject = new JTextArea("查询结果：");
		trainProject.setLineWrap(true);
		trainProject.setFont(new Font("monospaced", Font.PLAIN, 20) );
		trainProject.setEditable(false);//模仿JLabel 禁止编辑文字
		trainProject.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		trainProject.setBounds(20, 250, 780, 30);		
		
		cameralable = new JLabel("");
		cameralable.setBounds(30, 280, 720, 540);
		

		frame.getContentPane().add(photolable);		
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

		frame.setLocation((int)((width - frame.getWidth()) / 2), 20);

	}	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExamMainFrame window = new ExamMainFrame();
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
                BufferedImage bi=fs.mat2BImg(frame,720.0,540.0);

                long currenttime=System.currentTimeMillis();
                if(currenttime-pretime>4000) {
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
										String usercount =fs.searchExamUser(cardinfo.getIdnum());
										JacobDemo.readString("人脸认证成功，查询到"+usercount+"条考试信息！");
										trainProject.setText("查询结果：您今日有"+usercount+"场考试");
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