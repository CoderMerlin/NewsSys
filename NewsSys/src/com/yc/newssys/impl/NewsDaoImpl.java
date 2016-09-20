package com.yc.newssys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yc.newssys.dao.DBHelper;
import com.yc.newssys.dao.INewsDao;
import com.yc.newssys.entity.News;

public class NewsDaoImpl implements INewsDao {
	private DBHelper db = new DBHelper();
	
	/**
	 * 添加新闻
	 */
	public int addNews(News news) {
		String sql="insert into news values(seq_news_nid.nextval,?,to_date(?,'yyyy-mm-dd'),?,?,?,?,0,?)";
		List<Object> params=new ArrayList<Object>();
		params.add(news.getTitle());
		params.add(news.getNdate());
		params.add(news.getContent());
		params.add(news.getAuth());
		params.add(news.getPic());
		params.add(news.getTid());
		params.add(news.getWeight());
		return db.update(sql, params);
	}

	/**
	 * 更新新闻
	 */
	public int update(News news) {
		String sql="update news set nid=nid";
		List<Object> params=new ArrayList<Object>();
		
		String temp=news.getTitle();
		if(temp!=null && !"".equals(temp)){
			sql+=",title=?";
			params.add(temp);
		}
		
		temp=news.getNdate();
		if(temp!=null && !"".equals(temp)){
			sql+=",ndate=to_date(?,'yyyy-mm-dd')";
			params.add(temp);
		}
		
		temp=news.getContent();
		if(temp!=null && !"".equals(temp)){
			sql+=",content=?";
			params.add(temp);
		}
		
		temp=news.getAuth();
		if(temp!=null && !"".equals(temp)){
			sql+=",auth=?";
			params.add(temp);
		}
		
		temp=news.getPic();
		if(temp!=null && !"".equals(temp)){
			sql+=",pic=?";
			params.add(temp);
		}
		
		temp=String.valueOf(news.getTid());
		if(!"-1".equals(temp)){
			sql+=",tid=?";
			params.add(temp);
		}
		
		temp=String.valueOf(news.getWeight());
		if(!"-1".equals(temp)){
			sql+=",weight=?";
			params.add(temp);
		}
		sql+="where nid=?";		
		params.add(news.getNid());
		return db.update(sql, params);
	}

	/**
	 * 删除新闻
	 * 
	 */
	public int del(String nid) {
		List<Object> params=new ArrayList<Object>();
		String sql;
		if(nid.contains(",") && nid.indexOf("or")<=0){ //说明同时删除多个
			sql="delete from news where nid in("+nid+")";
		}else{
			sql="delete from news where nid=?";
			params.add(nid);
		}
		return db.update(sql, params);
	}
	
	
	/**
	 * 查询总记录数
	 */
	public int total() {
		String sql="select count(nid) from news";
		return (int)db.selectPloymer(sql, null);
	}

	/**
	 * 查询
	 */
	public List<News> find(Integer pageNo, Integer pageSize) {
		String sql="select * from (select a.*,rownum rn from (select n.*,tname from news n,newstype t " +
				"where n.tid=t.tid order by weight desc,ndate desc) a where rownum<=?)b where rn>?";
		List<Object> params=new ArrayList<Object>();
		params.add(pageNo*pageSize);
		params.add((pageNo-1)*pageSize);
		return db.find(sql, params, News.class);
	}
	
	public News find(int nid) {
		//String sql="select * from news where nid=?";
		String sql="select n.*,tname from news n,newstype t where n.tid=t.tid and nid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(nid);
		List<News> news=db.find(sql, params, News.class);
		if(news!=null && news.size()>0){
			return news.get(0);
		}else{
			return null;
		}
	}
	
	
	public List<News> find(int tid, Integer pageNo, Integer pageSize) {
		String sql="select * from (select a.*,rownum fn from (select n.*,tname from news n,newstype t" +
				"where n.tid=t.tid and tid=? order by weight desc,ndate desc) a where rownum<=?) b where rn>=?";
		
		List<Object> params=new ArrayList<Object>();
		params.add(tid);
		params.add(pageNo*pageSize);
		params.add((pageNo-1)*pageSize);
		return db.find(sql, params, News.class);
	}

	/**
	 * 更新新闻的浏览次数
	 */
	public int updateViews(int nid) {
		String sql="update news set views=views+1 where nid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(nid);
		return db.update(sql,params);
	}

	/**
	 * 查询首页的新闻信息
	 */
	public List<News> findIndex(Integer pageNo, Integer pageSize) {
		String sql="select * from (select a.*,rownum rn from (select title,nid,ndate,pic,tname from news n,newstype t " +
				"where n.tid=t.tid order by weight desc,ndate desc) a where rownum<=?)b where rn>?";
		List<Object> params=new ArrayList<Object>();
		params.add(pageNo*pageSize);
		params.add((pageNo-1)*pageSize);
		return db.find(sql, params, News.class);
	}

	/**
	 * 根据新闻类型查找
	 */
	public List<News> find(String tname, Integer pageNo, Integer pageSize) {
		String [] tnames=tname.split(",");
		String sql="";
		List<Object> params=new ArrayList<Object>();
		int i=0;
		for(i=0;i<tnames.length-1;i++){
			sql+="select * from (select a.*,rownum rn from (select nid,title,tname from news n,newstype t where n.tid=t.tid  and tname=? order by weight desc,ndate desc) a where rownum<=?)b where rn>? union ";
			params.add(tnames[i]);
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}
		sql+="select * from (select a.*,rownum rn from (select nid,title,tname from news n,newstype t where n.tid=t.tid  and tname=? order by weight desc,ndate desc) a where rownum<=?)b where rn>?";
		params.add(tnames[i]);
		params.add(pageNo*pageSize);
		params.add((pageNo-1)*pageSize);
		return db.find(sql, params, News.class);
	}

	
	/**
	 * 根据标题 日期 类型 查询新闻
	 */
	public List<News> find(String title, String date, Map<String, String> map,Integer pageNo, Integer pageSize) {
		String sql="select * from (select a.*,rownum rn from (select title,nid,ndate,auth,views from news n,newstype t " +
				"where n.tid=t.tid ";
		List<Object> params=new ArrayList<Object>();
		if(title!=null && !"".equals(title)){
			sql+=" and title like ?";
			params.add("%"+title+"%");
		}
		
		if(date!=null && !"".equals(date)){
			sql+=" and ndate=to_date(?,'yyyy-mm-dd')";
			params.add(date);
		}
		
		if(map!=null && map.size()>0){
			Set<String> keys=map.keySet();
			for(String key:keys){
				sql+=" and "+key+"=?";
				params.add(map.get(key));
			}
		}
		sql+=" order by weight desc,ndate desc) a where rownum<=?)b where rn>?";
		params.add(pageNo*pageSize);
		params.add((pageNo-1)*pageSize);
		return db.find(sql, params, News.class);
	}

}
