CREATE DATABASE  IF NOT EXISTS 'letusgo' /*!40100 DEFAULT CHARACTER SET utf8 */;
USE 'letusgo';
-- MySQL dump 10.13  Distrib 5.5.40, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: letusgo
-- ------------------------------------------------------
-- Server version	5.5.40-0ubuntu0.14.04.1

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
-- Table structure for table 'cartitem'
--

DROP TABLE IF EXISTS 'cartitem';
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE 'cartitem' (
  'id' int(11) NOT NULL AUTO_INCREMENT,
  'item_id' int(11) NOT NULL,
  'count' int(11) NOT NULL,
  PRIMARY KEY ('id'),
  KEY 'fk_cartitem_1_idx' ('item_id'),
  CONSTRAINT 'fk_cartitem_1' FOREIGN KEY ('item_id') REFERENCES 'item' ('id') ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table 'cartitem'
--

LOCK TABLES 'cartitem' WRITE;
/*!40000 ALTER TABLE 'cartitem' DISABLE KEYS */;
/*!40000 ALTER TABLE 'cartitem' ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table 'category'
--

DROP TABLE IF EXISTS 'category';
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE 'category' (
  'id' int(11) NOT NULL AUTO_INCREMENT,
  'name' varchar(45) NOT NULL,
  PRIMARY KEY ('id')
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table 'category'
--

LOCK TABLES 'category' WRITE;
/*!40000 ALTER TABLE 'category' DISABLE KEYS */;
INSERT INTO 'category' VALUES (null,'costume'),(null,'drink'),(null,'electric'),(null,'fruit');
/*!40000 ALTER TABLE 'category' ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table 'item'
--

DROP TABLE IF EXISTS 'item';
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE 'item' (
  'id' int(11) NOT NULL AUTO_INCREMENT,
  'barcode' varchar(45) NOT NULL,
  'name' varchar(45) NOT NULL,
  'price' double(10,2) NOT NULL,
  'unit' varchar(45) NOT NULL,
  'category_id' int(11) DEFAULT NULL,
  PRIMARY KEY ('id'),
  KEY 'fk_item_1_idx' ('category_id'),
  CONSTRAINT 'fk_item_1' FOREIGN KEY ('category_id') REFERENCES 'category' ('id') ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table 'item'
--

LOCK TABLES 'item' WRITE;
/*!40000 ALTER TABLE 'item' DISABLE KEYS */;
INSERT INTO 'item' VALUES (null,'ITEM000009','coca-cola',3.00,'bottle',3),(null,'ITEM000009','sprite',3.50,'bottle',3),(null,'ITEM000009','shoe',99.90,'pair',2),(null,'ITEM000009','apple',8.00,'kg',18),(null,'ITEM000009','banana',5.00,'kg',3),(null,'ITEM000009','battery',0.50,'pe',16);
/*!40000 ALTER TABLE 'item' ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-14 22:07:35
