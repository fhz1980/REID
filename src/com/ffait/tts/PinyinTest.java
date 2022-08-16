package com.ffait.tts;
import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Scanner;

public class PinyinTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        // 这里为什么用字符串数组接受返回的拼音呢，因为中文有多音字，输入“长”，会返回zhang3和chang2,（2,3表示第几声)
        String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(str.charAt(0)); // str.charAt(0) 第一个汉字
        for (String py : pinyin) {
            System.out.println(py);
        }
    }
}
