package com.ffait.reid;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.ffait.tts.JacobDemo;
import com.ffait.util.ParameterOperate;

public class FaceService {

	public float personCardAuth(BufferedImage source,BufferedImage target) {
		String result=HttpService.ImgImgSim(ParameterOperate.extract("faceImgImgService"),source,target);
		//System.out.println(result);
		if(result!=null) {
			return Float.parseFloat(result);
		}else {
			return 0.0f;
		}
	}

	public String personTrainProject(String filename,String idnum ,String name,String sex ,String nation) {
		return HttpService.trainProject(ParameterOperate.extract("mainServiceIDCard"), idnum);
	}
	
	public String allocateDuty(String filename,String idnum ,String name,String sex ,String nation) {
		return HttpService.allocateDuty(ParameterOperate.extract("mainServiceIDCard"), filename,idnum ,name,sex ,nation);
	}
	public String searchExamUser(String idnum) {
		return HttpService.searchExamUser(ParameterOperate.extract("examSearchUser"), idnum);
	}	
	public TraingResult findResult(Mat capImg) {
		
		String memberId=findTargetMember(capImg);
		if(memberId==null) {
			return null;
		}else {
			String result=HttpService.trainResult(ParameterOperate.extract("mainServiceResult"),memberId);
			TraingResult tr=forwardReuslt(result);
			return tr;
		}
	}

	public TraingResult findResult(BufferedImage capImg) {
		String memberId=findTargetMember(capImg);
		if(memberId==null) {
			return null;
		}else {
			String result=HttpService.trainResult(ParameterOperate.extract("mainServiceResult"),memberId);
			TraingResult tr=forwardReuslt(result);
			return tr;
		}
	}
    public TraingResult forwardReuslt(String result) {
    	TraingResult tr=new TraingResult();
    	if(result==null) {
    		return null;
    	}else {
    		String[] rs=result.split(";");
			tr.setName(rs[0]);
			tr.setFzk(rs[1]);
			tr.setCompleteProject(rs[2]);
			tr.setUncompleteProject(rs[3]);
			//System.out.print(rs[3]);
			String[] isun=rs[3].split(":");
			//System.out.println(isun.length);
			if(isun.length==1) {
			  tr.setUncomplete(false);
			}
			return tr;
    	}
    }	
	public String findTargetMember(BufferedImage image) {
		String feature=HttpService.faceFea(ParameterOperate.extract("faceFeaService"), image);
		if (feature!=null) {
			List<Member> ml=findMembers();
			float score=0.0f;
			String memberId=null;
			for(int i=0;i<ml.size();i++) {
				String sim=HttpService.faceSim(ParameterOperate.extract("faceSimService"), feature, ml.get(i).getFea());
			    float fsim=Float.parseFloat(sim);
				if(fsim>score) {
					score=fsim;
					memberId=ml.get(i).getId();
				}
			}
			//System.out.println(score);
			if(score<0.7f) {
				JacobDemo.readString("没有查到您的信息请联系管理员");
				return null;
			}else {
				return memberId;
			}
		}else {
			return null;
		}
		
	}	
	public String findTargetMember(Mat capImg) {
		BufferedImage image=mat2BI(capImg);
		String feature=HttpService.faceFea(ParameterOperate.extract("faceFeaService"), image);
		if (feature!=null) {
			List<Member> ml=findMembers();
			float score=0.0f;
			String memberId=null;
			for(int i=0;i<ml.size();i++) {
				String sim=HttpService.faceSim(ParameterOperate.extract("faceSimService"), feature, ml.get(i).getFea());
			    float fsim=Float.parseFloat(sim);
				if(fsim>score) {
					score=fsim;
					memberId=ml.get(i).getId();
				}
			}
			return memberId;
		}else {
			return null;
		}
		
	}
	public List<Member> findMembers() {
		String memberfeas=HttpService.allFea(ParameterOperate.extract("mainServiceFeas"));
		List<Member> meml=new ArrayList<Member>();
		if(memberfeas!=null) {
			String [] mems=memberfeas.split("_");
			for(int i=0;i<mems.length;i++) {
				Member m=new Member();
				String [] pairid=mems[i].split("=");
				m.setId(pairid[0]);
				m.setFea(pairid[1]);
				meml.add(m);
			}
		}
		return meml;
	}
	public BufferedImage mat2BI(Mat mat){
		//Mat mat=srcmat.clone();
		double scale=960.0/mat.cols();
		Imgproc.resize(mat, mat, new Size(mat.cols()*scale,mat.rows()*scale));
		Core.flip(mat,mat,1);
       int dataSize =mat.cols()*mat.rows()*(int)mat.elemSize();
        byte[] data=new byte[dataSize];
        mat.get(0, 0,data);
        int type=mat.channels()==1?BufferedImage.TYPE_BYTE_GRAY:BufferedImage.TYPE_3BYTE_BGR;
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
	
	public BufferedImage mat2BImg(Mat mat,double width,double height){
		//Mat mat=srcmat.clone();
		double scale=960.0/mat.cols();
		Imgproc.resize(mat, mat, new Size(width,height));
		Core.flip(mat,mat,1);
       int dataSize =mat.cols()*mat.rows()*(int)mat.elemSize();
        byte[] data=new byte[dataSize];
        mat.get(0, 0,data);
        int type=mat.channels()==1?BufferedImage.TYPE_BYTE_GRAY:BufferedImage.TYPE_3BYTE_BGR;
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
	
	public static void main(String[] args) {
		FaceService fs=new FaceService();
		System.out.println(fs.allocateDuty(ParameterOperate.extract("idCardLib")+"/zp.jpg", "333333333333333333", "99999", "男", "汉族"));
	}
}
