package com.Gary.dao;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.Gary.domain.Praise;

public class PraiseDao extends HibernateDaoSupport{

	//添加赞
	public void addPraise(Praise praise) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.save(praise);	
	}

	//根据id找praise
	public Praise findPraiseByIdReturnPraise(String userid, String answerid) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "select * from praise where userid = ? and answerid = ?";
		NativeQuery query = session.createSQLQuery(sql);
		query.addEntity(Praise.class);
		query.setParameter(1, userid);
		query.setParameter(2, answerid);
		Praise result = (Praise) query.uniqueResult();
		return result;
	}

	public void deletePraise(Praise praise) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.delete(praise);
	}

}
