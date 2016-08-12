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
 * TOrderHist entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sales.model.TOrderHist
 * @author MyEclipse Persistence Tools
 */

public class TOrderHistDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TOrderHistDAO.class);
	// property constants
	public static final String STATUS = "status";
	public static final String OPERATOR = "operator";
	public static final String OPERATION = "operation";
	public static final String ORDERID = "orderid";
	public static final String GOODSLIST = "goodslist";
	public static final String ADDRESSNAME = "addressname";
	public static final String ADDRESSPHONE = "addressphone";
	public static final String ADDRESS = "address";

	protected void initDao() {
		// do nothing
	}

	public void save(TOrderHist transientInstance) {
		log.debug("saving TOrderHist instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TOrderHist persistentInstance) {
		log.debug("deleting TOrderHist instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TOrderHist findById(java.lang.Integer id) {
		log.debug("getting TOrderHist instance with id: " + id);
		try {
			TOrderHist instance = (TOrderHist) getHibernateTemplate().get(
					"com.sales.model.TOrderHist", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TOrderHist> findByExample(TOrderHist instance) {
		log.debug("finding TOrderHist instance by example");
		try {
			List<TOrderHist> results = (List<TOrderHist>) getHibernateTemplate()
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
		log.debug("finding TOrderHist instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TOrderHist as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TOrderHist> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<TOrderHist> findByOperator(Object operator) {
		return findByProperty(OPERATOR, operator);
	}

	public List<TOrderHist> findByOperation(Object operation) {
		return findByProperty(OPERATION, operation);
	}

	public List<TOrderHist> findByOrderid(Object orderid) {
		return findByProperty(ORDERID, orderid);
	}

	public List<TOrderHist> findByGoodslist(Object goodslist) {
		return findByProperty(GOODSLIST, goodslist);
	}

	public List<TOrderHist> findByAddressname(Object addressname) {
		return findByProperty(ADDRESSNAME, addressname);
	}

	public List<TOrderHist> findByAddressphone(Object addressphone) {
		return findByProperty(ADDRESSPHONE, addressphone);
	}

	public List<TOrderHist> findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findAll() {
		log.debug("finding all TOrderHist instances");
		try {
			String queryString = "from TOrderHist";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TOrderHist merge(TOrderHist detachedInstance) {
		log.debug("merging TOrderHist instance");
		try {
			TOrderHist result = (TOrderHist) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TOrderHist instance) {
		log.debug("attaching dirty TOrderHist instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TOrderHist instance) {
		log.debug("attaching clean TOrderHist instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TOrderHistDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TOrderHistDAO) ctx.getBean("TOrderHistDAO");
	}
}