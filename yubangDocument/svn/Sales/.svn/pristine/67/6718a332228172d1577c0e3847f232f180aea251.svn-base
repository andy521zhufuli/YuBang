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
 * TGoodsHist entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sales.model.TGoodsHist
 * @author MyEclipse Persistence Tools
 */

public class TGoodsHistDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TGoodsHistDAO.class);
	// property constants
	public static final String GOODSID = "goodsid";
	public static final String TITLE = "title";
	public static final String SECONDARYTITLE = "secondarytitle";
	public static final String ORIGINALPRICE = "originalprice";
	public static final String DISCOUNTPRICE = "discountprice";
	public static final String TITLEIMGURL = "titleimgurl";
	public static final String THUMBIMGURL = "thumbimgurl";
	public static final String ORDER = "order";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	public void save(TGoodsHist transientInstance) {
		log.debug("saving TGoodsHist instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TGoodsHist persistentInstance) {
		log.debug("deleting TGoodsHist instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TGoodsHist findById(java.lang.Integer id) {
		log.debug("getting TGoodsHist instance with id: " + id);
		try {
			TGoodsHist instance = (TGoodsHist) getHibernateTemplate().get(
					"com.sales.model.TGoodsHist", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TGoodsHist> findByExample(TGoodsHist instance) {
		log.debug("finding TGoodsHist instance by example");
		try {
			List<TGoodsHist> results = (List<TGoodsHist>) getHibernateTemplate()
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
		log.debug("finding TGoodsHist instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TGoodsHist as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TGoodsHist> findByGoodsid(Object goodsid) {
		return findByProperty(GOODSID, goodsid);
	}

	public List<TGoodsHist> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<TGoodsHist> findBySecondarytitle(Object secondarytitle) {
		return findByProperty(SECONDARYTITLE, secondarytitle);
	}

	public List<TGoodsHist> findByOriginalprice(Object originalprice) {
		return findByProperty(ORIGINALPRICE, originalprice);
	}

	public List<TGoodsHist> findByDiscountprice(Object discountprice) {
		return findByProperty(DISCOUNTPRICE, discountprice);
	}

	public List<TGoodsHist> findByTitleimgurl(Object titleimgurl) {
		return findByProperty(TITLEIMGURL, titleimgurl);
	}

	public List<TGoodsHist> findByThumbimgurl(Object thumbimgurl) {
		return findByProperty(THUMBIMGURL, thumbimgurl);
	}

	public List<TGoodsHist> findByOrder(Object order) {
		return findByProperty(ORDER, order);
	}

	public List<TGoodsHist> findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findAll() {
		log.debug("finding all TGoodsHist instances");
		try {
			String queryString = "from TGoodsHist";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TGoodsHist merge(TGoodsHist detachedInstance) {
		log.debug("merging TGoodsHist instance");
		try {
			TGoodsHist result = (TGoodsHist) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TGoodsHist instance) {
		log.debug("attaching dirty TGoodsHist instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TGoodsHist instance) {
		log.debug("attaching clean TGoodsHist instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TGoodsHistDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TGoodsHistDAO) ctx.getBean("TGoodsHistDAO");
	}
}