-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: nomin
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment_restaurant`
--

DROP TABLE IF EXISTS `comment_restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_restaurant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `rate` int DEFAULT NULL,
  `users_id` int DEFAULT NULL,
  `restaurants_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_comment_restaurant_users1_idx` (`users_id`),
  KEY `fk_comment_restaurant_restaurants1_idx` (`restaurants_id`),
  CONSTRAINT `fk_comment_restaurant_restaurants1` FOREIGN KEY (`restaurants_id`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `fk_comment_restaurant_users1` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_restaurant`
--

LOCK TABLES `comment_restaurant` WRITE;
/*!40000 ALTER TABLE `comment_restaurant` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments_food`
--

DROP TABLE IF EXISTS `comments_food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments_food` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `rate` int DEFAULT NULL,
  `users_id` int DEFAULT NULL,
  `foods_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Comments_users1_idx` (`users_id`),
  KEY `fk_Comments_foods1_idx` (`foods_id`),
  CONSTRAINT `fk_Comments_foods1` FOREIGN KEY (`foods_id`) REFERENCES `food` (`id`),
  CONSTRAINT `fk_Comments_users1` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments_food`
--

LOCK TABLES `comments_food` WRITE;
/*!40000 ALTER TABLE `comments_food` DISABLE KEYS */;
INSERT INTO `comments_food` VALUES (1,'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis,',4,4,1),(2,'asdhjkashdjkashjkd',3,6,1),(3,'dở vãi',3,6,1),(11,'Món này ngon lắm nha :>> nhưng thích cho 1 sao à',1,4,2),(12,'Dở quá ',5,4,1),(15,'Mì gõ là nhất',5,4,6),(16,'siuuuuuuuuuuuuuu',4,4,7),(19,'ádfasgewq',0,6,2),(20,'fasdas',5,6,2);
/*!40000 ALTER TABLE `comments_food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL,
  `orders_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `expiration_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_coupon_orders1_idx` (`orders_id`),
  CONSTRAINT `fk_coupon_orders1` FOREIGN KEY (`orders_id`) REFERENCES `orderfood` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follow` (
  `id` int NOT NULL AUTO_INCREMENT,
  `restaurants_id` int DEFAULT NULL,
  `users_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_follow_restaurants1_idx` (`restaurants_id`),
  KEY `fk_follow_users1_idx` (`users_id`),
  CONSTRAINT `fk_follow_restaurants1` FOREIGN KEY (`restaurants_id`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `fk_follow_users1` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES (28,2,6),(30,2,4),(31,1,4),(33,1,6),(34,2,53),(35,1,53);
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `food_type` varchar(45) DEFAULT NULL,
  `restaurant_id` int DEFAULT NULL,
  `available` int DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_foods_restaurants1_idx` (`restaurant_id`),
  CONSTRAINT `fk_foods_restaurants1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food`
--

LOCK TABLES `food` WRITE;
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
INSERT INTO `food` VALUES (1,'Gà rán - Siêu ngon',39000,'Thức ăn nhanh',1,1,'https://res.cloudinary.com/dvyk1ajlj/image/upload/v1694193907/wf2hdutyjzaiaudarsyl.png'),(2,'Pepsi vị chanh không calo sảng khoái',30000,'Đồ uống',1,1,'https://res.cloudinary.com/dvyk1ajlj/image/upload/v1694147909/negogghwsl6jcz1aw8vb.jpg'),(3,'Hamburger',40000,'Thức ăn nhanh',1,1,'https://res.cloudinary.com/dvyk1ajlj/image/upload/v1691230804/pngegg_1_wbepht.png'),(4,'Khoai tây chiên',20000,'Ăn vặt',1,1,'https://res.cloudinary.com/dvyk1ajlj/image/upload/v1691230804/pngegg_1_wbepht.png'),(5,'Salad',2000,'Rau củ',1,1,'https://res.cloudinary.com/dvyk1ajlj/image/upload/v1691230804/pngegg_1_wbepht.png'),(6,'Mì gõ',25000,'Đồ nước',2,1,'https://res.cloudinary.com/dvyk1ajlj/image/upload/v1691230804/pngegg_1_wbepht.png'),(7,'Hủ tiếu',20000,'Đồ nước',2,1,'https://res.cloudinary.com/dvyk1ajlj/image/upload/v1692523146/bt5tzansdyftppdyhx6e.jpg'),(8,'Mỳ ý',40000,'Thức ăn nhanh',1,1,'https://res.cloudinary.com/dvyk1ajlj/image/upload/v1691230804/pngegg_1_wbepht.png'),(9,'Coca',20000,'Đồ uống',1,1,'https://res.cloudinary.com/dvyk1ajlj/image/upload/v1691230804/pngegg_1_wbepht.png'),(13,'Chú Thịnh',1000,'Thức ăn nhanh',1,1,'https://res.cloudinary.com/dvyk1ajlj/image/upload/v1691230804/pngegg_1_wbepht.png'),(14,'Bánh canh',25000,'Đồ nước',2,1,'https://res.cloudinary.com/dvyk1ajlj/image/upload/v1691230804/pngegg_1_wbepht.png');
/*!40000 ALTER TABLE `food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `orders_id` int DEFAULT NULL,
  `foods_id` int DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_detail_orders1_idx` (`orders_id`),
  KEY `fk_order_detail_foods1_idx` (`foods_id`),
  CONSTRAINT `fk_order_detail_foods1` FOREIGN KEY (`foods_id`) REFERENCES `food` (`id`),
  CONSTRAINT `fk_order_detail_orders1` FOREIGN KEY (`orders_id`) REFERENCES `orderfood` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (109,1,59,1,39000),(110,1,59,2,30000),(111,1,60,6,25000),(112,1,60,7,20000),(113,1,61,1,39000),(114,1,61,2,30000),(115,1,62,6,25000),(116,1,62,7,20000),(117,1,63,3,40000),(118,1,63,4,20000),(119,1,64,1,39000),(120,1,64,2,30000),(121,4,65,6,25000);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderfood`
--

DROP TABLE IF EXISTS `orderfood`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderfood` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` int DEFAULT NULL,
  `users_id` int DEFAULT NULL,
  `restaurants_id` int DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `payment_method` varchar(100) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orders_users1_idx` (`users_id`),
  KEY `fk_orders_restaurants1_idx` (`restaurants_id`),
  CONSTRAINT `fk_orders_restaurants1` FOREIGN KEY (`restaurants_id`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `fk_orders_users1` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderfood`
--

LOCK TABLES `orderfood` WRITE;
/*!40000 ALTER TABLE `orderfood` DISABLE KEYS */;
INSERT INTO `orderfood` VALUES (59,0,6,1,119000,'COD','2023-09-10'),(60,0,6,2,70000,'COD','2022-12-10'),(61,0,6,1,119000,'COD','2023-04-10'),(62,0,6,2,70000,'COD','2023-01-10'),(63,0,6,1,110000,'COD','2023-10-10'),(64,0,53,1,119000,'COD','2023-09-10'),(65,0,53,2,125000,'COD','2023-09-10');
/*!40000 ALTER TABLE `orderfood` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `delivery_fee` double NOT NULL,
  `status` int DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_restaurant_user1_idx` (`user_id`),
  CONSTRAINT `fk_restaurant_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (1,'KFC - Gà rán siêu ngon',50000,1,2),(2,'Amigogo',25000,1,3);
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `phone` varchar(10) NOT NULL,
  `cccd` varchar(12) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `gender` tinyint DEFAULT NULL,
  `address` varchar(150) NOT NULL,
  `role` varchar(20) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  UNIQUE KEY `cccd_UNIQUE` (`cccd`),
  UNIQUE KEY `password_UNIQUE` (`password`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Lê Minh Đức','0366004732','089202015394','leminhduc6402@gmail.com','2002-04-06',0,'Chung cư Gia Phú, Phường Bình Hưng Hoà, Quận Bình Tân, Thành phố Hồ Chí Minh','ROLE_ADMIN','admin','$2a$12$ZKfOeauVHWzpg29aCvzOL.gA6AV6Z1I6Capo8v7Z8OOxTVzTWOBAy','https://res.cloudinary.com/dvyk1ajlj/image/upload/v1692434573/qwwgnt0ieot3xvfxnvdg.png'),(2,'Lê Huỳnh Đức','0123456789','012312312312','truongthanhdat2002@gmail.com','2002-01-31',1,'Chung cư Gia Phú, Phường Bình Hưng Hoà, Quận Bình Tân, Thành phố Hồ Chí Minh','ROLE_RESTAURANT','restaurant','$2a$12$xMN0O4yR.NtC.EZNKIGJ7u0f89eNTmPzxU1Ebtek0vF0LWhPnSIMa','https://res.cloudinary.com/dvyk1ajlj/image/upload/v1692523146/bt5tzansdyftppdyhx6e.jpg'),(3,'Trần Ngọc Tuấn','0388005589','012312332112','tranngoctuan@gmail.com','2002-10-20',1,'371 Nguyễn Kiệm, Phường 3, Quận Gò Vấp, Thành phố Hồ Chí Minh','ROLE_RESTAURANT','restaurant1','$2a$12$5.iZ179C4hSkuKDwf3vekOpSvdnwhZw3/I1lnvvcZ7X6FkShABbZ6','https://res.cloudinary.com/dvyk1ajlj/image/upload/v1692523542/iibhhu4xdx3tj6kzdks1.png'),(4,'Nguyễn Trần Phước Thuận','0633113113','011234561231','2051052132thinh@ou.edu.vn','2002-09-09',1,'317 Hai Bà Trưng, Phường 6, Thành phố Đà Lạt','ROLE_USER','user12','$2a$12$5ARMvAOCiFirtHExUmwy5OxHnVpIa28DgDIfsF3HJHehmfArP9Ndm','https://res.cloudinary.com/dvyk1ajlj/image/upload/v1690620249/xofbyivlqlztrhwlz08u.png'),(6,'css123','0321321321','079796852819','2051052029duc@ou.edu.vn','2016-04-26',1,'317 Hai Bà Trưng, Phường 6, Thành phố Đà Lạt','ROLE_USER','user1','$2a$12$6vrvGPRJBMG5jvPmDUP0Su5DrPD9cwjfLwG/688V/hjtBj/owVYa.','https://res.cloudinary.com/dvyk1ajlj/image/upload/v1694196568/fru9bez2r0qvkl9a9tn6.jpg'),(53,'Trần Ngọc Tuấn','0388005529',NULL,'2051052145tuan@ou.edu.vn','2002-10-20',0,'371 Nguyễn Kiệm, Phường Đức Nghĩa, Thành phố Phan Thiết, Tỉnh Bình Thuận','ROLE_USER','tuan21','$2a$10$wvAeKhDs9rwK8Kmp2o/qSe9ur2dg2IUe.nFPsmLaGjaSJkFPVhePi','https://res.cloudinary.com/dvyk1ajlj/image/upload/v1694343949/q1kvjhuef2gh8b1ef8od.jpg');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-10 18:23:33
