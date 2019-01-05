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
	unit varchar(30),#单位
	quantity double,#数量
	quota_one double,#综合单价
	quota_num double,#合价
	sum_of_money_one double,#暂估价
	sum_of_money_num double,
	artificial_cost_one double,
	artificial_cost_num double,
	materials_expenses_one double,
	materials_expenses_num double,
	mechanical_fee_one double,
	mechanical_fee_num double,
	indexs int,
	upload_date datetime,
	user_id bigint(20) unsigned not null,
	project_id bigint(20) unsigned not null,
	budget_building_id bigint(20) unsigned not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

####班组成员
create table department_user(
	id serial primary key,
	department_id bigint(20) unsigned not null,
	name varchar(50),
	sex int,
	work_type_id bigint(20) unsigned not null,
	id_card varchar(100),
	tel varchar(50),
	salary int,
	id_card_img_z bigint(20) unsigned not null,
	id_card_img_f bigint(20) unsigned not null,
	create_date datetime,
	update_date datetime,
	create_user bigint(20) unsigned not null,
	update_user bigint(20) unsigned not null,
	project_id bigint(20) unsigned not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


####新版质量管理——质量整改单
create table quality_rectification(
	id serial primary key,
	project_id bigint(20) unsigned not null,
	create_user bigint(20) unsigned not null,
	nature_id varchar(255),/*检查性质id,id*/
	notice_type int,/*0、通过  1、口头警告 2、书面整改*/
	rectification_content text,/*整改要求（当notice_type为书面整改时，设置值）*/
	check_list varchar(255),/*检查项*/
	check_content text,/*检查结果*/
	status int,/*0、待整改（默认值，提交时设置 ） 1、待复检  2、已通过*/
	pictures varchar(50),/*图片id,id*/
	copy_user varchar(50),/*抄送人id,id*/
	voices varchar(50),/*语音文件*/
	level int,/*情况程度*/
	create_date date,/*创建时间*/
	finished_date date,/*限制完成时间*/	
	update_date datetime,
	score int/*评分（-1默认值）*/
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
####新版质量管理——质量检查单
create table quality_check(
	id serial primary key,
	project_id bigint(20) unsigned not null,
	quality_rectification_id bigint(20),
	create_user bigint(20) unsigned not null,
	nature_id varchar(255),/*检查性质id,id*/
	notice_type int,/*0、通过  1、口头警告 2、书面整改*/
	check_list varchar(255),/*检查项*/
	check_content text,/*检查结果*/
	status int,/*-1、待定（默认值，提交时设置 ） 1、未发整改  2、空*/
	pictures varchar(50),/*图片id,id*/
	inform_user varchar(50),/*通知人id,id*/
	voices varchar(50),/*语音文件*/
	create_date date,/*创建时间*/
	update_date datetime
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
####新版质量管理状态记录表
create table manage_log(
	id serial primary key,
	action_type int,/* 0、整改  1、复检*/
	action_date datetime,/*操作时间*/
	operate_user bigint(20) unsigned not null,/*操作人*/
	about_id bigint(20) unsigned not null/*实体*/
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
###新版质量管理——指定人员、质量安全关系表
create table relation(
	id serial primary key,
	relation_type int,/*0、质量 1、安全*/
	quality_type int,/*0、整改单 1、检查单*/
	about_id bigint(20) unsigned not null,
	user_id bigint(20) unsigned not null
	state int/*-1、不处理 0、待整改（默认值） 1、已整改（当回复进度为100%时设置这个参数）*/
)ENGINE=InnoDB DEFAULT CHARSET=utf8

###新版质量管理——检查项
create table check_lists(
	id serial primary key,
	check_type int,/*0、质量 1、安全*/
	content varchar(255),
	project_id bigint(20) unsigned not null,
	create_date datetime,
	create_user bigint(20) unsigned not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

###新版质量管理——性质
create table nature(
	id serial primary key,
	create_user bigint(20) unsigned not null,
	content varchar(255),
	create_date datetime
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

###新版质量管理——评论
create table comment(
	id serial primary key,
	pictures varchar(50),/*图片id,id*/
	voices varchar(50),/*语音文件*/
	reply_type int,/*0、质量整改单评论 1、质量检查单评论 2、安全整改单评论 3、安全检查单评论*/
	about_id bigint(20) unsigned not null,
	comment_user bigint(20) unsigned not null,
	create_date datetime,
	comment_content text
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

###新版质量管理——回复
create table reply(
	id serial primary key,
	reply_user bigint(20) unsigned not null,
	create_date datetime,
	reply_content text,
	reply_type int,/*0、质量 1、安全*/
	about_id bigint(20) unsigned not null,
	schedule int,/*进度*/
	pictures varchar(50),/*图片id,id*/
	voices varchar(50),/*语音文件*/
)ENGINE=InnoDB DEFAULT CHARSET=utf8

###新版质量管理——奖惩
create table award_ticket(
	id serial primary key,
	award_type int,/*奖罚类型：0、质量 1、安全*/
	ticket_type int,/*0、处罚 1、奖励 */
	about_id bigint(20) unsigned not null,/*奖惩事由关联实体id*/
	create_user bigint(20) unsigned not null,/*提交人*/
	award_date date,/*奖惩日期*/
	create_date datetime,/*创建时间*/
	person_liable varchar(255),/*责任人*/
	remark text,/*备注*/
	award_num int,/*奖罚金额*/
	pictures varchar(255),
	voices varchar(255)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
