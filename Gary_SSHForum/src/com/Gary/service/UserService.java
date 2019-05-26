package com.Gary.service;

import java.math.BigInteger;
import java.util.List;

import com.Gary.dao.UserDao;
import com.Gary.domain.User;
import com.Gary.utils.PageBean;

public class UserService {

	private UserDao userDao;

	public PageBean getUserPageBean(Integer currentPage) {
		Integer totalCount = userDao.findAllUserNum();
		PageBean userPageBean = new PageBean(currentPage, totalCount, 8);
		List<User> list = userDao.getUserPageBean();
		userPageBean.setList(list);
		
		return userPageBean;
	}

	
	public User findUserByUsernameReturnUser(User user) {
		
		return userDao.findUserByUsernameReturnUser(user);
	}

	
	public int checkUser(User user) {
		User temp = userDao.findUserByUsernameReturnUser(user);
		//用户名不存在
		if(temp==null)
		{
			return 1;
		}
		//判断密码是否相同
		if(temp.getPassword().equals(user.getPassword()))
		{
			if(temp.getState()==1)
			{
				//登陆成功
				return 0;
			}else {
				//没有激活
				return 3;
			}
		}else {
			//密码错误
			return 2;
		}

	}
	
	public UserDao getUserDao() {
		return userDao;
	}
	
	public void activeUser(String userCode) {
		// TODO Auto-generated method stub
		userDao.activeUser(userCode);
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void addUser(User user) {
		// TODO Auto-generated method stub
		 userDao.addUser(user);
	}

	public boolean findUserByUsername(String username) {
		// TODO Auto-generated method stub
		Long count = userDao.findUserByUsernameReturnNum(username);
		if(count==0)
			return true;
		else
			return false;
	}

}
