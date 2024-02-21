CREATE TABLE categories (
                            ID INT AUTO_INCREMENT PRIMARY KEY,
                            CATEGORY_NAME VARCHAR(50) NOT NULL UNIQUE,
                            DESCRIPTION VARCHAR(256),
                            USED_YN VARCHAR(1) DEFAULT 'Y',
                            REG_DT DATETIME DEFAULT CURRENT_TIMESTAMP,
                            REG_ID INT,
                            MOD_DT DATETIME,
                            MOD_ID INT
);


-- blog.posts definition
CREATE TABLE posts (
                       ID INT AUTO_INCREMENT PRIMARY KEY,
                       TITLE VARCHAR(100) NOT NULL UNIQUE,
                       SLUG VARCHAR(150),
                       DESCRIPTION VARCHAR(255),
                       CONTENT TEXT,
                       VIEW_CNT INT DEFAULT 0,
                       CATEGORY_ID INT,
                       THUMBNAIL VARCHAR(255),
                       THUMBNAIL_ID INT,
                       USER_ID INT,
                       USED_YN VARCHAR(1) DEFAULT 'Y',
                       REG_DT DATETIME DEFAULT CURRENT_TIMESTAMP,
                       REG_ID INT,
                       MOD_DT DATETIME,
                       MOD_ID INT,
                       CONSTRAINT FK_CATEGORY_POST FOREIGN KEY (CATEGORY_ID) REFERENCES categories (ID)
);


CREATE TABLE tags (
                      ID INT AUTO_INCREMENT PRIMARY KEY,
                      TAG_NAME VARCHAR(50) NOT NULL UNIQUE,
                      COLOR VARCHAR(20),
                      USED_YN VARCHAR(1) DEFAULT 'Y',
                      REG_DT DATETIME DEFAULT CURRENT_TIMESTAMP,
                      REG_ID INT,
                      MOD_DT DATETIME,
                      MOD_ID INT
);

CREATE TABLE post_tag (
                          POST_ID INT NOT NULL,
                          TAG_ID INT NOT NULL,
                          PRIMARY KEY (POST_ID, TAG_ID),
                          KEY FK_TAG_ID_POST_ID (TAG_ID),
                          CONSTRAINT FK_POST_ID_TAG_ID FOREIGN KEY (POST_ID) REFERENCES posts (ID),
                          CONSTRAINT FK_TAG_ID_POST_ID FOREIGN KEY (TAG_ID) REFERENCES tags (ID)
);


CREATE TABLE users (
                       ID INT AUTO_INCREMENT PRIMARY KEY,
                       USER_NAME VARCHAR(50) NOT NULL,
                       EMAIL VARCHAR(100) NOT NULL UNIQUE,
                       PASSWORD VARCHAR(255) NOT NULL,
                       AVATAR VARCHAR(255),
                       LAST_LOGIN_DT DATETIME,
                       USED_YN VARCHAR(1) DEFAULT 'Y',
                       REG_DT DATETIME DEFAULT CURRENT_TIMESTAMP,
                       REG_ID INT,
                       MOD_DT DATETIME,
                       MOD_ID INT
);


CREATE TABLE roles (
                       ID INT AUTO_INCREMENT PRIMARY KEY,
                       ROLE_NAME VARCHAR(20) NOT NULL,
                       UNIQUE KEY ROLE_NAME (ROLE_NAME)
);


CREATE TABLE user_role (
                           USER_ID INT NOT NULL,
                           ROLE_ID INT NOT NULL,
                           PRIMARY KEY (USER_ID, ROLE_ID),
                           KEY FK_ROLE_ID_USER_ID (ROLE_ID),
                           CONSTRAINT FK_ROLE_ID_USER_ID FOREIGN KEY (ROLE_ID) REFERENCES roles (ID),
                           CONSTRAINT FK_USER_ID_ROLE_ID FOREIGN KEY (USER_ID) REFERENCES users (ID)
);

CREATE TABLE menus (
                       ID INT AUTO_INCREMENT PRIMARY KEY,
                       MENU_CODE VARCHAR(10) NOT NULL,
                       MENU_NAME VARCHAR(30) NOT NULL,
                       PARENT_ID INT,
                       MENU_ORD INT,
                       MENU_URL VARCHAR(100),
                       MENU_ICON VARCHAR(40),
                       USED_YN VARCHAR(1) DEFAULT 'Y',
                       REG_DT DATETIME DEFAULT CURRENT_TIMESTAMP,
                       REG_ID INT,
                       MOD_DT DATETIME,
                       MOD_ID INT
);


CREATE TABLE comments (
                          ID INT AUTO_INCREMENT PRIMARY KEY,
                          COMMENT_NAME VARCHAR(50) NOT NULL,
                          COMMENT_EMAIL VARCHAR(100) NOT NULL,
                          CONTENT VARCHAR(1000),
                          POST_ID INT NOT NULL,
                          USED_YN VARCHAR(1) DEFAULT 'Y',
                          REG_DT DATETIME DEFAULT CURRENT_TIMESTAMP,
                          REG_ID INT,
                          MOD_DT DATETIME,
                          MOD_ID INT,
                          KEY FK_POST_COMMENT (POST_ID),
                          CONSTRAINT FK_POST_COMMENT FOREIGN KEY (POST_ID) REFERENCES posts (ID)
);


-- blog.code_group definition

CREATE TABLE code_group (
                            ID INT AUTO_INCREMENT PRIMARY KEY,
                            GROUP_CODE VARCHAR(20) NOT NULL,
                            GROUP_NAME VARCHAR(50),
                            DESCRIPTION VARCHAR(100),
                            USED_YN VARCHAR(1) DEFAULT 'Y',
                            REG_DT DATETIME DEFAULT CURRENT_TIMESTAMP,
                            REG_ID INT,
                            MOD_DT DATETIME,
                            MOD_ID INT,
                            UNIQUE KEY GROUP_CODE (GROUP_CODE)
);

CREATE TABLE code_detail (
                             ID INT AUTO_INCREMENT PRIMARY KEY,
                             CODE_CODE VARCHAR(20) NOT NULL,
                             CODE_NAME VARCHAR(50),
                             DESCRIPTION VARCHAR(100),
                             GROUP_CODE VARCHAR(20) NOT NULL,
                             USED_YN VARCHAR(1) DEFAULT 'Y',
                             REG_DT DATETIME DEFAULT CURRENT_TIMESTAMP,
                             REG_ID INT,
                             MOD_DT DATETIME,
                             MOD_ID INT,
                             UNIQUE KEY CODE_CODE (CODE_CODE),
                             KEY KF_CODE_GROUP_CODE_DETAIL (GROUP_CODE),
                             CONSTRAINT KF_CODE_GROUP_CODE_DETAIL FOREIGN KEY (GROUP_CODE) REFERENCES code_group (GROUP_CODE)
);

CREATE TABLE file_master (
                             ID INT AUTO_INCREMENT PRIMARY KEY,
                             FILE_NAME VARCHAR(200) NOT NULL,
                             FILE_ORIGINAL_NAME VARCHAR(200),
                             FILE_URL VARCHAR(255),
                             FILE_SIZE INT DEFAULT 0,
                             FILE_TYPE VARCHAR(10),
                             USED_YN VARCHAR(1) DEFAULT 'Y',
                             REG_DT DATETIME DEFAULT CURRENT_TIMESTAMP,
                             REG_ID INT,
                             MOD_DT DATETIME,
                             MOD_ID INT
);

CREATE TABLE file_relation (
                               ID INT AUTO_INCREMENT PRIMARY KEY,
                               RELATED_CODE VARCHAR(20),
                               RELATED_ID INT,
                               FILE_MASTER_ID INT,
                               KEY FK_FILE_MASTER_ID (FILE_MASTER_ID),
                               CONSTRAINT FK_FILE_MASTER_ID FOREIGN KEY (FILE_MASTER_ID) REFERENCES file_master (ID)
);

CREATE TABLE `tokens` (
                          `id` int AUTO_INCREMENT PRIMARY KEY,
                          `EXPIRATION_DATE` datetime DEFAULT NULL,
                          `EXPIRED` varchar(1),
                          `MOBILE_PC` varchar(1),
                          `REFRESH_EXPIRATION_DATE` datetime DEFAULT NULL,
                          `REFRESH_TOKEN` varchar(255),
                          `REVOKED` varchar(1),
                          `TOKEN` varchar(255),
                          `TOKEN_TYPE` varchar(20),
                          `USER_ID` int DEFAULT NULL,
                          KEY `FK3q5r8rac0xdi32ings41nu74g` (`USER_ID`),
                          CONSTRAINT `FK3q5r8rac0xdi32ings41nu74g` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`ID`)
)
