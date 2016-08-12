package com.sales.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.tree.BackedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.common.utils.TimeUtils;
import com.sales.common.until.CommonDefs;
import com.sales.common.until.IdGenerator;
import com.sales.common.until.ReturnCode;
import com.sales.dao.impl.TCashAccountDAOImpl;
import com.sales.dao.impl.TCashBankDAOImpl;
import com.sales.dao.impl.TCashDAOImpl;
import com.sales.dao.impl.TCashHistDAOImpl;
import com.sales.dao.impl.TUsrDAOImpl;
import com.sales.exception.SalesInternalException;
import com.sales.exception.SalesLogicalException;
import com.sales.model.OrderDealHis;
import com.sales.model.TCash;
import com.sales.model.TCashAccount;
import com.sales.model.TCashBank;
import com.sales.model.TCashHist;
import com.sales.model.TUsr;
import com.sales.vo.CashOutReq;
import com.sales.vo.GetCashInfoReq;
import com.sales.vo.GetCashInfoResp;
import com.sales.vo.base.CashAccountInfo;
import com.sales.vo.base.CashInfo;
import com.sales.web.base.SalesScreenAdapterAction;

@Service
@Transactional
public class CashService {

	final private static JLogger logger = LoggerUtils
			.getLogger(SalesScreenAdapterAction.class);
	
	@Autowired
	private TCashAccountDAOImpl tCashAccountDAO;
	
	@Autowired
	private TCashBankDAOImpl tCashBankDAO;
	
	@Autowired
	private TCashAccountDAOImpl accountDAO;
	
	@Autowired
	private TCashDAOImpl tCashDAO;
	
	@Autowired
	private TCashHistDAOImpl tCashHistDAO;
	
	
	@Autowired
	private TUsrDAOImpl tUsrDAO;
	
	public CashInfo updateCashInfo(CashOutReq cashOutReq){
		CashInfo cashInfo = cashOutReq.getCashInfo();
		
		
		List<TCashAccount> tCashAccounts = tCashAccountDAO.findByUserid(cashOutReq.getUserid());
		
		
		return cashInfo;
	}
	
	/**
	 * 获取用户的提现账户列表
	 * */
	public List<CashAccountInfo> getCashAccount(String userid){
		List<CashAccountInfo> list = new ArrayList<CashAccountInfo>();
		List<TCashAccount> tCashAccounts = tCashAccountDAO.getCashAccount(Integer.parseInt(userid));
		if (null != tCashAccounts && tCashAccounts.size() > 0)
		{
			for (TCashAccount tCashAccount : tCashAccounts)
			{
                CashAccountInfo accountInfo = new CashAccountInfo();
                accountInfo.setId(String.valueOf(tCashAccount.getId()));
                accountInfo.setAccountid(tCashAccount.getAccountid());
                accountInfo.setAccountname(tCashAccount.getAccountname());
                accountInfo.setAccounttype(tCashAccount.getType());
                accountInfo.setBankname(tCashAccount.getBankname());
                accountInfo.setSelected(tCashAccount.getSelected());
                if (CommonDefs.CASH_ACCOUNT_SELECTED == tCashAccount.getSelected())
                {
                	// 被选中的添加到链表的头部
                	list.add(0, accountInfo);
                }
                else
                {
                	// 未被选中的添加到链表的尾部
                	list.add(accountInfo);
                }                
			}
		}
	
		return list;
	}
	
	public GetCashInfoResp getCashInfoResp(GetCashInfoReq getCashInfoReq){
		
		GetCashInfoResp getCashInfoResp = new GetCashInfoResp();
		
		TCash tCash = tCashDAO.findById(getCashInfoReq.getCashid());
		
		getCashInfoResp.setCashId(String.valueOf(tCash.getId()));
		
		getCashInfoResp.setAmount(String.valueOf(tCash.getAmount()));
		getCashInfoResp.setAcount(tCash.getAccountid());
	    getCashInfoResp.setAcountName(tCash.getAccountname());
	    String accounttype = tCash.getAccounttype();
	    if (accounttype.equals(CommonDefs.CASH_METHOD_ALIPAY))
	    {
		     getCashInfoResp.setBank(CommonDefs.CASH_METHOD_NAME_ALIPAY);
	    }
	    else  if (tCash.getAccounttype().equals(CommonDefs.CASH_METHOD_WEIXIN))
	    {
	    	getCashInfoResp.setBank(CommonDefs.CASH_METHOD_NAME_WEIXIN);
	    }
	    else
	    {
	    	getCashInfoResp.setBank(tCash.getBankname());
	    }
	    
        // 交易单号， 暂时为空	    	
		getCashInfoResp.setTradId("");
		
		List<TCashHist> tCashHists =tCashHistDAO.findByCashid(getCashInfoReq.getCashid());
		List<OrderDealHis> orderDealHisList = new ArrayList<OrderDealHis>();
		for (TCashHist tCashHist : tCashHists) {
			OrderDealHis orderDealHis = new OrderDealHis();
			orderDealHis.setInfo(tCashHist.getOperation());
			orderDealHis.setTimeStamp("");
			orderDealHis.setUser(tCashHist.getOperator()+"");
			orderDealHisList.add(orderDealHis);
		}
		
		getCashInfoResp.setOrderDealHisList(orderDealHisList);
		return getCashInfoResp;
	}
	
	public List<TCashBank> getTCashBankList(){
		return tCashBankDAO.findAll();
	}

	public TCashAccountDAOImpl gettCashAccountDAO() {
		return tCashAccountDAO;
	}

	public void settCashAccountDAO(TCashAccountDAOImpl tCashAccountDAO) {
		this.tCashAccountDAO = tCashAccountDAO;
	}

	public TCashBankDAOImpl gettCashBankDAO() {
		return tCashBankDAO;
	}

	public void settCashBankDAO(TCashBankDAOImpl tCashBankDAO) {
		this.tCashBankDAO = tCashBankDAO;
	}
	
	public void cashOut(int userid, CashInfo cashInfo) throws Exception 
	{
		// 首先查询用户信息
		TUsr tusr = tUsrDAO.findById(userid);
		if(null == tusr)
		{
			logger.debug("user not existed:" + userid);
			throw new SalesInternalException(ReturnCode.RET_INTER_USR_NOT_EXISTED,"user not existed:" + userid );
		}
		
		// 校验用户提现余额与申请余额
		int cashBalance = tusr.getCashbalance();
		if(cashBalance < cashInfo.getAmount())
		{
			logger.debug("cash balance is not enough,balance:" + cashBalance + ", amout:" + cashInfo.getAmount());
			throw new SalesLogicalException(ReturnCode.RET_INTER_USR_NOT_EXISTED, "cash balance is not enough,balance:" + cashBalance + ", amout:" + cashInfo.getAmount());
		}
		else
		{
			cashBalance = cashBalance - cashInfo.getAmount();
		}
		// 更新用户可提现余额
		//tusr.setCashbalance(cashBalance);
		
		String cashid = IdGenerator.GenID(IdGenerator.ID_TYPE_CASH);
		
		// 将提现信息保存到表中
		TCash tcash = new TCash();
		tcash.setId(cashid);
		tcash.setUserid(userid);
		//tcash.setTUsr(tusr);
		tcash.setAmount(cashInfo.getAmount());
		tcash.setAccountname(cashInfo.getAccountname());
		tcash.setAccounttype(cashInfo.getAccounttype());
		tcash.setAccountid(cashInfo.getAccountid());
		tcash.setBankname(cashInfo.getBankname());
		tcash.setStatus(CommonDefs.CASH_STATUS_TO_APPROVE);

		// 保存提现订单
		tCashDAO.save(tcash);
		//通过trigger保存历史信息
		recordHist(tcash, CommonDefs.CASH_ACT_COMMIT, userid, "user commit order");
		// 保存修改后的提现信息
		tUsrDAO.updateBalance(userid, cashBalance);
	}
	
	/**
	 * 保存历史记录 
	 * */
	private void recordHist(TCash tcash, String action, int userid, String notes)
	{
		TCashHist tcashHist = new TCashHist();
		tcashHist.setCashid(tcash.getId());
		tcashHist.setStatus(tcash.getStatus());
		tcashHist.setOperator(userid);
		tcashHist.setOperation(action);
		tcashHist.setAmount(tcash.getAmount());
		tcashHist.setAccounttype(tcash.getAccounttype());
		tcashHist.setAccountname(tcash.getAccountname());
		tcashHist.setAccountid(tcash.getAccountid());
		tcashHist.setBankname(tcash.getBankname());
		tcashHist.setUserid(userid);
		tcashHist.setNotes(notes);
		
		tCashHistDAO.save(tcashHist);
		
	
	}
	
	public List<CashInfo> getCashList(String usrid,String status,  int start, int end) throws Exception
	{
		List<CashInfo>  list= new ArrayList<CashInfo>();
		List<TCash> cashList = tCashDAO.getCashList(usrid, status, start, end);
		if (cashList != null && cashList.size() > 0)
		{
			for (TCash tCash:cashList)
			{
				CashInfo cashInfo = new CashInfo();
				cashInfo.setCashid(tCash.getId());
				cashInfo.setAmount(tCash.getAmount());
				cashInfo.setStatus(tCash.getStatus());
				cashInfo.setAccountid(tCash.getAccountid());
				cashInfo.setAccountname(tCash.getAccountname());
				cashInfo.setBankname(tCash.getBankname());
				cashInfo.setAccounttype(tCash.getAccounttype());
				String updateTimeStr = TimeUtils.convertToDateStr(tCash.getUpdatetime(), 
						                                               CommonDefs.TIME_STAMP_FORMAT);
				cashInfo.setUpdatetime(updateTimeStr);
				list.add(cashInfo);
				
			}
		}
		return list;
	}

	/**
	 * 添加账户信息
	 * */
	public void saveCashAccount(String userid, CashAccountInfo info) {
		TCashAccount tCashAccount = new TCashAccount();
		tCashAccount.setUserid(Integer.valueOf(userid));
		tCashAccount.setType(info.getAccounttype());
		tCashAccount.setAccountid(info.getAccountid());
		tCashAccount.setAccountname(info.getAccountname());
		tCashAccount.setBankname(info.getBankname());
		tCashAccount.setSelected(info.getSelected());
		tCashAccountDAO.attachDirty(tCashAccount);
	}

	/**
	 * 修改账户信息
	 * @throws Exception 
	 * */
	@Transactional
	public void modifyCashAccount(String userid, CashAccountInfo info) throws Exception {
		TCashAccount tCashAccount = tCashAccountDAO.findById(Integer.valueOf(info.getId()));
		if (null == tCashAccount || (!tCashAccount.getUserid().equals(Integer.valueOf(userid))))
		{
			logger.error("CashAccountInfo is not existed or not belonged to userid:" + userid);
			throw new  SalesInternalException(ReturnCode.RET_CASH_ACCOUNT_CAN_NOT_BE_MODIFIED,"CashAccountInfo is not existed or not belonged to userid:" + userid);
		}
		tCashAccount.setType(info.getAccounttype());
		tCashAccount.setAccountid(info.getAccountid());
		tCashAccount.setAccountname(info.getAccountname());
		tCashAccount.setBankname(info.getBankname());
		tCashAccount.setSelected(info.getSelected());
		tCashAccountDAO.attachDirty(tCashAccount);
	}
	
	
}
