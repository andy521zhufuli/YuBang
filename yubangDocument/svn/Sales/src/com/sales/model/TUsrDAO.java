package com.sales.model;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for TUsr
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.sales.model.TUsr
 * @author MyEclipse Persistence Tools
 */

public class TUsrDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(TUsrDAO.class);
	// property constants
	public static final String USERNAME = "username";
	public static final String NICKNAME = "nickname";
	public static final String HEADIMGURL = "headimgurl";
	public static final String ACCESSTOKEN = "accesstoken";
	public static final String PUSERID = "puserid";
	public static final String STATUS = "status";
	public static final String CASHBALANCE = "cashbalance";
	public static final String PHONE = "phone";
	public static final String TYPE = "type";

	protected void initDao() {
		// do nothing
	}

	public void save(TUsr transientInstance) {
		log.debug("saving TUsr instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TUsr persistentInstance) {
		log.debug("deleting TUsr instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TUsr findById(java.lang.Integer id) {
		log.debug("getting TUsr instance with id: " + id);
		try {
			TUsr instance = (TUsr) getHibernateTemplate().get(
					"com.sales.model.TUsr", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TUsr> findByExample(TUsr instance) {
		log.debug("finding TUsr instance by example");
		try {
			List<TUsr> results = (List<TUsr>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TUsr instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TUsr as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TUsr> findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	public List<TUsr> findByNickname(Object nickname) {
		return findByProperty(NICKNAME, nickname);
	}

	public List<TUsr> findByHeadimgurl(Object headimgurl) {
		return findByProperty(HEADIMGURL, headimgurl);
	}

	public List<TUsr> findByAccesstoken(Object accesstoken) {
		return findByProperty(ACCESSTOKEN, accesstoken);
	}

	public List<TUsr> findByPuserid(Object puserid) {
		return findByProperty(PUSERID, puserid);
	}

	public List<TUsr> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<TUsr> findByCashbalance(Object cashbalance) {
		return findByProperty(CASHBALANCE, cashbalance);
	}

	public List<TUsr> findByPhone(Object phone) {
		return findByProperty(PHONE, phone);
	}

	public List<TUsr> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findAll() {
		log.debug("finding all TUsr instances");
		try {
			String queryString = "from TUsr";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TUsr merge(TUsr detachedInstance) {
		log.debug("merging TUsr instance");
		try {
			TUsr result = (TUsr) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TUsr instance) {
		log.debug("attaching dirty TUsr instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TUsr instance) {
		log.debug("attaching clean TUsr instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TUsrDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TUsrDAO) ctx.getBean("TUsrDAO");
	}
}