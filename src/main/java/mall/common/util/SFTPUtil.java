package mall.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

 
//import cres.com.context.ApplicationContextProvider;
 
public class SFTPUtil{
     
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());   
    
    private static Session session = null;
    
    private static Channel channel = null;
 
    private static ChannelSftp channelSftp = null;
    
    @Autowired
	private static Environment environment;
    
    
    /**
     * 서버와 연결에 필요한 값들을 가져와 초기화 시킴
     *
     * @param host
     *            서버 주소
     * @param userName
     *            접속에 사용될 아이디
     * @param password
     *            비밀번호
     * @param port
     *            포트번호
     */
    public static void init(String host, String userName, String password, int port) {
        JSch jsch = new JSch();
        String hostIp = "";
        try {
        	try {
				InetAddress byName = InetAddress.getByName(host);
				hostIp = byName.getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
            session = jsch.getSession(userName, hostIp, port);
            session.setPassword(password);
 
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
 
            channel = session.openChannel("sftp");
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
 
        channelSftp = (ChannelSftp) channel;
 
    }
 
    /**
     * 단일 파일을 업로드
     *
     * @param dir
     *            저장시킬 주소(서버)
     * @param file
     *            저장할 파일 경로
     */
    public static String upload(String dir, MultipartFile mulfile,String vewInf, Boolean bOriginalFilename) {
    	//String realPath = environment.getRequiredProperty("sftp.FILEPATH");
    	
    	boolean result = true;
        FileInputStream in = null;
        
        // 년월일시분초밀리초 파일명 생성		
  		GregorianCalendar gc = new GregorianCalendar();
  		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssS"); 
  		InputStream is = null;
  		Date d = gc.getTime(); 

  		String originalFileName = mulfile.getOriginalFilename();
  		String fileExtsn = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
  		
  		// 파일저장위치 및 이름 설정
  		String savePath = "/root/img_server/mallimage/"+dir.replace("\\", "/")+"/upload/" +vewInf;//+ File.separator;
  		System.out.println(dir.replace("\\", "/"));
  		//String savePath = ("http://www.cjfls.co.kr/").replace("\\", "/") + "upload/" + vewInf + File.separator;
  		 
  		String saveFileName = savePath +"/"+ sf.format(d) + fileExtsn;
  		
  		if(bOriginalFilename){
  			saveFileName = savePath + originalFileName;
  		}
  		
        
        try {
        	
        	//multipartfile to file
	    	 File convFile = new File(mulfile.getOriginalFilename());
	    	 convFile.createNewFile();
	    	 FileOutputStream fos = new FileOutputStream(convFile);
	    	 fos.write(mulfile.getBytes());
	    	 fos.close();
	    	 
	    	File file = convFile;//new File(localFilePath); // File 객체
	    	 
	    	 
            //File file = new File(filePath);
            String fileName = file.getName();
            //fileName = URLEncoder.encode(fileName,"EUC-KR");
            
            in = new FileInputStream(file);           
            
            SftpATTRS attrs=null;
            System.out.println("==================================");
            System.out.println("dir==="+dir);
            System.out.println("saveFileName==="+saveFileName);
            System.out.println("savePath==="+savePath);
            System.out.println("==================================");
            try {
            	attrs = channelSftp.stat(savePath);
            } catch (Exception e) {
                System.out.println(savePath+" not found");
            }

            if (attrs != null) {
                System.out.println("Directory exists IsDir="+attrs.isDir());
            } else {
                System.out.println("Creating dir "+savePath);
                channelSftp.mkdir(savePath);
            }
            
            channelSftp.cd(savePath);
            channelSftp.put(in, saveFileName);
            
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            return null;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        
        //return result;
        return sf.format(d) + fileExtsn;
    }
 
    /**
     * 단일 파일 다운로드
     *
     * @param dir
     *            저장할 경로(서버)
     * @param downloadFileName
     *            다운로드할 파일
     * @param path
     *            저장될 공간
     */
    public static void download(String dir, String downloadFileName, String path) {
        InputStream in = null;
        FileOutputStream out = null;
        
        FileInputStream callin = null;
        
        try {
            channelSftp.cd(dir);
            in = channelSftp.get(downloadFileName);
        } catch (SftpException e) {
            e.printStackTrace();
        }
 
        try {
            out = new FileOutputStream(new File(path));
            int i;
 
            while ((i = in.read()) != -1) {
                out.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
 
        }
        
        //return callin;
 
    }
 
    /**
     * 서버와의 연결을 끊는다.
     */
    public void disconnection() {
        channelSftp.quit();
 
    }
    
    
    /**
     * 단일 파일 즉시 업로드
     * 
     * @param sftpHost
     *                 SFTP 접속 주소(host:IP)
     * @param sftpUser
     *                 SFTP 접속 USER
     * @param sftpPass
     *                 SFTP 접속 패스워드
     * @param sftpPort
     *                 SFTP 접속 포트
     * @param sftpWorkingDir
     *                 SFTP 작업 경로
     * @param fileFullPath
     *                 업로드할 파일 경로
     */
    public static boolean directUpload(	//사용안함
             String sftpHost, String sftpUser, String sftpPass, 
             int sftpPort, String sftpWorkingDir, String fileFullPath) {
        
        boolean result = true;
        
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        System.out.println("preparing the host information for sftp.");
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(sftpUser, sftpHost, sftpPort);
            session.setPassword(sftpPass);
            
            // Host 연결.
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            
            // sftp 채널 연결.
            channel = session.openChannel("sftp");
            channel.connect();
            
            // 파일 업로드 처리.
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(sftpWorkingDir);
            File f = new File(fileFullPath);
            String fileName = f.getName();
            //fileName = URLEncoder.encode(f.getName(),"UTF-8");
            channelSftp.put(new FileInputStream(f), fileName);
        } catch (Exception ex) {
             System.out.println(ex.toString());
             System.out.println("Exception found while tranfer the response.");
             result = false;
        } finally {
            // sftp 채널을 닫음.
            channelSftp.exit();
            
            // 채널 연결 해제.
            channel.disconnect();
            
            // 호스트 세션 종료.
            session.disconnect();
        }
        
        return result;
    }    
}