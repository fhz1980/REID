package zkteco.id100com.jni;

public class id100sdk
{
	static  
	{  
		System.loadLibrary("termb");  
	}
	
	public native static int InitComm(int nPort);
	
	public native static int InitCommExt();
	
	public native static int CloseComm();
	
	public native static int Authenticate();
	
	public native static int ReadContent(int nActive);
	
	public native static int GetSAMID(byte[] bufSAMID);
	
	public native static int GetBmpPhoto(String FileName);
	
	public native static int GetSAMStatus();

	public static String readCard() {
		int nRet = 0;
		if (0 >= (nRet = InitCommExt())) {
			//System.out.printf("InitCommExt failed, ret=" + nRet);
			//System.out.printf("\r\n");
			return "no idcard reader!";

		} else {
			// Authenticate succ --> Read_Content succ --> GetBmpPhoto
			//System.out.printf("InitCommExt succ, port=" + nRet);
			//System.out.printf("\r\n");
			if (1 == Authenticate()) {
				int nRetry = 0;
				do {
					nRet = ReadContent(1);
					if (nRet == 1) {
						break;
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					nRetry++;
				} while (nRetry < 10);
				if (1 == nRet) {
					//System.out.println("ReadContent succ");
					if (1 == GetBmpPhoto("xp.wlt")) {
						//System.out.println("GetBmpPhoto success filename= zp.bmp");
					} else {
						
						//System.out.println("GetBmpPhoto fail");
						return "GetBmpPhoto fail";
					}
				} else {
					//System.out.println("ReadContent fail");
					return "ReadContent fail";
				}
			} else {
				//System.out.printf("Authenticate fail");
				//System.out.printf("\r\n");
				return "no card!";
			}
			CloseComm();
		}
		return "OK";
	}
	
	public static void main(String[] args) {
		System.out.println(readCard());
	}

}