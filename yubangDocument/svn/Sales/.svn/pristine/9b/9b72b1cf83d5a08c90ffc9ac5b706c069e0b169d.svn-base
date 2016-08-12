package com.sales.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.common.utils.StringUtils;
import com.common.web.hibernate.IBaseDAOBase;
import com.sales.model.TGoods;
import com.sales.model.TOrder;
import com.sales.model.TOrderDAO;

@Repository
public class TOrderDAOImpl extends TOrderDAO {

	private final static String FINDORDERHIS = "select * from t_order to ";

	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {

		super.setSessionFactory(sessionFactory);

	}

	/**
	 * 分页查找订单
	 * @param start 开始页码
	 * @param end  结束页码
	 * @param userid 用户id
	 * @param status 订单状态
	 * @return
	 */
	public List<TOrder> findMyorderBySQL(int start, int end, String userid,
			String status) {
		String sql = FINDORDERHIS + "where to.userid = " + userid;
		if (status == null || "".equals(status)) {

		} else {
			sql = sql + " and status = " + status;
		}
		sql = sql + " order by to.updatetime asc limit " + start + "," + end;
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		List<TOrder> list = sqlQuery.addEntity(TOrder.class).list();
		return list;
	}
}