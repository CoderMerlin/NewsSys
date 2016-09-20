package com.yc.newssys.dao;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.yc.newssys.utils.LogUtil;


/**
 * DBHelper
 * @author rdz
 *
 */
public class DBHelper {
	private Connection con=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	
	/**
	 * 获取连接
	 * @return 连接
	 */
	public Connection getConnection(){
		try { 
			Context ctx=new InitialContext();
			DataSource ds=(DataSource) ctx.lookup("java:comp/env/jdbc/exam");
			con=ds.getConnection();
		} catch (NamingException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		}
		
		return con;
	}
	
	/**
	 * 关闭资源
	 * @param con 要关闭的连接
	 * @param pstmt 要关闭的预编译快
	 * @param rs 要关闭的结果集
	 */
	public void closeAll(Connection con,PreparedStatement pstmt,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
				e.printStackTrace();
			}
		}
		
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
				e.printStackTrace();
			}
		}
		
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 给占位符赋值
	 * @param pstmt 要操作的sql语句
	 * @param params 要执行的sql语句中对应的占位符的值 为null则没有占位符
	 * @throws SQLException
	 */
	public void setValue(PreparedStatement pstmt,List<Object> params) throws SQLException{
		//给占位符赋值
		if(params!=null && params.size()>0){
			Object obj=null;
			String type="";
			for(int i=0;i<params.size();i++){
				obj=params.get(i);
				if(obj!=null){
					type=obj.getClass().getName();
					if("[B".equals(type)){
						pstmt.setBytes(i+1, (byte[]) obj);
					} else{
						pstmt.setString(i+1, String.valueOf(obj));
					}
				} else{
					pstmt.setString(i+1,String.valueOf(obj) );
				}
			}
		}
	}
	
	/**
	 * 更新操作
	 * @param sql 要操作的sql语句
	 * @param params 要执行的sql语句中对应的占位符的值 为null则没有占位符
	 * @return
	 */
	public int update(String sql,List<Object> params){
		int result=0;
		
		try {
			con=this.getConnection();	
			pstmt=con.prepareStatement(sql);
			
			this.setValue(pstmt, params);
			
			result=pstmt.executeUpdate(); //执行语句
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		} finally{
			this.closeAll(con, pstmt, null);
		}
		return result;
	}
	
	/**
	 * 查询结果集
	 * @param sql 要执行的sql语句
	 * @param params 要执行的sql语句中对应的占位符的值 为null则没有占位符
	 * @return
	 */
	public List<Map<String, Object> > select(String sql,List<Object> params){
		List<Map<String, Object> > list=new ArrayList<Map<String, Object> >();
		Map<String, Object> map=null; //以列名为键，以对应的值为键
		
		try {
			con=this.getConnection();
			pstmt=con.prepareStatement(sql);
			
			this.setValue(pstmt, params);
			
			rs=pstmt.executeQuery();
			
			ResultSetMetaData rsmd= rs.getMetaData();//获取元数据
			//从元数据中获取列的信息
			
			String[] colNames=new String[ rsmd.getColumnCount() ];;
			for(int i=0;i<colNames.length;i++){
				colNames[i]=rsmd.getColumnName(i+1);
			}
			
			while(rs.next()){
				map=new HashMap<String, Object>();
				Object obj=null;
				String type;
				if(colNames!=null && colNames.length>0){ 
					//循环取出每一个值
					for(String s:colNames){
						obj=rs.getObject(s);
						if(obj!=null){
							type=obj.getClass().getName();
							if("oracle.sql.BLOB".equals(type)){
								Blob blob=rs.getBlob(s);
								byte[] bt=null;
								InputStream is=blob.getBinaryStream();
								if(is!=null){
									bt=new byte[(int) blob.length()];
									try {
										is.read(bt);
									} catch (IOException e) {
										e.printStackTrace();
									} finally{
										if(is!=null){
											try {
												is.close();
											} catch (IOException e) {
												e.printStackTrace();
											}
										}
									}
									map.put(s, bt);
								} else{
									map.put(s, null);
								}
							} else{
								map.put(s,String.valueOf(obj));
							}
						} else{
							map.put(s, null);
						}
					}
				}
				list.add(map);
			}
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		} finally{
			this.closeAll(con, pstmt, rs);
		}
		return list;
	}
	
	/**
	 * 聚合查询
	 * @param sql　要执行的sql语句
	 * @param params　要执行的sql语句中对应的占位符的值 为null则没有占位符
	 * @return
	 */
	public double selectPloymer(String sql,List<Object> params){
		double result=0;
		
		con=this.getConnection();
		try {
			pstmt=con.prepareStatement(sql);
			this.setValue(pstmt, params);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				result=rs.getDouble(1);
			}
			
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		} finally{
			this.closeAll(con, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 聚合查询
	 * @param sql　要执行的sql语句
	 * @param params　要执行的sql语句中对应的占位符的值 为null则没有占位符
	 * @return
	 */
	public List<Double> selectPloymers(String sql,List<Object> params){
		List<Double> result=new ArrayList<Double>();;
		
		con=this.getConnection();
		try {
			pstmt=con.prepareStatement(sql);
			this.setValue(pstmt, params);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				for(int i=0;i<rs.getMetaData().getColumnCount();i++){
					result.add(rs.getDouble(i+1));
				}
			}
			
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		} finally{
			this.closeAll(con, pstmt, rs);
		}
		return result;
	}
	
	/**
	 * DDL操作
	 * @param sql 要执行的语句
	 * @return
	 */
	public boolean createOp(String sql){
		boolean bl=false;
		try {
			con=this.getConnection();
			pstmt=con.prepareStatement(sql);
			bl=pstmt.execute();
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		} finally{
			this.closeAll(con, pstmt, rs);
		}
		return bl;
	}
																					
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List<T> find(String sql,List<Object> params,Class c){
		List<T> list=new ArrayList<T>();
		
		try {
			con=this.getConnection();
			pstmt=con.prepareStatement(sql);
			this.setValue(pstmt, params);
			rs=pstmt.executeQuery();
			
			//获取返回的结果集中的所有列的内容
			ResultSetMetaData rsmd=rs.getMetaData(); //获取元数据，包含类的名称、类型等信息
			
			String[] colsName=new String[rsmd.getColumnCount()];
			
			//循环取出每一个列的名字存入到colsName数组中
			for(int i=0;i<colsName.length;i++){
				colsName[i]=rsmd.getColumnName(i+1);
			}
			
			//获取给定的的类的所有共有的方法
			Method[] methods=c.getMethods();
			//set+列名 与给定的类中的方法忽略大小写循环比较，如果能匹配的上，则激活该方法将当前列的值注入到对象的对应属性中
			T t; //实例化对象
			String methodName=null; //方法名
			String colName=null; //列名
			String typeName=null; //参数类型名
			Object val=null; //当前循环的列的值
			
			while(rs.next()){
				t=(T) c.newInstance();
				for(int i=0;i<colsName.length;i++){
					//获取当前循环的列的列名
					colName=colsName[i];
					
					//根据列名取出当前列中的值
					val=rs.getObject(colName);
					
					for(Method md:methods){ //循环跟方法名比较
						methodName=md.getName();
						if(("set"+colName).equalsIgnoreCase(methodName) && val!=null){
							//获取返回值的类型
							typeName=val.getClass().getName();
							if("java.math.BigDecimal".equals(typeName)){
								//激活当前方法注值
								md.invoke(t, rs.getInt(colName));
							} else{
								md.invoke(t, rs.getString(colName));
							}
						}
					}
				}
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}
}
