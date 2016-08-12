package com.sales.vo.base;

import com.google.gson.Gson;
import com.sales.vo.AppConfigReq;
import com.sales.vo.AppConfigResp;
import com.sales.vo.CashOutReq;
import com.sales.vo.CommitCashReq;
import com.sales.vo.CommitCashResp;
import com.sales.vo.ExitLoginReq;
import com.sales.vo.GetAboutInfoReq;
import com.sales.vo.GetAboutInfoResp;
import com.sales.vo.GetAccountInfoReq;
import com.sales.vo.GetAccountInfoResp;
import com.sales.vo.GetBankListReq;
import com.sales.vo.GetBankListResp;
import com.sales.vo.GetCancelOrderPromptReq;
import com.sales.vo.GetCancelOrderPromptResp;
import com.sales.vo.GetCashAccountListReq;
import com.sales.vo.GetCashAccountListResp;
import com.sales.vo.GetCashHistReq;
import com.sales.vo.GetCashHistResp;
import com.sales.vo.GetCashInfoReq;
import com.sales.vo.GetCashInfoResp;
import com.sales.vo.GetContactInfoReq;
import com.sales.vo.GetContactInfoResp;
import com.sales.vo.GetFriendListReq;
import com.sales.vo.GetFriendListResp;
import com.sales.vo.GetHistOrdersReq;
import com.sales.vo.GetHistOrdersResp;
import com.sales.vo.GetOrderReq;
import com.sales.vo.GetOrderResp;
import com.sales.vo.GetVerifyCodeReq;
import com.sales.vo.GetVerifyCodeResp;
import com.sales.vo.LoadGoodsDetailReq;
import com.sales.vo.LoadGoodsDetailResp;
import com.sales.vo.LoadGoodsListReq;
import com.sales.vo.LoadGoodsListResp;
import com.sales.vo.ModifyAddressInfoReq;
import com.sales.vo.ModifyAddressInfoResp;
import com.sales.vo.ModifyCashAccountReq;
import com.sales.vo.ModifyCashAccountResp;
import com.sales.vo.ModifyOrderReq;
import com.sales.vo.ModifyOrderResp;
import com.sales.vo.UploadImgReq;
import com.sales.vo.UploadImgResp;

public class SalesMsgUtils {
	
	public static final String FORMAT_JSON = "json";
	public static final String FORMAT_HTML = "html";
	

	// �����������
	public static SalesBaseMsg CreateRequestMsg(String msgtype)
	{
		if (MSG_TYPES.MSG_APP_CONFIG.equals(msgtype))
			return new AppConfigReq();
		if (MSG_TYPES.MSG_COMMIT_CASH.equals(msgtype))
			return new CommitCashReq();
		if (MSG_TYPES.MSG_GET_ABOUT_INFO.equals(msgtype))
			return new GetAboutInfoReq();
		if (MSG_TYPES.MSG_GET_ACCOUNT_INFO.equals(msgtype))
			return new GetAccountInfoReq();
		if (MSG_TYPES.MSG_GET_BANK_LIST.equals(msgtype))
			return new GetBankListReq();
		if (MSG_TYPES.MSG_GET_CANCEL_ORDER_PROMPT.equals(msgtype))
			return new GetCancelOrderPromptReq();
		if (MSG_TYPES.MSG_GET_CASH_ACCOUNT_LIST.equals(msgtype))
			return new GetCashAccountListReq();
		if (MSG_TYPES.MSG_GET_CASH_HIST_LIST.equals(msgtype))
			return new GetCashHistReq();
		if (MSG_TYPES.MSG_GET_CASH_INFO.equals(msgtype))
			return new GetCashInfoReq();
		if (MSG_TYPES.MSG_GET_CONTACT_INFO.equals(msgtype))
			return new GetContactInfoReq();
		if (MSG_TYPES.MSG_GET_FRIENT_LIST.equals(msgtype))
			return new GetFriendListReq();
		if (MSG_TYPES.MSG_GET_HIST_ORDERS.equals(msgtype))
			return new GetHistOrdersReq();
		if (MSG_TYPES.MSG_GET_ORDER.equals(msgtype))
			return new GetOrderReq();
		if (MSG_TYPES.MSG_GET_VERIFY_CODE.equals(msgtype))
			return new GetVerifyCodeReq();
		if (MSG_TYPES.MSG_LOAD_GOODS_DETAIL.equals(msgtype))
			return new LoadGoodsDetailReq();
		if (MSG_TYPES.MSG_LOAD_GOODS_LIST.equals(msgtype))
			return new LoadGoodsListReq();
		if (MSG_TYPES.MSG_MODIFY_ORDER.equals(msgtype))
			return new ModifyOrderReq();
		if (MSG_TYPES.MSG_UPLOAD_IMG.equals(msgtype))
			return new UploadImgReq();
		if (MSG_TYPES.MSG_ExitLogin.equals(msgtype))
			return new ExitLoginReq();
		if (MSG_TYPES.MSG_CASH_OUT.equals(msgtype))
			return new CashOutReq();
		if (MSG_TYPES.MSG_MODIFY_CASH_ACCOUNT.equals(msgtype))
			return new ModifyCashAccountReq();
		if (MSG_TYPES.MSG_MODIFY_ADDRESS_INFO.equals(msgtype))
			return new ModifyAddressInfoReq();
		return null;
	}

	
	// ������Ӧ����
	public static SalesBaseMsg CreateRespMsg(String msgtype)
	{
		if (MSG_TYPES.MSG_APP_CONFIG.equals(msgtype))
			return new AppConfigResp();
		if (MSG_TYPES.MSG_COMMIT_CASH.equals(msgtype))
			return new CommitCashResp();
		if (MSG_TYPES.MSG_GET_ABOUT_INFO.equals(msgtype))
			return new GetAboutInfoResp();
		if (MSG_TYPES.MSG_GET_ACCOUNT_INFO.equals(msgtype))
			return new GetAccountInfoResp();
		if (MSG_TYPES.MSG_GET_BANK_LIST.equals(msgtype))
			return new GetBankListResp();
		if (MSG_TYPES.MSG_GET_CANCEL_ORDER_PROMPT.equals(msgtype))
			return new GetCancelOrderPromptResp();
		if (MSG_TYPES.MSG_GET_CASH_ACCOUNT_LIST.equals(msgtype))
			return new GetCashAccountListResp();
		if (MSG_TYPES.MSG_GET_CASH_HIST_LIST.equals(msgtype))
			return new GetCashHistResp();
		if (MSG_TYPES.MSG_GET_CASH_INFO.equals(msgtype))
			return new GetCashInfoResp();
		if (MSG_TYPES.MSG_GET_CONTACT_INFO.equals(msgtype))
			return new GetContactInfoResp();
		if (MSG_TYPES.MSG_GET_FRIENT_LIST.equals(msgtype))
			return new GetFriendListResp();
		if (MSG_TYPES.MSG_GET_HIST_ORDERS.equals(msgtype))
			return new GetHistOrdersResp();
		if (MSG_TYPES.MSG_GET_ORDER.equals(msgtype))
			return new GetOrderResp();
		if (MSG_TYPES.MSG_GET_VERIFY_CODE.equals(msgtype))
			return new GetVerifyCodeResp();
		if (MSG_TYPES.MSG_LOAD_GOODS_DETAIL.equals(msgtype))
			return new LoadGoodsDetailResp();
		if (MSG_TYPES.MSG_LOAD_GOODS_LIST.equals(msgtype))
			return new LoadGoodsListResp();
		if (MSG_TYPES.MSG_MODIFY_ORDER.equals(msgtype))
			return new ModifyOrderResp();
		if (MSG_TYPES.MSG_UPLOAD_IMG.equals(msgtype))
			return new UploadImgResp();
		if (MSG_TYPES.MSG_MODIFY_CASH_ACCOUNT.equals(msgtype))
			return new ModifyCashAccountResp();
		if (MSG_TYPES.MSG_MODIFY_ADDRESS_INFO.equals(msgtype))
			return new ModifyAddressInfoResp();

		return null;
	}
	
	// ������ת��Ϊjson
	public  static String toJson(SalesBaseMsg obj)
	{
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		return json;
	}
	
	// ��jsonת��Ϊ����
	public static SalesBaseMsg fromJson(String json, String msgtype,  Boolean request)
	{
		SalesBaseMsg obj = null;
		if (request)
		{
			obj = SalesMsgUtils.CreateRequestMsg(msgtype);
		}
		else
		{
			obj = SalesMsgUtils.CreateRespMsg(msgtype);
		}
		
		if (null != obj)
		{
			Gson gson = new Gson();	
			obj = gson.fromJson(json,obj.getClass());
		}
		
		return obj;
	}
	

}
