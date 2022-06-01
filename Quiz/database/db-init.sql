create table multichoicequiz (
	id int not null primary key,
	question varchar(500),
	answerA varchar(500),
	answerB varchar(500),
	answerC varchar(500),
	answerD varchar(500),
	correctAnswer varchar(500)
);

create table binaryquiz (
	id int not null primary key,
	question varchar(500),
	correctAnswer varchar(500)
);

create table score (
	id int not null primary key,
	username varchar(255),
	score int,
	topic varchar(255)
);

create table user (
	username varchar(255) not null primary key,
	name varchar(255),
	password varchar(255)
);