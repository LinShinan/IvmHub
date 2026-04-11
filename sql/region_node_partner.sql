use ivmhub;
select schema();

create table tb_region(
    id int auto_increment primary key comment '主键ID',
    region_name varchar(64) not null comment '区域名称',
    create_time timestamp default current_timestamp comment '创建时间',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    create_by varchar(64) comment '创建人',
    update_by varchar(64) comment '修改人',
    remark text comment '备注'
)ENGINE= InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区域表';

insert into tb_region (region_name) values ('杭州市临平区'),('杭州市萧山区'),('杭州市余杭区');

create table tb_partner(
    id int auto_increment comment '主键id' primary key,
    partner_name   varchar(255) not null comment '合作商名称',
    contact_person varchar(64)  null comment '联系人',
    contact_phone  varchar(15)  null comment '联系电话',
    profit_ratio   int          null comment '分成比例',
    account        varchar(64)  null comment '账号',
    password       varchar(64)  null comment '密码',
    create_time    timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_time    timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
    create_by      varchar(64) null comment '创建人',
    update_by      varchar(64) null comment '修改人',
    remark         text        null comment '备注'
)comment '合作商表';

insert into tb_partner(partner_name,contact_person,contact_phone,profit_ratio,account,password)
values ('杭州一店','张三','13345678901',10,'partner1','123456'),
       ('杭州二店','李四','13345678902',20,'partner2','123456'),
       ('杭州三店','王五','13345678903',30,'partner3','123456');

create table tb_node
(
    id            int auto_increment comment '主键id'
        primary key,
    node_name     varchar(255)  not null comment '点位名称',
    address       varchar(255)  not null comment '详细地址',
    business_type int null comment '商圈类型',
    region_id     int null comment '区域ID',
    partner_id    int null comment '合作商ID',
    create_time   timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
    create_by     varchar(64) null comment '创建人',
    update_by     varchar(64) null comment '修改人',
    remark        text  null comment '备注'
 # 为了性能，不使用物理外键，在逻辑外键中添加外键约束，故不添加下面的代码
#     constraint tb_node_ibfk_1
#         foreign key (region_id) references tb_region (id)
#             on update cascade on delete cascade,
#     constraint tb_node_ibfk_2
#         foreign key (partner_id) references tb_partner (id)
#             on update cascade on delete cascade
)comment '点位表';

-- 插入点位数据（使用已有的区域和合作商）
INSERT INTO tb_node (node_name, address, business_type, region_id, partner_id)
VALUES
('临平银泰城', '杭州市临平区迎宾路1号', 1, 1, 1),
('萧山万象汇', '杭州市萧山区市心中路890号', 2, 2, 2);