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
-- Table structure for table `preguntas`
--

DROP TABLE IF EXISTS `preguntas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `preguntas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quizz` varchar(40) NOT NULL,
  `pregunta` varchar(360) NOT NULL,
  `tipo` varchar(10) NOT NULL,
  `num_resp` varchar(10) NOT NULL,
  `puntuacion_total` varchar(5) NOT NULL,
  `resp1` varchar(40) NOT NULL,
  `r1` varchar(10) NOT NULL,
  `resp2` varchar(40) NOT NULL,
  `r2` varchar(10) NOT NULL,
  `resp3` varchar(40) NOT NULL,
  `r3` varchar(10) NOT NULL,
  `resp4` varchar(40) NOT NULL,
  `r4` varchar(10) NOT NULL,
  `dis1` varchar(40) NOT NULL,
  `dis2` varchar(40) NOT NULL,
  `dis3` varchar(40) NOT NULL,
  `dis4` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preguntas`
--

LOCK TABLES `preguntas` WRITE;
/*!40000 ALTER TABLE `preguntas` DISABLE KEYS */;
INSERT INTO `preguntas` VALUES (1,'3','numeracion','unico','1','80.0','1','80.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','2','3','4','5'),(20,'3','consecutivo','multiple','4','40.0','1','10.0','2','10.0','3','10.0','4','10.0','5','6','7','8'),(21,'3','multiplos de 2','unico','1','60.0','2','60.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','1','*/null/*','*/null/*','*/null/*'),(22,'6','primera letra del abc-dario','unico','1','10.0','a','10.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','c','w','x','f'),(23,'6','ultima letra del abc-dario','unico','1','10.0','z','10.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','x','y','w','a'),(24,'3','triple de 3','unico','1','15.0','18','15.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','24','17','9','6');
/*!40000 ALTER TABLE `preguntas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-09 12:39:16
