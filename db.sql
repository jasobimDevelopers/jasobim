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
#注册
#登录
#修改密码 忘记密码 暂时不做
#修改个人资料   自己
#查看个人资料   自己 管理员
#查看用户列表   管理员
#修改权限       管理员


create table file(
id serial primary key,
name varchar(30),
intro text,
file_type int,
url varchar(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#不提供接口，值提供服务

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
#增删改  管理员
#查看列表    all 按日期 分页
#查看项目详情

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
#增删改  管理员
#增删改  管理员
#查看    all



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
#excel录入 type_name name profession_type 单独传，其他excel。  增加时计算quantity
#按类型type_name删除 输入project_id和type_name删除，并删除quantity里面project_id和name一致的
#按项目删除          输入project_id，删除对应的item和quantity
#查看列表            project_id必要 type_name非必要，楼、层、单元、户型 非必要（递进查看），profession_type 非必要
#查看单个            按id

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
#查看列表 project_id必要 type_name非必要，楼、层、单元、户型 非必要（递进查看），profession_type 非必要



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
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table question_file(
id serial primary key,
file_id bigint(20) unsigned not null,
question_id bigint(20) unsigned not null,
foreign key(file_id) references file(id),
foreign key(question_id) references question(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#查一条的时候会放
#查多条不放



create table message(
id serial primary key,
content text,
message_date date,
user_id bigint(20) unsigned not null,
foreign key(user_id) references user(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
#按日期排序

create table message_file(
id serial primary key,
file_id bigint(20) unsigned not null,
message_id bigint(20) unsigned not null,
foreign key(file_id) references file(id),
foreign key(message_id) references message(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

