package com.yc.newssys.dao;

import java.util.List;

import com.yc.newssys.entity.Users;

public interface IUsersDao {
	/**
	 * 会员员登录	
	 * @param Users:要登录的会员
	 * @return
	 */
	public Users login(Users users);
	
	/**
	 * 添加会员
	 * @param Users：要添加的会员
	 * @return
	 */
	public int addUsers(Users users);

	/**
	 * 密码修改
	 * @param aid：要修改的会员编号
	 * @param oldPwd：旧密码
	 * @param newPwd：新密码
	 * @return
	 */
	public int updatePwd(int usid,String oldPwd,String newPwd);
	
	
	public int updateUsers(Users users);
	
	/**
	 * 删除会员
	 * @param aid:要删除的会员编号，如果要用时删除多个，则用逗号隔开
	 * @return
	 */
	public int del(String usid);
	
	/**
	 * 总记录数
	 * @return
	 */
	public int total();
	
	/**
	 * 分页查询会员信息
	 * @param pageNo：要查询的页数，如果为null,则查询全部
	 * @param pageSize：页面显示的条数
	 * @return
	 */
	public List<Users> find(Integer pageNo,Integer pageSize);
	
	
	/**
	 * 查询指定的会员信息
	 * @param aid：要查询会员的Id
	 * @return
	 */
	public Users find(int usid);
	
	
	/**
	 * 查询数据库中是否有此账号名
	 * @param uname
	 * @return
	 */
	public Users findZC(String zcop,String zc);
	
}
