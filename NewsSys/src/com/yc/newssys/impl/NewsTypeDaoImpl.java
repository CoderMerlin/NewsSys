package com.yc.newssys.impl;

import java.util.ArrayList;
import java.util.List;

import com.yc.newssys.dao.DBHelper;
import com.yc.newssys.dao.INewsTypeDao;
import com.yc.newssys.entity.NewsType;

public class NewsTypeDaoImpl implements INewsTypeDao{
	private DBHelper db = new DBHelper();
	
	/**
	 * 添加新闻类型
	 */
	public int addNewsType(NewsType newsType) {
		String sql="insert into newsType values(seq_newsType_tid.nextval,?,?)";
		List<Object> params=new ArrayList<Object>();
		params.add(newsType.getTname());
		params.add(newsType.getStatus());
		return db.update(sql, params);
	}

	/**
	 * 更新新闻类型
	 */
	public int update(NewsType newsType) {
		String sql="update newsType set tid=tid";
		List<Object> params=new ArrayList<Object>();
		if(newsType.getTname()!=null){
			sql+=",tname=?";
			params.add(newsType.getTname());
		}
		
		if(newsType.getStatus()!=-1){
			sql+=",status=?";
			params.add(newsType.getStatus());
		}
		
		sql+="where tid=?";
		params.add(newsType.getTid());
		
		return db.update(sql, params);
	}

	
	//这是错的
	public int del(String tid) {
		List<Object> params=new ArrayList<Object>();
		String sql;
		if(tid.contains(",") && tid.indexOf("or")<=0){ //说明同时删除多个
			//sql="delete from admin where aid in("+aid+")";
			sql="update newsType set status=0 where tid in("+tid+")";
		}else{
			sql="update newsType set status=0 where tid=?";
			params.add(tid);
		}
		return db.update(sql, params);
	}

	/**
	 * 查询所有的新闻类型
	 */
	public int total() {
		String sql="select count(tid) from newsType where status=1";
		return (int) db.selectPloymer(sql, null);
	}

	public List<NewsType> find(Integer pageNo, Integer pageSize) {
		String sql="select * from (select a.*,rownum rn from (select * from newsType where status=1 order by tid) a where rownum<=?) b where rn>?";
		List<Object> params=new ArrayList<Object>();
		params.add(pageNo*pageSize);
		params.add((pageNo-1)*pageSize);
		return db.find(sql, params, NewsType.class);
	}

	public NewsType find(int tid) {
		String sql="select tid,tname,status from newsType where tid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(tid);
		List<NewsType> list =db.find(sql, params, NewsType.class);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public List<NewsType> finds() {
		String sql="select tid,tname,status from newsType where status=1";
		return db.find(sql, null, NewsType.class);
	}

}
