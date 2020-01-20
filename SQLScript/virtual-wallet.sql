-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.10-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for virtual_wallet
CREATE DATABASE IF NOT EXISTS `virtual_wallet` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `virtual_wallet`;

-- Dumping structure for table virtual_wallet.authorities
CREATE TABLE IF NOT EXISTS `authorities` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `username_authority` (`username`,`authority`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.authorities: ~6 rows (approximately)
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
REPLACE INTO `authorities` (`role_id`, `username`, `authority`) VALUES
	(41, 'qtea1qlq1aearikag', 'ROLE_USER'),
	(43, 'qtea1qlq1aeariqkag', 'ROLE_USER'),
	(40, 'qtea1qlqaearikag', 'ROLE_USER'),
	(44, 'qtea1qlqq1aeariqkag', 'ROLE_USER'),
	(39, 'qteaqlqaearikag', 'ROLE_USER'),
	(37, 'tealqaearikag', 'ROLE_USER'),
	(38, 'teaqlqaearikag', 'ROLE_USER');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.physical_card
CREATE TABLE IF NOT EXISTS `physical_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(50) NOT NULL,
  `expiration_date` varchar(5) NOT NULL,
  `type` varchar(10) DEFAULT NULL,
  `security_code` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `number` (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.physical_card: ~0 rows (approximately)
/*!40000 ALTER TABLE `physical_card` DISABLE KEYS */;
REPLACE INTO `physical_card` (`id`, `number`, `expiration_date`, `type`, `security_code`, `status`) VALUES
	(1, '1233-1243-1111-2222', '22/03', NULL, 123, 1);
/*!40000 ALTER TABLE `physical_card` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.users
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT 1,
  `email` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `debit_card` int(11) DEFAULT 0,
  `credit_card` int(11) DEFAULT 0,
  `phone_number` varchar(50) NOT NULL,
  `picture` mediumblob NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone_number` (`phone_number`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `debit_card` (`debit_card`),
  UNIQUE KEY `credit_card` (`credit_card`),
  CONSTRAINT `FK_users_credit_card` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`),
  CONSTRAINT `FK_users_debit_card` FOREIGN KEY (`debit_card`) REFERENCES `physical_card` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.users: ~6 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
REPLACE INTO `users` (`user_id`, `username`, `enabled`, `email`, `password`, `debit_card`, `credit_card`, `phone_number`, `picture`) VALUES
	(37, 'tealqaearikag', 1, 'kureca@aaabqqavaa.bg', 'akurqaaa', NULL, NULL, 'qaqk1qaaaaura', _binary ''),
	(38, 'teaqlqaearikag', 1, 'kureca@aaabqqvaa.bg', 'akurqqaaa', NULL, NULL, 'aqkqaaaura', _binary ''),
	(39, 'qteaqlqaearikag', 1, 'kureqca@aaabqqvaa.bg', 'akqurqqaaa', NULL, NULL, 'qaqkqaaaura', _binary ''),
	(40, 'qtea1qlqaearikag', 1, 'kureqca@1aaabqqvaa.bg', 'akqurq1qaaa', NULL, NULL, 'qaqk1qaaaura', _binary ''),
	(41, 'qtea1qlq1aearikag', 1, 'kureqca@1aaa1bqqvaa.bg', 'akqurq1qaa1a', NULL, NULL, 'qaqk1qaa1aura', _binary ''),
	(43, 'qtea1qlq1aeariqkag', 1, 'kureqca@1aaa1bqqvaqa.bg', 'akqurq1qaa1aq', NULL, NULL, 'qaqk1qaa1aqura', _binary ''),
	(44, 'qtea1qlqq1aeariqkag', 1, 'kureqca@1aaaq1bqqvaqa.bg', 'akqurq1qaaq1aq', 1, NULL, 'qaqk1qaaq1aqura', _binary '');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` int(11) NOT NULL DEFAULT 0,
  `role_id` int(11) NOT NULL DEFAULT 0,
  KEY `FK_user_role_users` (`user_id`),
  KEY `FK_user_role_authorities` (`role_id`),
  CONSTRAINT `FK_user_role_authorities` FOREIGN KEY (`role_id`) REFERENCES `authorities` (`role_id`),
  CONSTRAINT `FK_user_role_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.user_role: ~6 rows (approximately)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
REPLACE INTO `user_role` (`user_id`, `role_id`) VALUES
	(37, 37),
	(38, 38),
	(39, 39),
	(40, 40),
	(41, 41),
	(43, 43),
	(44, 44);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.virtual_card
CREATE TABLE IF NOT EXISTS `virtual_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(50) NOT NULL DEFAULT '',
  `type` varchar(10) DEFAULT NULL,
  `expiration_date` varchar(5) NOT NULL,
  `security_code` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `number` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.virtual_card: ~0 rows (approximately)
/*!40000 ALTER TABLE `virtual_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtual_card` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
