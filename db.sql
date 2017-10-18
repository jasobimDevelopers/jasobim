use bimwork;

create table user(
    id serial primary key,
    user_name varchar(20),
    password varchar(128),
    real_name varchar(32),
    user_type int,#0 admin 1 user
    email varchar(64),
    tel varchar(20),
    register_date date
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE user add UNIQUE KEY(user_name); 
#注册                              完成
#登录                              完成
#修改密码 忘记密码                   暂时不做
#修改个人资料   自己                 完成   
#查看个人资料   自己 管理员           完成   
#查看用户列表   管理员               完成
#修改权限       管理员               完成


create table file(
id serial primary key,
name varchar(30),
intro text,
file_type int,
url varchar(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#不提供接口，值提供服务             完成上传文件和删除文件       

create table project(
id serial primary key,
num varchar(50),
name varchar(20),
description text,
construction_unit varchar(50),
design_unit varchar(50),
building_unit varchar(50),
leader varchar(50),
pic_id bigint(20) unsigned not null,
phase varchar(50), #工期
place varchar(100),
model_id bigint(20) unsigned not null,
start_date date,
version varchar(30),
foreign key(model_id) references file(id),
foreign key(pic_id) references file(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#增删改  管理员     ///////完成
#查看列表    all 按日期 分页  ////完成
#查看项目详情              ////完成（管理员身份才能查看项目详情）

create table building(
id serial primary key,
name varchar(50),
project_id bigint(20) unsigned not null,
building_num int,#楼量总数
unit_num int,#单元总数
floor_num int,#楼层总数
household_num int,#单元户总数
area double,
description text,
height double,
foreign key(project_id) references project(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#增删改  管理员        ////完成
#增删改  管理员		  ////完成
#查看    all			  ///完成



create table item(
id serial primary key,
name varchar(50),
location varchar(50),#A1B1C1D1E1
project_id bigint(20) unsigned not null,
building_num int,#第几个楼，下面一样
floor_num int,
unit_num int,
household_num int,
service_type varchar(200),
family_and_type varchar(200),
system_type varchar(200),
length double,
size varchar(100),
area double,
level varchar(30),
offset double,
material varchar(50),
self_id bigint,
type_name varchar(50),
bottom_elevation double,
profession_type int,
foreign key(project_id) references project(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#excel录入 type_name name profession_type 单独传，其他excel。  增加时计算quantity				 //////完成
#按类型type_name删除 输入project_id和type_name删除，并删除quantity里面project_id和name一致的    /////完成
#按项目删除          输入project_id，删除对应的item和quantity                                  //////完成
#查看列表            project_id必要 type_name非必要，楼、层、单元、户型 非必要（递进查看），profession_type 非必要 ////完成
#查看单个            按id /////完成

create table quantity(
id serial primary key,
name varchar(50),
profession_type int,
value double,
unit varchar(20),
project_id bigint(20) unsigned not null,
building_num int,#和item一样
floor_num int,
unit_num int,
household_num int,
family_and_type varchar(200),
system_type varchar(200),
service_type varchar(200),
size varchar(100),
material varchar(50),
foreign key(project_id) references project(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#查看列表 project_id必要 type_name非必要，楼、层、单元、户型 非必要（递进查看），profession_type 非必要/////完成



create table paper(
id serial primary key,
project_id bigint(20) unsigned not null,#和item一样
building_num int,
floor_num int,
profession_type int,#0 电气 1 暖通 2给排水 3 建筑 
file_id bigint(20) unsigned not null,
foreign key(file_id) references file(id),
foreign key(project_id) references project(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#？
alter table paper add column origin_name varchar(300);



create table question(
id serial primary key,
question_type int,#0 质量问题 1安全问题 2其他
user_id bigint(20) unsigned not null,
name varchar(100),
trades varchar(50),
intro text,
question_date date,
priority int,
state int,
code_information varchar(100),
foreign key(user_id) references user(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#增加
#删除 admin， 删除时，question_file，message，message_file都要删除
#查看列表  按紧要度、日期排序，按trades，question_type，state查询
#查看      根据id，把第一页message放进去


create table video(
id serial primary key,
file_id  bigint(20) unsigned not null,
project_id bigint(20) unsigned not null,
building_id bigint(20) unsigned not null,
foreign key(file_id) references file(id),
foreign key(project_id) references project(id),
foreign key(building_id) references building(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;/////完成

create table question_file(
id serial primary key,
file_id bigint(20) unsigned not null,
question_id bigint(20) unsigned not null,
foreign key(file_id) references file(id),
foreign key(question_id) references question(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#查一条的时候会放
#查多条不放



#产值表
create table value_output(
id serial primary key,
others varchar(50),
project_id bigint(20) unsigned not null,
date datetime,
num double,
finished double,
foreign key(project_id) references project(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#查看
#增加


create table message(
id serial primary key,
content text,
message_date date,
user_id bigint(20) unsigned not null,
foreign key(user_id) references user(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#按日期排序    /////完成

create table message_file(
id serial primary key,
file_id bigint(20) unsigned not null,
message_id bigint(20) unsigned not null,
foreign key(file_id) references file(id),
foreign key(message_id) references message(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create index item_project_id_idx on item(project_id);
create index item_building_num_idx on item(building_num);
create index item_unit_num_idx on item(unit_num);
create index item_floor_num_idx on item(floor_num);
create index item_household_num_idx on item(household_num);
create index item_system_type_idx on item(system_type);
create index item_service_type_idx on item(service_type);
create index item_family_and_type_idx on item(family_and_type);
create index item_size_idx on item(size);
create index item_material_idx on item(material);
create index item_name_idx on item(name);
create index item_type_name_idx on item(type_name);

				 
DROP PROCEDURE IF EXISTS exportQuantity;
DELIMITER // 
create procedure exportQuantity(in file_path  text,in project_id long)
begin
SET @sql = 'select id,name,profession_type,value,unit,project_id,building_num,floor_num,unit_num,household_num,family_and_type,system_type,service_type,size,material from quantity where project_id=';
SET @insertSql = CONCAT(@sql,project_id,' into outfile ','\'',file_path,'\'');  
end ;
PREPARE stmtinsert FROM @insertSql;   
EXECUTE stmtinsert;
end//
DELIMITER ;

////////////////////////////////////////////////////
////////////////////////////////////////////////////
create table duct_log(
id serial primary key,
duct_id bigint(20) unsigned not null,
date date,
user_id bigint(20) unsigned not null,
state int,
foreign key(duct_id ) references duct(id),
foreign key(user_id) references user(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/////预制化构件状态记录表

//////////////施工任务单
create table construction_task(
id serial primary key,
company_name varchar(255),
create_date datetime,
user_id bigint(20) unsigned not null,
create_user_name varchar(50),
receive_user_id bigint(20) unsigned not null,
team_name varchar(50),
task_content varchar(255),
finished_date date,
rewards varchar(50),
work_people_name_list varchar(255),
detail_content varchar(255),
approval_people_name text,
approval_date_list text,
approval_people_idea_list text,
approval_people_type_list text,
approval_people_note_list text,
task_flag int,
others_attention varchar(50),
project_id bigint(20) unsigned not null,
user_project_id_list varchar(50),
foreign key(user_id) references user(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

//////预付单
create table advanced_order(
id serial primary key,
project_name varchar(255),
create_date datetime,
submit_user_id bigint(20) unsigned not null,
create_user_name varchar(50),
construct_part varchar(255),
quantity_des varchar(255),
next_approval_people_type varchar(50),
next_approval_people_id varchar(50),
approval_people_name text,
approval_update_date text,
approval_people_idea text,
approval_people_type text,
approval_people_note text,
status int,
project_id bigint(20) unsigned not null,
user_project_id_list varchar(50),
content_files_id varchar(50),
photo_of_finished varchar(50),
foreign key(submit_user_id) references user(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


///预付单汇总表
create table advance_order_collect(
id serial primary key,
project_name varchar(255),
create_date datetime,
submit_user_id bigint(20) unsigned not null,
create_user_name varchar(50),
leader varchar(50),
month int,
construct_part varchar(255),
advanced_order_id bigint(20) unsigned not null,
current_finished bigint(20) unsigned not null,
before_finished bigint(20) unsigned not null,
leader_name varchar(50),
constructor_name varchar(50),
quantityer_name varchar(50),
foreign key(advanced_order_id) references advanced_order(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP PROCEDURE IF EXISTS exportDuct;
DELIMITER // 
create procedure exportDuct(in file_path  text,in project_id long,in date_start datetime,in date_finished datetime)
begin
SET @sql = 'select b.name,count(*) as value,sum(length) as length,sum(area) as area,b.project_id,b.building_num,b.family_and_type,
b.size,a.name as project_name,b.date from duct b left join project a on a.id=b.project_id where b.project_id=';

if (date_start is not null and date_finished is not null) then 
	SET @insertSql = CONCAT(@sql,project_id,'and b.date between ',date_start,' and ',date_finished,' group by size,
		family_and_type order by b.date desc',' into outfile ','\'',file_path,'\'');  
else
	SET @insertSql = CONCAT(@sql,project_id,' group by size,family_and_type order by b.date desc',' into outfile ','\'',file_path,'\'');  

end if;
PREPARE stmtinsert FROM @insertSql;   
EXECUTE stmtinsert;
end//
DELIMITER ;
			

create table model(
id serial primary key,
code_url varchar(20),
construction_unit varchar(50),
size varchar(50),
others varchar(50), #工期
project_id int(20) unsigned not null,
file_id int(20) unsigned not null,
upload_date date,
version varchar(30),
foreign key(file_id) references file(id),
foreign key(project_id) references project(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table user_log(
id serial primary key,
project_part int,#0.模型区域 1.图纸区域 2.登录区域 3.交底区域 4.预制化区域 5.没有
system_type int,#(0.安卓系统 2.苹果系统)
version varchar(32),#(版本号)
action_date date,
user_id bigint(20) unsigned not null,
project_id bigint(20),
foreign key(user_id) references user(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table production_percent(
    id serial primary key,
	project_id BIGINT(20),
	user_id BIGINT(20),
    building_num varchar(20),
    profession_type varchar(128),
    part_name varchar(32),
    plan_num DOUBLE,#0 admin 1 user
    finished_num DOUBLE,#0 admin 1 user
    some_thing varchar(64),
    date date
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table production(
    id serial primary key,
	project_id BIGINT(20),
	user_id BIGINT(20),
    building_num varchar(20),
    profession_type varchar(128),
    part_name varchar(32),
    plan_num int,#0 admin 1 user
    finished_num int,#0 admin 1 user
    some_thing varchar(64),
    date date
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
				 