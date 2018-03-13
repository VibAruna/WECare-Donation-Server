Drop database Donation;
create database Donation;
use Donation;

create table user(
	fName char(20),
	lName char(20),
	nic char(10),
	tp int,
	mail char(50),
	address char(50),
	password char(15),
	constraint primary key(nic)
); 

create table donations(
	DonId char(10),
	donation char(20),
	category char(20),
	description char(100),
	donar char(10),
	recipient char(10),
	donDate date,
	constraint primary key(DonId),
	constraint foreign key(donar) references user(nic) on delete cascade on update cascade
);

create table requests(
	DonId char(10),
	nic char(10),
	constraint foreign key(DonId) references donations(DonId) on delete cascade on update cascade,
	constraint foreign key(nic) references user(nic) on delete cascade on update cascade
);

create table management(
	num int,
	pass char(10)
);

insert into management values(1,'management');
