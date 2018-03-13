-- MySQL dump 10.13  Distrib 5.5.57, for Win64 (AMD64)
--
-- Host: localhost    Database: Donation
-- ------------------------------------------------------
-- Server version	5.5.57-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `donations`
--

DROP TABLE IF EXISTS `donations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `donations` (
  `DonId` char(10) NOT NULL DEFAULT '',
  `donation` char(20) DEFAULT NULL,
  `category` char(20) DEFAULT NULL,
  `description` char(100) DEFAULT NULL,
  `donar` char(10) DEFAULT NULL,
  `recipient` char(10) DEFAULT NULL,
  `donDate` date DEFAULT NULL,
  PRIMARY KEY (`DonId`),
  KEY `donar` (`donar`),
  CONSTRAINT `donations_ibfk_1` FOREIGN KEY (`donar`) REFERENCES `user` (`nic`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donations`
--

LOCK TABLES `donations` WRITE;
/*!40000 ALTER TABLE `donations` DISABLE KEYS */;
INSERT INTO `donations` VALUES ('D000000001','Arduino','Electronics','Arduino Mega Board.','952050826V','','2017-12-26'),('D000000002','Toy Car','Kids','Batery powered toy car','952050826V','','2017-12-26'),('D000000003','Pic','Electronics','Pic 16F877A IC','952050826V','','2017-12-26'),('D000000004','GPS module','Electronics','M8N GPS module. Compass module is included.','952316482V','','2017-12-26'),('D000000005','Flight Controller','Electronics','Naza M Light flight controller.','952316482V','958331252V','2017-12-26'),('D000000006','Shoes','Sport','Red Coloured pair of shoes','952316482V','952050826V','2017-12-26'),('D000000007','Quadcopter','Electronics','F450 Quadcopter. Powered by Ardupilot\n2.8','958331252V','952316482V','2017-12-26'),('D000000008','Story Books','Books','Set of 4 old story books.','958331252V','','2017-12-26'),('D000000009','GPS Module','Electronics','Naza M8N GPS module','952050826V','','2017-12-26'),('D000000010','Sofa','Household','Used 1 year.','958331252V','','2017-12-26'),('D000000011','Toy Train','Kids','Toy Train set.','958331252V','','2017-12-26');
/*!40000 ALTER TABLE `donations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `management`
--

DROP TABLE IF EXISTS `management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `management` (
  `num` int(11) DEFAULT NULL,
  `pass` char(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `management`
--

LOCK TABLES `management` WRITE;
/*!40000 ALTER TABLE `management` DISABLE KEYS */;
INSERT INTO `management` VALUES (1,'management');
/*!40000 ALTER TABLE `management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requests` (
  `DonId` char(10) DEFAULT NULL,
  `nic` char(10) DEFAULT NULL,
  KEY `DonId` (`DonId`),
  KEY `nic` (`nic`),
  CONSTRAINT `requests_ibfk_1` FOREIGN KEY (`DonId`) REFERENCES `donations` (`DonId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `requests_ibfk_2` FOREIGN KEY (`nic`) REFERENCES `user` (`nic`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requests`
--

LOCK TABLES `requests` WRITE;
/*!40000 ALTER TABLE `requests` DISABLE KEYS */;
INSERT INTO `requests` VALUES ('D000000001','958331252V'),('D000000001','952316482V'),('D000000004','952050826V'),('D000000006','952050826V'),('D000000004','958331252V'),('D000000005','958331252V'),('D000000007','952316482V'),('D000000011','952050826V'),('D000000011','952316482V'),('D000000002','952316482V'),('D000000002','958331252V');
/*!40000 ALTER TABLE `requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `fName` char(20) DEFAULT NULL,
  `lName` char(20) DEFAULT NULL,
  `nic` char(10) NOT NULL DEFAULT '',
  `tp` int(11) DEFAULT NULL,
  `mail` char(50) DEFAULT NULL,
  `address` char(50) DEFAULT NULL,
  `password` char(15) DEFAULT NULL,
  PRIMARY KEY (`nic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('Vibhath','Ileperuma','952050826V',772715343,'arunapriya11@yahoo.com','146ZB, Doowa Road, Kolamediriya, Bandaragama','vibhath'),('Lochana','Chathuranga','952316482V',778945612,'lochana.chathura@gmail.com','789, sbyucii,cvysbc','lochana'),('Isuru','Nuwanthilaka','958331252V',779456136,'isurunuwanthilaka@gmail.com','sxacsa dcds dcs','isuru');
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

-- Dump completed on 2017-12-26 22:28:02
