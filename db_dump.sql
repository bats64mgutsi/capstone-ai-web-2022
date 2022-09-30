-- MySQL dump 10.13  Distrib 5.7.39, for Linux (x86_64)
--
-- Host: localhost    Database: aiweb
-- ------------------------------------------------------
-- Server version	5.7.39-0ubuntu0.18.04.2

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
-- Current Database: `aiweb`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `aiweb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `aiweb`;

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hashedPassword` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES (1,'b.mgutsi@gmail.com','e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855'),(2,'b.mgutsi@gmail.com','4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aiKeywords`
--

DROP TABLE IF EXISTS `aiKeywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aiKeywords` (
  `keyword` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aiKeywords`
--

LOCK TABLES `aiKeywords` WRITE;
/*!40000 ALTER TABLE `aiKeywords` DISABLE KEYS */;
INSERT INTO `aiKeywords` VALUES ('artificial intelligence'),('machine learning'),('machine vision'),('neural networks'),('cognitive computing'),('natural language processing'),('computer vision'),('image processing'),('pattern recognition'),('deep learning'),('deep reinforcement learning'),('speech recognition'),('brain-computer interfacing'),('automatic speech recognition');
/*!40000 ALTER TABLE `aiKeywords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `allAuthors`
--

DROP TABLE IF EXISTS `allAuthors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `allAuthors` (
  `id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `surname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `initials` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `institution` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `rating` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `yearAdded` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allAuthors`
--

LOCK TABLES `allAuthors` WRITE;
/*!40000 ALTER TABLE `allAuthors` DISABLE KEYS */;
/*!40000 ALTER TABLE `allAuthors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authorToSubfieldsMap`
--

DROP TABLE IF EXISTS `authorToSubfieldsMap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authorToSubfieldsMap` (
  `authorId` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `subfieldId` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorToSubfieldsMap`
--

LOCK TABLES `authorToSubfieldsMap` WRITE;
/*!40000 ALTER TABLE `authorToSubfieldsMap` DISABLE KEYS */;
/*!40000 ALTER TABLE `authorToSubfieldsMap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authors` (
  `id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `surname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `initials` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `institution` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `rating` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `yearAdded` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors`
--

LOCK TABLES `authors` WRITE;
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contributions`
--

DROP TABLE IF EXISTS `contributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contributions` (
  `publicationId` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contributorId` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` enum('MainAuthor','CoAuthor') COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contributions`
--

LOCK TABLES `contributions` WRITE;
/*!40000 ALTER TABLE `contributions` DISABLE KEYS */;
/*!40000 ALTER TABLE `contributions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `institutions`
--

DROP TABLE IF EXISTS `institutions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `institutions` (
  `id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `latitude` decimal(10,6) DEFAULT NULL,
  `longitude` decimal(10,6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institutions`
--

LOCK TABLES `institutions` WRITE;
/*!40000 ALTER TABLE `institutions` DISABLE KEYS */;
INSERT INTO `institutions` VALUES ('university_of_south_africa','University of South Africa',25.767700,28.199300),('north-west_university','North-West University',26.690600,27.092900),('university_of_pretoria','University of Pretoria',25.754500,28.231400),('tshwane_university_of_technology','Tshwane University of Technology',25.732200,28.161900),('university_of_johannesburg','University of Johannesburg',26.183200,27.999000),('university_of_kwaZulu-Natal','University of KwaZulu-Natal',29.867400,30.980700),('university_of_the_free_state','University of the Free State',29.107600,26.192500),('cape_peninsula_university_of_technology','Cape Peninsula University of Technology',33.930500,18.639100),('university_of_the_witwatersrand','University of the Witwatersrand',26.192900,28.030500),('university_of_stellenbosch','University of Stellenbosch',33.932800,18.864400),('university_of_cape_town','University of Cape Town',33.957700,18.461200),('nelson_mandela_metropolitan_university','Nelson Mandela Metropolitan University',34.001000,25.671500),('walter_sisulu_university','Walter Sisulu University',31.603600,28.750700),('durban_university_of_technology','Durban University of Technology',29.853600,31.006100),('university_of_limpopo','University of Limpopo',23.888800,29.738600),('vaal_university_of_technology','Vaal University of Technology',26.711500,27.861700),('university_of_zululand','University of Zululand',28.757600,32.049700),('university_of_the_western_cape','University of the Western Cape',33.933500,18.628000),('central_university_of_technology','Central University of Technology',29.121700,26.212800),('university_of_fort_hare','University of Fort Hare',32.785900,26.845900),('university_of_venda','University of Venda',22.976100,30.446500),('mangosuthu_university_of_technology','Mangosuthu University of Technology',29.969800,30.913300),('rhodes_university','Rhodes University',33.313600,26.516300),('sefako_makgatho_health_sciences_university','Sefako Makgatho Health Sciences University',25.619200,28.016100),('university_of_mpumalanga','University of Mpumalanga',25.437100,30.981800),('sol_plaatje_university','Sol Plaatje University',28.745000,24.766200);
/*!40000 ALTER TABLE `institutions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keyValues`
--

DROP TABLE IF EXISTS `keyValues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keyValues` (
  `sKey` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sValue` varchar(5000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`sKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyValues`
--

LOCK TABLES `keyValues` WRITE;
/*!40000 ALTER TABLE `keyValues` DISABLE KEYS */;
/*!40000 ALTER TABLE `keyValues` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publications`
--

DROP TABLE IF EXISTS `publications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `publications` (
  `id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `citationCount` int(11) DEFAULT NULL,
  `externalLink` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `year` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publications`
--

LOCK TABLES `publications` WRITE;
/*!40000 ALTER TABLE `publications` DISABLE KEYS */;
/*!40000 ALTER TABLE `publications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subFields`
--

DROP TABLE IF EXISTS `subFields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subFields` (
  `id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subFields`
--

LOCK TABLES `subFields` WRITE;
/*!40000 ALTER TABLE `subFields` DISABLE KEYS */;
/*!40000 ALTER TABLE `subFields` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-30  5:51:22
