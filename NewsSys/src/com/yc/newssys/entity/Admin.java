package com.yc.newssys.entity;

public class Admin {
	private int aid;
	private String aname;
	private String pwd;
	
	
	public int getAid() {
		return aid;
	}
	
	public int getAids() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + aid;
		result = prime * result + ((aname == null) ? 0 : aname.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
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
		Admin other = (Admin) obj;
		if (aid != other.aid)
			return false;
		if (aname == null) {
			if (other.aname != null)
				return false;
		} else if (!aname.equals(other.aname))
			return false;
		if (pwd == null) {
			if (other.pwd != null)
				return false;
		} else if (!pwd.equals(other.pwd))
			return false;
		return true;
	}
	
	
	public Admin(int aid, String aname, String pwd) {
		super();
		this.aid = aid;
		this.aname = aname;
		this.pwd = pwd;
	}
	
	
	public Admin() {
		super();
	}
	@Override
	public String toString() {
		return "Admin [aid=" + aid + ", aname=" + aname + ", pwd=" + pwd + "]";
	}
	
	
}
