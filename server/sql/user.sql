CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `username` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '昵称',
                        `account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账号',
                        `avatar` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '头像',
                        `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
                        `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
                        `phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号',
                        `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮箱',
                        `status` tinyint(4) DEFAULT '1' COMMENT '状态',
                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `del_flag` tinyint(4) DEFAULT NULL COMMENT '逻辑删除',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户表';

# 增加用户角色
alter table user
    add role int default 2 null comment '角色(1-管理员；2-普通用户)';

# del_flag 默认0
alter table user
    alter column del_flag set default 0;

# 增加星球编号
alter table user
    add planet_code varchar(255) null comment '知识星球编号' after role;

