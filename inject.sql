/*
SQLyog 企业版 - MySQL GUI
MySQL - 5.5.19 
*********************************************************************
*/

/*
	************添加添加/更新  企业审核, 自动匹配字段*************
*/
alter table company add institutional_framework varchar(50) comment '组织机构代码图片id';
alter table company add tax_registration varchar(50) comment '税务登记证图片id';
alter table company add business_license varchar(50) comment '营业执照副本图片id';

insert into `parameter` (`id`, `name`, `value`, `type`, `mark`, `acode`) values('rmp0','请选择','0','resumeMatchPer','简历匹配百分比','10000000');
insert into `parameter` (`id`, `name`, `value`, `type`, `mark`, `acode`) values('rmp1','10%','10','resumeMatchPer','简历匹配百分比','10000000');
insert into `parameter` (`id`, `name`, `value`, `type`, `mark`, `acode`) values('rmp2','20%','20','resumeMatchPer','简历匹配百分比','10000000');
insert into `parameter` (`id`, `name`, `value`, `type`, `mark`, `acode`) values('rmp3','30%','30','resumeMatchPer','简历匹配百分比','10000000');
insert into `parameter` (`id`, `name`, `value`, `type`, `mark`, `acode`) values('rmp4','40%','40','resumeMatchPer','简历匹配百分比','10000000');
insert into `parameter` (`id`, `name`, `value`, `type`, `mark`, `acode`) values('rmp5','50%','50','resumeMatchPer','简历匹配百分比','10000000');
insert into `parameter` (`id`, `name`, `value`, `type`, `mark`, `acode`) values('rmp6','60%','60','resumeMatchPer','简历匹配百分比','10000000');
insert into `parameter` (`id`, `name`, `value`, `type`, `mark`, `acode`) values('rmp7','70%','70','resumeMatchPer','简历匹配百分比','10000000');
insert into `parameter` (`id`, `name`, `value`, `type`, `mark`, `acode`) values('rmp8','80%','80','resumeMatchPer','简历匹配百分比','10000000');
insert into `parameter` (`id`, `name`, `value`, `type`, `mark`, `acode`) values('rmp9','90%','90','resumeMatchPer','简历匹配百分比','10000000');

insert into `menu` (`id`, `text`, `state`, `url`, `checked`, `icon_cls`, `enable`, `type_name`, `authority`) values('9','职位自动匹配',NULL,'/jobservice/manage/mjb',NULL,NULL,'1','ge/listmate','600');
insert into `menu` (`id`, `text`, `state`, `url`, `checked`, `icon_cls`, `enable`, `type_name`, `authority`) values('91','简历自动匹配',NULL,'/jobservice/manage/mre',NULL,NULL,'1','ge/materesume','600');

