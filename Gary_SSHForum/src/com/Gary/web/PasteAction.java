package com.Gary.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.Gary.domain.Answer;
import com.Gary.domain.Paste;
import com.Gary.domain.User;
import com.Gary.service.AnswerService;
import com.Gary.service.PasteService;
import com.Gary.service.PraiseService;
import com.Gary.utils.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class PasteAction extends ActionSupport implements ModelDriven<Paste> {

    public Paste paste = new Paste();
    private String pasteid;

	private PasteService pasteService;
	private AnswerService answerService;
    private PraiseService praiseService;
	//解决帖子
    public String solvePaste() throws Exception{
		
    	pasteService.solvePasteByIdAndAnswerid(pasteid,paste.getAnswerid());
		ActionContext.getContext().put("pasteid", pasteid);
		
		return "toDetail";
	}
	
	
    //查看帖子详细信息
    public String getDetail() throws Exception{
    
    	User user = (User)ActionContext.getContext().getSession().get("user");
    	
    	//得到最近热帖
    	PageBean glanceoverPageBean = pasteService.getGlanceoverPageBean(null);
    	ActionContext.getContext().put("glanceoverPageBean", glanceoverPageBean);
    			
    	//得到最近热议
    	PageBean ansnumPageBean = pasteService.getAnsnumPageBean(null);
    	ActionContext.getContext().put("ansnumPageBean", ansnumPageBean);
    			
    	//获得帖子
    	Paste paste = pasteService.findPasteByIdReturnPaste(pasteid);
		ActionContext.getContext().put("paste", paste);
        
		//获得该帖子的所有回复
		List<Answer> answerList = answerService.findAllAnswerByPasteid(pasteid);
		for(Answer answer:answerList) 
		{
			if(user != null)
			{
				//判断用户是否点赞了该回复
				boolean success = praiseService.findPraiseByIdReturnPraise(user.getId(),answer.getId());
				if(success)
				{
					//如果查询到用户点赞了该回复，将loginUserIsAgree设置为1
					answer.setLoginUserIsAgree(1);
				}
				else
				{
					//如果没有查询到用户点赞了该回复，将loginUserIsAgree设置为0
					answer.setLoginUserIsAgree(0);
				}
			}
			else 
			{
				//如果没有登陆，将loginUserIsAgree设置为0
				answer.setLoginUserIsAgree(0);
			}
		}
		ActionContext.getContext().put("answerList", answerList);
		
        return "detail";
    }
    
    public PraiseService getPraiseService() {
		return praiseService;
	}


	public void setPraiseService(PraiseService praiseService) {
		this.praiseService = praiseService;
	}


	public AnswerService getAnswerService() {
		return answerService;
	}

	public void setAnswerService(AnswerService answerService) {
		this.answerService = answerService;
	}

	//添加帖子
    public String addPaste() throws Exception {
        User user = (User)ActionContext.getContext().getSession().get("user");
        
        if(user==null)
        {
            ActionContext.getContext().put("error", "只有登陆之后才可以发帖子！！");
            return "error";
        }
        
        //private Integer ansnum;
        paste.setAnsnum(0);
        //private String createtime;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String createtime = format.format(date);
        paste.setCreatetime(createtime);
        //private Integer glanceover;
        paste.setGlanceover(0);
        //是否结帖  0未结 1结束
        //private Integer solve;
        paste.setSolve(0);
        //private Integer isdelete;
        paste.setIsdelete(0);
        //private User user;
        
        paste.setUser(user);
        
        pasteService.addPaste(paste);
        
        
        //重定向到主页
        return "toIndex";
    }

    public PasteService getPasteService() {
        return pasteService;
    }

    public void setPasteService(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @Override
    public Paste getModel() {
        // TODO Auto-generated method stub
        return paste;
    }

    public String getPasteid() {
        return pasteid;
    }

    public void setPasteid(String pasteid) {
        this.pasteid = pasteid;
    }

}