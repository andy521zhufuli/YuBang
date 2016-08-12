package com.sales.dao.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.common.web.hibernate.IBaseDAOBase;
import com.sales.common.until.ReturnCode;
import com.sales.exception.SalesInternalException;
import com.sales.model.TMsg;
import com.sales.model.TMsgDAO;
import com.sun.org.apache.bcel.internal.generic.RETURN;

@Repository
public class TMsgDAOImpl extends TMsgDAO {
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {

		super.setSessionFactory(sessionFactory);

	}

	/**
	 * 获取该userid在当天内发送的验证码
	 * 
	 * @param userid
	 * @return
	 * @throws SalesInternalException
	 */
	public List<TMsg> getTMsgByUserIdAndTime(String userid)
			throws SalesInternalException {
		String sql = "select * from t_msg t  where t.userid = ? and t.updatetime > ? order by t.updatetime asc";
		try {
			SQLQuery sqlQuery = getSessionFactory().getCurrentSession()
					.createSQLQuery(sql);
			sqlQuery.setInteger(0, Integer.parseInt(userid));
			sqlQuery.setDate(1, getNowDate());
			sqlQuery.addEntity(TMsg.class);
			return sqlQuery.list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			throw new SalesInternalException(ReturnCode.RET_SQL_ERROR,
					"find Tmsg error");
		}

	}

	private Date getNowDate() throws ParseException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		String nowDate = sdf.format(date);
		return sdf.parse(nowDate);

	}
}