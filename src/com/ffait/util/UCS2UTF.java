package com.ffait.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.ffait.reid.IDCard;

public class UCS2UTF {
	static String sex = "2:女,1:男,0:未知,9:未说明";
	static String nation = "01:汉,02:蒙古,03:回,04:藏,05:维吾尔,06:苗,07:彝,08:壮,09:布衣,10:朝鲜,11:满,12:侗,13:瑶,14:白,15:土家,16:哈尼,17:哈萨克,18:傣,19:黎,20:傈僳,21:佤,22:畲,23:高山,24:拉祜,25:水,26:东乡,27:纳西,28:景颇,29:柯尔克孜,30:土,31:达斡尔,32:仫佬,33:羌,34:布朗,35:撒拉,36:毛南,37:仡佬,38:锡伯,39:阿昌,40:普米,41:塔吉克,42:怒,43:乌孜别克,44:俄罗斯,45:鄂温克,46:德昂,47:保安,48:裕固,49:京,50:塔塔尔,51:独龙,52:鄂伦春,53:赫哲,54:门巴,55:珞巴,56:基诺";
	static HashMap<String, String> sexMap = new HashMap<String, String>();
	static HashMap<String, String> nationMap = new HashMap<String, String>();

	static {
		String[] sexs = sex.split(",");
		for (int i = 0; i < sexs.length; i++) {
			String[] item = sexs[i].split(":");
			sexMap.put(item[0], item[1]);
		}
		String[] nations = nation.split(",");
		for (int i = 0; i < nations.length; i++) {
			String[] item = nations[i].split(":");
			nationMap.put(item[0], item[1]);
		}
	}

	public static String ucs2utf16(String pathname) {
		try {
			char[] cbuf = new char[1024];
			// GB18030
			InputStream inputStream = new FileInputStream(pathname);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_16LE);
			inputStreamReader.read(cbuf);
			return String.valueOf(cbuf);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String[] ucs2utfstr(String pathname) {
		try {
			char[] cbuf = new char[1024];
			// GB18030
			InputStream inputStream = new FileInputStream(pathname);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_16LE);
			inputStreamReader.read(cbuf);
			String abc = String.valueOf(cbuf).replaceAll(" +", " ").trim();
			String[] values = abc.split(" ");
			return values;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static IDCard readCard(String path) {
		String[] ms_list = ucs2utfstr(path);
		String name = ms_list[0];
		String item_sex = ms_list[1].substring(0, 1);
		item_sex = sexMap.get(item_sex);
		String item_nation = ms_list[1].substring(1, 3);
		item_nation = nationMap.get(item_nation);
		String birthday = ms_list[1].substring(3, 11);
		String address = ms_list[1].substring(11);
		String idnum = ms_list[2].substring(0, 18);
		String pso = ms_list[2].substring(18);
		String starttime = ms_list[3].substring(0, 8);
		String endtime = ms_list[3].substring(8);
		birthday = birthday.substring(0, 4) + "年" + birthday.substring(4, 6) + "月" + birthday.substring(6) + "日";
		String validity_day = starttime.substring(0, 4) + "年" + starttime.substring(4, 6) + "月" + starttime.substring(6)
				+ "日" + "-" + endtime.substring(0, 4) + "年" + endtime.substring(4, 6) + "月" + endtime.substring(6)
				+ "日";
		IDCard card = new IDCard();
		card.setAddress(address);
		card.setSex(item_sex);
		card.setBirthday(birthday);
		card.setIdnum(idnum);
		card.setName(name);
		card.setNation(item_nation);
		card.setOrg(pso);
		card.setValidateTime(validity_day);
		return card;
	}

	public static void main(String[] args) {
		String pathname =ParameterOperate.extract("idCardLib")+"/wz.txt";
		//String pathname = "D:\\idcard-reader-master-exam\\idcard-reader-master\\wz.txt";
		//UCS2UTF ucs = new UCS2UTF();
		System.out.println(readCard(pathname));
		readCard(pathname);
	}
}
