package com.Gary.service;

import java.util.List;

import com.Gary.dao.AnswerDao;
import com.Gary.dao.PasteDao;
import com.Gary.dao.UserDao;
import com.Gary.domain.Answer;
import com.Gary.domain.Paste;
import com.Gary.domain.User;
import com.Gary.utils.PageBean;

public class PasteService {

    private PasteDao pasteDao;
    private AnswerDao answerDao;
    private UserDao userDao;
    
	public void solvePasteByIdAndAnswerid(String pasteid, String answerid) {
		//给回复的人添加金币
		Paste paste = pasteDao.findPasteByIdReturnPaste(pasteid);
		//通过answerid查找user
		Answer answer = answerDao.findAnswerById(answerid);
		//通过userid找user对象
		//User user = userDao.finduserByIdReturnUser(answer.getUser().getId());
		//获得answer下的user对象(持久化数据类型)
		User user = answer.getUser();
		//因为是持久化类型对象，即可直接修改属性，同步到数据库
		user.setCoin(user.getCoin()+paste.getOffer());
		//解决问题
		pasteDao.solvePasteByIdAndAnswerid(pasteid,answerid);
	}
    
    public AnswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(AnswerDao answerDao) {
		this.answerDao = answerDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public Paste findPasteByIdReturnPaste(String pasteid) {
        
        //pasteDao.addPasteGlanceover(pasteid);
        //数据库SQL语句：update paste set glanceover = glanceover+1 where id = ?;
        Paste paste = pasteDao.findPasteByIdReturnPaste(pasteid);
        paste.setGlanceover(paste.getGlanceover()+1);
        return paste;
    }
    
    public PageBean getGlanceoverPageBean(Integer currentPage) {
        Integer totalCount = pasteDao.findAllPasteNum();
        PageBean glanceoverPageBean = new PageBean(currentPage, totalCount, 8);
        List<Paste> list = pasteDao.getGlanceoverPageList();
        glanceoverPageBean.setList(list);
        
        return glanceoverPageBean;
    }
    

    
    public PageBean getPastePageBean(Integer currentPage) {
        
        Integer totalCount = pasteDao.findAllPasteNum();
        PageBean pageBean = new PageBean(currentPage,totalCount,8);
        List<Paste> list = pasteDao.getPastePageList(pageBean.getStart(),pageBean.getPageSize());
        pageBean.setList(list);
        
        
        return pageBean;
    }
    
    public PasteDao getPasteDao() {
        return pasteDao;
    }

    public List<Paste> findAllPaste() {
    
        return pasteDao.findAllPaste();
    
    }
    
    public void setPasteDao(PasteDao pasteDao) {
        this.pasteDao = pasteDao;
    }

    public void addPaste(Paste paste) {
        
        pasteDao.addPaste(paste);
        
    }



    public PageBean getAnsnumPageBean(Integer currentPage) {
        //获得所有的帖子数目
        Integer totalCount = pasteDao.findAllPasteNum();
        //创建PageBean
        PageBean ansnumPageBean = new PageBean(currentPage, totalCount, 8);
        //得到List
        List<Paste> list = pasteDao.getAnsnumPageList();
        //封装List
        ansnumPageBean.setList(list);    
        return ansnumPageBean;
    }


}