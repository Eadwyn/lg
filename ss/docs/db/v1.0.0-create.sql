CREATE DATABASE IF NOT EXISTS newip_demo default charset utf8 COLLATE utf8_general_ci; 

use newip_demo;
drop table if exists t_Settings;

drop table if exists t_SmokeAlarm;

drop table if exists t_SosAlarm;

drop table if exists t_Unlock;

drop table if exists t_VDP;

/*==============================================================*/
/* Table: t_Settings                                            */
/*==============================================================*/
create table t_Settings
(
   id                   bigint not null comment 'ID',
   siteServerIp         varchar(30) comment 'Site Server IP',
   primary key (id)
);

alter table t_Settings comment '系统配置';

/*==============================================================*/
/* Table: t_SmokeAlarm                                          */
/*==============================================================*/
create table t_SmokeAlarm
(
   id                   bigint not null,
   vdpId               bigint,
   dtime                timestamp comment '报警时间',
   areaNum              int comment '防区号',
   primary key (id)
);

alter table t_SmokeAlarm comment '烟雾报警';

/*==============================================================*/
/* Table: t_SosAlarm                                            */
/*==============================================================*/
create table t_SosAlarm
(
   id                   bigint not null,
   vdpId               bigint,
   dtime                timestamp comment '报警时间',
   primary key (id)
);

alter table t_SosAlarm comment 'SOS报警';

/*==============================================================*/
/* Table: t_Unlock                                              */
/*==============================================================*/
create table t_Unlock
(
   id                   bigint not null,
   vdpId               bigint,
   dtime                timestamp comment '开门时间',
   type                 int comment 'int	开门方式。1-卡片开门；2-蓝牙开门；',
   primary key (id)
);

alter table t_Unlock comment '开门记录';

/*==============================================================*/
/* Table: t_VDP                                                 */
/*==============================================================*/
create table t_VDP
(
   id                   bigint not null,
   mac                  varchar(30) comment 'VDP MAC发址',
   callNum              varchar(30) comment '呼叫号码',
   siteServerIP         varchar(30) comment 'Site Server IP地址',
   mcIP                 varchar(30) comment '本级管理机IP',
   childMcIP            varchar(30) comment '下级管理机IP',
   alarmDuration        int comment '报警持续时间。单位：秒',
   sceneMode            int comment '场景模式。0-在家；1-离家；',
   frimwareVersion      varchar(30) comment '固件版本号',
   primary key (id)
);

alter table t_VDP comment 'VDP信息';

alter table t_SmokeAlarm add constraint FK_Relationship_2 foreign key (vdpId)
      references t_VDP (id) on delete restrict on update restrict;

alter table t_SosAlarm add constraint FK_Relationship_1 foreign key (vdpId)
      references t_VDP (id) on delete restrict on update restrict;

alter table t_Unlock add constraint FK_Relationship_3 foreign key (vdpId)
      references t_VDP (id) on delete restrict on update restrict;
