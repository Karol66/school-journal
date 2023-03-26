-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: projekt
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `frekwencja`
--

DROP TABLE IF EXISTS `frekwencja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `frekwencja` (
  `id_frekwencja` int NOT NULL AUTO_INCREMENT,
  `frekwencja` varchar(100) DEFAULT NULL,
  `index_ucznia` int NOT NULL,
  PRIMARY KEY (`id_frekwencja`),
  KEY `index_ucznia_idx` (`index_ucznia`),
  CONSTRAINT `index` FOREIGN KEY (`index_ucznia`) REFERENCES `uczen` (`index_ucznia`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `frekwencja`
--

LOCK TABLES `frekwencja` WRITE;
/*!40000 ALTER TABLE `frekwencja` DISABLE KEYS */;
INSERT INTO `frekwencja` VALUES (1,'12',1),(2,'12',1),(3,'12',1),(4,'12',1),(5,'12',1),(6,'12',1),(7,'12',2),(8,'32',2),(9,'22',2),(10,'22',2),(11,'11',2),(12,'33',2),(13,'44',3),(14,'22',3),(15,'11',3),(16,'38',3),(17,'86',3),(18,'43',3),(19,'33',4),(20,'21',4),(21,'22',4),(22,'3',4),(23,'44',4),(24,'12',4),(25,'33',5),(26,'22',5),(27,'11',5),(28,'22',5),(29,'33',5),(30,'21',5),(31,'12',6),(32,'12',6),(33,'12',6),(34,'12',6),(35,'12',6),(36,'12',6),(37,'32',7),(38,'12',7);
/*!40000 ALTER TABLE `frekwencja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lekcje`
--

DROP TABLE IF EXISTS `lekcje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lekcje` (
  `id_lekcje` int NOT NULL AUTO_INCREMENT,
  `nazwa_lekcji` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_lekcje`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lekcje`
--

LOCK TABLES `lekcje` WRITE;
/*!40000 ALTER TABLE `lekcje` DISABLE KEYS */;
INSERT INTO `lekcje` VALUES (1,'matematyka'),(2,'jezyk_polski'),(3,'geografia'),(4,'informatyka'),(5,'wychowanie_fizyczne'),(6,'biologia');
/*!40000 ALTER TABLE `lekcje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nauczyciel`
--

DROP TABLE IF EXISTS `nauczyciel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nauczyciel` (
  `index_nauczyciela` int NOT NULL,
  `imie` varchar(45) DEFAULT NULL,
  `nazwisko` varchar(45) DEFAULT NULL,
  `plec` varchar(45) DEFAULT NULL,
  `data_urodzenia` date DEFAULT NULL,
  `prowadzony_przedmiot` varchar(45) DEFAULT NULL,
  `wyplata` double DEFAULT NULL,
  `zdjecie` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`index_nauczyciela`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nauczyciel`
--

LOCK TABLES `nauczyciel` WRITE;
/*!40000 ALTER TABLE `nauczyciel` DISABLE KEYS */;
INSERT INTO `nauczyciel` VALUES (1,'Liane','Kilbourne','Mezczyzna','1960-02-20','Geografia',2976,NULL),(2,'Kathleen','Klessmann','Kobieta','1971-12-20','Język Polski',2640,NULL),(3,'Timi','Bunning','Mezczyzna','1990-02-20','Informatyka',2932,NULL),(4,'Lenard','Narracott','Mezczyzna','1990-03-20','Geografia',2502,NULL),(5,'Kaitlynn','Orwell','Kobieta','1971-02-20','Wychowanie Fizyczne',2478,NULL),(6,'Xenia','Aven','Kobieta','1989-02-20','Wychowanie Fizyczne',2796,NULL),(7,'Ernaline','Currier','Mezczyzna','1971-11-20','Matematyka',2453,NULL),(8,'Garreth','Bittleson','Kobieta','1983-02-20','Biologia',2838,NULL),(9,'Alphonso','Kasbye','Kobieta','1971-02-20','Informatyka',2702,NULL),(10,'Tobe','Wormell','Kobieta','1990-02-20','Informatyka',2118,NULL),(11,'Mireille','Cerith','Mezczyzna','2000-02-20','Wychowanie Fizyczne',2615,NULL),(12,'Whitaker','Clough','Mezczyzna','2000-02-20','Matematyka',2460,NULL),(13,'Bary','Smieton','Mezczyzna','1998-02-20','Informatyka',2879,NULL),(14,'Dael','Ashlin','Kobieta','1971-02-20','Biologia',2664,NULL),(15,'Obadias','Muzzi','Kobieta','2000-02-20','Język Polski',2128,NULL),(16,'Danny','Goakes','Mezczyzna','1998-02-20','Wychowanie Fizyczne',2315,NULL),(17,'Chester','McCory','Mezczyzna','1998-02-20','Geografia',2672,NULL),(18,'Adah','Germon','Kobieta','1989-02-20','Wychowanie Fizyczne',2134,NULL),(19,'Maurita','Battany','Mezczyzna','1989-02-20','Matematyka',2323,NULL),(20,'Geno','Wardhough','Kobieta','1998-07-20','Wychowanie Fizyczne',2416,NULL),(21,'Simone','Suddards','Kobieta','1987-07-20','Biologia',2776,NULL),(22,'Gail','Lissaman','Mezczyzna','1987-07-20','Matematyka',2340,NULL),(23,'Cassius','MacNeil','Mezczyzna','1971-10-20','Informatyka',2818,NULL),(24,'Teodoro','Di Bartolomeo','Mezczyzna','1987-10-20','Wychowanie Fizyczne',2789,NULL),(25,'Milicent','Vinau','Kobieta','1987-10-20','Język Polski',2366,NULL),(26,'Jan','Nowak','Mężczyzna','1987-10-20','Matematyka',2366,NULL);
/*!40000 ALTER TABLE `nauczyciel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oceny`
--

DROP TABLE IF EXISTS `oceny`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oceny` (
  `id_oceny` int NOT NULL AUTO_INCREMENT,
  `ocena` varchar(100) DEFAULT NULL,
  `index_ucznia` int NOT NULL,
  `id_lekcje` int NOT NULL,
  PRIMARY KEY (`id_oceny`),
  KEY `index_ucznia_idx` (`index_ucznia`),
  KEY `id_lekcje_idx` (`id_lekcje`),
  CONSTRAINT `id_lekcje` FOREIGN KEY (`id_lekcje`) REFERENCES `lekcje` (`id_lekcje`),
  CONSTRAINT `index_ucznia` FOREIGN KEY (`index_ucznia`) REFERENCES `uczen` (`index_ucznia`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oceny`
--

LOCK TABLES `oceny` WRITE;
/*!40000 ALTER TABLE `oceny` DISABLE KEYS */;
INSERT INTO `oceny` VALUES (143,'2',1,2),(144,'3',1,2),(145,'4',1,2),(146,'5',1,2),(147,'6',1,2),(148,'1',1,2),(149,'2',1,1),(150,'3',1,1),(151,'4',1,1),(152,'5',1,1),(153,'6',1,1),(154,'1',1,1);
/*!40000 ALTER TABLE `oceny` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rejestracja`
--

DROP TABLE IF EXISTS `rejestracja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rejestracja` (
  `id_rejestracja` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) DEFAULT NULL,
  `login` varchar(45) NOT NULL,
  `haslo` varchar(45) NOT NULL,
  PRIMARY KEY (`id_rejestracja`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rejestracja`
--

LOCK TABLES `rejestracja` WRITE;
/*!40000 ALTER TABLE `rejestracja` DISABLE KEYS */;
INSERT INTO `rejestracja` VALUES (20,'bb','bb','bb'),(21,'aa','aa','aa'),(22,'karol','karol','karol'),(23,'asd','asd','asd'),(24,'asd','asd',''),(25,'','',''),(26,'asda','asdd',''),(27,'aa','aa','aa'),(28,'aabbcc@asdfg','aa','aa'),(29,'aabbcc@asdfg','aa','aa'),(30,'aabbcc@asdfg','aa','aa'),(31,'aabbcc@asdfg','aa','aa'),(32,'aabbcc@asdfg','aa','aa'),(33,'aabbcc@asdfg','aa','aa'),(34,'aabbcc@asdfg','aa','aa'),(35,'aabbcc@asdfg','aa','aa'),(36,'aabbcc@asdfg','aa','aa'),(37,'aabbcc@asdfg','aa','aa'),(38,'aabbcc@asdfg','aa','aa'),(39,'adfsas@gdff','',''),(40,'','','Aa1asdfg'),(41,'asdfg@sadg1234','sdag','Asdf1234gh'),(42,'asfddghj@dfdsdgh','asgdhfj','dfdsgfhdgjA123'),(43,'asdfdsgfdhg@adfsgdh','dsafdgsfdghghk','dsfdgfhgklkA123'),(44,'asd@asd','123dsa','Asd123asd'),(46,'karol@hgfdas','karol','Karol12345'),(47,'karooool@asdfgh','karool','Karol54321'),(48,'karl@basbdyfb','karl123','Karol123456x'),(49,'costam@hds','cos','Cos12345');
/*!40000 ALTER TABLE `rejestracja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uczen`
--

DROP TABLE IF EXISTS `uczen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `uczen` (
  `index_ucznia` int NOT NULL,
  `rok` varchar(45) DEFAULT NULL,
  `profil` varchar(45) DEFAULT NULL,
  `imie` varchar(45) NOT NULL,
  `nazwisko` varchar(45) NOT NULL,
  `plec` varchar(45) DEFAULT NULL,
  `data_urodzenia` date DEFAULT NULL,
  `zdjecie` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`index_ucznia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uczen`
--

LOCK TABLES `uczen` WRITE;
/*!40000 ALTER TABLE `uczen` DISABLE KEYS */;
INSERT INTO `uczen` VALUES (1,'II','biol-chem','Cynthie','Houndsom','Mezczyzna','2005-02-20',NULL),(2,'III','geograficzny','Aaren','Amorine','Mezczyzna','2011-12-20',NULL),(3,'II','humanistyczny','Nissie','Hatter','Kobieta','2005-02-20',NULL),(4,'III','biol-chem','Ced','Whelpton','Kobieta','2005-03-20',NULL),(5,'III','mat-inf','Karol','Gallety','Mezczyzna','2005-02-20',NULL),(6,'I','mat-inf','Kerwinn','Gudgion','Kobieta','2005-02-20',NULL),(7,'II','biol-chem','Madelina','Talboy','Kobieta','2009-11-20',NULL),(8,'III','geograficzny','Germana','Pelz','Mezczyzna','2005-02-20',NULL),(9,'I','geograficzny','Michaelina','Helstrom','Kobieta','2005-02-20',NULL),(10,'II','humanistyczny','Shae','Lavalde','Kobieta','2005-02-09',NULL),(11,'III','biol-chem','Miof mela','Romeril','Kobieta','2005-02-20',NULL),(12,'IV','humanistyczny','Alasteir','Guile','Mezczyzna','2005-02-20',NULL),(13,'II','humanistyczny','Kellie','Benstead','Kobieta','2005-02-20',NULL),(14,'IV','biol-chem','Uri','Stimson','Kobieta','2005-02-20',NULL),(15,'I','geograficzny','Sigvard','Fairlem','Mezczyzna','2005-02-20',NULL),(16,'V','geograficzny','Calv','Rickardsson','Mezczyzna','2006-02-20',NULL),(17,'IV','geograficzny','Cly','Phateplace','Kobieta','2007-02-20',NULL),(18,'V','mat-inf','Waring','Scimoni','Kobieta','2005-02-20',NULL),(19,'V','humanistyczny','Tova','Loukes','Mezczyzna','2005-02-20',NULL),(20,'V','geograficzny','Chantalle','Gomes','Kobieta','2011-07-20',NULL),(21,'IV','geograficzny','Shaughn','Allso','Kobieta','2012-07-20',NULL),(22,'II','geograficzny','Colby','Plumtree','Mezczyzna','2013-07-20',NULL),(23,'I','humanistyczny','Hymie','Jerrams','Kobieta','2002-10-20',NULL),(24,'IV','mat-inf','Fabiano','Lucian','Mezczyzna','2003-10-20',NULL),(25,'II','geograficzny','Roanne','Letessier','Kobieta','2010-10-20',NULL),(26,'II','geograficzny','Josi','Hofler','Mezczyzna','2002-11-20',NULL),(27,'III','biol-chem','Persis','Niave','Mezczyzna','2011-01-20',NULL),(28,'IV','mat-inf','Miof mela','Purrington','Mezczyzna','2005-03-20',NULL),(29,'IV','geograficzny','Graehme','Donovan','Kobieta','2013-01-20',NULL),(30,'IV','geograficzny','Humfrid','Coon','Mezczyzna','2014-01-20',NULL),(31,'III','mat-inf','Jodi','Bexley','Kobieta','2005-03-20',NULL),(32,'I','biol-chem','Eva','Camelli','Mezczyzna','2016-01-20',NULL),(33,'III','biol-chem','Lind','Dallimare','Kobieta','2017-01-20',NULL),(34,'V','humanistyczny','Zak','Fillimore','Mezczyzna','2018-01-20',NULL),(35,'IV','humanistyczny','Alfreda','McGibbon','Kobieta','2019-01-20',NULL),(36,'V','geograficzny','Rochella','Kennaway','Mezczyzna','2004-02-20',NULL),(37,'V','mat-inf','Wittie','Blanden','Mezczyzna','2005-03-20',NULL),(38,'II','humanistyczny','Jacki','Mathan','Mezczyzna','2010-10-20',NULL),(39,'IV','humanistyczny','Carce','Aisthorpe','Mezczyzna','2007-09-20',NULL),(40,'I','geograficzny','Sonya','Gerbel','Mezczyzna','2008-09-20',NULL),(41,'III','biol-chem','Yelena','Brassington','Kobieta','2009-09-20',NULL),(42,'I','humanistyczny','Lawry','Bryceson','Mezczyzna','2010-09-20',NULL),(43,'V','mat-inf','Roderigo','Weerdenburg','Kobieta','2004-09-20',NULL),(44,'II','geograficzny','Carling','Withams','Mezczyzna','2005-09-20',NULL),(45,'III','mat-inf','Deedee','Every','Mezczyzna','2006-09-20',NULL),(46,'I','biol-chem','Carey','Dowall','Mezczyzna','2010-09-20',NULL),(47,'I','biol-chem','Durward','Maudlen','Mezczyzna','2011-09-20',NULL),(48,'II','biol-chem','Giffy','Boyne','Kobieta','2005-03-20',NULL),(49,'I','humanistyczny','Florry','Edson','Kobieta','2006-07-20',NULL),(50,'I','humanistyczny','Hershel','McGiff','Mezczyzna','2005-08-20',NULL),(51,'II','biol-chem','Maria','Nowak','Kobieta','2005-08-20',NULL);
/*!40000 ALTER TABLE `uczen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wydarzenia`
--

DROP TABLE IF EXISTS `wydarzenia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wydarzenia` (
  `id_wydarzenia` int NOT NULL AUTO_INCREMENT,
  `wydarzenie` varchar(45) DEFAULT NULL,
  `data` date DEFAULT NULL,
  PRIMARY KEY (`id_wydarzenia`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wydarzenia`
--

LOCK TABLES `wydarzenia` WRITE;
/*!40000 ALTER TABLE `wydarzenia` DISABLE KEYS */;
INSERT INTO `wydarzenia` VALUES (1,'przerwa wakacyjna','2023-01-12'),(4,'egzamin','2023-01-13'),(5,'ferie','2019-01-01'),(6,'sprawdzian biologia','2023-07-03'),(7,'sprawdzian matematyka','2023-01-17'),(8,'sprawdzian język polski','2023-01-17'),(9,'sprawdzian geografia','2023-01-17'),(10,'przerwa wielkanocna','2023-01-17'),(11,'kartkówka matematyka','2023-01-01'),(12,'kartkówka język polski','2022-12-31'),(13,'zadanie domowe matematyka','2023-01-01'),(14,'zadanie domowe geografia','2023-01-20'),(15,'zadanie domowe matematyka','2023-01-17'),(16,'Poczatek przerwy świątecznej','2022-07-14');
/*!40000 ALTER TABLE `wydarzenia` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-30 12:49:34
