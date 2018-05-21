create table folder(
	id serial primary key,
	name varchar(30),
	remark text,
	file_type int,
	parrent_id bigint,
	file_id bigint,
	create_date date,
	size text,
	user_id bigint(20)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table sign_user_info(
	id serial primary key,
	mobile varchar(30),
	code varchar(30),
	register_date date,
	system_type int
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table user_project(
	id serial primary key,
	user_id bigint(20) unsigned not null,
	project_id bigint(20) unsigned not null,
	foreign key(user_id) references user(id) on delete cascade,
	foreign key(project_id) references project(id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table construction_log(
	id serial primary key,
	construction_date datetime,
	create_date datetime,
	weather varchar(250),
	content text,
	pic_id varchar(50),
	user_id bigint(20) unsigned not null,
	project_id bigint(20) unsigned not null,
	foreign key(user_id) references user(id) on delete cascade,
	foreign key(project_id) references project(id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

////物资表
create table material(
	id serial primary key,
	project_id bigint(20) unsigned not null,
	user_id bigint(20) unsigned not null,
	material_type varchar(50),
	size varchar(250),
	material_name varchar(250),
	unit varchar(20),
	in_num int,
	out_num int,
	unicode varchar(200),
	leave_num int,
	create_date date,
	remark varchar(250),
	foreign key(user_id) references user(id) on delete cascade,
	foreign key(project_id) references project(id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/////物资上传文件表
create table material_file(
	id serial primary key,
	user_id bigint(20) unsigned not null,
	file_id bigint(20) unsigned not null,
	project_id bigint(20) unsigned not null,
	upload_date datetime,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/////物资类别表
create table material_type(
	id serial primary key,
	user_id bigint(20) unsigned not null,
	name varchar(250),
	create_date datetime,
	update_date datetime,
	remark varchar(50),
	foreign key(user_id) references user(id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

////物资操作记录表
create table material_log(
	id serial primary key,
	material_id bigint(20) unsigned not null,
	user_id bigint(20) unsigned not null,
	log_date date,
	intro varchar(1000),
	log_type int,
	num int,
	sum int,
	foreign key(user_id) references user(id) on delete cascade,
	foreign key(material_id) references material(id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/////菜单表
create table menu_list(
	id serial primary key,
	menu_path varchar(50),
	menu_name varchar(250),
	pid bigint(20),
	create_date datetime,
	create_user bigint(20),
	remark varchar(50) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/////权限表
create table role(
	id serial primary key,
	name varchar(50),
	menu_list varchar(50),
	read_state int,
	create_date datetime,
	update_date datetime,
	create_user bigint(20) unsigned not null,
	remark varchar(50)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/////////部门表
create table department(
	id serial primary key,
	name varchar(50),
	create_user bigint(20) unsigned not null,
	create_date datetime,
	remark varchar(50),
	foreign key(create_user) references user(id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

//////用户
create table user(
    id serial primary key,
    user_name varchar(20),
    password varchar(128),
    real_name varchar(32),
    user_type int,#0 admin 1 user 2 visitor
    email varchar(64),
    tel varchar(20),
    register_date datetime,
    update_date datetime,
    team_id bigint(20),
    menu_item_list varchar(50),#app
    role_id bigint(20),
    department_id bigint(20),
    user_icon bigint(20),
    system_id int,
    system_type int,
    user_icon_url varchar(250) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE user add UNIQUE KEY(user_name); 
ALTER TABLE user add UNIQUE KEY(tel); 
/////班组信息表
create table user_team(
	id serial primary key,
	name varchar(50),
	create_user bigint(20) unsigned not null,
	create_date datetime,
	remark varchar(250),
	foreign key(create_user) references user(id) on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

//////通知关系表
create table notice(
	id serial primary key,
	notice_type int,##通知关联表：0 质量问题、 1 安全问题、 2 施工任务单、 3 预付单
	about_id bigint(20) unsigned not null,
	user_id bigint(20) unsigned not null,
	read_state int,
	create_date datetime,
	update_date datetime,
	remark varchar(50)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

//////考勤模板表
create table attence_model(
	id serial primary key,
	start_time varchar(50),
	end_time varchar(50),
	place varchar(50),
	place_lat double,
	place_lng double,
	attence_day varchar(250),
	attence_range int,
	user_id bigint(20) unsigned not null,
	project_id bigint(20) unsigned not null,
	create_date datetime
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
//////考勤记录表
create table attence_log(
	id serial primary key,
	start_work_time varchar(50),
	end_work_time varchar(50),
	user_id bigint(20) unsigned not null,
	project_id bigint(20) unsigned not null,
	attence_model_id bigint(20) unsigned not null,
	late int,
	leave_early int,
	forget_clock int,
	not_work int,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/////技工考勤记录表
create table mechanic_attendance(
	id serial primary key,
	
)
/////技工信息表
create table mechanic(
	id serial primary key,
	real_name varchar(50),
	id_card varchar(250),
	tel varchar(100),
	work_name varchar(100),
	day_salary int,
	remark varchar(50),
	create_date datetime,
	day_hours int,
	id_card_img bigint(20) unsigned not null,
	project_id bigint(20) unsigned not null,
	create_user bigint(20) unsigned not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/////技工工资表
create table mechanic_price(
	id serial primary key,
	mechanic_id bigint(20) unsigned not null,
	hour int,
	edit_date datetime,
	create_date datetime,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table material_plan(
	id serial primary key,
	name varchar(30),#材料名称
	model varchar(30),#型号规格
	standard varchar(30),#质量标准
	unit varchar(30),#单位
	num int,#数量
	get_time varchar(50),#供货时间
	out_place varchar(50),#卸货地点
	use_place varchar(50),#用料地点
	pid bigint,
	create_date datetime,
	update_date datetime,
	user_id bigint(20) unsigned not null,
	project_id bigint(20) unsigned not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table budget(
	id serial primary key,
	self_id varchar(30),#序号
	project_code varchar(30),#项目编码
	project_name varchar(30),#项目名称
	project_description varchar(500),#项目特征描述
	unit varchar(30),#单位
	quantity double,#数量
	onePrice double,#综合单价
	priceNum double,#合价
	maybe_price double,#暂估价
	upload_date datetime,
	user_id bigint(20) unsigned not null,
	project_id bigint(20) unsigned not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;