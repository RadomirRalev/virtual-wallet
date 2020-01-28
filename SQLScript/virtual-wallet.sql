-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.11-MariaDB - mariadb.org binary distribution
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
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `username_authority` (`username`,`authority`),
  KEY `FK_authorities_users` (`user_id`),
  CONSTRAINT `FK_authorities_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.authorities: ~2 rows (approximately)
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
REPLACE INTO `authorities` (`role_id`, `username`, `authority`, `user_id`) VALUES
	(66, 'atomik333', 'ROLE_USER', 63),
	(70, 'atomik3333', 'ROLE_USER', 67);
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.card
CREATE TABLE IF NOT EXISTS `card` (
  `card_id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(50) NOT NULL,
  `expiration_date` varchar(5) NOT NULL,
  `security_code` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `cardholder_name` varchar(40) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`card_id`),
  UNIQUE KEY `number` (`number`),
  KEY `FK_card_users` (`user_id`),
  CONSTRAINT `FK_card_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.card: ~0 rows (approximately)
/*!40000 ALTER TABLE `card` DISABLE KEYS */;
/*!40000 ALTER TABLE `card` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.deposit
CREATE TABLE IF NOT EXISTS `deposit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` int(11) NOT NULL,
  `currency` varchar(3) NOT NULL DEFAULT '',
  `idempotencykey` varchar(19) NOT NULL DEFAULT '',
  `sender_card_id` int(19) DEFAULT 0,
  `description` varchar(500) DEFAULT '0',
  `wallet_receiver_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sender_card` (`sender_card_id`),
  KEY `FK_sender_wallet` (`wallet_receiver_id`),
  CONSTRAINT `FK_sender_card` FOREIGN KEY (`sender_card_id`) REFERENCES `card` (`card_id`),
  CONSTRAINT `FK_sender_wallet` FOREIGN KEY (`wallet_receiver_id`) REFERENCES `wallet` (`wallet_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.deposit: ~0 rows (approximately)
/*!40000 ALTER TABLE `deposit` DISABLE KEYS */;
/*!40000 ALTER TABLE `deposit` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.internal
CREATE TABLE IF NOT EXISTS `internal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` int(11) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `currency` varchar(3) DEFAULT NULL,
  `idempotencykey` varchar(19) NOT NULL,
  `sender_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_receiver` (`receiver_id`),
  KEY `fk_sender` (`sender_id`),
  CONSTRAINT `FK_internal_wallet` FOREIGN KEY (`receiver_id`) REFERENCES `wallet` (`wallet_id`),
  CONSTRAINT `fk_sender` FOREIGN KEY (`sender_id`) REFERENCES `wallet` (`wallet_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.internal: ~0 rows (approximately)
/*!40000 ALTER TABLE `internal` DISABLE KEYS */;
/*!40000 ALTER TABLE `internal` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.users
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT 1,
  `email` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `phone_number` varchar(50) NOT NULL,
  `picture` mediumblob NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `blocked` tinyint(4) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone_number` (`phone_number`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.users: ~1 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
REPLACE INTO `users` (`user_id`, `username`, `enabled`, `email`, `password`, `phone_number`, `picture`, `first_name`, `last_name`, `blocked`) VALUES
	(63, 'atomik333', 1, 'adidas@abv.bg', 'adidas', '0888474742', _binary '', '', '', 0),
	(67, 'atomik3333', 1, 'adidas@abv.bg1', 'adidas', '0888474741', _binary '', '', '', 0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.wallet
CREATE TABLE IF NOT EXISTS `wallet` (
  `wallet_id` int(11) NOT NULL AUTO_INCREMENT,
  `balance` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `currency` varchar(3) NOT NULL,
  PRIMARY KEY (`wallet_id`),
  KEY `FK_wallet_users` (`user_id`),
  CONSTRAINT `FK_wallet_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.wallet: ~2 rows (approximately)
/*!40000 ALTER TABLE `wallet` DISABLE KEYS */;
REPLACE INTO `wallet` (`wallet_id`, `balance`, `user_id`, `currency`) VALUES
	(8, 0, 63, 'USD'),
	(9, 0, 67, 'USD');
/*!40000 ALTER TABLE `wallet` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.withdrawal
CREATE TABLE IF NOT EXISTS `withdrawal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` int(11) NOT NULL,
  `currency` varchar(3) DEFAULT NULL,
  `idempotencykey` varchar(19) NOT NULL,
  `sender_wallet_id` int(11) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `receiver_card_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sender_id` (`sender_wallet_id`),
  KEY `receiver___fk` (`receiver_card_id`),
  CONSTRAINT `receiver___fk` FOREIGN KEY (`receiver_card_id`) REFERENCES `card` (`card_id`),
  CONSTRAINT `sender_id` FOREIGN KEY (`sender_wallet_id`) REFERENCES `wallet` (`wallet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.withdrawal: ~0 rows (approximately)
/*!40000 ALTER TABLE `withdrawal` DISABLE KEYS */;
/*!40000 ALTER TABLE `withdrawal` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
