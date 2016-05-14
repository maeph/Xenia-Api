alter table prize add column tempid varchar(255) not null default 1;
update prize set tempid=id;
alter table prize drop column id;
alter table prize change column tempid id text;
alter table prize add primary key(id);