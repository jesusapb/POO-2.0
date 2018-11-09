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
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  `ap_pat` varchar(20) NOT NULL,
  `ap_mat` varchar(20) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `matricula` varchar(20) NOT NULL,
  `contraseña` varchar(60) NOT NULL,
  `correo` varchar(40) NOT NULL,
  `dia` varchar(20) NOT NULL,
  `hora` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  `ip` varchar(40) NOT NULL,
  `equipo` varchar(80) NOT NULL,
  `comando` varchar(20) NOT NULL,
  `codigo` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'ANTONIO','CETZAL','PATRON','Administrador','A14000516','Rfmb5851','tonicp97@gmail.com','08/11/2018','21:30:26','Desconectado','192.168.1.142','LAPTOP-4T4LV7H9','Deshabilitado','4a0c5bdb-1e36-4e89-aa26-703e2b648de1'),(3,'POO','ACOMPAÑAMIENTO','PROYECTO','Administrador','PAP123456','Rfmb5851','poo.acompanamiento@gmail.com','23/10/2018','15:27:00','Desconectado','192.168.1.142','LAPTOP-4T4LV7H9','Deshabilitado','Nuevo27887436'),(4,'JESSICA','GONZÁLEZ','BAUTISTA','Administrador','JGB342917','12345678','jessicabautista1698@gmail.com','19/10/2018','14:39:26','Desconectado','148.209.74.25','LAPTOP-4T4LV7H9','Deshabilitado','Nuevo68066997'),(5,'KARINA','CARMONA','VARGAS','Empleado','KCV84890','@NUEVO8266','karina240898@gmail.com','Nuevo','Nuevo','Desconectado','Nuevo','Nuevo','Deshabilitado','Nuevo37581113'),(9,'ANTONIO','CETZAL','PATRON','Empleado','ACP123456','Rfmb5851','tonicp97@gmail.com','06/11/2018','17:02:05','Desconectado','192.168.1.142','LAPTOP-4T4LV7H9','Deshabilitado','Nuevo54334435'),(10,'OTRO','OTRO','OTRO','Empleado','OOO420766','@NUEVO2509','no_tiene@gmail.com','Nuevo','Nuevo','Permanente','Nuevo','Nuevo','Deshabilitado','Nuevo57890456'),(11,'OTRO','MAS','PRUEBA','Empleado','OMP766898','@NUEVO6889','no_tiene@gmail.com','Nuevo','Nuevo','Permanente','Nuevo','Nuevo','Deshabilitado','Nuevo28241659'),(17,'HBJH','KJBNKJ','YGBJYV','Empleado','HKY699031','@NUEVO6873','no_tiene@gmail.com','Nuevo','Nuevo','Permanente','Nuevo','Nuevo','Deshabilitado','Nuevo73484501'),(19,'A','B','C','Empleado','ABC389275','@NUEVO4181','no_tiene@gmail.com','Nuevo','Nuevo','Permanente','Nuevo','Nuevo','Deshabilitado','Nuevo73995724'),(20,'A','A','A','Empleado','AAA599853','@NUEVO5964','no_tiene@gmail.com','Nuevo','Nuevo','Permanente','Nuevo','Nuevo','Deshabilitado','Nuevo299889'),(21,'AA','A','A','Empleado','AAA718393','@NUEVO8776','notiene@gmail.com','Nuevo','Nuevo','Permanente','Nuevo','Nuevo','Deshabilitado','Nuevo45652923'),(22,'P','PRUEBA','PRUEBA','Empleado','PPP635933','@NUEVO9093','ntien@gmail.com','Nuevo','Nuevo','Permanente','Nuevo','Nuevo','Deshabilitado','Nuevo85732652'),(23,'HOLA','OLA','LA','Empleado','HOL696250','@NUEVO5795','ntia@gmail.com','Nuevo','Nuevo','Desconectado','Nuevo','Nuevo','Deshabilitado','Nuevo41471454'),(24,'HJBS<X','DSJINK','JHBSJCH','Empleado','HDJ264080','@NUEVO2910','qwerty@gmail.com','Nuevo','Nuevo','Desconectado','Nuevo','Nuevo','Deshabilitado','Nuevo88353238');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-09 12:39:15
