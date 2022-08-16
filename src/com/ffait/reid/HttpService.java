package com.ffait.reid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.ffait.tts.JacobDemo;

public class HttpService {
	//url：请求路经
	//filePath：需要上传的文件路径
	public static String fileUpload(String url,String filePath){
		 String result=null;
		 CloseableHttpClient httpclient = HttpClients.createDefault(); 
       try { 
           HttpPost httppost = new HttpPost(url); 
           RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
           httppost.setConfig(requestConfig);
           FileBody bin = new FileBody(new File(filePath)); 
           //StringBody comment = new StringBody("This is comment", ContentType.TEXT_PLAIN); 
           HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("fileName", bin).build(); 
           httppost.setEntity(reqEntity); 
           CloseableHttpResponse response = httpclient.execute(httppost); 
           try { 
               HttpEntity resEntity = response.getEntity(); 
               if (resEntity != null) { 
                   String responseEntityStr = EntityUtils.toString(response.getEntity());
                   result=responseEntityStr;
                   if("null".equals(result)){
	                	  return null;
	                  }
                   
				   //System.out.println("Response content length: " + resEntity.getContentLength()); 
               } 
               EntityUtils.consume(resEntity); 
           } finally { 
               response.close(); 
           } 
       } catch (ClientProtocolException e) { 
           e.printStackTrace(); 
       } catch (IOException e) { 
           e.printStackTrace(); 
       } finally { 
           try { 
               httpclient.close(); 
           } catch (IOException e) { 
               e.printStackTrace(); 
           } 
       } 
       return result;
	}

	public static String searchExamUser(String url,String idNum){
		 String result=null;
		 CloseableHttpClient httpclient = HttpClients.createDefault(); 
     try { 
         HttpPost httppost = new HttpPost(url); 

//         httppost.setEntity(new StringEntity(jsonParam.toString(), Charset.forName("UTF-8")));
         RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
         httppost.setConfig(requestConfig);
         StringBody username = new StringBody(idNum, Charset.forName("UTF-8")); 
         HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("username",username).build(); 
         httppost.setEntity(reqEntity); 
         CloseableHttpResponse response = httpclient.execute(httppost); 
         try { 
             HttpEntity resEntity = response.getEntity(); 
             if (resEntity != null) { 
                 String responseEntityStr = EntityUtils.toString(response.getEntity());
                 result=responseEntityStr;
                 if("null".equals(result)){
	                	  return null;
	                  }
                 
				   //System.out.println("Response content length: " + resEntity.getContentLength()); 
             } 
             EntityUtils.consume(resEntity); 
         } finally { 
             response.close(); 
         } 
     } catch (ClientProtocolException e) { 
         e.printStackTrace(); 
     } catch (IOException e) { 
         e.printStackTrace(); 
     } finally { 
         try { 
             httpclient.close(); 
         } catch (IOException e) { 
             e.printStackTrace(); 
         } 
     } 
     return result;
	}	
	
	
	public static String allocateDuty(String url,String filePath,String idNum,String name,String sex ,String nation){
		 String result=null;
		 CloseableHttpClient httpclient = HttpClients.createDefault(); 
      try { 
          HttpPost httppost = new HttpPost(url); 

//          httppost.setEntity(new StringEntity(jsonParam.toString(), Charset.forName("UTF-8")));
          RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
          httppost.setConfig(requestConfig);
          FileBody bin = new FileBody(new File(filePath)); 
          StringBody workerId = new StringBody(idNum, Charset.forName("UTF-8")); 
          System.out.println(name);
          StringBody workername = new StringBody(name, Charset.forName("UTF-8")); 
//          StringEntity workername =new StringEntity(name, Charset.forName("UTF-8"));
          StringBody workersex = new StringBody(sex, Charset.forName("UTF-8")); 
          StringBody workernation = new StringBody(nation, Charset.forName("UTF-8")); 
          HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("fileName", bin).addPart("idNum",workerId).addPart("name",workername).addPart("sex",workersex).addPart("nation",workernation).build(); 
          httppost.setEntity(reqEntity); 
          CloseableHttpResponse response = httpclient.execute(httppost); 
          try { 
              HttpEntity resEntity = response.getEntity(); 
              if (resEntity != null) { 
                  String responseEntityStr = EntityUtils.toString(response.getEntity());
                  result=responseEntityStr;
                  if("null".equals(result)){
	                	  return null;
	                  }
                  
				   //System.out.println("Response content length: " + resEntity.getContentLength()); 
              } 
              EntityUtils.consume(resEntity); 
          } finally { 
              response.close(); 
          } 
      } catch (ClientProtocolException e) { 
          e.printStackTrace(); 
      } catch (IOException e) { 
          e.printStackTrace(); 
      } finally { 
          try { 
              httpclient.close(); 
          } catch (IOException e) { 
              e.printStackTrace(); 
          } 
      } 
      return result;
	}
	
	
	public static void fileUpload(String url,BufferedImage bufferedImage,String name){
		 CloseableHttpClient httpclient = HttpClients.createDefault(); 
      try { 
          HttpPost httppost = new HttpPost(url); 
          RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
          httppost.setConfig(requestConfig);
          File outputfile = new File("image.jpg");
          ImageIO.write(bufferedImage, "jpg", outputfile);
          FileBody bin = new FileBody(outputfile); 
          StringBody comment = new StringBody(name, ContentType.TEXT_PLAIN); 
          HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin).addPart("worker", comment).build(); 
           httppost.setEntity(reqEntity); 
          //System.out.println("executing request " + httppost.getRequestLine()); 
          CloseableHttpResponse response = httpclient.execute(httppost); 
          try { 
              //System.out.println(response.getStatusLine()); 
              HttpEntity resEntity = response.getEntity(); 
              if (resEntity != null) { 
                  String responseEntityStr = EntityUtils.toString(response.getEntity());
                  //System.out.println(responseEntityStr);
                  //System.out.println("Response content length: " + resEntity.getContentLength()); 
              } 
              EntityUtils.consume(resEntity); 
          } finally { 
              response.close(); 
          } 
      } catch (ClientProtocolException e) { 
          e.printStackTrace(); 
      } catch (IOException e) { 
          e.printStackTrace(); 
      } finally { 
          try { 
              httpclient.close(); 
          } catch (IOException e) { 
              e.printStackTrace(); 
          } 
      } 
	}
	public static String faceFea(String url,BufferedImage bufferedImage){
		String result=null;
		 CloseableHttpClient httpclient = HttpClients.createDefault(); 
     try { 
         HttpPost httppost = new HttpPost(url); 
         RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
         httppost.setConfig(requestConfig);
         File outputfile = new File("image.jpg");
         ImageIO.write(bufferedImage, "jpg", outputfile);
         FileBody bin = new FileBody(outputfile); 
         HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("fileName", bin).build(); 
         httppost.setEntity(reqEntity); 
         //System.out.println("executing request " + httppost.getRequestLine()); 
         CloseableHttpResponse response = httpclient.execute(httppost); 
         try { 
             //System.out.println(response.getStatusLine()); 
             HttpEntity resEntity = response.getEntity(); 
             if (resEntity != null) { 
                 String responseEntityStr = EntityUtils.toString(response.getEntity());
                 result= responseEntityStr;
                 if("null".equals(result)){
               	  return null;
                 }
                 //System.out.println(responseEntityStr);
                 //System.out.println("Response content length: " + resEntity.getContentLength()); 
             }
             EntityUtils.consume(resEntity); 
         } finally { 
             response.close(); 
         } 
     } catch (ClientProtocolException e) { 
         e.printStackTrace(); 
     } catch (IOException e) { 
         e.printStackTrace(); 
     } finally { 
         try { 
             httpclient.close(); 
         } catch (IOException e) { 
             e.printStackTrace(); 
         } 
     } 
     return result;
	}
	
	public static String ImgImgSim(String url,BufferedImage bufferedImage,BufferedImage bufferedImage1){
		String result=null;
		 CloseableHttpClient httpclient = HttpClients.createDefault(); 
     try { 
         HttpPost httppost = new HttpPost(url); 
         RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
         httppost.setConfig(requestConfig);
         File outputfile = new File("D:/image.jpg");
         ImageIO.write(bufferedImage, "jpg", outputfile);
         FileBody bin = new FileBody(outputfile); 
         
         File outputfile1 = new File("D:/image1.jpg");
         ImageIO.write(bufferedImage1, "jpg", outputfile1);
         FileBody bin1 = new FileBody(outputfile1); 
         
         HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("source", bin).addPart("target", bin1).build(); 
         httppost.setEntity(reqEntity); 
         //System.out.println("executing request " + httppost.getRequestLine()); 
         CloseableHttpResponse response = httpclient.execute(httppost); 
         try { 
             //System.out.println(response.getStatusLine()); 
             HttpEntity resEntity = response.getEntity(); 
             if (resEntity != null) { 
                 String responseEntityStr = EntityUtils.toString(response.getEntity());
                 result= responseEntityStr;
                 if("null".equals(result)){
               	  return null;
                 }
                 //System.out.println(responseEntityStr);
                 //System.out.println("Response content length: " + resEntity.getContentLength()); 
             }
             EntityUtils.consume(resEntity); 
         } finally { 
             response.close(); 
         } 
     } catch (ClientProtocolException e) { 
         e.printStackTrace(); 
     } catch (IOException e) { 
         e.printStackTrace(); 
     } finally { 
         try { 
             httpclient.close(); 
         } catch (IOException e) { 
             e.printStackTrace(); 
         } 
     } 
     return result;
	}
	
	public static String faceSim(String url,String fea1,String fea2){
		String result=null;
		 CloseableHttpClient httpclient = HttpClients.createDefault(); 
	      try { 
	          HttpPost httppost = new HttpPost(url); 
	          RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
	          httppost.setConfig(requestConfig);
	         
	          StringBody source = new StringBody(fea1, ContentType.TEXT_PLAIN); 
	          StringBody target = new StringBody(fea2, ContentType.TEXT_PLAIN); 
	          HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("source", source).addPart("target", target).build(); 
	          httppost.setEntity(reqEntity); 
	          //System.out.println("executing request " + httppost.getRequestLine()); 
	          CloseableHttpResponse response = httpclient.execute(httppost); 
	          try { 
	              //System.out.println(response.getStatusLine()); 
	              HttpEntity resEntity = response.getEntity(); 
	              if (resEntity != null) { 
	                  String responseEntityStr = EntityUtils.toString(response.getEntity());
	                  result=responseEntityStr;
	                  if("null".equals(result)){
	                	  return null;
	                  }
	                  //System.out.println(responseEntityStr);
	                  //System.out.println("Response content length: " + resEntity.getContentLength()); 
	              } 
	              EntityUtils.consume(resEntity); 
	          } finally { 
	              response.close(); 
	          } 
	      } catch (ClientProtocolException e) { 
	          e.printStackTrace(); 
	      } catch (IOException e) { 
	          e.printStackTrace(); 
	      } finally { 
	          try { 
	              httpclient.close(); 
	          } catch (IOException e) { 
	              e.printStackTrace(); 
	          } 
	      } 
     return result;
	}	
	public static String trainResult(String url,String arg){
		String result=null;
		 CloseableHttpClient httpclient = HttpClients.createDefault(); 
	      try { 
	          HttpPost httppost = new HttpPost(url); 
	          RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
	          httppost.setConfig(requestConfig);
	         
	          StringBody workerId = new StringBody(arg, ContentType.TEXT_PLAIN); 
	          HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("workerId", workerId).build(); 
	          httppost.setEntity(reqEntity); 
	          //System.out.println("executing request " + httppost.getRequestLine()); 
	          CloseableHttpResponse response = httpclient.execute(httppost); 
	          try { 
	              //System.out.println(response.getStatusLine()); 
	              HttpEntity resEntity = response.getEntity(); 
	              if (resEntity != null) { 
	                  String responseEntityStr = EntityUtils.toString(response.getEntity());
	                  result=responseEntityStr;
	                  if("null".equals(result)){
	                	  return null;
	                  }
	                  //System.out.println(responseEntityStr);
	                  //System.out.println("Response content length: " + resEntity.getContentLength()); 
	              } 
	              EntityUtils.consume(resEntity); 
	          } finally { 
	              response.close(); 
	          } 
	      } catch (ClientProtocolException e) { 
	          e.printStackTrace(); 
	      } catch (IOException e) { 
	          e.printStackTrace(); 
	      } finally { 
	          try { 
	              httpclient.close(); 
	          } catch (IOException e) { 
	              e.printStackTrace(); 
	          } 
	      } 
     return result;
	}
	
	public static String trainProject(String url,String arg){
		String result=null;
		 CloseableHttpClient httpclient = HttpClients.createDefault(); 
	      try { 
	          HttpPost httppost = new HttpPost(url); 
	          RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
	          httppost.setConfig(requestConfig);
	         
	          StringBody workerId = new StringBody(arg, ContentType.TEXT_PLAIN); 
	          HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("idnum", workerId).build(); 
	          httppost.setEntity(reqEntity); 
	          //System.out.println("executing request " + httppost.getRequestLine()); 
	          CloseableHttpResponse response = httpclient.execute(httppost); 
	          try { 
	              //System.out.println(response.getStatusLine()); 
	              HttpEntity resEntity = response.getEntity(); 
	              if (resEntity != null) { 
	                  String responseEntityStr = EntityUtils.toString(response.getEntity());
	                  result=responseEntityStr;
	                  if("null".equals(result)){
	                	  return null;
	                  }
	                  //System.out.println(responseEntityStr);
	                  //System.out.println("Response content length: " + resEntity.getContentLength()); 
	              } 
	              EntityUtils.consume(resEntity); 
	          } finally { 
	              response.close(); 
	          } 
	      } catch (ClientProtocolException e) { 
	          e.printStackTrace(); 
	      } catch (IOException e) { 
	          e.printStackTrace(); 
	      } finally { 
	          try { 
	              httpclient.close(); 
	          } catch (IOException e) { 
	              e.printStackTrace(); 
	          } 
	      } 
     return result;
	}		
	public static String allFea(String url){
		String result=null;
		 CloseableHttpClient httpclient = HttpClients.createDefault(); 
	      try { 
	          HttpPost httppost = new HttpPost(url); 
	          RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
	          httppost.setConfig(requestConfig);
	          HttpEntity reqEntity = MultipartEntityBuilder.create().build(); 
	          httppost.setEntity(reqEntity); 
	          //System.out.println("executing request " + httppost.getRequestLine()); 
	          CloseableHttpResponse response = httpclient.execute(httppost); 
	          try { 
	              //System.out.println(response.getStatusLine()); 
	              HttpEntity resEntity = response.getEntity(); 
	              if (resEntity != null) { 
	                  String responseEntityStr = EntityUtils.toString(response.getEntity());
	                  result=responseEntityStr;
	                  if("null".equals(result)){
	                	  JacobDemo.readString("您今天没有培训任务");
	                	  return null;
	                  }
	              } 
	              EntityUtils.consume(resEntity); 
	          } finally { 
	              response.close(); 
	          } 
	      } catch (ClientProtocolException e) { 
	          e.printStackTrace(); 
	      } catch (IOException e) { 
	          e.printStackTrace(); 
	      } finally { 
	          try { 
	              httpclient.close(); 
	          } catch (IOException e) { 
	              e.printStackTrace(); 
	          } 
	      } 
     return result;
	}		
public static void main(String[] args) throws FileNotFoundException, IOException {
	//String imgPath = "G:\\尼古拉\\智能配电房\\公司人员照片\\李军.jpg";  
	 // BufferedImage image = ImageIO.read(new FileInputStream(imgPath));
	//  String fea1="[0, -128, -6, 68, 0, 0, -96, 65, 101, -33, 15, -67, -3, -66, 32, 60, -19, 114, 11, -67, 13, 15, 24, 60, -40, 112, -127, 62, -69, -114, 0, 61, -4, 77, 21, 60, -90, 102, 17, -69, -64, -90, -113, -68, -49, -31, 8, 62, 74, -104, 3, -66, 51, 72, 124, 61, 106, -80, -118, 60, 24, 60, -70, -68, 80, -31, 67, -67, 19, 111, -93, 61, -7, -74, 108, 60, 91, -41, 62, -67, -6, -48, 90, -67, -51, -83, -106, -67, 63, -71, 65, -68, 72, 108, 24, -67, -16, -89, -93, 61, -83, 40, 99, 61, 8, -1, -85, -67, 72, 95, 28, -68, 45, -127, 49, 60, -113, 101, 99, 59, -43, -83, 116, 60, -122, 91, 111, 61, 41, 59, -105, 60, -123, 24, 69, -67, -79, -34, 108, -67, -106, -125, 47, -67, 120, -3, 69, -67, 28, 43, 23, -67, -67, -76, 15, 60, 78, 25, 24, -67, -96, 37, 65, -67, -12, -35, 72, -68, -38, -110, 17, 62, -120, -11, 0, 61, 43, -45, -14, -69, -100, 42, -27, -67, 49, -97, -86, 59, -19, 27, 40, 61, -8, -117, -107, -67, 66, 25, -116, 61, 111, -71, 83, 61, -58, 66, -52, 58, 56, -59, 99, -68, -8, -70, -60, -67, 15, 103, -105, -67, 92, -96, -64, -67, 16, 19, 21, -68, -109, 42, 32, 61, 66, 10, 88, 59, -17, 27, -126, -67, 75, -1, -10, -68, 42, -83, 109, 60, 77, 27, 68, -68, 55, 90, -103, -67, 21, 101, -118, 61, -1, -55, -77, -67, 98, 49, -113, 61, -77, 63, -112, 61, -62, 56, -45, 59, 53, 54, 33, -68, 72, 2, -109, -68, -55, -115, -111, -67, -19, -106, 1, -67, -46, -45, 87, -69, -35, -45, -26, -68, 30, 57, 127, -67, 37, -85, 64, -68, 56, 61, 41, -67, 68, 67, -105, -67, -101, -114, -68, 61, -78, -117, 89, -67, -67, -82, -36, -67, -99, 73, 86, -67, -72, 87, -26, -67, -1, -86, 33, 60, -87, -45, 124, 61, 8, 90, -80, -67, 14, -6, -49, -67, 110, -47, 62, 62, -74, 117, 101, 59, -83, 14, 59, 61, -114, 27, -95, 61, 116, 62, -125, -67, -95, 60, -78, -67, 56, 37, 72, -67, 70, 117, -31, 61, -69, 77, -33, 60, 64, -29, 98, 61, 21, -18, 64, -68, 69, -63, -46, 61, 93, -5, -43, 59, 48, -74, -83, 61, -33, -114, -57, -67, 71, 6, 41, 61, 69, -38, -109, 60, -64, -125, -25, 59, -11, 47, -118, -71, 19, 91, -56, -67, 10, -115, -39, -67, -44, -71, -113, 61, -74, 25, -102, 60, 73, -31, -107, 61, 87, -40, -116, 61, 24, -118, -70, -68, -107, 92, -81, -67, -36, -41, -85, -68, 57, -123, 13, 60, -24, 35, 44, 62, 40, 35, 12, 62, -81, 57, -111, 60, -66, 48, 25, -66, -115, 4, 52, 61, -16, -82, 90, -67, 106, -118, 104, -67, -109, -44, 20, -67, -110, 118, -96, -67, -24, -69, -7, -67, 29, 12, 77, 61, -73, -9, 38, -67, 99, -51, 85, -67, -61, -73, -123, -67, -29, -59, -77, 61, -27, -113, 81, 61, 84, 35, 46, -67, 97, -100, 35, -68, 14, 111, 28, -68, 71, -93, -106, 60, 85, 105, 125, 61, 34, -16, -125, -67, 97, 98, -109, 61, -123, 48, -23, -68, -101, 87, -26, -67, 67, 18, 73, -68, 64, 75, 38, -67, -7, 97, 30, -67, -8, 35, 122, 60, -81, 45, -118, -67, 12, -119, 38, 60, 40, 57, 45, -68, 96, -28, -89, -67, 76, 59, -72, -68, 35, 22, 65, -66, -103, -86, -107, 61, 26, 51, 59, 61, -30, 42, 39, 61, -41, -40, -111, 61, 71, -75, 67, 61, 119, 98, 48, 60, -114, 49, -120, 61, -114, -45, -109, -68, -75, 41, 81, 60, -8, -123, 79, 61, 101, 50, 53, 60, -39, 61, 85, -67, -113, 125, 109, 60, -118, -2, 57, -67, 116, 115, -77, -68, 99, 107, 31, 60, -80, 109, 5, 60, -41, 122, -68, 61, -90, 89, -113, -67, 66, -16, -121, -67, -12, -18, -128, -68, -125, -72, 4, -67, -94, -4, 110, -69, 42, 18, -105, -70, -25, 121, 104, 60, -123, 4, -119, -67, -4, -123, 106, -70, 96, 92, -35, -68, 12, -109, 87, -67, 119, -112, 21, 62, -104, 10, 5, 61, 0, -13, -15, -67, -59, -50, 37, 61, 8, -3, -101, -67, 89, 37, -74, 61, 121, -55, -79, -68, -15, 123, 79, -67, 107, -40, -35, -69, -115, -19, -97, 61, 62, 103, -111, -67, -116, 77, 72, 61, 84, 45, -41, 61, 80, -57, -53, -68, 72, -56, -83, 61, 81, 98, -28, -68, 67, 78, 35, -68, 43, 112, 8, 61, -40, 82, 26, 61, -20, 86, -105, 61, -1, -36, 70, 61, 76, -40, -96, 61, -28, 29, -14, 61, -118, -62, 78, -68, 1, -13, 109, 61, -60, -54, 74, 61, -2, -69, -21, -69, -13, 74, 122, -67, 81, 55, 46, 60, 70, -62, 94, 60, -7, 13, 106, 61, -124, 30, 54, 61, -96, -10, -116, 60, -24, 91, -128, 61, -88, -15, -109, -67, 102, -9, 104, 61, -99, 93, -32, 61, -64, -47, -12, -67, -117, -115, 59, -67, -20, -120, -17, 60, 24, -23, 39, 61, -2, -26, -33, -67, -102, -101, -15, -68, 29, 42, -126, -68, 50, 41, -66, 61, 32, 90, 83, 61, -45, -93, -107, -67, -61, 65, -27, -67, 70, -84, -59, -68, 102, 60, 50, 61, -110, -128, 12, 62, -33, 92, 2, 61, -30, -114, -41, -68, 0, 29, 18, 59, 27, 13, -101, 61, 120, -105, 112, -68, -46, 96, -16, -68, 43, -42, 71, 60, 17, -30, 114, 59, -60, -114, -120, 61, 15, -15, 15, -67, 71, 88, 29, 61, 79, -106, -30, -67, 97, 50, -33, -67, 92, -48, 42, -67, 104, -29, -128, -67, 27, 10, 9, 60, 16, 53, -49, -68, -50, 81, -76, -67, -33, 14, 65, -67, 58, 27, 29, -67, -45, 85, -118, -67, 90, 90, -63, 61, -75, -59, -18, 61, 37, 125, 28, 61, -30, -63, -104, -68, -17, -93, -74, 61]";
	//  String fea2="[0, -128, -6, 68, 0, 0, -96, 65, 101, -33, 15, -67, -3, -66, 32, 60, -19, 114, 11, -67, 13, 15, 24, 60, -40, 112, -127, 62, -69, -114, 0, 61, -4, 77, 21, 60, -90, 102, 17, -69, -64, -90, -113, -68, -49, -31, 8, 62, 74, -104, 3, -66, 51, 72, 124, 61, 106, -80, -118, 60, 24, 60, -70, -68, 80, -31, 67, -67, 19, 111, -93, 61, -7, -74, 108, 60, 91, -41, 62, -67, -6, -48, 90, -67, -51, -83, -106, -67, 63, -71, 65, -68, 72, 108, 24, -67, -16, -89, -93, 61, -83, 40, 99, 61, 8, -1, -85, -67, 72, 95, 28, -68, 45, -127, 49, 60, -113, 101, 99, 59, -43, -83, 116, 60, -122, 91, 111, 61, 41, 59, -105, 60, -123, 24, 69, -67, -79, -34, 108, -67, -106, -125, 47, -67, 120, -3, 69, -67, 28, 43, 23, -67, -67, -76, 15, 60, 78, 25, 24, -67, -96, 37, 65, -67, -12, -35, 72, -68, -38, -110, 17, 62, -120, -11, 0, 61, 43, -45, -14, -69, -100, 42, -27, -67, 49, -97, -86, 59, -19, 27, 40, 61, -8, -117, -107, -67, 66, 25, -116, 61, 111, -71, 83, 61, -58, 66, -52, 58, 56, -59, 99, -68, -8, -70, -60, -67, 15, 103, -105, -67, 92, -96, -64, -67, 16, 19, 21, -68, -109, 42, 32, 61, 66, 10, 88, 59, -17, 27, -126, -67, 75, -1, -10, -68, 42, -83, 109, 60, 77, 27, 68, -68, 55, 90, -103, -67, 21, 101, -118, 61, -1, -55, -77, -67, 98, 49, -113, 61, -77, 63, -112, 61, -62, 56, -45, 59, 53, 54, 33, -68, 72, 2, -109, -68, -55, -115, -111, -67, -19, -106, 1, -67, -46, -45, 87, -69, -35, -45, -26, -68, 30, 57, 127, -67, 37, -85, 64, -68, 56, 61, 41, -67, 68, 67, -105, -67, -101, -114, -68, 61, -78, -117, 89, -67, -67, -82, -36, -67, -99, 73, 86, -67, -72, 87, -26, -67, -1, -86, 33, 60, -87, -45, 124, 61, 8, 90, -80, -67, 14, -6, -49, -67, 110, -47, 62, 62, -74, 117, 101, 59, -83, 14, 59, 61, -114, 27, -95, 61, 116, 62, -125, -67, -95, 60, -78, -67, 56, 37, 72, -67, 70, 117, -31, 61, -69, 77, -33, 60, 64, -29, 98, 61, 21, -18, 64, -68, 69, -63, -46, 61, 93, -5, -43, 59, 48, -74, -83, 61, -33, -114, -57, -67, 71, 6, 41, 61, 69, -38, -109, 60, -64, -125, -25, 59, -11, 47, -118, -71, 19, 91, -56, -67, 10, -115, -39, -67, -44, -71, -113, 61, -74, 25, -102, 60, 73, -31, -107, 61, 87, -40, -116, 61, 24, -118, -70, -68, -107, 92, -81, -67, -36, -41, -85, -68, 57, -123, 13, 60, -24, 35, 44, 62, 40, 35, 12, 62, -81, 57, -111, 60, -66, 48, 25, -66, -115, 4, 52, 61, -16, -82, 90, -67, 106, -118, 104, -67, -109, -44, 20, -67, -110, 118, -96, -67, -24, -69, -7, -67, 29, 12, 77, 61, -73, -9, 38, -67, 99, -51, 85, -67, -61, -73, -123, -67, -29, -59, -77, 61, -27, -113, 81, 61, 84, 35, 46, -67, 97, -100, 35, -68, 14, 111, 28, -68, 71, -93, -106, 60, 85, 105, 125, 61, 34, -16, -125, -67, 97, 98, -109, 61, -123, 48, -23, -68, -101, 87, -26, -67, 67, 18, 73, -68, 64, 75, 38, -67, -7, 97, 30, -67, -8, 35, 122, 60, -81, 45, -118, -67, 12, -119, 38, 60, 40, 57, 45, -68, 96, -28, -89, -67, 76, 59, -72, -68, 35, 22, 65, -66, -103, -86, -107, 61, 26, 51, 59, 61, -30, 42, 39, 61, -41, -40, -111, 61, 71, -75, 67, 61, 119, 98, 48, 60, -114, 49, -120, 61, -114, -45, -109, -68, -75, 41, 81, 60, -8, -123, 79, 61, 101, 50, 53, 60, -39, 61, 85, -67, -113, 125, 109, 60, -118, -2, 57, -67, 116, 115, -77, -68, 99, 107, 31, 60, -80, 109, 5, 60, -41, 122, -68, 61, -90, 89, -113, -67, 66, -16, -121, -67, -12, -18, -128, -68, -125, -72, 4, -67, -94, -4, 110, -69, 42, 18, -105, -70, -25, 121, 104, 60, -123, 4, -119, -67, -4, -123, 106, -70, 96, 92, -35, -68, 12, -109, 87, -67, 119, -112, 21, 62, -104, 10, 5, 61, 0, -13, -15, -67, -59, -50, 37, 61, 8, -3, -101, -67, 89, 37, -74, 61, 121, -55, -79, -68, -15, 123, 79, -67, 107, -40, -35, -69, -115, -19, -97, 61, 62, 103, -111, -67, -116, 77, 72, 61, 84, 45, -41, 61, 80, -57, -53, -68, 72, -56, -83, 61, 81, 98, -28, -68, 67, 78, 35, -68, 43, 112, 8, 61, -40, 82, 26, 61, -20, 86, -105, 61, -1, -36, 70, 61, 76, -40, -96, 61, -28, 29, -14, 61, -118, -62, 78, -68, 1, -13, 109, 61, -60, -54, 74, 61, -2, -69, -21, -69, -13, 74, 122, -67, 81, 55, 46, 60, 70, -62, 94, 60, -7, 13, 106, 61, -124, 30, 54, 61, -96, -10, -116, 60, -24, 91, -128, 61, -88, -15, -109, -67, 102, -9, 104, 61, -99, 93, -32, 61, -64, -47, -12, -67, -117, -115, 59, -67, -20, -120, -17, 60, 24, -23, 39, 61, -2, -26, -33, -67, -102, -101, -15, -68, 29, 42, -126, -68, 50, 41, -66, 61, 32, 90, 83, 61, -45, -93, -107, -67, -61, 65, -27, -67, 70, -84, -59, -68, 102, 60, 50, 61, -110, -128, 12, 62, -33, 92, 2, 61, -30, -114, -41, -68, 0, 29, 18, 59, 27, 13, -101, 61, 120, -105, 112, -68, -46, 96, -16, -68, 43, -42, 71, 60, 17, -30, 114, 59, -60, -114, -120, 61, 15, -15, 15, -67, 71, 88, 29, 61, 79, -106, -30, -67, 97, 50, -33, -67, 92, -48, 42, -67, 104, -29, -128, -67, 27, 10, 9, 60, 16, 53, -49, -68, -50, 81, -76, -67, -33, 14, 65, -67, 58, 27, 29, -67, -45, 85, -118, -67, 90, 90, -63, 61, -75, -59, -18, 61, 37, 125, 28, 61, -30, -63, -104, -68, -17, -93, -74, 61]";

//	  faceSim("http://jxust-db:8888/facereg/faceFeaFeaSimilarity",fea1,fea2);
	  
	 // allFea("http://172.26.20.63:9090/common/outlineservice/faceFeature");
	System.out.println(ImgImgSim("http://localhost:8088/secondfacereg/faceSimilarity",ImageIO.read(new File("d:/image.jpg")),ImageIO.read(new File("d:/image1.jpg"))));
}
}
