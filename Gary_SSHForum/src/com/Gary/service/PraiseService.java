package com.Gary.service;

import com.Gary.dao.PraiseDao;
import com.Gary.domain.Praise;

public class PraiseService {

	private PraiseDao praiseDao;
	

	public boolean findPraiseByIdReturnPraise(String userid, String answerid) {
		Praise praise = praiseDao.findPraiseByIdReturnPraise(userid,answerid);
		return praise==null?false:true;
	}
	
	public void addPraise(Praise praise) {
		praiseDao.addPraise(praise);
		
	}

	public PraiseDao getPraiseDao() {
		return praiseDao;
	}

	public void setPraiseDao(PraiseDao praiseDao) {
		this.praiseDao = praiseDao;
	}

	public void deletePraise(Praise praise) {
		praiseDao.deletePraise(praise);
	}


}
