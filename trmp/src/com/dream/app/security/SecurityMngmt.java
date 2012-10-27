/**
 * 
 */
package com.dream.app.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.database.dao.DAO;

/**
 * @author divine
 *
 */
public class SecurityMngmt implements SecurityMngmtInterface {
	private Logger log;
	public SecurityMngmt(){
		log = Logger.getLogger(SecurityMngmt.class);
	}
	public void compareDiskSerialNm() {
		
		String disNO = getDiskNO();
		String osName = System.getProperty("os.name");
		if(osName.indexOf("Windows") > -1){
			File file = new File("C:\\WINDOWS\\sys.conf");
			if(file.exists() == false){
				log.error("exit");
				System.exit(0);
			}			
			try {
				FileInputStream fileIn = new FileInputStream(file);
				byte[] buff = new byte[(int)file.length()];
				while(fileIn.read(buff) > -1){}
				String buffStr = new String(buff);
				if(buffStr.indexOf(getMD5(disNO.getBytes())) < 0){
					log.error("exit");
					System.exit(0);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}

	public void compareMac() {
		String mac = getWindowsMACAddress();
		String osName = System.getProperty("os.name");
		if(osName.indexOf("Windows") > -1){
			File file = new File("C:\\WINDOWS\\sys.conf");
			if(file.exists() == false){
				log.error("exit");
				System.exit(0);
			}			
			try {
				FileInputStream fileIn = new FileInputStream(file);
				byte[] buff = new byte[(int)file.length()];
				while(fileIn.read(buff) > -1){}
				String buffStr = new String(buff);
				if(buffStr.indexOf(getMD5(mac.getBytes())) < 0){
					log.error("exit");
					System.exit(0);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}

	public boolean compareUserCounts(DAO dao, int counts) {
		return false;
	}
	
	private String getDiskNO(){

		StringBuffer systemPathBuff = new StringBuffer("");
		String osName = System.getProperty("os.name");
		if(osName.indexOf("Windows") > -1)
			systemPathBuff.append("c:\\WINDOWS\\system32\\cmd.exe"); 
		else if(osName.indexOf("NT") > -1)
			systemPathBuff.append("c:\\WINDOWS\\command.com"); 
		return getDiskNOOnWindows(systemPathBuff.toString());
	}

	private String getDiskNOOnWindows(String command) {

		String disNO = "";
		Process pro = null;
		
		try {
			pro = Runtime.getRuntime().exec(command.toString() + " cmd /c dir");
			pro.getInputStream();
			InputStream is = pro.getInputStream(); 
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
            
            String message = br.readLine(); 
            int index = -1; 
            while (message != null) { 
                if ((index = message.indexOf("卷的序列号是")) > 0) {
                	disNO = message.substring(index + 6).trim(); 
                    break; 
                } 
                // 读取下一行 
                message = br.readLine(); 
            } 
            return disNO;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return disNO;
	}
	
	private String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	private String getWindowsMACAddress() {
        String mac = null;   
        BufferedReader bufferedReader = null;   
        Process process = null;   
        try {   
            process = Runtime.getRuntime().exec("ipconfig /all");
            bufferedReader = new BufferedReader(new InputStreamReader(process   
                    .getInputStream()));   
            String line = null;   
            int index = -1;   
            while ((line = bufferedReader.readLine()) != null) {
                index = line.toLowerCase().indexOf("physical address");
                if (index >= 0) {
                    index = line.indexOf(":");
                    if (index>=0) {   
                        mac = line.substring(index + 1).trim();
                    }   
                    break;   
                }   
            }   
        } catch (IOException e) {
            e.printStackTrace();   
        } finally {   
            try {   
                if (bufferedReader != null) {   
                    bufferedReader.close();   
                }   
            } catch (IOException e1) {   
                e1.printStackTrace();   
            }   
            bufferedReader = null;   
            process = null;   
        }   
  
        return mac;   
    }   

	
	private String getUnixMACAddress() {   
        String mac = null;   
        BufferedReader bufferedReader = null;   
        Process process = null;   
        try {   
            process = Runtime.getRuntime().exec("ifconfig eth0");
            bufferedReader = new BufferedReader(new InputStreamReader(process   
                    .getInputStream()));   
            String line = null;   
            int index = -1;   
            while ((line = bufferedReader.readLine()) != null) {   
                index = line.toLowerCase().indexOf("hwaddr");
                if (index >= 0) {
                    mac = line.substring(index +"hwaddr".length()+ 1).trim();
                    break;   
                }   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (bufferedReader != null) {   
                    bufferedReader.close();   
                }   
            } catch (IOException e1) {   
                e1.printStackTrace();   
            }   
            bufferedReader = null;   
            process = null;   
        }   
  
        return mac;   
    }   

	public static void main(String a[]){
		SecurityMngmt sm = new SecurityMngmt();
		String diskNo = sm.getDiskNO();
		String disNoMd5 = sm.getMD5("D097-6BA9".getBytes());
		System.out.println(disNoMd5);
		
		String macStr = sm.getWindowsMACAddress();
		String macStrMd5 = sm.getMD5("00-21-5E-6D-B8-F3".getBytes());
		System.out.println(macStrMd5);
		
	}
	
}
