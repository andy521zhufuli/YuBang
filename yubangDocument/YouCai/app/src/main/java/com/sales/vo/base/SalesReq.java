package com.sales.vo.base;

public class SalesReq  extends SalesBaseMsg {
	public SalesReq(String msgtype) {
		super(msgtype);
		// TODO Auto-generated constructor stub
	}

	// �豸ID
	private String deviceid = null;
	
	// �ͻ��˰汾��
	private String appversion = null;
	
	// ����ϵͳ
	private String os = null;
	
	// �ն����ͣ� appΪ�ͻ��ˣ� browserΪ�����
	private String apptype = null;
	
	// ���ĸ�ʽ��Ĭ��Ϊjson
	private String format = SalesMsgUtils.FORMAT_JSON;
	
	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getApptype() {
		return apptype;
	}

	public void setApptype(String apptype) {
		this.apptype = apptype;
	}
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	public Boolean isHtml()
	{
		if (format != null && format.equals(SalesMsgUtils.FORMAT_HTML))
		    return true;
		
		return false;
	}

	public String getJson()
	{
		String jsonStr = SalesMsgUtils.toJson(this);
		return jsonStr;
	}
	
}

