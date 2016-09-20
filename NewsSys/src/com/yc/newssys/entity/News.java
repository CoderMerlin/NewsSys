package com.yc.newssys.entity;

public class News {
	private int nid;  //新闻编号
	private String title;  //新闻标题
	private String ndate;  //新闻最后修改日期
	private String content; //新闻内容
	private String auth; 	//作者
	private String pic;  //新闻的图片地址
	private int tid=-1;   // 新闻类型id外键
	private int views;  //浏览次数
	private int weight=-1;  //权重
	
	
	private String tname;  //新闻类型名称
	
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public int getNid() {
		return nid;
	}
	
	public int getNids() {
		return nid;
	}
	public void setNid(int nid) {
		this.nid = nid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getTitle20() {
		if(title.length()>20){
			return title.substring(0,20)+"...";
		}
		return title;
	}
	
	
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNdate() {
		return ndate;
	}
	
	public String getNdates() {
		if(ndate !=null && ndate.length()>10){
			return ndate.substring(0,10);
		}
		return ndate;
	}
	
	public void setNdate(String ndate) {
		this.ndate = ndate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getPic() {
		return pic;
	}
	public String getTitle10() {
		if(title.length()>10){
			return title.substring(0,10)+"...";
		}
		return title;
	}
	public String getFirstPic() {
		if(pic!=null && pic.indexOf(",")>0){
			return pic.substring(0,pic.indexOf(","));
		}
		return pic;
	}
	
	
	public String getPicStr() {
		if(pic!=null && pic.indexOf(",")>0){
			String[] pics=pic.split(",");
			String str="";
			for(int i=0;i<pics.length;i++){
				str+="<img str='"+pics[i]+"' width='56px' height='300px'/><br/>";
			}
			return str;
		}
		return "<img src='"+pic+"' width='560px' height='300px'/>";
	}
	
	
	public void setPic(String pic) {
		this.pic = pic;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}

	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auth == null) ? 0 : auth.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((ndate == null) ? 0 : ndate.hashCode());
		result = prime * result + nid;
		result = prime * result + ((pic == null) ? 0 : pic.hashCode());
		result = prime * result + tid;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((tname == null) ? 0 : tname.hashCode());
		result = prime * result + views;
		result = prime * result + weight;
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
		News other = (News) obj;
		if (auth == null) {
			if (other.auth != null)
				return false;
		} else if (!auth.equals(other.auth))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (ndate == null) {
			if (other.ndate != null)
				return false;
		} else if (!ndate.equals(other.ndate))
			return false;
		if (nid != other.nid)
			return false;
		if (pic == null) {
			if (other.pic != null)
				return false;
		} else if (!pic.equals(other.pic))
			return false;
		if (tid != other.tid)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (tname == null) {
			if (other.tname != null)
				return false;
		} else if (!tname.equals(other.tname))
			return false;
		if (views != other.views)
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	
	
	
	@Override
	public String toString() {
		return "News [nid=" + nid + ", title=" + title + ", ndate=" + ndate
				+ ", content=" + content + ", auth=" + auth + ", pic=" + pic
				+ ", tid=" + tid + ", views=" + views + ", weight=" + weight
				+ ", tname=" + tname + "]";
	}
	

	
	public News(int nid, String title, String ndate, String content,
			String auth, String pic, int tid, int views, int weight,
			String tname) {
		super();
		this.nid = nid;
		this.title = title;
		this.ndate = ndate;
		this.content = content;
		this.auth = auth;
		this.pic = pic;
		this.tid = tid;
		this.views = views;
		this.weight = weight;
		this.tname = tname;
	}
	public News() {
		super();
	}
	
	
	
	
}
