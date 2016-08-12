package com.sales.model;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * TGoodsImg entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sales.model.TGoodsImg
 * @author MyEclipse Persistence Tools
 */

public class TGoodsImgDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TGoodsImgDAO.class);
	// property constants
	public static final String URL = "url";
	public static final String GOODSID = "goodsid";
	public static final String ORDER = "order";

	protected void initDao() {
		// do nothing
	}

	public void save(TGoodsImg transientInstance) {
		log.debug("saving TGoodsImg instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TGoodsImg persistentInstance) {
		log.debug("deleting TGoodsImg instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TGoodsImg findById(java.lang.Integer id) {
		log.debug("getting TGoodsImg instance with id: " + id);
		try {
			TGoodsImg instance = (TGoodsImg) getHibernateTemplate().get(
					"com.sales.model.TGoodsImg", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TGoodsImg> findByExample(TGoodsImg instance) {
		log.debug("finding TGoodsImg instance by example");
		try {
			List<TGoodsImg> results = (List<TGoodsImg>) getHibernateTemplate()
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
		log.debug("finding TGoodsImg instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TGoodsImg as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TGoodsImg> findByUrl(Object url) {
		return findByProperty(URL, url);
	}

	public List<TGoodsImg> findByGoodsid(Object goodsid) {
		return findByProperty(GOODSID, goodsid);
	}

	public List<TGoodsImg> findByOrder(Object order) {
		return findByProperty(ORDER, order);
	}

	public List findAll() {
		log.debug("finding all TGoodsImg instances");
		try {
			String queryString = "from TGoodsImg";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TGoodsImg merge(TGoodsImg detachedInstance) {
		log.debug("merging TGoodsImg instance");
		try {
			TGoodsImg result = (TGoodsImg) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TGoodsImg instance) {
		log.debug("attaching dirty TGoodsImg instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TGoodsImg instance) {
		log.debug("attaching clean TGoodsImg instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TGoodsImgDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TGoodsImgDAO) ctx.getBean("TGoodsImgDAO");
	}
}