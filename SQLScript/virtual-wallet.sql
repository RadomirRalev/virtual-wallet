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
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `authority` (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.authorities: ~0 rows (approximately)
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.credit_card
CREATE TABLE IF NOT EXISTS `credit_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(50) NOT NULL,
  `expiration_date` varchar(5) NOT NULL,
  `security_code` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `number` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.credit_card: ~0 rows (approximately)
/*!40000 ALTER TABLE `credit_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit_card` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.debit_card
CREATE TABLE IF NOT EXISTS `debit_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(50) NOT NULL,
  `expiration_date` varchar(5) NOT NULL,
  `security_code` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `number` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.debit_card: ~0 rows (approximately)
/*!40000 ALTER TABLE `debit_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `debit_card` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.users
CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(50) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT 1,
  `email` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `debit_card` int(11) NOT NULL DEFAULT 0,
  `credit_card` int(11) NOT NULL DEFAULT 0,
  `phone_number` int(11) NOT NULL,
  `picture` mediumblob NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone_number` (`phone_number`),
  UNIQUE KEY `debit_card` (`debit_card`),
  UNIQUE KEY `credit_card` (`credit_card`),
  CONSTRAINT `FK_users_credit_card` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`),
  CONSTRAINT `FK_users_debit_card` FOREIGN KEY (`debit_card`) REFERENCES `debit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.users: ~0 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Dumping structure for table virtual_wallet.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_username` varchar(50) NOT NULL,
  `role_username` varchar(50) NOT NULL,
  KEY `FK_user_role_users` (`user_username`),
  KEY `FK_user_role_authorities` (`role_username`),
  CONSTRAINT `FK_user_role_authorities` FOREIGN KEY (`role_username`) REFERENCES `authorities` (`username`),
  CONSTRAINT `FK_user_role_users` FOREIGN KEY (`user_username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table virtual_wallet.user_role: ~0 rows (approximately)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
