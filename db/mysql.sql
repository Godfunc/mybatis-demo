CREATE DATABASE mybatis;
CREATE TABLE `t_user` (
    `id`   bigint(16) NOT NULL,
    `name` varchar(128) DEFAULT NULL,
    `age`  int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;