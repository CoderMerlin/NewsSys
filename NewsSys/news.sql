--管理员信息表
create table admin(
	aid int primary key, --管理员id
	aname varchar2(50) unique not null,  --管理员名称
	pwd varchar2(20)  --管理员密码
);
create sequence seq_admin_aid start with 1001 increment by 1;
insert into admin values(seq_admin_aid.nextval,'zs','123');

drop sequence seq_admin_aid;
drop table admin;
select * from admin;



--会员信息
create table users(
	usid int primary key, --会员编号
	uname varchar2(100) unique not null,  --会员名称
	pwd varchar2(20), --登录密码
	email varchar2(100) unique not null,   --注册邮箱
	status  int --状态
);

delete users where usid=1021;

create sequence seq_users_usid start with 1001 increment by 1;
insert into users values(seq_users_usid.nextval,'a','a','a',1);
update users set status=0 where usid in(1002,1003);
update users set status=0 where usid =1002;

update users set status=110 where usid =1002;
select * from users;
commit;
--新闻类型
create table newsType(
	tid int primary key,   --类型编号
	tname varchar2(100),   --类型名称
	status int --状态
);

select n.*,tname from news n,newstype t where n.tid=t.tid and nid=1021;

create sequence seq_newsType_tid start with 1001 increment by 1;
insert into newsType values(seq_newsType_tid.nextval,'娱乐','1');  --1表示存在
update newsType set status=0 where tid=1002;
select * from newsType;

--新闻内容
create table news(
	nid int primary key,   --新闻编号
	title varchar2(400) not null,   --新闻标题
	ndate date, --最后修改日期时间
	content clob,  --新闻内容
	auth varchar2(100),  --作者
	pic varchar2(4000),  --图片地址
	tid int 
		constraint FK_news_newsType_tid references newsType(tid),
	views int,  --浏览次数
	weight int --权重
	--status int  --状态
);

select n.*,tname from news n,newstype t where n.tid=t.tid and nid=1001;
create sequence seq_news_nid start with 1001 increment by 1;
select * from news;

select aid,aname,pwd from admin a where aname='a' and pwd='DF2107E57ED58419';

select * from (select a.*,rownum rn from (select * from users u order by usid) a where rownum<=1) b where rn>0

select uname from users where uname='z';


select aid,aname,pwd from admin where  aname='a' and aid=1041;
