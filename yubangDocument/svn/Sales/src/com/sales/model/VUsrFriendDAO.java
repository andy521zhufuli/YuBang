package com.sales.model;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * VUsrFriend entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sales.model.VUsrFriend
 * @author MyEclipse Persistence Tools
 */

public class VUsrFriendDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(VUsrFriendDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(VUsrFriend transientInstance) {
		log.debug("saving VUsrFriend instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(VUsrFriend persistentInstance) {
		log.debug("deleting VUsrFriend instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public VUsrFriend findById(com.sales.model.VUsrFriendId id) {
		log.debug("getting VUsrFriend instance with id: " + id);
		try {
			VUsrFriend instance = (VUsrFriend) getHibernateTemplate().get(
					"com.sales.model.VUsrFriend", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<VUsrFriend> findByExample(VUsrFriend instance) {
		log.debug("finding VUsrFriend instance by example");
		try {
			List<VUsrFriend> results = (List<VUsrFriend>) getHibernateTemplate()
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
		log.debug("finding VUsrFriend instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from VUsrFriend as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all VUsrFriend instances");
		try {
			String queryString = "from VUsrFriend";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public VUsrFriend merge(VUsrFriend detachedInstance) {
		log.debug("merging VUsrFriend instance");
		try {
			VUsrFriend result = (VUsrFriend) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(VUsrFriend instance) {
		log.debug("attaching dirty VUsrFriend instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(VUsrFriend instance) {
		log.debug("attaching clean VUsrFriend instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static VUsrFriendDAO getFromApplicationContext(ApplicationContext ctx) {
		return (VUsrFriendDAO) ctx.getBean("VUsrFriendDAO");
	}
}