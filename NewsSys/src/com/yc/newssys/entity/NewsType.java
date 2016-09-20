package com.yc.newssys.entity;


/**
 * 新闻类型
 * @author Administrator
 *
 */
public class NewsType {
	private int tid;  //类型编号
	private String tname; //类型名称
	private int status;  //状态
	
	
	public int getTid() {
		return tid;
	}
	public int getTids() {
		return tid;
	}
	
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + status;
		result = prime * result + tid;
		result = prime * result + ((tname == null) ? 0 : tname.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsType other = (NewsType) obj;
		if (status != other.status)
			return false;
		if (tid != other.tid)
			return false;
		if (tname == null) {
			if (other.tname != null)
				return false;
		} else if (!tname.equals(other.tname))
			return false;
		return true;
	}
	
	
	
	
	
	@Override
	public String toString() {
		return "NewsType [tid=" + tid + ", tname=" + tname + ", status="
				+ status + "]";
	}
	
	
	public NewsType(int tid, String tname, int status) {
		super();
		this.tid = tid;
		this.tname = tname;
		this.status = status;
	}
	
	
	public NewsType() {
		super();
	}
	
	
	
	
	
}
