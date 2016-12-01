create index IX_DEC4D01 on book (groupId, status);
create index IX_5F6BB699 on book (isbn[$COLUMN_LENGTH:75$]);
create index IX_4D6C0CA3 on book (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_22C55FE5 on book (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_781C02E0 on book_entry (groupId, bookId);