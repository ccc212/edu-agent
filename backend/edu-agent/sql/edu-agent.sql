CREATE DATABASE IF NOT EXISTS edu_agent;
USE edu_agent;

# 用户表
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user
(
    `user_id`         BIGINT UNSIGNED                                                  NOT NULL AUTO_INCREMENT COMMENT '用户主键',
    `name`            VARCHAR(50)                                                      NOT NULL COMMENT '姓名',
    `username`        VARCHAR(50)                                                      NOT NULL COMMENT '用户名',
    `password`        VARCHAR(255)                                                     NOT NULL COMMENT '密码',
    `student_id`      VARCHAR(20)                                                      NULL COMMENT '学号',
    `email`           VARCHAR(100)                                                     NULL COMMENT '用户邮箱（可选，用于找回密码）',
    `role_code`       INT        DEFAULT 4                                             NOT NULL COMMENT '角色编号',
    `last_login_time` DATETIME                                                         NULL COMMENT '上次登录时间',
    `del_flag`        TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
    `create_time`     DATETIME   DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    `update_time`     DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    KEY `idx_role` (`role_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户信息表';

# 管理员
INSERT INTO t_user (name, username, password, student_id, role_code, last_login_time)
VALUES ('管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 202200000000, 1, CURRENT_TIMESTAMP);

# 角色表
DROP TABLE IF EXISTS t_role;
CREATE TABLE t_role
(
    `role_id`     BIGINT                                                         NOT NULL AUTO_INCREMENT COMMENT '角色主键',
    `role_code`   INT                                                            NOT NULL COMMENT '角色编号（1为管理员，2为教师，3为学生）',
    `role_name`   VARCHAR(50)                                                    NOT NULL COMMENT '角色名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`role_id`),
    UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色表';

# 角色
INSERT INTO t_role (role_code, role_name)
VALUES (1, '管理员'),
       (2, '教师'),
       (3, '学生'),
       (4, '游客');

# 班级
DROP TABLE IF EXISTS t_class;
CREATE TABLE t_class
(
    `class_id`    BIGINT UNSIGNED                                                NOT NULL AUTO_INCREMENT COMMENT '班级主键',
    `class_name`  VARCHAR(100)                                                   NOT NULL COMMENT '班级名称',
    `teacher_id`  BIGINT UNSIGNED                                               NOT NULL COMMENT '班级所属教师ID',
    `description` TEXT                                                           NULL COMMENT '班级描述',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`class_id`),
    UNIQUE KEY `uk_class_name` (`class_name`),
    KEY `idx_teacher_id` (`teacher_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='班级信息表';

# 学生-班级关联表
DROP TABLE IF EXISTS t_student_class;
CREATE TABLE t_student_class
(
    `student_id` BIGINT UNSIGNED                    NOT NULL COMMENT '学生ID',
    `class_id`   BIGINT UNSIGNED                    NOT NULL COMMENT '班级ID',
    `status`     INT DEFAULT 0 COMMENT '加入状态（0未加入 1已加入 2申请中 3邀请中）',
    `joined_at`  DATETIME NULL COMMENT '加入班级的时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`student_id`, `class_id`),
    KEY `idx_student_id` (`student_id`),
    KEY `idx_class_id` (`class_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='学生与班级关联表';

# 操作日志表
DROP TABLE IF EXISTS t_oper_log;
CREATE TABLE t_oper_log
(
    `oper_id`             bigint     NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    `title`          varchar(50)         DEFAULT '' COMMENT '模块标题',
    `business_type`  varchar(20)         DEFAULT 'OTHER' COMMENT '业务类型',
    `method`         varchar(100)        DEFAULT '' COMMENT '方法名称',
    `request_method` varchar(10)         DEFAULT '' COMMENT '请求方式',
    `oper_user_id`   bigint              DEFAULT 0 COMMENT '操作人员id',
    `oper_name`      varchar(50)         DEFAULT '' COMMENT '操作人员',
    `oper_url`       varchar(255)        DEFAULT '' COMMENT '请求URL',
    `oper_ip`        varchar(128)        DEFAULT '' COMMENT '主机地址',
    `oper_param`     varchar(2000)       DEFAULT '' COMMENT '请求参数',
    `json_result`    varchar(2000)       DEFAULT '' COMMENT '返回参数',
    `status`         int(1)              DEFAULT '0' COMMENT '操作状态（1正常 0异常）',
    `error_msg`      varchar(2000)       DEFAULT '' COMMENT '错误消息',
    `oper_time`      datetime            DEFAULT NULL COMMENT '操作时间',
    `execute_time`   bigint(20) NOT NULL DEFAULT '0' COMMENT '执行时长(毫秒)',
    PRIMARY KEY (`oper_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='操作日志记录';