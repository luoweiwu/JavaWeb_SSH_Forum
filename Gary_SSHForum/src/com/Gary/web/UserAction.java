package com.Gary.web;

import java.util.Random;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

import com.Gary.domain.User;
import com.Gary.service.UserService;
import com.Gary.utils.MailUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

//采用模型驱动 泛型User
public class UserAction extends ActionSupport implements ModelDriven<User>{

	public User user =  new User();
	
	private String userCode;
	
	private UserService userService;
	
	//用户登陆
	public String Login() throws Exception {
	
		int success = userService.checkUser(user);
		
		if(success == 0)
		{
			
			ActionContext.getContext().getSession().put("user", userService.findUserByUsernameReturnUser(user));
			return "toIndex";
		}
		else if(success ==1) {
			//用户名不存在
			ActionContext.getContext().put("error","用户名不存在！！");
			return "login";
		}
		else if(success ==2) {
			//密码错误
			ActionContext.getContext().put("error", "密码错误");
			return "login";
		}
		else if(success ==3) {
			//用户未激活
			ActionContext.getContext().put("error","用户未激活！！");
			return "login";
		}else {
			return "error";
		}
		
	}
	
	//用户登出
	public String logout() throws Exception {
		
		ActionContext.getContext().getSession().remove("user");
		
		return "toLogin";
	}
	
	//用户激活
	public String active() throws Exception {
	
		userService.activeUser(userCode);
		
		return "toLogin";	
	}
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	//校验用户名是否存在
	public String checkUsername() throws Exception {
		//获得用户username
		boolean success = userService.findUserByUsername(user.getUsername());
		//获得原生Servlet对象
		ServletActionContext.getResponse().getWriter().write("{\"success\":"+success+"}");
		
		return null;
	}
	
	
	//用户注册
	public String register() throws Exception {
		// TODO Auto-generated method stub
	
		//没有的数据手动封装
		user.setState(0);
		user.setCode(UUID.randomUUID().toString());
		Random r = new Random();
		user.setImage("/images/"+r.nextInt(21)+".gif");
		
		user.setLevel(1);
		user.setCoin(1000);
			
		//是否添加成功
		userService.addUser(user);
	
		MailUtils.sendMail(user.getEmail(), "请激活", "恭喜您注册成功，请点击下面的链接激活！！ <a href='http://localhost:8080/Gary_SSHForum/UserAction_active?userCode="+user.getCode()+"'>点击这里</a>");
		
		return "toRegisterSuccess";	
		
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getModel() {
		return user;
	}

}
