package com.yc.newssys.impl;

import java.util.ArrayList;
import java.util.List;

import com.yc.newssys.dao.DBHelper;
import com.yc.newssys.dao.IUsersDao;
import com.yc.newssys.entity.Users;
import com.yc.newssys.utils.MD5Encryption;

public class UsersDaoImpl implements IUsersDao {
	private DBHelper db=new DBHelper();
	
	
	/**
	 * 登录的会员
	 */
	public Users login(Users users) {
		String sql="select usid,uname,pwd from users where uname=? and pwd=?";
		List<Object> params=new ArrayList<Object>();
		params.add(users.getUname());
		params.add(MD5Encryption.createPassword(users.getPwd()));
		List<Users> list=db.find(sql, params, Users.class);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 添加会员
	 */
	public int addUsers(Users users) {
		String sql="insert into users values(seq_users_usid.nextval,?,?,?,?)";
		List<Object> params=new ArrayList<Object>();
		params.add(users.getUname());
		params.add(MD5Encryption.createPassword(users.getPwd()));
		params.add(users.getEmail());
		params.add(users.getStatus());
		return db.update(sql, params);
	}

	
	
	/**
	 * 修改会员姓名和密码邮箱和状态
	 */
	public int updateUsers(Users users) {
		String sql="update users set uname=?,pwd=?,email=?,status=?  where usid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(users.getUname());
		params.add(MD5Encryption.createPassword(users.getPwd()));
		params.add(users.getEmail());
		params.add(users.getStatus());
		params.add(users.getUsid());
		return db.update(sql, params);
	}
	
	/**
	 * 修改会员密码
	 */
	public int updatePwd(int usid, String oldPwd, String newPwd) {
		String sql="update users set pwd=? where usid=? and pwd=?";
		List<Object> params=new ArrayList<Object>();
		params.add(newPwd);
		params.add(usid);
		params.add(oldPwd);
		return db.update(sql, params);
	}

	/**
	 * 删除会员
	 */
	public int del(String usid) {
		List<Object> params=new ArrayList<Object>();
		String sql;
		if(usid.contains(",") && usid.indexOf("or")<=0){  //说明同时删除多个
			sql="update users set status=0 where usid in("+usid+")";		
		}else{  //删除一个
			sql="update users set status=0 where usid =?";
			params.add(usid);
		}
		return db.update(sql, params);
	}

	/**
	 * 查询所有会员
	 */
	public int total() {
		String sql="select count(usid) from users";
		return (int) db.selectPloymer(sql, null);
	}

	/**
	 * 分页查询
	 */
	public List<Users> find(Integer pageNo, Integer pageSize) {
		String sql="select * from (select a.*,rownum rn from (select * from users u order by usid) a where rownum<=?) b where rn>?";
		List<Object> params=new ArrayList<Object>();
		params.add(pageNo*pageSize);
		params.add((pageNo-1)*pageSize);
		return db.find(sql, params, Users.class);
	}

	
	/**
	 * 查询会员
	 */
	public Users find(int usid) {
		String sql="select usid,uname,pwd from users where usid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(usid);
		List<Users> list =db.find(sql, params, Users.class);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	
	/**
	 * 查询账户是否被注册
	 */
	public Users findZC(String zcop,String zc) {
		String sql="select * from users where "+zcop+"=?";
		List<Object> params=new ArrayList<Object>();
		params.add(zc);
		List<Users> list =db.find(sql, params, Users.class);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
