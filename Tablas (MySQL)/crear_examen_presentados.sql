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
-- Table structure for table `presentados`
--

DROP TABLE IF EXISTS `presentados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `presentados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ident` varchar(100) NOT NULL,
  `quizz` varchar(40) NOT NULL,
  `intento` varchar(10) NOT NULL,
  `p_totales` varchar(15) NOT NULL,
  `calificacion` varchar(25) NOT NULL,
  `status` varchar(45) NOT NULL,
  `abrtNum` int(11) NOT NULL,
  `abrtTot` int(11) NOT NULL,
  `abrt` longtext NOT NULL,
  `mod_calif` varchar(45) NOT NULL,
  `marca` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=198 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presentados`
--

LOCK TABLES `presentados` WRITE;
/*!40000 ALTER TABLE `presentados` DISABLE KEYS */;
INSERT INTO `presentados` VALUES (185,'ACP123456','prueba con un intento','1','13.33~100','10.0~75.0~0','Aprobado',0,0,'nada','Primer intento','Sin acceso'),(186,'ACP123456','prueba con calificacion más alta','1','15.0~100','5.0~33.33~0','Sin acceso',0,0,'nada','Calificacion más alta','Sin acceso'),(187,'ACP123456','prueba con calificacion más alta','2','15.0~100','8.33~55.56~0','Sin acceso',0,0,'nada','Calificacion más alta','Sin acceso'),(188,'ACP123456','prueba con calificacion más alta','3','15.0~100','13.33~88.89~0','Aprobado',0,0,'nada','Calificacion más alta','Sin acceso'),(189,'ACP123456','prueba con promedio de calificaciones','1','15.0~100','5.0~33.33~0','Sin acceso',0,0,'nada','Promedio de calificaciones','Sin acceso'),(190,'ACP123456','prueba con promedio de calificaciones','2','17.5~66.0','12.5~47.14~0','Sin acceso',0,0,'nada','Promedio de calificaciones','Sin acceso'),(191,'ACP123456','prueba con promedio de calificaciones','3','15.0~100','15.0~100.0~0','Aprobado',0,0,'nada','Promedio de calificaciones','Sin acceso'),(192,'ACP123456','abierto unico','1','10.0~100','9.67~96.67~9.67','Aprobado',3,3,'abierto c~abierto a~abierto b','Primer intento','Acceso'),(193,'ACP123456','abierto promedio','1','10.0~100','5.0~50.0~5.0','Reprobado',3,3,'promedio a~promedio c~promedio b','Promedio de calificaciones','Sin acceso'),(194,'ACP123456','abierto alto','1','10.0~100','5.0~50.0~5.0','Reprobado',3,3,'alto a~alto b~alto c','Calificacion más alta','Sin acceso'),(195,'ACP123456','abierto promedio','2','10.0~100','7.0~70.0~7.0','Aprobado',3,3,'promedio a~promedio c~promedio b','Promedio de calificaciones','Sin acceso'),(196,'ACP123456','abierto alto','2','10.0~100','7.0~70.0~7.0','Aprobado',3,3,'alto a~alto c~alto b','Calificacion más alta','Acceso'),(197,'ACP123456','abierto promedio','3','10.0~100','10.0~100.0~10.0','Aprobado',3,3,'promedio b~promedio a~promedio c','Promedio de calificaciones','Acceso');
/*!40000 ALTER TABLE `presentados` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-24 22:55:49
