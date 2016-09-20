package com.yc.newssys.dao;

import java.util.List;
import java.util.Map;

import com.yc.newssys.entity.News;

public interface INewsDao {
	/**
	 * 添加新闻信息
	 * @param News：要添加的新闻
	 * @return
	 */
	public int addNews(News news);

	/**
	 * 修改新闻
	 * @param aid：要修改的新闻
	 * @return
	 */
	public int update(News news);
	
	
	/**
	 * 删除新闻
	 * @param tid:要删除的新闻编号，如果要用时删除多个，则用逗号隔开
	 * @return
	 */
	public int del(String nid);
	
	/**
	 * 总记录数
	 * @return
	 */
	public int total();
	
	/**
	 * 分页查询新闻信息
	 * @param pageNo：要查询的页数，如果为null,则查询全部
	 * @param pageSize：页面显示的条数
	 * @return
	 */
	public List<News> find(Integer pageNo,Integer pageSize);
	
	/**
	 * 查询指定的新闻信息
	 * @param usid：要查询的新闻编号
	 * @return
	 */
	public News find(int nid);
	
	/**
	 * 根据新闻类型分页查询
	 * @param tid：要查询的新闻类型
	 * @param pageNo：要查询的页数
	 * @param pageSize：每页显示的条数
	 * @return
	 */
	public List<News> find(int nid,Integer pageNo,Integer pageSize);
	
	/**
	 * 更新改浏览次数
	 * @param nid:新闻的id
	 * @return
	 */
	public int updateViews(int nid);
	
	
	/**
	 * 查询首页的新闻
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<News> findIndex(Integer pageNo,Integer pageSize);
	
	/**
	 * 根据类型查询新闻
	 * 
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<News> find(String tname,Integer pageNo,Integer pageSize);
	
	
	/**
	 * 根据标题 日期 类型 查询新闻
	 * @param title
	 * @param date
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<News> find(String title,String date,Map<String,String> map,Integer pageNo,Integer pageSize );
}
