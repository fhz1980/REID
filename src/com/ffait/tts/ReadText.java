package com.ffait.tts;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
 
public class ReadText {
    public static void main(String[] args) {
//        ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
        // Dispatch是做什么的？
//        Dispatch sapo = sap.getObject();
        try {
 
//            // 音量 0-100
//            sap.setProperty("Volume", new Variant(100));
//            // 语音朗读速度 -10 到 +10
//            sap.setProperty("Rate", new Variant(0));
//
//            Variant defalutVoice = sap.getProperty("Voice");
//
//            Dispatch dispdefaultVoice = defalutVoice.toDispatch();
//            Variant allVoices = Dispatch.call(sapo, "GetVoices");
//            Dispatch dispVoices = allVoices.toDispatch();
//
//            Dispatch setvoice = Dispatch.call(dispVoices, "Item", new Variant(1)).toDispatch();
//            ActiveXComponent setvoiceActivex = new ActiveXComponent(setvoice);
//
//            Variant item = Dispatch.call(setvoiceActivex, "GetDescription");
//            // 执行朗读
//            Dispatch.call(sapo, "Speak", new Variant("你好！"));
 
 
            //朗读
            ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
        // Dispatch是做什么的？
            Dispatch sapo = sap.getObject();
 
            Dispatch.call(sapo, "Speak", new Variant("你好！"));
 
 //         下面是构建文件流把生成语音文件
            sap = new ActiveXComponent("Sapi.SpFileStream");
            Dispatch spFileStream = sap.getObject();
 
            sap = new ActiveXComponent("Sapi.SpAudioFormat");
            Dispatch spAudioFormat = sap.getObject();
 
            // 设置音频流格式
            Dispatch.put(spAudioFormat, "Type", new Variant(22));
            // 设置文件输出流格式
            Dispatch.putRef(spFileStream, "Format", spAudioFormat);
            // 调用输出 文件流打开方法，创建一个.wav文件
            Dispatch.call(spFileStream, "Open", new Variant("f://0.wav"), new Variant(3), new Variant(true));
            // 设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(sapo, "AudioOutputStream", spFileStream);
            // 设置音量 0到100
            Dispatch.put(sapo, "Volume", new Variant(100));
            // 设置朗读速度
            Dispatch.put(sapo, "Rate", new Variant(0.1));
            // 开始朗读
            Dispatch.call(sapo, "Speak", new Variant("有人跌倒"));
 
            // 关闭输出文件
            Dispatch.call(spFileStream, "Close");
            Dispatch.putRef(sapo, "AudioOutputStream", null);
 
            spAudioFormat.safeRelease();
            spFileStream.safeRelease();
            sapo.safeRelease();
            sap.safeRelease();
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            sapo.safeRelease();
//            sap.safeRelease();
        }
    }
 
}