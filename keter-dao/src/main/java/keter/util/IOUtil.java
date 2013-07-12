package keter.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.sound.sampled.AudioFormat.Encoding;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
/**
 * 
 */
public class IOUtil {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(IOUtil.class);

    /**
     * <p>
     * 获取文件输入流
     * </p>
     * 
     * @param fileName
     * @return
     * @author: 顾力行 - gulixing@msn.com
     * @date: Created on 2009-9-14 上午09:48:28
     */
    public static InputStream getInputStream(String fileName) {
        FileSystemResource fr = new FileSystemResource(fileName);
        try {
            return fr.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>
     * Method ：getInputStream
     * <p>
     * Description : 功能描述
     * 
     * @param fileUri
     * @return
     * @author 顾力行 - gulixing@msn.com
     * @version 1.0.0
     */
    public static Properties getConfigProps(String fileUri) {
        Properties connProperties = new Properties();
        InputStream iss = getInputStream(fileUri);
        try {
            connProperties.load(iss);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connProperties;
    }

    /**
     * <p>
     * 获取文件输入BufferedReader流
     * </p>
     * 
     * @param fileName
     * @return
     * @author: 顾力行 - gulixing@msn.com
     * @date: Created on 2009-9-14 上午09:48:28
     */
    public static BufferedReader getBufferedReader(String fileName) {
        FileSystemResource fr = new FileSystemResource(fileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(fr.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return br;
    }

    /**
     * <p>
     * 输出文件
     * </p>
     * 
     * @param fileURI
     * @param list
     * @param isOverWritable
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @author: 顾力行 - gulixing@msn.com
     * @date: Created on 2009-9-15 上午11:02:12
     */
    public static void outputFile(String fileURI, List list, boolean isOverWritable)
            throws UnsupportedEncodingException, IOException {
        logger.error(fileURI);
        File file = new File(fileURI);
        if (isOverWritable == true && file.exists()) {
            file.delete();
        } else {
            file.createNewFile();
        }
        FileOutputStream out = new FileOutputStream(file, true);
        Iterator itr = list.iterator();
        while (itr.hasNext()) {
            out.write((itr.next().toString() + "\n").getBytes("GBK"));
        }
        out.flush();
        out.close();
    }
    
    public static void outputFile(String fileURI, boolean isOverWritable)
    throws UnsupportedEncodingException, IOException {
        
        File file = new File(fileURI);
        if (isOverWritable == true && file.exists()) {
            file.delete();
        } else {
            file.createNewFile();
        }
        FileOutputStream out = new FileOutputStream(file, true);
        out.flush();
        out.close();
    }
    
        
    /**
     * <p>Method ：writeInputStream
     * <p>Description : 将InputSrtram输出到OutputStream
     *
     * @param in
     * @param out
     * @throws IOException 
     * @author  顾力行-gulixing@msn.com
     *<p>
     *--------------------------------------------------------------<br>
     * 修改履历：<br>
     *        <li> Jun 2, 2011，gulixing@msn.com，创建方法；<br>
     *--------------------------------------------------------------<br>
     *</p>
     */
    public static void writeInputStream(InputStream in,OutputStream out) throws IOException {
        byte[] bb = new byte[1024 * 10];//10Kb
        int a = -1;
        while ((a = in.read(bb)) != -1) {
            out.write(bb, 0, a);
        }
        in.close();
        out.close();
    }
    
    /**
     * <p>Method ：convertInputStreamToString
     * <p>Description : 将inputStream转换成字符串
     *
     * @param is
     * @return 
     * @author  顾力行-gulixing@msn.com
     *<p>
     *--------------------------------------------------------------<br>
     * 修改履历：<br>
     *        <li> Jun 18, 2011，gulixing@msn.com，创建方法；<br>
     *--------------------------------------------------------------<br>
     *</p>
     */
    public static String convertInputStreamToString(InputStream is,String encoding){
        StringWriter writer = new StringWriter();
        try {
              IOUtils.copy(is, writer, encoding);                
        } catch (IOException e) {
            logger.error(e);
        }
        return writer.toString();
    }
    
    public static String convertInputStreamToString(InputStream is){
        if(is == null){
        	return null;
        }
    	StringWriter writer = new StringWriter();
    	try {
    		IOUtils.copy(is, writer, "utf-8");                
    	} catch (IOException e) {
    		logger.error(e);
    	}
    	return writer.toString();
    }
    
    public static final int StreamFlushBufferSzie=100;//buffer size= 1K  
    public static OutputStream castToOStream(InputStream fis, int bufferSize) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream bos = new BufferedOutputStream(baos);
        int ch;
        int i = 0;
        while ((ch = bis.read()) != -1) {
            bos.write(ch);
            if (i++ == bufferSize) {
                bos.flush();
                i = 0;
            }
        }
        bos.flush(); // 提交文件流，很关键
        bis.close();
        return baos;
    }
        
    /**
     * @throws IOException 
     * 
     */
    public static void main(String[] args) throws IOException {
        InputStream in = getInputStream("e:/Program Files/PerforceWeb/i18nnotes.txt");
        OutputStream out = new FileOutputStream("e:/haha.haha");
        writeInputStream(in, out);
//        Properties connProperties = IOUtil.getConfigProps(ConfigPath.projectRoot + "/test/conf/DB.properties");
//        logger.info(connProperties.get("UserName"));
    }
}
