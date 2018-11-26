-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: crear_examen
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `abierto`
--

DROP TABLE IF EXISTS `abierto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `abierto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ident` varchar(100) NOT NULL,
  `puntuacion` varchar(5) NOT NULL,
  `quizz` varchar(40) NOT NULL,
  `pregunta` varchar(360) NOT NULL,
  `respuesta` mediumtext NOT NULL,
  `status` varchar(20) NOT NULL,
  `p_asignada` varchar(5) NOT NULL,
  `retro` longtext NOT NULL,
  `intento` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=529 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abierto`
--

LOCK TABLES `abierto` WRITE;
/*!40000 ALTER TABLE `abierto` DISABLE KEYS */;
INSERT INTO `abierto` VALUES (511,'ACP123456','10.0','abierto unico','abierto c','c','Calificado','10.0','/*null*/','1'),(512,'ACP123456','10.0','abierto unico','abierto a','a','Calificado','10.0','/*null*/','1'),(513,'ACP123456','10.0','abierto unico','abierto b','b','Calificado','9.0','/*null*/','1'),(514,'ACP123456','10.0','abierto promedio','promedio a','a','Calificado','10.0','/*null*/','1'),(515,'ACP123456','10.0','abierto promedio','promedio c','c','Calificado','10.0','/*null*/','1'),(516,'ACP123456','10.0','abierto promedio','promedio b','b','Calificado','10.0','/*null*/','1'),(517,'ACP123456','10.0','abierto alto','alto a','a','Calificado','7.0','/*null*/','1'),(518,'ACP123456','10.0','abierto alto','alto b','b','Calificado','7.0','/*null*/','1'),(519,'ACP123456','10.0','abierto alto','alto c','c','Calificado','7.0','/*null*/','1'),(520,'ACP123456','10.0','abierto promedio','promedio a','a','Calificado','10.0','/*null*/','2'),(521,'ACP123456','10.0','abierto promedio','promedio c','c','Calificado','10.0','/*null*/','2'),(522,'ACP123456','10.0','abierto promedio','promedio b','b','Calificado','10.0','/*null*/','2'),(523,'ACP123456','10.0','abierto alto','alto a','a','Calificado','7.0','/*null*/','2'),(524,'ACP123456','10.0','abierto alto','alto c','c','Calificado','7.0','/*null*/','2'),(525,'ACP123456','10.0','abierto alto','alto b','b','Calificado','7.0','/*null*/','2'),(526,'ACP123456','10.0','abierto promedio','promedio b','b','Calificado','10.0','/*null*/','3'),(527,'ACP123456','10.0','abierto promedio','promedio a','a','Calificado','10.0','/*null*/','3'),(528,'ACP123456','10.0','abierto promedio','promedio c','c','Calificado','10.0','/*null*/','3');
/*!40000 ALTER TABLE `abierto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-24 22:55:52
