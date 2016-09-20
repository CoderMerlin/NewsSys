package com.yc.newssys.dao;

import java.util.List;

import com.yc.newssys.entity.Admin;

public interface IAdminDao {
	/**
	 * 后台管理员登录	
	 * @param admin:要登录的管理员
	 * @return
	 */
	public Admin login(Admin admin);
	
	/**
	 * 添加管理员
	 * @param admin：要添加的管理员
	 * @return
	 */
	public int addAdmin(Admin admin);

	
	/**
	 * 修改管理员
	 * @param admin
	 * @return
	 */
	public int updateAdmin(Admin admin);
	
	
	
	/**
	 * 密码修改
	 * @param aid：要修改的管理员编号
	 * @param oldPwd：旧密码
	 * @param newPwd：新密码
	 * @return
	 */	
	public int updatePwd(int aid,String oldPwd,String newPwd);
	
	
	/**
	 * 删除管理员
	 * @param aid:要删除的鹳狸猿编号，如果要用时删除多个，则用逗号隔开
	 * @return
	 */
	public int del(String aid);
	
	/**
	 * 总记录数
	 * @return
	 */
	public int total();
	
	/**
	 * 分页查询管理员信息
	 * @param pageNo：要查询的页数，如果为null,则查询全部
	 * @param pageSize：页面显示的条数
	 * @return
	 */
	public List<Admin> find(Integer pageNo,Integer pageSize);
	
	
	/**
	 * 查询指定的管理员信息
	 * @param aid：要查询管理员的Id
	 * @return
	 */
	public Admin find(int aid);
	
	
	/**
	 * 查看管理员的id和密码
	 * @param aid
	 * @param pwd
	 * @return
	 */
	public Admin find(int aid,String pwd);
	
}	
