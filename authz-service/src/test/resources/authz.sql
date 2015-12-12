/*
SQLyog Community v12.08 (64 bit)
MySQL - 5.6.27-0ubuntu0.14.04.1 : Database - hcomb_authz
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hcomb_authz` /*!40100 DEFAULT CHARACTER SET latin1 */;

/*Table structure for table `role_user` */

DROP TABLE IF EXISTS `role_user`;

CREATE TABLE `role_user` (
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `fk_user_id` (`user_id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `role_user` */

insert  into `role_user`(`role_id`,`user_id`) values (1,1);
insert  into `role_user`(`role_id`,`user_id`) values (2,1);

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `roles` */

insert  into `roles`(`id`,`name`) values (1,'USER');
insert  into `roles`(`id`,`name`) values (2,'ADMIN');
insert  into `roles`(`id`,`name`) values (3,'OPERATOR');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13453 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`id`,`username`,`password`) values (1,'alex','1000:d054a114154508a1bca7c450a642d76c62f1a07252e3454f:ae989e1c440314c46b3117f8247f88fad05d94b6d7454aac');
insert  into `users`(`id`,`username`,`password`) values (2,'test','test');
insert  into `users`(`id`,`username`,`password`) values (3,'test2','1000:2f91f3c5460d036dbdb7cc9e5269cf7501607955206dc2e8:7267aa45de28990eaf45dba682f76ef0367b84f62321fed0');
insert  into `users`(`id`,`username`,`password`) values (1176,'test234','1000:d1f44ac3188e06b5794d85e9d1a5ed38b100f8580b0835a7:84fa59da03b2018fea266ade6e8480731e81fb54990cf696');
insert  into `users`(`id`,`username`,`password`) values (8642,'0.5184465551524545','1000:8d4df5430be791f71034d999ca3a13215cccd31b98e727b9:21c0a789eddd57a2dc2712b46055c636e2d2c9e401510554');
insert  into `users`(`id`,`username`,`password`) values (8643,'0.8732703683740476','1000:34d46520a1bca271f78bfad8faebd78a7e6aed9e7cfffdab:42cef0a9badf6ab65161ebd956cd932d502e9b83acae4381');
insert  into `users`(`id`,`username`,`password`) values (13306,'0.6302361399919508','1000:8366b42126a914547f0551775082fdecf647599a53628c6b:081ddfa27ca0f6af0263aed365ea07744db9230cf8ad293a');
insert  into `users`(`id`,`username`,`password`) values (13387,'0.423480053461024','1000:527615d170b39070fe9a71c330b8cf8bc057d9b857eff530:c6aae14ef6d8472bcadaeab9e0b4d2b5f6394822527c74cd');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
