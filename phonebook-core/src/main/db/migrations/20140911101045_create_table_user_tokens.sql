CREATE TABLE `user_tokens` (
  `token` varchar(32) NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
-- email in case of canceling email change
  `email` varchar(100),
  `type_id` tinyint(3) unsigned NOT NULL,
  `created` datetime NOT NULL,
  `expiration_time` datetime NOT NULL,
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
