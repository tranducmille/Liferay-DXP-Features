create table BOOK_Book (
	id_ INTEGER not null primary key,
	title VARCHAR(75) null,
	author VARCHAR(75) null,
	isbn VARCHAR(75) null,
	summary VARCHAR(75) null
);

create table XT_Book (
	id_ INTEGER not null primary key,
	title VARCHAR(75) null,
	author VARCHAR(75) null,
	isbn VARCHAR(75) null,
	summary VARCHAR(75) null
);

create table book (
	uuid_ VARCHAR(75) null,
	bookId LONG not null primary key,
	title VARCHAR(75) null,
	author VARCHAR(75) null,
	isbn VARCHAR(75) null,
	summary VARCHAR(75) null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	userId LONG,
	companyId LONG,
	groupId LONG,
	createdDate DATE null,
	modifiedDate DATE null,
	entryId LONG
);

create table book_entry (
	entryId LONG not null primary key,
	userId LONG,
	groupId LONG,
	companyId LONG,
	userName VARCHAR(75) null,
	createdDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	email VARCHAR(75) null,
	message VARCHAR(75) null,
	bookId LONG
);