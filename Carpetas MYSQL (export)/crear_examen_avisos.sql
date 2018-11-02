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
-- Table structure for table `avisos`
--

DROP TABLE IF EXISTS `avisos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `avisos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(20) NOT NULL,
  `quien` varchar(100) NOT NULL,
  `que` tinytext NOT NULL,
  `cuando` varchar(40) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avisos`
--

LOCK TABLES `avisos` WRITE;
/*!40000 ALTER TABLE `avisos` DISABLE KEYS */;
INSERT INTO `avisos` VALUES (77,'A14000516','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se activó el Quizz: prueba','22/10/2018 18:08:55','visto'),(78,'JGB342917','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se activó el Quizz: prueba','22/10/2018 18:08:55','no visto'),(79,'KCV84890','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se activó el Quizz: prueba','22/10/2018 18:08:55','no visto'),(80,'NNN99214','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se activó el Quizz: prueba','22/10/2018 18:08:55','no visto'),(81,'OOO420766','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se activó el Quizz: prueba','22/10/2018 18:08:55','no visto'),(82,'OMP766898','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se activó el Quizz: prueba','22/10/2018 18:08:55','no visto'),(83,'HKY699031','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se activó el Quizz: prueba','22/10/2018 18:08:55','no visto'),(84,'ABC389275','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se activó el Quizz: prueba','22/10/2018 18:08:55','no visto'),(85,'AAA599853','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se activó el Quizz: prueba','22/10/2018 18:08:55','no visto'),(86,'AAA718393','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se activó el Quizz: prueba','22/10/2018 18:08:55','no visto'),(87,'PPP635933','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se activó el Quizz: prueba','22/10/2018 18:08:55','no visto'),(88,'HOL696250','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se activó el Quizz: prueba','22/10/2018 18:08:55','no visto'),(89,'HDJ264080','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se activó el Quizz: prueba','22/10/2018 18:08:55','no visto'),(90,'A14000516','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Desactivó el Quizz: prueba','22/10/2018 18:11:53','visto'),(91,'JGB342917','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Desactivó el Quizz: prueba','22/10/2018 18:11:53','no visto'),(92,'KCV84890','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se desactivó el Quizz: prueba','22/10/2018 18:11:53','no visto'),(93,'NNN99214','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se desactivó el Quizz: prueba','22/10/2018 18:11:53','no visto'),(94,'OOO420766','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se desactivó el Quizz: prueba','22/10/2018 18:11:53','no visto'),(95,'OMP766898','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se desactivó el Quizz: prueba','22/10/2018 18:11:53','no visto'),(96,'HKY699031','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se desactivó el Quizz: prueba','22/10/2018 18:11:53','no visto'),(97,'ABC389275','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se desactivó el Quizz: prueba','22/10/2018 18:11:53','no visto'),(98,'AAA599853','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se desactivó el Quizz: prueba','22/10/2018 18:11:53','no visto'),(99,'AAA718393','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se desactivó el Quizz: prueba','22/10/2018 18:11:53','no visto'),(100,'PPP635933','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se desactivó el Quizz: prueba','22/10/2018 18:11:53','no visto'),(101,'HOL696250','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se desactivó el Quizz: prueba','22/10/2018 18:11:53','no visto'),(102,'HDJ264080','PAP123456/ POO ACOMPAÑAMIENTO PROYECTO','Se desactivó el Quizz: prueba','22/10/2018 18:11:53','no visto'),(103,'PAP123456','A14000516/ ANTONIO CETZAL PATRON','Desactivó el Documento: LOGIN','22/10/2018 18:13:26','visto'),(104,'JGB342917','A14000516/ ANTONIO CETZAL PATRON','Desactivó el Documento: LOGIN','22/10/2018 18:13:26','no visto'),(105,'KCV84890','A14000516/ ANTONIO CETZAL PATRON','Se desactivó el Documento: LOGIN','22/10/2018 18:13:26','no visto'),(106,'NNN99214','A14000516/ ANTONIO CETZAL PATRON','Se desactivó el Documento: LOGIN','22/10/2018 18:13:26','no visto'),(107,'OOO420766','A14000516/ ANTONIO CETZAL PATRON','Se desactivó el Documento: LOGIN','22/10/2018 18:13:26','no visto'),(108,'OMP766898','A14000516/ ANTONIO CETZAL PATRON','Se desactivó el Documento: LOGIN','22/10/2018 18:13:26','no visto'),(109,'HKY699031','A14000516/ ANTONIO CETZAL PATRON','Se desactivó el Documento: LOGIN','22/10/2018 18:13:26','no visto'),(110,'ABC389275','A14000516/ ANTONIO CETZAL PATRON','Se desactivó el Documento: LOGIN','22/10/2018 18:13:26','no visto'),(111,'AAA599853','A14000516/ ANTONIO CETZAL PATRON','Se desactivó el Documento: LOGIN','22/10/2018 18:13:26','no visto'),(112,'AAA718393','A14000516/ ANTONIO CETZAL PATRON','Se desactivó el Documento: LOGIN','22/10/2018 18:13:26','no visto'),(113,'PPP635933','A14000516/ ANTONIO CETZAL PATRON','Se desactivó el Documento: LOGIN','22/10/2018 18:13:26','no visto'),(114,'HOL696250','A14000516/ ANTONIO CETZAL PATRON','Se desactivó el Documento: LOGIN','22/10/2018 18:13:26','no visto'),(115,'HDJ264080','A14000516/ ANTONIO CETZAL PATRON','Se desactivó el Documento: LOGIN','22/10/2018 18:13:26','no visto');
/*!40000 ALTER TABLE `avisos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-02 16:10:35
