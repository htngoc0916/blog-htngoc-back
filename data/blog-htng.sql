
CREATE TABLE `categories` (
                              `ID` int NOT NULL AUTO_INCREMENT,
                              `CATEGORY_NAME` varchar(50) DEFAULT NULL,
                              `DESCRIPTION` varchar(256) DEFAULT NULL,
                              `USED_YN` varchar(1) DEFAULT 'Y',
                              `REG_DT` datetime DEFAULT CURRENT_TIMESTAMP,
                              `REG_ID` varchar(50) DEFAULT NULL,
                              `MOD_DT` datetime DEFAULT NULL,
                              `MOD_ID` varchar(50) DEFAULT NULL,
                              PRIMARY KEY (`ID`),
                              UNIQUE KEY `CATEGORY_NAME` (`CATEGORY_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- `blog-htng`.posts definition
CREATE TABLE `posts` (
                         `ID` int NOT NULL AUTO_INCREMENT,
                         `TITLE` varchar(100) NOT NULL,
                         `SLUG` varchar(150) DEFAULT NULL,
                         `DESCRIPTION` varchar(255) DEFAULT NULL,
                         `CONTENT` text,
                         `VIEW_CNT` int DEFAULT NULL,
                         `CATEGORY_ID` int DEFAULT NULL,
                         `USED_YN` varchar(1) DEFAULT 'Y',
                         `REG_DT` datetime DEFAULT CURRENT_TIMESTAMP,
                         `REG_ID` varchar(50) DEFAULT NULL,
                         `MOD_DT` datetime DEFAULT NULL,
                         `MOD_ID` varchar(50) DEFAULT NULL,
                         PRIMARY KEY (`ID`),
                         UNIQUE KEY `TITLE` (`TITLE`),
                         KEY `FK_CATEGORY_POST` (`CATEGORY_ID`),
                         CONSTRAINT `FK_CATEGORY_POST` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `categories` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- `blog-htng`.tags definition
CREATE TABLE `tags` (
                        `ID` int NOT NULL AUTO_INCREMENT,
                        `TAG_NAME` varchar(50) NOT NULL,
                        `COLOR` varchar(20) DEFAULT NULL,
                        `USED_YN` varchar(1) DEFAULT 'Y',
                        `REG_DT` datetime DEFAULT CURRENT_TIMESTAMP,
                        `REG_ID` varchar(50) DEFAULT NULL,
                        `MOD_DT` datetime DEFAULT NULL,
                        `MOD_ID` varchar(50) DEFAULT NULL,
                        PRIMARY KEY (`ID`),
                        UNIQUE KEY `TAG_NAME` (`TAG_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- `blog-htng`.post_tag definition
CREATE TABLE `post_tag` (
                            `POST_ID` int NOT NULL,
                            `TAG_ID` int NOT NULL,
                            PRIMARY KEY (`POST_ID`,`TAG_ID`),
                            KEY `FK_TAG_ID_POST_ID` (`TAG_ID`),
                            CONSTRAINT `FK_POST_ID_TAG_ID` FOREIGN KEY (`POST_ID`) REFERENCES `posts` (`ID`),
                            CONSTRAINT `FK_TAG_ID_POST_ID` FOREIGN KEY (`TAG_ID`) REFERENCES `tags` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- `blog-htng`.users definition
CREATE TABLE `users` (
                         `ID` int NOT NULL AUTO_INCREMENT,
                         `USER_NAME` varchar(50) NOT NULL,
                         `EMAIL` varchar(100) NOT NULL,
                         `PASSWORD` varchar(255) NOT NULL,
                         `AVATAR` varchar(255) DEFAULT NULL,
                         `LAST_LOGIN_DT` datetime DEFAULT NULL,
                         `USED_YN` varchar(1) DEFAULT 'Y',
                         `REG_DT` datetime DEFAULT CURRENT_TIMESTAMP,
                         `REG_ID` varchar(50) DEFAULT NULL,
                         `MOD_DT` datetime DEFAULT NULL,
                         `MOD_ID` varchar(50) DEFAULT NULL,
                         PRIMARY KEY (`ID`),
                         UNIQUE KEY `EMAIL` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- `blog-htng`.roles definition
CREATE TABLE `roles` (
                         `ID` int NOT NULL AUTO_INCREMENT,
                         `ROLE_NAME` varchar(20) NOT NULL,
                         PRIMARY KEY (`ID`),
                         UNIQUE KEY `ROLE_NAME` (`ROLE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- `blog-htng`.user_role definition
CREATE TABLE `user_role` (
                             `user_id` int NOT NULL,
                             `role_id` int NOT NULL,
                             PRIMARY KEY (`user_id`,`role_id`),
                             KEY `FK_ROLE_ID_USER_ID` (`role_id`),
                             CONSTRAINT `FK_ROLE_ID_USER_ID` FOREIGN KEY (`role_id`) REFERENCES `roles` (`ID`),
                             CONSTRAINT `FK_USER_ID_ROLE_ID` FOREIGN KEY (`user_id`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- `blog-htng`.menu definition
CREATE TABLE `menu` (
                        `ID` int NOT NULL AUTO_INCREMENT,
                        `MENU_CODE` varchar(10) NOT NULL,
                        `MENU_NAME` varchar(30) NOT NULL,
                        `PARENT_ID` int DEFAULT NULL,
                        `MENU_ORD` int DEFAULT NULL,
                        `MENU_URL` varchar(100) DEFAULT NULL,
                        `USED_YN` varchar(1) DEFAULT 'Y',
                        `REG_DT` datetime DEFAULT CURRENT_TIMESTAMP,
                        `REG_ID` varchar(50) DEFAULT NULL,
                        `MOD_DT` datetime DEFAULT NULL,
                        `MOD_ID` varchar(50) DEFAULT NULL,
                        PRIMARY KEY (`ID`),
                        UNIQUE KEY `MENU_CODE` (`MENU_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- `blog-htng`.comments definition
CREATE TABLE `comments` (
                            `ID` int NOT NULL AUTO_INCREMENT,
                            `COMMENT_NAME` varchar(50) NOT NULL,
                            `COMMENT_EMAIL` varchar(100) NOT NULL,
                            `CONTENT` varchar(1000) DEFAULT NULL,
                            `POST_ID` int NOT NULL,
                            `USED_YN` varchar(1) DEFAULT 'Y',
                            `REG_DT` datetime DEFAULT CURRENT_TIMESTAMP,
                            `REG_ID` varchar(50) DEFAULT NULL,
                            `MOD_DT` datetime DEFAULT NULL,
                            `MOD_ID` varchar(50) DEFAULT NULL,
                            PRIMARY KEY (`ID`),
                            KEY `FK_POST_COMMENT` (`POST_ID`),
                            CONSTRAINT `FK_POST_COMMENT` FOREIGN KEY (`POST_ID`) REFERENCES `posts` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- `blog-htng`.code_group definition
CREATE TABLE `code_group` (
                              `ID` int NOT NULL AUTO_INCREMENT,
                              `GROUP_CODE` varchar(20) NOT NULL,
                              `GROUP_NAME` varchar(50) DEFAULT NULL,
                              `DESCRIPTION` varchar(100) DEFAULT NULL,
                              `USED_YN` varchar(1) DEFAULT 'Y',
                              `REG_DT` datetime DEFAULT CURRENT_TIMESTAMP,
                              `REG_ID` varchar(50) DEFAULT NULL,
                              `MOD_DT` datetime DEFAULT NULL,
                              `MOD_ID` varchar(50) DEFAULT NULL,
                              PRIMARY KEY (`ID`),
                              UNIQUE KEY `GROUP_CODE` (`GROUP_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- `blog-htng`.code_detail definition
CREATE TABLE `code_detail` (
                               `ID` int NOT NULL AUTO_INCREMENT,
                               `CODE_CODE` varchar(20) NOT NULL,
                               `CODE_NAME` varchar(50) DEFAULT NULL,
                               `DESCRIPTION` varchar(100) DEFAULT NULL,
                               `GROUP_CODE` varchar(20) NOT NULL,
                               `USED_YN` varchar(1) DEFAULT 'Y',
                               `REG_DT` datetime DEFAULT CURRENT_TIMESTAMP,
                               `REG_ID` varchar(50) DEFAULT NULL,
                               `MOD_DT` datetime DEFAULT NULL,
                               `MOD_ID` varchar(50) DEFAULT NULL,
                               PRIMARY KEY (`ID`),
                               UNIQUE KEY `CODE_CODE` (`CODE_CODE`),
                               KEY `KF_CODE_GROUP_CODE_DETAIL` (`GROUP_CODE`),
                               CONSTRAINT `KF_CODE_GROUP_CODE_DETAIL` FOREIGN KEY (`GROUP_CODE`) REFERENCES `code_group` (`GROUP_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;