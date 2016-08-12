package com.sales.model;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * TShare entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sales.model.TShare
 * @author MyEclipse Persistence Tools
 */

public class TShareDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(TShareDAO.class);
	// property constants
	public static final String URL = "url";
	public static final String TID = "tid";
	public static final String ADDITIONCONTENT = "additioncontent";
	public static final String USERID = "userid";

	protected void initDao() {
		// do nothing
	}

	public void save(TShare transientInstance) {
		log.debug("saving TShare instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TShare persistentInstance) {
		log.debug("deleting TShare instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TShare findById(java.lang.Integer id) {
		log.debug("getting TShare instance with id: " + id);
		try {
			TShare instance = (TShare) getHibernateTemplate().get(
					"com.sales.model.TShare", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TShare> findByExample(TShare instance) {
		log.debug("finding TShare instance by example");
		try {
			List<TShare> results = (List<TShare>) getHibernateTemplate()
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
		log.debug("finding TShare instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TShare as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TShare> findByUrl(Object url) {
		return findByProperty(URL, url);
	}

	public List<TShare> findByTid(Object tid) {
		return findByProperty(TID, tid);
	}

	public List<TShare> findByAdditioncontent(Object additioncontent) {
		return findByProperty(ADDITIONCONTENT, additioncontent);
	}

	public List<TShare> findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List findAll() {
		log.debug("finding all TShare instances");
		try {
			String queryString = "from TShare";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TShare merge(TShare detachedInstance) {
		log.debug("merging TShare instance");
		try {
			TShare result = (TShare) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TShare instance) {
		log.debug("attaching dirty TShare instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TShare instance) {
		log.debug("attaching clean TShare instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TShareDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TShareDAO) ctx.getBean("TShareDAO");
	}
}