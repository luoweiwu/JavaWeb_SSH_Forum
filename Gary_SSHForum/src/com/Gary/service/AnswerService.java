package com.Gary.service;

import java.util.List;

import com.Gary.dao.AnswerDao;
import com.Gary.dao.PasteDao;
import com.Gary.domain.Answer;
import com.Gary.domain.Paste;

public class AnswerService {



	private AnswerDao answerDao;
	private PasteDao pasteDao;
	

	public void addAnswerAgree(String answerid) {
		Answer answer = answerDao.findAnswerById(answerid);
		answer.setAgree(answer.getAgree() + 1);
		//第二种方式sql
		//update answer set agree = agree +1 where answer id = ?
	}
	
	//根据answerid查找answer对象
	public Answer findAnswerByIdReturnAnswer(String answerid) {
		Answer answer = answerDao.findAnswerById(answerid);

		return answer;
	}
	
	public List<Answer> findAllAnswerByPasteid(String pasteid) {
		
		Paste paste = pasteDao.findPasteByIdReturnPaste(pasteid);
		List<Answer> answerList = null;
		// 判断paste中的solve是否为1
		if (paste.getSolve() == 1) {
			// 查找最佳答案
			Answer answer = answerDao.findAnswerById(paste.getAnswerid());
			// 查找所有答案
			answerList = answerDao.findAllAnswerByPasteid(pasteid);
			// 查找所有答案中包括了最佳答案所以将最佳答案删除
			answerList.remove(answer);
			// 在头元素中插入最佳答案
			answerList.add(0, answer);
		} else {
			answerList = answerDao.findAllAnswerByPasteid(pasteid);
		}

		return answerList;
	}
	
	public void addAnswer(Answer answer, Paste paste) {

		paste.setAnsnum(paste.getAnsnum() + 1);
		answerDao.addAnswer(answer);
	}
	
	public void deleteAnswerById(String answerid, Paste paste) {
		paste.setAnsnum(paste.getAnsnum()-1);
		answerDao.deleteAnswerById(answerid);
	}
	
	public AnswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(AnswerDao answerDao) {
		this.answerDao = answerDao;
	}

	public PasteDao getPasteDao() {
		return pasteDao;
	}

	public void setPasteDao(PasteDao pasteDao) {
		this.pasteDao = pasteDao;
	}

	public void deleteAnswerAgree(String answerid) {
		Answer answer = answerDao.findAnswerById(answerid);
		answer.setAgree(answer.getAgree() - 1);
	}


}
