-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: localhost    Database: sgh
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Address`
--

DROP TABLE IF EXISTS `Address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `zipCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Address`
--

LOCK TABLES `Address` WRITE;
/*!40000 ALTER TABLE `Address` DISABLE KEYS */;
INSERT INTO `Address` VALUES (1,'Franca','Jardim América','1F','SP','Rua 1','14409012'),(4,'Franca','Jardim América','2F','SP','Rua 3','14409012'),(5,'Barretos','Jardim Europa','3G','SP','Rua 2','12030530'),(6,'Manaus','Centro','25','AM','Rua 5','78895874'),(7,'Recife','Jardim Botanico','125','PE','Rua 6','88895874'),(9,'cidade','bairro','1','sp','endereco','14409012'),(21,'Paris','Jd Eifel','1','SP','Rua Paris','14409012'),(23,'Cidade','bairro','1','SP','endereco','14409012'),(24,'city contact','district contact','11','SP','street Address','13604304'),(25,'city contact','district contact','11','SP','street Address','13604304'),(26,'city contact','district contact','11','SP','street Address','13604304'),(27,'city contact','district contact','11','SP','street Address','13604304'),(28,'city contact','district contact','11','SP','street Address','13604304'),(29,'city contact','district contact','11','SP','street Address','13604304'),(30,'city contact','district contact','11','SP','street Address','13604304'),(31,'city contact','district contact','11','SP','street Address','13604304'),(32,'city contact','district contact','11','SP','street Address','13604304'),(33,'city contact','district contact','11','SP','street Address','13604304'),(34,'city contact','district contact','11','SP','street Address','13604304'),(35,'city contact','district contact','11','SP','street Address','13604304'),(36,'city contact','district contact','11','SP','street Address','13604304'),(37,'city contact','district contact','11','SP','street Address','13604304'),(38,'city contact','district contact','11','SP','street Address','13604304'),(39,'city contact','district contact','11','SP','street Address','13604304'),(40,'city contact','district contact','11','SP','street Address','13604304'),(41,'city contact','district contact','11','SP','street Address','13604304'),(42,'city contact','district contact','11','SP','street Address','13604304'),(43,'city contact','district contact','11','SP','street Address','13604304'),(44,'city contact','district contact','11','SP','street Address','13604304'),(45,'city contact','district contact','11','SP','street Address','13604304'),(46,'city contact','district contact','11','SP','street Address','13604304'),(47,'city contact','district contact','11','SP','street Address','13604304'),(48,'Grecia','Roma','1','SP','Rua Coliseu','14409012'),(49,'city contact','district contact','11','SP','street Address','13604304'),(50,'city contact','district contact','11','SP','street Address','13604304'),(51,'city contact','district contact','11','SP','street Address','13604304'),(53,'asdfasf','Centro','13','SP','Rua rua rua','13604304'),(55,'city contact','district contact','11','SP','street Address','13604304'),(56,'Araras','ffffffffffff','1','SP','ddddddddddd','14409012'),(57,'city contact','district contact','11','SP','street Address','13604304'),(58,'city contact','district contact','11','SP','street Address','13604304'),(59,'city contact','district contact','11','SP','street Address','13604304'),(60,'city contact','district contact','11','SP','street Address','13604304'),(61,'city contact','district contact','11','SP','street Address','13604304'),(63,'cidade','bairro','1','SP','endereco','14409012'),(64,'cidade','bairro\'','1','SP','endereco','14409012'),(65,'cidade','bairro\'','1','SP','endereco','14409012'),(66,'Cidade','Bairro','1','SP','Endereco','14409012'),(67,'city contact','district contact','11','SP','street Address','13604304'),(68,'cidade','bairro','1','SP','endereco','14409012'),(69,'city contact','district contact','11','SP','street Address','13604304'),(70,'city contact','district contact','11','SP','street Address','13604304'),(71,'city contact','district contact','11','SP','street Address','13604304'),(72,'cidade','bairro','1','SP','endereco','14409012'),(73,'cidade','Bairro\'','1','SP','Endereco','14409012'),(74,'city contact','district contact','11','SP','street Address','13604304'),(75,'teste','teste\'','1','SP','teste','14409012'),(76,'teste','teste','1','SP','teste','14409012'),(77,'teste','teste\'','1','SP','teste','14409012'),(78,'teste','teste\'','1','SP','teste','14409012'),(79,'teste','teste\'','1','SP','teste','14409012'),(80,'teste','teste\'','1','SP','teste','14409012'),(81,'teste','teste\'','1','SP','teste','14409012'),(83,'city contact','district contact','11','SP','street Address','13604304'),(84,'city contact','district contact','11','SP','street Address','13604304'),(85,'city contact','district contact','11','SP','street Address','13604304'),(86,'city contact','district contact','11','SP','street Address','13604304'),(87,'city contact','district contact','11','SP','street Address','13604304'),(88,'city contact','district contact','11','SP','street Address','13604304'),(89,'city contact','district contact','11','SP','street Address','13604304'),(90,'city contact','district contact','11','SP','street Address','13604304'),(91,'city contact','district contact','11','SP','street Address','13604304'),(92,'city contact','district contact','11','SP','street Address','13604304'),(93,'city contact','district contact','11','SP','street Address','13604304'),(94,'city contact','district contact','11','SP','street Address','13604304'),(95,'city contact','district contact','11','SP','street Address','13604304'),(96,'city contact','district contact','11','SP','street Address','13604304'),(97,'city contact','district contact','11','SP','street Address','13604304'),(98,'city contact','district contact','11','SP','street Address','13604304'),(99,'city contact','district contact','11','SP','street Address','13604304'),(100,'city contact','district contact','11','SP','street Address','13604304'),(101,'city contact','district contact','11','SP','street Address','13604304'),(102,'city contact','district contact','11','SP','street Address','13604304'),(103,'city contact','district contact','11','SP','street Address','13604304'),(104,'city contact','district contact','11','SP','street Address','13604304'),(105,'city contact','district contact','11','SP','street Address','13604304'),(106,'city contact','district contact','11','SP','street Address','13604304'),(107,'cidade','bairro\'','1','SP','endereco','14409012'),(108,'cidade','bairro\'','1','SP','endereco','14409012'),(109,'cidade','barro\'','1','SP','endereco','14409012'),(110,'city contact','district contact','11','SP','street Address','13604304'),(111,'cidade','bairro\'','145','SP','rua asdfadsf','13604304'),(112,'cidade','bairro\'','1','SP','endereco','14409012'),(113,'asdf','asdf\'','21','SP','asdfad','13604340'),(114,'asdfa','asdfa\'','182','SP','rua','13640340'),(115,'asdf','asdf\'','1','SP','asdf','13604304'),(116,'asdf','asdf\'','1','SP','asdf','13604340'),(117,'1','asdf\'','1','SP','asdf','13604304'),(118,'asdf','asdf\'','1','SP','asdf','13604304'),(119,'cidade','bairro\'','1','SP','endereco','14409012'),(120,'TESTE','BARIRRO\'','14','sp','TESTE','13604304'),(121,'CIDADE','BAIRRI\'','1','SP','ENDERECO','14409012'),(122,'cidade','teste\'','45','sp','teste','13604304'),(123,'araras','jd alvorada\'','183','SP','rua ','13604304'),(124,'araras','jd alvorada\'','183','SP','rua ','13604304'),(125,'araras','jd alvorada\'','183','SP','rua ','13604304'),(126,'araras','jd alvorada\'','183','SP','rua ','13604304'),(127,'21asdf','asdfas\'','32','SP','asdf','13604304'),(128,'cidade','baitto\'','1','SP','endereco','14409012'),(129,'cidade','bairro\'','14','SP','Rua teste','13604307'),(130,'cidade','bairro\'','147','SP','rua','13604304'),(131,'cidade','bairro\'','145','sp','rua','13604304'),(132,'cidade','bairtto\'','185','SP','Rua sua','13604301'),(133,'Araras','bairtto\'','47','SP','Rua','13604304'),(134,'cidade','bairro\'','183','sp','Rua','13604304'),(135,'cidade','bairro\'','1','SP','endereco','14409012'),(136,'cidade','bairro\'','183','sp','rua','13604301'),(137,'araras','bairro\'','183','sp','rua','13604301'),(138,'cidade','bairro\'','1','SP','endereco','14409012'),(139,'teste','teste \'','1','SP','teste','14409012'),(141,'cidade','bairro','1','SP','endereco','14409012'),(142,'Araras','bairro\'','183','sp','teste','13604304'),(143,'cidade','bairro\'','1','SP','endereco','14409012'),(144,'cidade','bairro\'','111','sp','Rua','13604304'),(145,'cidade','bairro\'','111','sp','Rua','13604304'),(146,'asdf','asdf\'','185','sp','rua','13604304'),(147,'asdf','asdf\'','185','sp','rua','13604304'),(148,'asdf','asdf\'','185','sp','rua','13604304'),(149,'cidade','baiiro\'','123','SP','rua','13604304'),(150,'cidade','bairro\'','1','SP','endereco','14409012'),(151,'cidade','bairro\'','183','SP','rua ','13604304'),(152,'cidade','bairrto\'','183','SP','rua','13604304'),(153,'Manaus','Periferia\'','25','AM','Teste','78895874'),(154,'Cidade 1','Bairro 1\'','1','SP','Rua 1','14409012'),(155,'Manaus','Bairro\'','25','AM','Rua 5','78895874'),(156,'cidade','bairro\'','999','SP','Rua teste','13604304'),(158,'string','string','string','SP','string','13640304'),(159,'teste','teste','1','SP','teste','13604304'),(160,'cidade','bairro','1','SP','endereco','13604304'),(161,'cidade','bairro','1','SP','rua endereco','13604304'),(162,'cidade','bairro','1','SP','rua endereco','13604304'),(163,'cidade','bairro','1','SP','endereco ','13604304'),(164,'cidade','bairro','1','SP','endereco ','13604304'),(165,'cidade','bairro','1','SP','endereco','13604304'),(166,'araras','bairro\'','123','SP','rua aaaa','13604304'),(167,'cidade','bairro\'','1','sp','endereco','13604304'),(168,'cidade','bairro\'','1','sp','endereco','13604304'),(169,'cidade','bairro\'','1','sp','endereco','13604304'),(170,'cidade','bairro\'','1','sp','endereco','13604304'),(171,'cidade3','bairro','1','SP','endereco','13604304'),(172,'cidade','bairro','1','sp','endereco','13604304'),(173,'asdfasd','asdfasd\'','18','sp','asdfasd','13604304'),(174,'Araras','bairro','1','SP','endereco','13604304'),(175,'cidade','bairro\'','183','SP','Rua do diácono','13604304'),(176,'Teste','bairro\'','183','SP','rua rua rua ','13604304'),(177,'Araras','bairro\'','183','SP','Rua rua','13604304'),(178,'Araras','bairro\'','1','SP','endereco','13640304'),(179,'Araras','bairro','1','SP','endereco','13604304'),(180,'cidade','bairro\'','183','SP','Rua rua','13604304'),(181,'cidade','bairro\'','183','SP','rua rua ','13604304'),(182,'cidade','bairro\'','1','SP','endereco','13604304'),(183,'cidade','bairro\'','183','SP','rua rua ','13604304');
/*!40000 ALTER TABLE `Address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Contact`
--

DROP TABLE IF EXISTS `Contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Contact` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `celNumber` varchar(255) DEFAULT NULL,
  `ministery` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `observation` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `relationship` varchar(255) DEFAULT NULL,
  `address_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_510n2cfle7gkafydaj6na02ur` (`address_id`),
  CONSTRAINT `FKj0ag8jhilwi89v3crnwqu5i3w` FOREIGN KEY (`address_id`) REFERENCES `Address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Contact`
--

LOCK TABLES `Contact` WRITE;
/*!40000 ALTER TABLE `Contact` DISABLE KEYS */;
INSERT INTO `Contact` VALUES (1,'16988745224','Ancião','José Contato','Observação','1688554789','Irmão',6),(2,'19988745224','Diácono','João Contato','Observação 2','1988554789','Amigo',7),(16,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',24),(17,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',25),(18,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',26),(19,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',27),(20,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',28),(21,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',29),(22,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',30),(23,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',31),(24,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',32),(25,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',33),(26,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',34),(27,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',35),(28,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',36),(29,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',37),(30,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',38),(31,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',39),(32,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',40),(33,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',41),(34,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',42),(35,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',43),(36,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',44),(37,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',45),(38,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',46),(39,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',47),(40,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',49),(41,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',50),(42,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',51),(44,'19994120827','Diacono','Delei Mococa','observation contact','1999412082','Brother',53),(46,'19994120827','Super Saiyajin','Goku','observation contact','1999412082','relationship contact',55),(47,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',57),(48,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',58),(49,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',59),(50,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',60),(51,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',61),(52,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',67),(53,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',69),(54,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',70),(55,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',71),(56,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',74),(57,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',83),(58,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',84),(59,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',85),(60,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',86),(61,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',87),(62,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',88),(63,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',89),(64,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',90),(65,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',91),(66,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',92),(67,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',93),(68,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',94),(69,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',95),(70,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',96),(71,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',97),(72,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',98),(73,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',99),(74,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',100),(75,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',101),(76,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',102),(77,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',103),(78,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',104),(79,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',105),(80,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',106),(81,'19994120827','ministery contact','contact name','observation contact','1999412082','relationship contact',110),(82,'16998877445','ministério','Teste','teste','16998877445','relacionamento',111),(83,'1999999999','asdfasdf','ddddddddddddd','teste','19999999999','asdfas',113),(84,'19999999999','ministerio','asdfasdfasdf','teste','19999999999','relacionamento',114),(85,'19999999999','asdfa','asdfasdfadsf','teste','19999999999','asdf',115),(86,'19999999999','asdfa','asdfasdf','teste','19999999999','asdf',116),(87,'1999999999','asdf','nome','teste','1999999999','asdf',117),(88,'1999999999','asdf','asdfasd','teste','19999999999','asdf',118),(89,'19999999999','TESTE','TESTE','teste','1999999999','TESTE',120),(90,'1999999999','ministerio','teste','teste','19999999999','teste',122),(91,'1999999999','ministerio','nome','teste','1999999999','relacionmente',123),(92,'1999999999','ministerio','nome','teste','1999999999','relacionmente',124),(93,'1999999999','ministerio','nome','teste','1999999999','relacionmente',125),(94,'1999999999','ministerio','nome','teste','1999999999','relacionmente',126),(95,'19999999999','asdfasd','nome','teste','19999999999','asdfa',127),(96,'1999999999','Diacono','teste','teste','199999999','NA',129),(97,'19999999999','diacono','contato','teste','19999999999','teste',130),(98,'88888888','Diacono','Contato Diferente teste','teste','8888888888','Relacionamento',131),(99,'19999999999','Diacono','Contato nome teste 1 2901','teste','19999999999','relacionamento',132),(100,'19994120827','Diacono','nome','teste','19994120827','relacionamento',133),(101,'19999999999','Diacono','nome','teste','19999999999','relacionamento',134),(102,'1999999999','ministerio','nome','teste','199999999','relacionamento',136),(103,'19999999999','Diacono','teste','teste','19999999999','relacionamento',137),(104,'19994120827','Diacono','contato Moises','teste','19994120827','relacionamento',142),(105,'19999999999','Diacono','teste contato 1','teste','19999999999','relacionamento',144),(106,'19999999999','Diacono','teste contato 1','teste','19999999999','relacionamento',145),(107,'199999999','diacono','Davi contato','teste','1999999999','relac',146),(108,'199999999','diacono','Davi contato','teste','1999999999','relac',147),(109,'199999999','diacono','Davi contato','teste','1999999999','relac',148),(110,'1999999999','Diacono','Irmao contat4','teste','199999999','relacionamento',149),(111,'1999999999','ministerio','nome contato','teste','199999999','relacionamento',151),(112,'1999999999','diacono','Contato','teste','1999999999','relacionamento',152),(113,'16988745224','Ancião','José Contato','teste','1688554789','Irmão',153),(114,'16988745224','Ancião','José Contato','teste','1688554789','Irmão',155),(115,'19999999999','Diacono','Contato','teste','19999999999','lakjsldkfjasdf',156),(116,'18888888888','ministerio','Contato cadastro','teste','19999999999','relacionamento',166),(117,'19999999999','asdfasdf','ddddddd','teste','19999999999','asdf',173),(118,'19999999999','Diacono','Diacono indicou','teste','19999999999','amigo',175),(119,'19999999999','Diacono','Contato','teste','19999999999','teste',176),(120,'19999999999','Diacono','Diacono teste','teste','19999999999','asdfasdf',177),(121,'19999999999','Diacono','Contato ','teste','19999999999','relacionamento',180),(122,'19999999999','diacono','Nome teste','teste','1999999999','relacionamento',181),(123,'19999999999','diacono','nome nome','teste','19999999999','kkkkkkkkkkkkk',183);
/*!40000 ALTER TABLE `Contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Guest`
--

DROP TABLE IF EXISTS `Guest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Guest` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `baptized` bit(1) DEFAULT NULL,
  `celNumber` varchar(255) DEFAULT NULL,
  `cpf` varchar(255) DEFAULT NULL,
  `dateOfBaptism` date DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `ministery` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `observation` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `prayingHouse` varchar(255) DEFAULT NULL,
  `rg` varchar(255) DEFAULT NULL,
  `address_id` bigint NOT NULL,
  `type_id` bigint NOT NULL,
  `gender` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6ob6uk0mpirnuspmrvpgxrs84` (`address_id`),
  KEY `FKyc35c80s51sagtptdit8hk4w` (`type_id`),
  CONSTRAINT `FK9dsr92l06ocy5kgap5g46w3nw` FOREIGN KEY (`address_id`) REFERENCES `Address` (`id`),
  CONSTRAINT `FKyc35c80s51sagtptdit8hk4w` FOREIGN KEY (`type_id`) REFERENCES `GuestType` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Guest`
--

LOCK TABLES `Guest` WRITE;
/*!40000 ALTER TABLE `Guest` DISABLE KEYS */;
INSERT INTO `Guest` VALUES (2,_binary '\0','16998877445','82056528053','2020-02-02','1990-02-02',_binary '','Geraldina Manuela','Alimentação sem restrições','16998877445','Jardim América','224567898',4,2,NULL),(3,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','Davi Jason','																\r\n																observação																\r\n																','16998877445','prayingHouse','RG',9,1,NULL),(4,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','Nilton Lima','\r\n																																	\r\n																																\r\n																observação																\r\n																																\r\n																																\r\n																','16998877445','prayingHouse','999999999',21,1,NULL),(5,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','Davi da Silva','																\r\n																observação																\r\n																','16998877445','prayingHouse','RG',23,1,NULL),(6,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','Mario da Silva','\r\n																																	\r\n																																\r\n																																\r\n																																\r\n																observação																\r\n																																\r\n																																\r\n				','16998877445','prayingHouse','999999999',48,1,NULL),(7,_binary '','16998877445','28368382807','2000-12-01','2000-12-01',_binary '\0','Hospede teste','\r\n																	\r\n																	observação																\r\n																																\r\n																','16998877445','prayingHouse','999999999',56,1,NULL),(9,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','Julia Da Silva','\r\n																	\r\n																	observação																\r\n																																\r\n																','16998877445','prayingHouse','999999999',63,1,NULL),(10,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','Roberto de Souza','\r\n																	 observação 																\r\n																','16998877445','prayingHouse','999999999',64,1,NULL),(11,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','João da Silva','\r\n																	 observação 																\r\n																','16998877445','prayingHouse','999999999',65,1,NULL),(12,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','Alberto de Souza','\r\n																	\r\n																	observação																\r\n																																\r\n																','16998877445','prayingHouse','999999999',66,1,NULL),(13,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','Hospede Teste','\r\n																	\r\n																	observação																\r\n																																\r\n																','16998877445','prayingHouse','999999999',68,1,NULL),(14,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','Claudio Miranda','\r\n																	\r\n																	observação																\r\n																																\r\n																','16998877445','prayingHouse','999999999',72,1,NULL),(15,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','Jair Jose',' observação ','16998877445','prayingHouse','RG',73,2,NULL),(16,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','teste',' observação ','16998877445','prayingHouse','RG',75,2,NULL),(17,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','teste 3','\r\n																	observação																\r\n																','16998877445','prayingHouse','RG',76,1,NULL),(18,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','teste 40000','\r\n																	a																\r\n																','16998877445','prayingHouse','RG',77,1,NULL),(19,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','teste 4','a','16998877445','prayingHouse','RG',78,2,NULL),(20,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','teste 5','linha','16998877445','prayingHouse','RG',79,2,NULL),(21,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','teste 5','linha','16998877445','prayingHouse','RG',80,2,NULL),(22,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','teste 5','linha','16998877445','prayingHouse','RG',81,2,NULL),(24,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','Murilo da Silva','\r\n																																	\r\n																','16998877445','prayingHouse','999999999',107,1,NULL),(25,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','Eduardo Gama','\r\n																	  observação   																\r\n																','16998877445','prayingHouse','555555555',108,1,NULL),(26,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','Acompanhante 78','asdfasd  observação  dddd  ','16998877445','prayingHouse','RG',109,2,NULL),(27,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','teste 0512','  observação   ','16998877445','prayingHouse','RG',112,2,NULL),(28,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','teste','  observação   ','16998877445','prayingHouse','RG',119,2,NULL),(29,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','ACOMPANHANTE','  observação   ','16998877445','prayingHouse','RG',121,2,NULL),(30,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','Acompanhante reserva 93','  observação   ','16998877445','prayingHouse','RG',128,2,NULL),(31,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','Acompanhante','  observação asdfasdf asdf asdf ','16998877445','prayingHouse','RG',135,2,NULL),(32,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','teste Acompanhante','  observação   ','16998877445','prayingHouse','RG',138,2,NULL),(33,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','ultimo acompanhante','  observação   ','16998877445','prayingHouse','RG',139,2,NULL),(35,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','Moises do Silva','\r\n																	\r\n																	observação																\r\n																																\r\n																','16998877445','prayingHouse','999999999',141,1,NULL),(36,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','acompanhante Moises','  observação asdf asdf    ','16998877445','prayingHouse','RG',143,2,NULL),(37,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '\0','acompanhante AA','\r\n																	  observação   																\r\n																','16998877445','prayingHouse','999999999',150,1,NULL),(38,_binary '','16998877445','82056528053','2000-12-01','2000-12-01',_binary '','Elbio Otávio Caetano','  observação   ','16998877445','prayingHouse','RG',154,2,NULL),(40,_binary '','string','928.251.700-46','2022-04-06','2022-04-06',_binary '','string','string','string','string','999999999',158,1,NULL),(41,_binary '','16998877445','759.229.380-37','2000-10-10','1982-05-10',_binary '\0','teste 2','\r\n																	\r\n																	\r\n																	asdfasdf																\r\n																																\r\n																																\r\n																','16998877445','asdfasd','345503077',159,1,NULL),(42,_binary '','1999999999','75922938037','2005-05-10','1982-05-10',_binary '\0','teste data','\r\n																																	\r\n			asdfasd													','19999999999','asdfasdf','999999999',160,1,NULL),(43,_binary '','99999999999','75922938037','2005-10-05','1982-05-10',_binary '\0','teste 0604','teste\r\nteste\r\nteste\r\n																																	\r\n																','99999999999','comum congregacao','999999999',161,1,NULL),(44,_binary '\0','99999999999','75922938037','2005-10-05','1982-05-10',_binary '\0','teste 0604','teste\r\nteste\r\nteste\r\n																																	\r\n																','99999999999','comum congregacao','999999999',162,1,NULL),(45,_binary '','16998877445','05280089001','2022-04-06','1980-04-10',_binary '\0','nome teste batizado alterado','\r\n																	observacao\r\nteste\r\nteste\r\n\r\n																																	\r\n																																\r\n																','16998877445','comum congregacao','999999999',163,1,NULL),(46,_binary '\0','19999999999','05280089001','2022-04-06','1980-04-10',_binary '\0','nome teste batizado 2','observacao\r\nteste\r\nteste\r\n\r\n																																	\r\n																','19999999999','comum congregacao','999999999',164,1,NULL),(47,_binary '','18888888888','63264109832','2001-01-05','1981-05-10',_binary '\0','nome teste incluir/alterado 3','\r\n																																		\r\nobservacao 2\r\n																																	\r\n																																\r\n																																\r\n																																\r\n																																												\r\n																				','19999999999','comum congregacao 333','999999999',165,1,NULL),(48,_binary '\0','19999999999','52705526200','2020-02-01','2000-04-07',_binary '','Acompanhante','acompahante observacao ','19999999999','comum congregacao teste','999999999',167,2,NULL),(49,_binary '\0','19999999999','52705526200','2020-02-01','2000-04-07',_binary '','Acompanhante 2','acompahante observacao ','19999999999','comum congregacao teste','999999999',168,2,NULL),(50,_binary '\0','19999999999','52705526200','2020-02-01','2000-04-07',_binary '','Acompanhante 2','acompahante observacao    ','19999999999','comum congregacao teste','999999999',169,2,NULL),(51,_binary '','19999999999','52705526200','2020-02-01','2000-04-07',_binary '','Acompanhante 3','acompahante observacao    ','19999999999','comum congregacao teste','999999999',170,2,NULL),(52,_binary '','1999999999','05647266304','2020-04-10','2000-04-07',_binary '\0','hospede 3','\r\n																	\r\n																	observacao\r\n																																	\r\n																																\r\n																																\r\n																','19999999999','Comum Congregacao','999999999',171,1,NULL),(53,_binary '','1999999999','05647266304','2020-04-05','2000-04-07',_binary '\0','Hospede 4','observacao\r\n																																	\r\n																','19999999999','comum congregacao','999999999',172,1,NULL),(54,_binary '','19999999999','66073638221','2005-10-10','1985-10-10',_binary '\0','novo hospede 1508  dasdf','\r\n																	asdfasdf\r\n																																	\r\n																																\r\n																','19999999999','RPF','999999999',174,1,NULL),(55,_binary '','19999999999','64480718818','2020-10-10','1990-09-10',_binary '','Acompanhante Geraldina','asdfasdfasdfasdf    ','19999999999','Central','999999999',178,2,NULL),(56,_binary '','19999999999','64480718818','1995-10-10','1989-10-10',_binary '\0','Novo Hospede Barretos 1508','\r\nAlimentação sem restrição																											\r\n																','19999999999','Central','333333333',179,1,NULL),(57,_binary '','1999999999','15251170688','2000-10-10','1990-10-10',_binary '','Nome Acompanhante Hospede barretos','observacao    ','19999999999','Central','999999999',182,2,NULL);
/*!40000 ALTER TABLE `Guest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GuestType`
--

DROP TABLE IF EXISTS `GuestType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GuestType` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GuestType`
--

LOCK TABLES `GuestType` WRITE;
/*!40000 ALTER TABLE `GuestType` DISABLE KEYS */;
INSERT INTO `GuestType` VALUES (1,'Hóspede'),(2,'Acompanhante');
/*!40000 ALTER TABLE `GuestType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Reservation`
--

DROP TABLE IF EXISTS `Reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Reservation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `checkinDate` date DEFAULT NULL,
  `checkoutDate` date DEFAULT NULL,
  `finalDate` date DEFAULT NULL,
  `initialDate` date DEFAULT NULL,
  `observation` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `contact_id` bigint NOT NULL,
  `room_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5xgbh4nvscg4n4scng5fxr4gx` (`contact_id`),
  KEY `FKprp9xfstf1buic0e0mg1tndfu` (`room_id`),
  CONSTRAINT `FKprp9xfstf1buic0e0mg1tndfu` FOREIGN KEY (`room_id`) REFERENCES `Room` (`id`),
  CONSTRAINT `FKqmnw4fdgia8xvdpqny80t288j` FOREIGN KEY (`contact_id`) REFERENCES `Contact` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reservation`
--

LOCK TABLES `Reservation` WRITE;
/*!40000 ALTER TABLE `Reservation` DISABLE KEYS */;
INSERT INTO `Reservation` VALUES (1,'2022-03-08','2022-04-06','2021-02-20','2021-02-01','Observação','FINISHED',1,1),(2,'2021-09-25','2021-10-03','2021-02-20','2021-02-01','Observação','FINISHED',2,1),(16,'2021-09-25','2021-10-03','2021-09-30','2021-09-25','string','FINISHED',16,4),(17,'2021-09-25','2021-10-03','2021-09-30','2021-09-25','string','FINISHED',17,4),(18,NULL,'2021-10-03','2021-10-08','2021-09-25','string','FINISHED',18,2),(19,NULL,'2021-10-03','2021-09-30','2021-09-25','string','FINISHED',19,3),(20,NULL,'2021-10-03','2021-09-29','2021-09-26','string','FINISHED',20,3),(21,NULL,'2021-10-03','2021-09-30','2021-09-29','string','FINISHED',21,5),(22,NULL,'2021-10-03','2021-10-04','2021-10-02','string','FINISHED',22,1),(23,NULL,'2021-10-03','2021-10-04','2021-10-02','string','FINISHED',23,1),(24,NULL,'2021-10-03','2021-10-08','2021-10-05','string','FINISHED',24,1),(25,NULL,'2021-10-03','2021-10-08','2021-10-05','string','FINISHED',25,1),(26,'2021-10-05','2021-10-03','2021-10-08','2021-10-05','string','FINISHED',26,1),(27,'2021-10-05','2021-10-03','2021-10-08','2021-10-05','string','FINISHED',27,1),(28,'2021-10-09','2021-10-03','2021-10-11','2021-10-09','string','FINISHED',28,5),(29,'2021-10-09','2021-10-03','2021-10-11','2021-10-09','string','FINISHED',29,5),(30,NULL,'2021-10-03','2021-10-13','2021-10-11','string','FINISHED',30,5),(31,NULL,'2021-10-03','2021-10-17','2021-10-16','string','FINISHED',31,6),(32,NULL,'2021-10-03','2021-10-13','2021-10-11','string','FINISHED',32,5),(33,NULL,'2021-10-03','2021-10-13','2021-10-11','string','FINISHED',33,5),(34,NULL,'2021-10-03','2021-10-13','2021-10-11','string','FINISHED',34,5),(35,NULL,'2021-10-03','2021-10-13','2021-10-11','string','FINISHED',35,5),(36,NULL,'2021-10-03','2021-10-17','2021-10-16','string','FINISHED',36,6),(37,NULL,'2021-10-03','2021-11-03','2021-10-31','string','FINISHED',37,6),(38,'2021-11-25','2021-10-30','2021-11-26','2021-11-25','string','FINISHED',38,6),(39,NULL,'2021-10-05','2021-10-05','2021-10-04','string','FINISHED',39,1),(40,'2021-10-11','2021-10-13','2021-10-12','2021-10-10','string','FINISHED',40,8),(41,'2021-10-10','2021-10-12','2021-10-13','2021-10-10','string','FINISHED',41,7),(42,'2021-10-16','2021-10-17','2021-10-18','2021-10-16','string','FINISHED',42,1),(43,'2021-11-07','2021-11-08','2021-10-18','2021-10-16','string','FINISHED',44,1),(44,'2021-11-06','2021-11-07','2021-10-18','2021-10-16','string','FINISHED',46,2),(45,'2021-11-06','2021-11-06','2021-11-07','2021-11-06','string','FINISHED',47,1),(46,'2021-11-06','2021-11-06','2021-11-07','2021-11-06','string','FINISHED',48,1),(47,'2021-11-06','2021-11-13','2021-11-07','2021-11-06','string','FINISHED',49,1),(48,'2021-11-06','2021-11-15','2021-11-08','2021-11-06','string','FINISHED',50,2),(49,NULL,'2021-11-14','2021-11-14','2021-11-13','string','FINISHED',51,3),(50,NULL,'2021-11-17','2021-11-17','2021-11-15','string','FINISHED',52,8),(51,NULL,'2021-11-28','2021-11-17','2021-11-15','string','FINISHED',53,7),(52,'2021-11-15','2021-11-15','2021-11-17','2021-11-15','string','FINISHED',54,7),(53,'2021-11-17','2021-11-17','2021-11-22','2021-11-20','string','FINISHED',55,1),(54,'2021-11-17','2021-11-28','2021-11-19','2021-11-17','string','FINISHED',56,1),(55,NULL,'2021-11-28','2021-11-30','2021-11-28','string','FINISHED',57,2),(56,NULL,'2021-11-28','2021-11-30','2021-11-28','string','FINISHED',58,1),(57,NULL,'2021-11-28','2021-11-30','2021-11-28','string','FINISHED',59,8),(58,NULL,'2021-11-28','2021-11-29','2021-11-27','string','FINISHED',60,1),(59,NULL,'2021-11-28','2021-12-03','2021-12-01','string','FINISHED',61,1),(60,NULL,'2021-11-28','2021-11-30','2021-11-27','string','FINISHED',62,1),(61,NULL,'2021-11-28','2021-11-30','2021-11-28','string','FINISHED',63,1),(62,NULL,'2021-11-28','2021-11-30','2021-11-28','string','FINISHED',64,1),(63,NULL,'2021-11-28','2021-12-03','2021-11-30','string','FINISHED',65,2),(64,NULL,'2021-11-28','2021-11-30','2021-11-28','string','FINISHED',66,3),(65,NULL,'2021-11-28','2021-11-30','2021-11-29','string','FINISHED',67,1),(66,NULL,'2021-11-28','2021-12-02','2021-11-28','string','FINISHED',68,2),(67,NULL,'2021-11-28','2021-11-30','2021-11-28','string','FINISHED',69,1),(68,NULL,'2021-11-28','2021-11-30','2021-11-28','string','FINISHED',70,1),(69,NULL,'2021-11-29','2021-11-30','2021-11-28','string','FINISHED',71,1),(70,NULL,'2021-11-29','2021-11-30','2021-11-28','string','FINISHED',72,1),(71,NULL,'2021-11-29','2021-12-04','2021-11-30','string','FINISHED',73,2),(72,NULL,'2021-11-29','2021-12-03','2021-11-29','string','FINISHED',74,1),(73,NULL,'2021-11-29','2021-12-02','2021-11-29','string','FINISHED',75,3),(74,NULL,'2021-11-29','2021-12-03','2021-11-29','string','FINISHED',76,6),(75,NULL,'2021-11-29','2021-12-01','2021-11-27','string','FINISHED',77,7),(76,'2021-12-06','2021-12-08','2021-12-10','2021-12-06','string','FINISHED',78,4),(77,NULL,'2021-11-29','2021-12-14','2021-12-10','string','FINISHED',79,8),(78,NULL,'2021-11-29','2021-12-15','2021-12-13','string','FINISHED',80,6),(79,'2021-12-05','2021-12-06','2021-12-02','2021-11-30','string','FINISHED',81,1),(80,'2021-12-05','2021-12-06','2021-12-08','2021-12-05','string','FINISHED',82,2),(81,'2021-12-05','2021-12-05','2021-12-16','2021-12-10','string','FINISHED',83,3),(82,'2021-12-05','2021-12-05','2021-12-11','2021-12-10','string','FINISHED',84,4),(83,'2021-12-05','2021-12-05','2021-12-07','2021-12-05','string','FINISHED',85,1),(84,NULL,'2021-12-05','2021-12-08','2021-12-06','string','FINISHED',86,2),(85,'2021-12-07','2021-12-20','2021-12-08','2021-12-05','string','FINISHED',87,8),(86,NULL,'2021-12-20','2021-12-08','2021-12-05','string','FINISHED',88,7),(87,NULL,'2021-12-20','2021-12-14','2021-12-12','string','FINISHED',89,1),(88,NULL,'2021-12-20','2021-12-16','2021-12-13','string','FINISHED',90,2),(89,NULL,'2021-12-20','2021-12-15','2021-12-13','string','FINISHED',91,3),(90,NULL,'2021-12-13','2021-12-15','2021-12-13','string','FINISHED',92,3),(91,NULL,'2021-12-13','2021-12-15','2021-12-13','string','FINISHED',93,3),(92,NULL,'2021-12-13','2021-12-15','2021-12-13','string','FINISHED',94,3),(93,NULL,'2021-12-20','2021-12-20','2021-12-13','string','FINISHED',95,4),(94,'2022-01-23','2022-01-29','2022-01-25','2022-01-23','string','FINISHED',96,1),(95,'2022-01-31','2022-01-29','2022-01-28','2022-01-23','string','FINISHED',97,2),(96,'2022-01-29','2022-01-30','2022-01-29','2022-01-23','string','FINISHED',98,3),(97,'2022-02-19','2022-02-22','2022-02-01','2022-01-29','string','FINISHED',99,2),(98,'2022-02-22','2022-02-22','2022-02-26','2022-02-22','string','FINISHED',100,1),(99,NULL,'2022-02-24','2022-02-26','2022-02-23','string','FINISHED',101,7),(100,'2022-02-23','2022-02-26','2022-02-26','2022-02-23','string','FINISHED',102,6),(101,NULL,'2022-02-27','2022-02-28','2022-02-24','string','FINISHED',103,6),(102,NULL,'2022-02-27','2022-03-03','2022-02-27','string','FINISHED',104,1),(103,NULL,'2022-03-05','2022-02-27','2022-02-27','string','FINISHED',105,1),(104,NULL,'2022-03-05','2022-02-27','2022-02-27','string','FINISHED',106,1),(105,NULL,'2022-02-27','2022-02-27','2022-02-27','string','FINISHED',107,2),(106,NULL,'2022-02-27','2022-02-27','2022-02-27','string','FINISHED',108,2),(107,NULL,'2022-02-27','2022-02-27','2022-02-27','string','FINISHED',109,2),(108,'2022-03-05','2022-04-06','2022-03-05','2022-03-05','string','FINISHED',110,1),(109,NULL,'2022-03-05','2022-03-12','2022-03-05','string','FINISHED',111,2),(110,NULL,'2022-04-06','2022-03-09','2022-03-05','string','FINISHED',112,3),(111,NULL,'2022-04-06','2022-03-07','2022-03-07','string','FINISHED',113,1),(112,NULL,'2022-04-06','2022-03-07','2022-03-07','string','FINISHED',114,1),(113,NULL,'2022-08-15','2022-04-06','2022-04-06','string','FINISHED',115,1),(114,NULL,'2022-08-15','2022-04-11','2022-04-07','string','FINISHED',116,2),(115,NULL,'2022-08-15','2022-04-09','2022-04-07','string','FINISHED',117,3),(116,'2022-08-15','2022-08-15','2022-08-15','2022-08-15','string','FINISHED',118,10),(117,NULL,'2022-08-15','2022-08-15','2022-08-15','string','FINISHED',119,10),(118,'2022-08-15','2022-08-15','2022-08-15','2022-08-15','string','FINISHED',120,1),(119,'2022-08-16','2022-08-15','2022-08-28','2022-08-16','string','FINISHED',121,1),(120,NULL,NULL,'2022-08-21','2022-08-15','string','CONFIRMED',122,2),(121,'2022-08-15',NULL,'2022-08-15','2022-08-15','string','CONFIRMED',123,3);
/*!40000 ALTER TABLE `Reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `floor` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `numberOfBeds` int DEFAULT NULL,
  `support_house_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK41vfxf5cx67j9igekc3njw4o2` (`support_house_id`),
  CONSTRAINT `FK41vfxf5cx67j9igekc3njw4o2` FOREIGN KEY (`support_house_id`) REFERENCES `SupportHouse` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

LOCK TABLES `Room` WRITE;
/*!40000 ALTER TABLE `Room` DISABLE KEYS */;
INSERT INTO `Room` VALUES (1,'1','Quarto 01','1F',3,1),(2,'2','Quarto 02','2G',2,2),(3,'1','Quarto 03','2A',4,1),(4,'1','Quarto 04','4',2,1),(5,'1','Quarto 05','5',2,1),(6,'1','Quarto 06','6',2,1),(7,'1','Quarto 07','7',2,1),(8,'1','Quarto 08','8',2,1),(10,'1','Quarto 09 - leito x','09',2,1),(15,'1','QUARTO 110 - CAMA 1','1',1,1),(17,'1','QUARTO 120 - A','120 A',1,1),(18,'1','QUARTO 120 - CAMA 2','120 B',1,1);
/*!40000 ALTER TABLE `Room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SupportHouse`
--

DROP TABLE IF EXISTS `SupportHouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SupportHouse` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cnpj` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `address_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_paax8y2ckggcrgfr5e7efg8ae` (`address_id`),
  CONSTRAINT `FKonxnxs8463pa1qt392mpwtgx3` FOREIGN KEY (`address_id`) REFERENCES `Address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SupportHouse`
--

LOCK TABLES `SupportHouse` WRITE;
/*!40000 ALTER TABLE `SupportHouse` DISABLE KEYS */;
INSERT INTO `SupportHouse` VALUES (1,'04170023000103','Test',1),(2,'45488909000179','Test 1',5);
/*!40000 ALTER TABLE `SupportHouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservations_guests`
--

DROP TABLE IF EXISTS `reservations_guests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservations_guests` (
  `reservation_id` bigint NOT NULL,
  `guest_id` bigint NOT NULL,
  KEY `FK1ow8olxrms1lqt6brgdv3dvlw` (`guest_id`),
  KEY `FKf3pygh6bio6c4o39f7mu6tydi` (`reservation_id`),
  CONSTRAINT `FK1ow8olxrms1lqt6brgdv3dvlw` FOREIGN KEY (`guest_id`) REFERENCES `Guest` (`id`),
  CONSTRAINT `FKf3pygh6bio6c4o39f7mu6tydi` FOREIGN KEY (`reservation_id`) REFERENCES `Reservation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations_guests`
--

LOCK TABLES `reservations_guests` WRITE;
/*!40000 ALTER TABLE `reservations_guests` DISABLE KEYS */;
INSERT INTO `reservations_guests` VALUES (2,2),(16,5),(17,5),(18,4),(19,4),(20,4),(21,4),(22,2),(23,2),(24,5),(25,5),(26,2),(27,5),(28,5),(29,5),(30,5),(31,5),(32,5),(33,5),(34,4),(35,4),(36,2),(37,5),(38,4),(39,5),(40,6),(41,5),(42,3),(43,4),(44,4),(45,2),(46,3),(48,2),(49,6),(47,4),(47,10),(47,11),(50,12),(52,11),(53,2),(51,11),(51,15),(51,16),(54,3),(54,18),(54,19),(54,20),(54,21),(54,22),(55,6),(56,5),(57,12),(58,12),(59,4),(60,2),(61,2),(62,2),(63,3),(64,4),(65,2),(66,3),(67,2),(68,2),(69,3),(70,3),(71,3),(72,4),(73,6),(74,10),(75,11),(76,5),(77,6),(78,9),(78,24),(78,25),(78,26),(80,10),(79,2),(79,27),(81,11),(82,12),(83,2),(84,2),(86,11),(85,12),(85,28),(87,5),(87,29),(88,6),(89,9),(90,9),(91,9),(92,9),(93,12),(93,30),(94,2),(95,3),(96,11),(97,6),(98,12),(99,11),(99,31),(100,9),(101,5),(101,32),(101,33),(102,35),(102,36),(103,2),(104,2),(105,3),(106,3),(107,3),(108,12),(108,37),(109,11),(110,10),(111,2),(1,2),(1,38),(112,4),(114,5),(113,2),(113,50),(113,51),(115,6),(116,54),(117,54),(118,2),(118,55),(120,2),(119,56),(119,57),(121,12);
/*!40000 ALTER TABLE `reservations_guests` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-20 11:31:55
