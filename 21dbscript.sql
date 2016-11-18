drop table users; 

create table users(
username varchar(30),
password varbinary(100),
balance decimal(7,2));


alter table users
	add constraint users_username_pk primary key(username);
	


	
