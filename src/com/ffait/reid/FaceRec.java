package com.ffait.reid;
import com.arcsoft.face.*;
import com.arcsoft.face.enums.*;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.arcsoft.face.toolkit.ImageInfoEx;
import com.ffait.tts.JacobDemo;
import com.ffait.util.BlackImg;
import com.ffait.util.ParameterOperate;

import static com.arcsoft.face.toolkit.ImageFactory.getGrayData;
import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;
public class FaceRec {
	final String URL=ParameterOperate.extract("url");
    FaceEngine faceEngine;
    FunctionConfiguration functionConfiguration;
    long delayDisplay=0;
    public FaceRec(){
        String osname=System.getProperty("os.name");
        System.out.println(osname);
        String appId="";
        String sdkKey="";
        
        if(osname.contains("Win")) {
            appId = "BAMq2pgdyXrJNWFJEbHSucktXZSy4UHacGvWK4PgbT2q";
            sdkKey = "wQF24pvHLgr5Vu8pHqAuvYuJjtm4pWnbsxQ2JFZwFUu";
            faceEngine = new FaceEngine(ParameterOperate.extract("EngineDirWin"));
        }else{
            appId = "BAMq2pgdyXrJNWFJEbHSucktXZSy4UHacGvWK4PgbT2q";
            sdkKey = "wQF24pvHLgr5Vu8pHqAuvYuAdKdUr1ZZfqTymSuXsC4";
            faceEngine = new FaceEngine(ParameterOperate.extract("EngineDirLinux"));
        }
        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey);
        System.out.println(errorCode);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
        }


        ActiveFileInfo activeFileInfo=new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("获取激活文件信息失败");
        }
        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_0_ONLY);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);
        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);
        System.out.println("init="+errorCode);
        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }
    }
    public byte[] extractFaceFeature(String imgPath){
        //人脸检测
        return extractFaceFeature(new File(imgPath));
    }
    public BufferedImage faceDetect(Mat capImg,long n) {
    	Core.flip(capImg,capImg,1);
    	BufferedImage BcapImgnew=mat2BI(capImg);
    	
    	
    	
    	ImageInfo imageInfo =ImageFactory.bufferedImage2ImageInfo(BcapImgnew);
    	
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        
        int errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        
        if(faceInfoList.size()>0) {
        	delayDisplay=n;
        	
        	//三维角度检测
//        	face3DAngle(imageInfo,faceInfoList);
        	Rect rect=faceInfoList.get(0).getRect();
        	Imgproc.rectangle(capImg, new Point(rect.left, rect.top), new Point(rect.right, rect.bottom),
                    new Scalar(0, 0, 255), 2);
        	 if(n%60==0) {
	        	 FaceFeature faceFeature = new FaceFeature();
	             errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
	          	 CompareResult cr=findtarget(faceFeature.getFeatureData());
	             System.out.println(cr.getScore());
	             if(cr.getScore()>0.8) {
	            	JacobDemo.readString(cr.name+"认证通过，请进行工作！");
	             	BufferedImage BcapImg=mat2BI(capImg);
	            	BcapImgnew =PutText2Img.drawTextWithFontStyleLineFeed(BcapImg, cr.name, rect.left, rect.top, 600, 50, 20.0, new Font("宋体",Font.BOLD,20), Color.red);
	            	HttpService.fileUpload(URL, BcapImgnew, cr.getId());
	             }else {
	            	 BcapImgnew=mat2BI(capImg);
	             }
        	 }else {
        		 BcapImgnew=mat2BI(capImg); 
        	 }
        }else {
        	if(n-delayDisplay>60) {
        		BcapImgnew=BlackImg.pureColorPictures(1280, 720);
        	}else if(n-delayDisplay<0) {
        		delayDisplay=0;
        	}
        }
        
        return BcapImgnew;
        
    }
    public byte[] extractFaceFeature(File imgfile){
        //人脸检测
        if(imgfile!=null){
            ImageInfo imageInfo = getRGBData(imgfile);
            List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
            int errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
//            System.out.println("detectFaces="+errorCode);
//            System.out.println(faceInfoList);
            if(faceInfoList.size()>0) {
                //特征提取
                FaceFeature faceFeature = new FaceFeature();
                errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
//                System.out.println("extractFaceFeature=" + errorCode);
                return faceFeature.getFeatureData();
            }else{
                return null;
            }
        }else{
            return null;
        }

    }
    public float FaceSimilar(byte[] f1,byte[]  f2){
        //特征比对
        if(f1==null || f2==null){
            return 0.0f;
        }else {
            FaceFeature targetFaceFeature = new FaceFeature();
            targetFaceFeature.setFeatureData(f1);
            FaceFeature sourceFaceFeature = new FaceFeature();
            sourceFaceFeature.setFeatureData(f2);
            FaceSimilar faceSimilar = new FaceSimilar();
            int errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
            return faceSimilar.getScore();
        }
    }
    public float FaceSimilar(File f1,File f2){
        //特征比对
        if(f1==null || f2==null){
            return -1.0f;
        }else {
            byte[] ff1=extractFaceFeature(f1);
            byte[] ff2=extractFaceFeature(f2);
            if(ff1!=null && ff2!=null) {
                FaceFeature targetFaceFeature = new FaceFeature();
                targetFaceFeature.setFeatureData(ff1);
                FaceFeature sourceFaceFeature = new FaceFeature();
                sourceFaceFeature.setFeatureData(ff2);
                FaceSimilar faceSimilar = new FaceSimilar();
                int errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
                return faceSimilar.getScore();
            }else{
                return -1.0f;
            }
        }
    }
    public float FaceSimilar(String soruce,String target){
        return FaceSimilar(new File(soruce),new File(target));
    }
 
    public void destory(){
        int errorCode = faceEngine.unInit();
    }
    private BufferedImage mat2BI(Mat mat){
        int dataSize =mat.cols()*mat.rows()*(int)mat.elemSize();
        byte[] data=new byte[dataSize];
        mat.get(0, 0,data);
        int type=mat.channels()==1?
                BufferedImage.TYPE_BYTE_GRAY:BufferedImage.TYPE_3BYTE_BGR;

        if(type==BufferedImage.TYPE_3BYTE_BGR){
            for(int i=0;i<dataSize;i+=3){
                byte blue=data[i+0];
                data[i+0]=data[i+2];
                data[i+2]=blue;
            }
        }
        BufferedImage image=new BufferedImage(mat.cols(),mat.rows(),type);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);

        return image;
    }
    public CompareResult findtarget( byte[] target) {
    	String name="null";
		float score=0.0f;
		String id="null";
		List<PowerWorker> workers=DBProcess.allFeature();
		for (PowerWorker pw:workers) {
			if(pw!=null&&pw.getFeature()!=null) {
				byte[] source = String2Byte.generateByte(pw.getFeature());
				float result=FaceSimilar(source,target);
				if(result>score){
					score=result;
					name=pw.getName();
					id=pw.getId();
				}
			}
		}
		CompareResult cr=new CompareResult();
		cr.setName(name);
		cr.setScore(score);
		cr.setId(id);
		return cr;
    }
    public  List<Face3DAngle> face3DAngle(ImageInfo imageInfo,List<FaceInfo> faceInfoList){
    	FunctionConfiguration configuration = new FunctionConfiguration();
        configuration.setSupportFace3dAngle(true);
    	int errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);
    	if(errorCode==0) {
        	List<Face3DAngle> face3DAngleList = new ArrayList<Face3DAngle>();
            errorCode = faceEngine.getFace3DAngle(face3DAngleList);
            if(face3DAngleList.size()>0) {
	            System.out.println("3D角度：" + face3DAngleList.get(0).getPitch() + "," + face3DAngleList.get(0).getRoll() + "," + face3DAngleList.get(0).getYaw());
	            return face3DAngleList;
            }else {
            	return null;
            }
            
        }else {
        	return null;
        }
    }
    
    public void other(String imgPath){
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        ArrayList<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        ImageInfo imageInfo = getRGBData(new File(imgPath));
        //设置活体测试
        int errorCode = faceEngine.setLivenessParam(0.5f, 0.7f);
        //人脸属性检测
        FunctionConfiguration configuration = new FunctionConfiguration();
        configuration.setSupportAge(true);
        configuration.setSupportFace3dAngle(true);
        configuration.setSupportGender(true);
        configuration.setSupportLiveness(true);
        errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);
        //性别检测
        List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
        errorCode = faceEngine.getGender(genderInfoList);
        System.out.println("性别：" + genderInfoList.get(0).getGender());

        //年龄检测
        List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
        errorCode = faceEngine.getAge(ageInfoList);
        System.out.println("年龄：" + ageInfoList.get(0).getAge());

        //3D信息检测
        List<Face3DAngle> face3DAngleList = new ArrayList<Face3DAngle>();
        errorCode = faceEngine.getFace3DAngle(face3DAngleList);
        System.out.println("3D角度：" + face3DAngleList.get(0).getPitch() + "," + face3DAngleList.get(0).getRoll() + "," + face3DAngleList.get(0).getYaw());

        //活体检测
        List<LivenessInfo> livenessInfoList = new ArrayList<LivenessInfo>();
        errorCode = faceEngine.getLiveness(livenessInfoList);
        System.out.println("活体：" + livenessInfoList.get(0).getLiveness());


        //IR属性处理
        ImageInfo imageInfoGray = getGrayData(new File("G:\\AIFace\\img\\fz.jpg"));
        List<FaceInfo> faceInfoListGray = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray);

        FunctionConfiguration configuration2 = new FunctionConfiguration();
        configuration2.setSupportIRLiveness(true);
        errorCode = faceEngine.processIr(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray, configuration2);
        //IR活体检测
        List<IrLivenessInfo> irLivenessInfo = new ArrayList<>();
        errorCode = faceEngine.getLivenessIr(irLivenessInfo);
        System.out.println("IR活体：" + irLivenessInfo.get(0).getLiveness());

        ImageInfoEx imageInfoEx = new ImageInfoEx();
        imageInfoEx.setHeight(imageInfo.getHeight());
        imageInfoEx.setWidth(imageInfo.getWidth());
        imageInfoEx.setImageFormat(imageInfo.getImageFormat());
        imageInfoEx.setImageDataPlanes(new byte[][]{imageInfo.getImageData()});
        imageInfoEx.setImageStrides(new int[]{imageInfo.getWidth() * 3});
        List<FaceInfo> faceInfoList1 = new ArrayList<>();
        errorCode = faceEngine.detectFaces(imageInfoEx, DetectModel.ASF_DETECT_MODEL_RGB, faceInfoList1);

        FunctionConfiguration fun = new FunctionConfiguration();
        fun.setSupportAge(true);
        errorCode = faceEngine.process(imageInfoEx, faceInfoList1, functionConfiguration);
        List<AgeInfo> ageInfoList1 = new ArrayList<>();
        int age = faceEngine.getAge(ageInfoList1);
        System.out.println("年龄：" + ageInfoList1.get(0).getAge());

        FaceFeature feature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfoEx, faceInfoList1.get(0), feature);
    }
    public static void main(String[] args) {
    	FaceRec fr=new FaceRec();
    	fr.other("G:\\尼古拉\\智能配电房\\公司人员照片\\康俊方.jpg");
	}
}

