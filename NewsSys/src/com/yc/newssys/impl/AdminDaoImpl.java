package com.yc.newssys.impl;

import java.util.ArrayList;
import java.util.List;

import com.yc.newssys.dao.DBHelper;
import com.yc.newssys.dao.IAdminDao;
import com.yc.newssys.entity.Admin;
import com.yc.newssys.utils.MD5Encryption;

public class AdminDaoImpl implements IAdminDao{
	private DBHelper db = new DBHelper();
	
	/**
	 * 管理员登录
	 */
	public Admin login(Admin admin) {
		String sql="select aid,aname,pwd from admin where aname=? and pwd=?";
		List<Object> params=new ArrayList<Object>();
		params.add(admin.getAname());
		params.add(MD5Encryption.createPassword(admin.getPwd()));
		List<Admin> list=db.find(sql, params, Admin.class);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 添加管理员
	 */
	public int addAdmin(Admin admin) {
		String sql="insert into admin values(seq_admin_aid.nextval,?,?)";
		List<Object> params=new ArrayList<Object>();
		params.add(admin.getAname());
		params.add(MD5Encryption.createPassword(admin.getPwd()));
		return db.update(sql, params);
	}

	
	/**
	 * 修改管理员姓名和密码
	 */
	public int updateAdmin(Admin admin) {
		String sql="update admin set aname=?,pwd=? where aid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(admin.getAname());
		params.add(MD5Encryption.createPassword(admin.getPwd()));
		params.add(admin.getAid());
		return db.update(sql, params);
	}
	
	
	
	/**
	 * 修改管理员密码
	 */
	public int updatePwd(int aid, String oldPwd, String newPwd) {
		String sql="update admin set pwd=? where aid=? and pwd=?";
		List<Object> params=new ArrayList<Object>();
		params.add(newPwd);
		params.add(aid);
		params.add(oldPwd);
		return db.update(sql, params);
	}
	
	/**
	 * 删除管理员
	 */
	public int del(String aid) {
		List<Object> params=new ArrayList<Object>();
		String sql;
		if(aid.contains(",") && aid.indexOf("or")<=0){  //说明同时删除多个
			sql="delete from admin where aid in("+aid+")";
		}else{  //删除一个
			sql="delete from admin where aid=?";
			params.add(aid);
		}
		return db.update(sql, params);
	}

	/**
	 * 查询所有的管理员
	 */
	public int total() {
		String sql="select count(aid) from admin";
		return (int) db.selectPloymer(sql, null);
	}
	
	/**
	 * 分页查询
	 */
	public List<Admin> find(Integer pageNo, Integer pageSize) {
		String sql="select * from (select a.*,rownum rn from (select * from admin a order by aid) a where rownum<=?) b where rn>?";
		List<Object> params=new ArrayList<Object>();
		params.add(pageNo*pageSize);
		params.add((pageNo-1)*pageSize);
		return db.find(sql, params, Admin.class);
	}

	public Admin find(int aid) {
		String sql="select aid,aname,pwd from admin where aid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(aid);
		List<Admin> list =db.find(sql, params, Admin.class);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 查找密码后id
	 */
	public Admin find(int aid, String pwd) {
		String sql="select aid,aname,pwd from admin where aid=? and pwd=?";
		List<Object> params=new ArrayList<Object>();
		params.add(aid);
		params.add(MD5Encryption.createPassword(pwd));
		List<Admin> list =db.find(sql, params, Admin.class);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	

}
