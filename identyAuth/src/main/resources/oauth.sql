use oauth;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authorities
-- ----------------------------
DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
                               `username` varchar(50) NOT NULL,
                               `authority` varchar(50) NOT NULL,
                               UNIQUE KEY `ix_auth_username` (`username`,`authority`),
                               CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
                                   `userId` varchar(256) DEFAULT NULL,
                                   `clientId` varchar(256) DEFAULT NULL,
                                   `partnerKey` varchar(32) DEFAULT NULL,
                                   `scope` varchar(256) DEFAULT NULL,
                                   `status` varchar(10) DEFAULT NULL,
                                   `expiresAt` datetime DEFAULT NULL,
                                   `lastModifiedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
                                        `client_id` varchar(255) NOT NULL,
                                        `resource_ids` varchar(255) DEFAULT NULL,
                                        `client_secret` varchar(255) DEFAULT NULL,
                                        `scope` varchar(255) DEFAULT NULL,
                                        `authorized_grant_types` varchar(255) DEFAULT NULL,
                                        `web_server_redirect_uri` varchar(255) DEFAULT NULL,
                                        `authorities` varchar(255) DEFAULT NULL,
                                        `access_token_validity` int(11) DEFAULT NULL,
                                        `refresh_token_validity` int(11) DEFAULT NULL,
                                        `additional_information` varchar(4096) DEFAULT NULL,
                                        `autoapprove` varchar(255) DEFAULT NULL,
                                        PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
                              `code` varchar(255) DEFAULT NULL,
                              `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `username` varchar(50) NOT NULL,
                         `password` varchar(100) NOT NULL,
                         `enabled` tinyint(1) NOT NULL,
                         PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
