package com.yc.newssys.dao;

import java.util.List;

import com.yc.newssys.entity.NewsType;

public interface INewsTypeDao {
	/**
	 * 添加新闻类型
	 * @param NewsType：要添加的新闻员
	 * @return
	 */
	public int addNewsType(NewsType newsType);

	/**
	 * 修改新闻类型
	 * @param aid：要修改的新闻类型编号
	 * @param oldPwd：旧密码
	 * @param newPwd：新密码
	 * @return
	 */
	public int update(NewsType newsType);
	
	
	/**
	 * 删除新闻类型
	 * @param aid:要删除的新闻类型编号，如果要用时删除多个，则用逗号隔开
	 * @return
	 */
	public int del(String tid);
	
	/**
	 * 总记录数
	 * @return
	 */
	public int total();
	
	/**
	 * 分页查询新闻类型信息
	 * @param pageNo：要查询的页数，如果为null,则查询全部
	 * @param pageSize：页面显示的条数
	 * @return
	 */
	public List<NewsType> find(Integer pageNo,Integer pageSize);
	
	
	/**
	 * 查询指定的新闻累心信息
	 * @param aid：要查询新闻累心的的Id
	 * @return
	 */
	public NewsType find(int tid);
	
	/**
	 * 查询所有新闻类型
	 * @return
	 */
	public List<NewsType> finds();
}
