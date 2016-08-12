package com.common.web.struts;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @ClassName��BaseAction
 * @Description���ṩ��� request��response
 * @author��zp
 * @Modifier��zp
 * @version 1.0
 */

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	private ObjectOutputStream objectOutputStream;

	private PrintWriter printWriter;

	public BaseAction() {
		setRequest(ServletActionContext.getRequest());
		setResponse(ServletActionContext.getResponse());
		setSession(ServletActionContext.getRequest().getSession());
	}

	public PrintWriter getPrintWriter() throws IOException {
		if (this.printWriter == null) {
			response.setCharacterEncoding(HttpConfiguration.HTTP_CONTENT_CHARSET);
			response.setContentType(HttpConfiguration.ContentType);
			printWriter = response.getWriter();
		}
		return printWriter;
	}

	public void setPrintWriter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	public ObjectOutputStream getObjectOutputStream() throws IOException {
		if (this.objectOutputStream == null) {
			objectOutputStream = new ObjectOutputStream(
					response.getOutputStream());
		}
		return objectOutputStream;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HttpServletRequest getRequest() {
		request.setAttribute("messages", "");
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void destroy() throws IOException {
		if (this.objectOutputStream != null) {
			this.objectOutputStream.flush();
			this.objectOutputStream.close();
		}
		if (this.printWriter != null) {
			this.printWriter.flush();
			this.printWriter.close();
		}
	}
	
	protected String getRequestPostMsg(HttpServletRequest request)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		int size = request.getContentLength();
		InputStream is = request.getInputStream();
		byte[] reqBodyBytes = readBytes(is, size);
		String res = new String(reqBodyBytes);
		if (res != null && res.length() > 0)
			return res;

		return null;
	}

	public static final byte[] readBytes(InputStream is, int contentLen) {
		if (contentLen > 0) {
			int readLen = 0;
			int readLengthThisTime = 0;
			byte[] message = new byte[contentLen];
			try {
				while (readLen != contentLen) {
					readLengthThisTime = is.read(message, readLen, contentLen
							- readLen);
					if (readLengthThisTime == -1) {// Should not happen.
						break;
					}
					readLen += readLengthThisTime;
				}
				return message;
			} catch (IOException e) {
				// Ignore
				// e.printStackTrace();
			}
		}
		return new byte[] {};
	}

}
