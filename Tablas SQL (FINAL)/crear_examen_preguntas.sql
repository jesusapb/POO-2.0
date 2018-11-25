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
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preguntas`
--

LOCK TABLES `preguntas` WRITE;
/*!40000 ALTER TABLE `preguntas` DISABLE KEYS */;
INSERT INTO `preguntas` VALUES (56,'11','hola1','unico','1','10.0','a','10.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','b','c','d','e'),(57,'11','hola2','multiple','2','10.0','a','5.0','b','5.0','*/null/*','*/null/*','*/null/*','*/null/*','c','d','e','f'),(58,'11','hola3','multiple','3','15.0','a','5.0','b','5.0','c','5.0','*/null/*','*/null/*','d','e','f','g'),(59,'11','hola4','multiple','4','20.0','a','5.0','b','5.0','c','5.0','d','5.0','*/null/*','*/null/*','*/null/*','*/null/*'),(60,'13','holaprom2','multiple','2','10.0','a','5.0','b','5.0','*/null/*','*/null/*','*/null/*','*/null/*','c','d','*/null/*','*/null/*'),(61,'13','holaprom3','multiple','3','15.0','a','5.0','b','5.0','c','5.0','*/null/*','*/null/*','d','e','*/null/*','*/null/*'),(62,'13','holaprom4','multiple','4','20.0','a','5.0','b','5.0','c','5.0','d','5.0','*/null/*','*/null/*','*/null/*','*/null/*'),(63,'12','holaalto2','multiple','2','10.0','a','5.0','b','5.0','*/null/*','*/null/*','*/null/*','*/null/*','c','*/null/*','*/null/*','*/null/*'),(64,'12','holaalto3','multiple','3','15.0','a','5.0','b','5.0','c','5.0','*/null/*','*/null/*','d','*/null/*','*/null/*','*/null/*'),(65,'12','holaalto4','multiple','4','20.0','a','5.0','b','5.0','c','5.0','d','5.0','*/null/*','e','*/null/*','f'),(66,'14','abierto a','abierto','*/null/*','10.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*'),(67,'14','abierto b','abierto','*/null/*','10.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*'),(68,'14','abierto c','abierto','*/null/*','10.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*'),(69,'16','alto a','abierto','*/null/*','10.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*'),(70,'16','alto b','abierto','*/null/*','10.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*'),(71,'16','alto c','abierto','*/null/*','10.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*'),(72,'15','promedio a','abierto','*/null/*','10.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*'),(73,'15','promedio b','abierto','*/null/*','10.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*'),(74,'15','promedio c','abierto','*/null/*','10.0','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*','*/null/*');
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

-- Dump completed on 2018-11-24 22:55:46
