package com.ffait.tts;

public class TestClass {
    
	
	public void fun1() {
		
		String test = "我是好人";
		char[] py = test.toCharArray();
		StringBuffer res = new StringBuffer();
		ICharactorTool myPinYin = new LookForSounds();
		
		for(int i=0; i < py.length; i++) {
			try {
				res.append(myPinYin.getFullSpell(py[i])+"...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(res);
	}
	
	
	public void fun2() {
		ICharactorTool myPinYin = new MyGetPinYin();
		try {
			for(String s : myPinYin.getNcnFullSpell("我是测试"))
				System.out.print(s+"...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void fun3() {
//		MyGetPinYin.setPath("D:\\pinyin\\"); // 通过设置MyGetPinYin的变量path设置声源库
		String test2 = "老王";
		ISpeakSound play = new SpeakSoundImpl(test2);
		play.playSound();
	}
	
	public static void main(String[] args) {
		TestClass tc=new TestClass();
		tc.fun3();
	}
}