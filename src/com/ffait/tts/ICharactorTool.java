package com.ffait.tts;

import java.util.List;

/**
 * 输入字符获取对应的拼音
 * @author JiangGengChao
 */
public interface ICharactorTool {
	
	public String getFullSpell(char cnChar) throws Exception;
	
	public List<String> getNcnFullSpell(String cnstr) throws Exception;
	
}
