# Stored Procedure

create database if not exists briup;
use briup;

delimiter //

create Procedure create_tables () 
begin 
declare i int;
declare table_pre varchar(50);
declare table_name varchar(50);
declare sql_txt varchar(10000);

set i = 1;
set table_pre  = 'information_';
set table_name = '';
set sql_txt = '';

while i < 32
do 
set table_name = concat( table_pre, i);

set sql_txt = concat( 'create table ',table_name," (
name varchar(50),
SrcID varchar(50), 
DstID varchar(50),
DevID varchar(50),
SensorAddress varchar(50),
Ncount int,
cmd varchar(50),
data int,
status int,
day varchar(50)
);");

set @sql_txt = sql_txt;
prepare stmt
from 
# sql 语句
	@sql_txt;
execute stmt;


set i = i + 1;
end while;

end;

CALL create_tables ();

# 删除程序
drop procedure create_tables;
//
delimiter ;

