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
 * TGoods entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sales.model.TGoods
 * @author MyEclipse Persistence Tools
 */

public class TGoodsDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(TGoodsDAO.class);
	// property constants
	public static final String TITLE = "title";
	public static final String SECONDARYTITLE = "secondarytitle";
	public static final String ORIGINALPRICE = "originalprice";
	public static final String DISCOUNTPRICE = "discountprice";
	public static final String TITLEIMGURL = "titleimgurl";
	public static final String THUMBIMGURL = "thumbimgurl";
	public static final String ORDER = "order";
	public static final String VERSION = "version";
	public static final String LISTIMGURL = "listimgurl";

	protected void initDao() {
		// do nothing
	}

	public void save(TGoods transientInstance) {
		log.debug("saving TGoods instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TGoods persistentInstance) {
		log.debug("deleting TGoods instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TGoods findById(java.lang.Integer id) {
		log.debug("getting TGoods instance with id: " + id);
		try {
			TGoods instance = (TGoods) getHibernateTemplate().get(
					"com.sales.model.TGoods", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TGoods> findByExample(TGoods instance) {
		log.debug("finding TGoods instance by example");
		try {
			List<TGoods> results = (List<TGoods>) getHibernateTemplate()
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
		log.debug("finding TGoods instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TGoods as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TGoods> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<TGoods> findBySecondarytitle(Object secondarytitle) {
		return findByProperty(SECONDARYTITLE, secondarytitle);
	}

	public List<TGoods> findByOriginalprice(Object originalprice) {
		return findByProperty(ORIGINALPRICE, originalprice);
	}

	public List<TGoods> findByDiscountprice(Object discountprice) {
		return findByProperty(DISCOUNTPRICE, discountprice);
	}

	public List<TGoods> findByTitleimgurl(Object titleimgurl) {
		return findByProperty(TITLEIMGURL, titleimgurl);
	}

	public List<TGoods> findByThumbimgurl(Object thumbimgurl) {
		return findByProperty(THUMBIMGURL, thumbimgurl);
	}

	public List<TGoods> findByOrder(Object order) {
		return findByProperty(ORDER, order);
	}

	public List<TGoods> findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List<TGoods> findByListimgurl(Object listimgurl) {
		return findByProperty(LISTIMGURL, listimgurl);
	}

	public List findAll() {
		log.debug("finding all TGoods instances");
		try {
			String queryString = "from TGoods";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TGoods merge(TGoods detachedInstance) {
		log.debug("merging TGoods instance");
		try {
			TGoods result = (TGoods) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TGoods instance) {
		log.debug("attaching dirty TGoods instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TGoods instance) {
		log.debug("attaching clean TGoods instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TGoodsDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TGoodsDAO) ctx.getBean("TGoodsDAO");
	}
}