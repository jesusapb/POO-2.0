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
-- Table structure for table `mensajes`
--

DROP TABLE IF EXISTS `mensajes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mensajes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `de_mat` varchar(20) NOT NULL,
  `de_nom` varchar(60) NOT NULL,
  `para_mat` varchar(20) NOT NULL,
  `para_nom` varchar(60) NOT NULL,
  `fecha` varchar(40) NOT NULL,
  `asunto` tinytext NOT NULL,
  `mensaje` text NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensajes`
--

LOCK TABLES `mensajes` WRITE;
/*!40000 ALTER TABLE `mensajes` DISABLE KEYS */;
INSERT INTO `mensajes` VALUES (2,'A14000516','ANTONIO CETZAL PATRON','PAP123456','POO ACOMPAÑAMIENTO PROYECTO','14/10/2018 22:16:18','esto es una prueba','probando el sistema de envio de mensajes.\r\nespero que funcione.','NO VISTO'),(3,'A14000516','ANTONIO CETZAL PATRON','PAP123456','POO ACOMPAÑAMIENTO PROYECTO','14/10/2018 23:42:16','esto es una prueba','probando el sistema de envio de mensajes.\r\nespero que funcione.','NO VISTO'),(4,'A14000516','ANTONIO CETZAL PATRON','PAP123456','POO ACOMPAÑAMIENTO PROYECTO','14/10/2018 23:44:06','esto es una prueba','probando el sistema de envio de mensajes.\r\nespero que funcione.','NO VISTO'),(21,'JGB342917','JESSICA GONZÁLEZ BAUTISTA','A14000516','ANTONIO CETZAL PATRON','19/10/2018 14:26:59','rfgfrrgr','rggr','VISTO'),(22,'JGB342917','JESSICA GONZÁLEZ BAUTISTA','JGB342917','JESSICA GONZÁLEZ BAUTISTA','19/10/2018 14:27:55','efce','cfec','VISTO'),(23,'A14000516','ANTONIO CETZAL PATRON','A14000516','ANTONIO CETZAL PATRON','20/10/2018 14:35:27','hola','hola, que tal?','VISTO'),(25,'A14000516','ANTONIO CETZAL PATRON','A14000516','ANTONIO CETZAL PATRON','20/10/2018 14:36:38','contestar','bien y tu?','NO VISTO');
/*!40000 ALTER TABLE `mensajes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-02 16:10:43
