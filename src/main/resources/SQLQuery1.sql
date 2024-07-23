use master;
go
create database calendarize;
go
use calendarize;
go
create table App_User(
	id bigint identity ,
	name nvarchar(50),
	email varchar(30),
	pwd varchar(500),
	constraint User_PK Primary key(id),
)
create table Project (
	id bigint identity,
	name nvarchar(50),
	[description] nvarchar(100),
	startDate datetime,
	endDate datetime,
	[status] int,
	hostId bigint,
	constraint Project_PK primary key(id),
	constraint Project_AppUser_FK foreign key(hostId) references App_User(id)
)
create table Project_Member(
	project_id bigint,
	user_id bigint,
	constraint ProjectMember_PK primary key(project_id,user_id),
	constraint ProjectMember_Project_FK foreign key(project_id) references Project(id),
	constraint ProjectMember_User_FK foreign key(user_id) references App_User(id), 
)
create table Life_Task(
	id bigint identity,
	name nvarchar(50),
	[description] nvarchar(100),
	startDate datetime,
	endDate datetime,
	user_id bigint,
	constraint LifeTask_PK primary key(id),
	constraint LifeTask_User_FK foreign key(user_id) references App_User(id)
)
create table Project_Task(
	id bigint identity,
	name nvarchar(50),
	[description] nvarchar(100),
	startDate datetime,
	endDate datetime,
	project_id bigint,
	constraint ProjectTask_PK primary key(id),
	constraint ProjectTask_Project_FK foreign key(project_id) references Project(id)
)
create table Authority(
	id int identity,
	name nvarchar(20),
	constraint Authority_PK primary key(id)
)
create table User_Authority(
	user_id bigint,
	authority_id int,
	constraint User_Authority_PK primary key (user_id,authority_id),
	constraint User_Authority_User_FK foreign key(user_id) references App_User(id),
	constraint User_Authority_Authority_FK foreign key(authority_id) references Authority(id)
)

create table Task_Status(
	task_status_id int,
	name nvarchar(50),
	constraint Task_Status_PK primary key(task_status_id)
)

insert into Task_Status(
	task_status_id,name
)
values(0,'Processing'),(1,'Done')

alter table Project 
add constraint Project_TaskStatus_FK foreign key([status]) references Task_Status(task_status_id)

create table Token(
	id bigint identity,
	token varchar(200),
	token_type varchar(50),
	revoked bit,
	expired bit,
	user_id bigint,
	constraint Token_PK primary key(id),
	constraint Token_User_FK foreign key(user_id) references App_User(id)
)
/*
use master;
go
drop database calendarize;
*/