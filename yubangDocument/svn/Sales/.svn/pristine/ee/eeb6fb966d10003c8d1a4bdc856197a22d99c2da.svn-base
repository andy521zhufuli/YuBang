package qtp.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

public class HttpsTester {
	
	  /*
 private static final String password = "123456";  
    // 密钥库  
   	private static final String keyStorePath = "d:\\load\\testuser1.p12";  
    // 信任库  
    	private static final String trustStorePath = "d:\\load\\truststore.jks";  
		private static final String TARGET_HTTPS_SERVER = "10.12.3.120";
	private static final int TARGET_HTTPS_PORT = 8443;
*/	
     private static final String password = "123456";  
//    // 密钥库
	private static final String keyStorePath = "d:\\tools\\SecureCRT\\download\\testuser1.p12";  
//    // 信任库  
	private static final String trustStorePath = "d:\\tools\\SecureCRT\\download\\truststore.jks";  
	private static final String TARGET_HTTPS_SERVER = "119.29.82.148";
	private static final int TARGET_HTTPS_PORT = 8443;
	public static void doGet(String url) throws Exception
	{
		//URL keyurl = new URL(keyStorePath);
		//URL trustUrl= new URL(trustStorePath);
		AuthSSLProtocolSocketFactory sslFactory = new AuthSSLProtocolSocketFactory(keyStorePath, password, trustStorePath, password);
		
		Socket socket = sslFactory.createSocket(TARGET_HTTPS_SERVER, TARGET_HTTPS_PORT);
		try {
	         Writer out = new OutputStreamWriter(
	            socket.getOutputStream(), "UTF-8");
	         out.write("GET "+ url +" HTTP/1.1\r\n");  
	         out.write("Host: " + TARGET_HTTPS_SERVER + ":" + 
	             TARGET_HTTPS_PORT + "\r\n");  
	         out.write("\r\n");  
	         out.flush();  
	         BufferedReader in = new BufferedReader(
	            new InputStreamReader(socket.getInputStream(), "UTF-8"));
	         String line = null;
	         while ((line = in.readLine()) != null) {
	            System.out.println(line);
	         }
	       } finally {
	         socket.close(); 
	       }
	}
	
	public static void doPost(String url, String post) throws Exception
	{
		AuthSSLProtocolSocketFactory sslFactory = new AuthSSLProtocolSocketFactory(keyStorePath, password, trustStorePath, password);
		
		Socket socket = sslFactory.createSocket(TARGET_HTTPS_SERVER, TARGET_HTTPS_PORT);
		try {
	         Writer out = new OutputStreamWriter(
	            socket.getOutputStream(), "UTF-8");
	         out.write("POST "+ url +" HTTP/1.1\r\n");  
	         out.write("Host: " + TARGET_HTTPS_SERVER + ":" + 
	             TARGET_HTTPS_PORT + "\r\n");  
	         out.write("Agent: SSL-TEST\r\n");  
	         out.write("Content-Length: "+post.length()+"\r\n");  
	         out.write("\r\n");  
	         out.write(post);
	         out.flush();  
	         BufferedReader in = new BufferedReader(
	            new InputStreamReader(socket.getInputStream(), "UTF-8"));
	         String line = null;
	         while ((line = in.readLine()) != null) {
	            System.out.println(line);
	         }
	       } finally {
	         socket.close(); 
	       }
	}
	
	
	
	public static void main(String[] args) throws Exception {  
		System.setProperty("javax.net.debug", "all");
		System.setProperty("https.protocols", "TLSv1");
		//doGet("/");
			doGet("/Sales/temp/index.html");
	}

}
