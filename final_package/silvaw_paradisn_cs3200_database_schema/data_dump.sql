-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: music
-- ------------------------------------------------------
-- Server version	5.6.26-log

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
-- Dumping data for table `album`
--

LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` VALUES (1,'1989','2014-10-27','Taylor_Swift_-_1989.png'),(2,'Red','2012-10-22','Taylor_Swift_-_Red.png'),(3,'Let It Bleed','1969-12-05','Rolling_Stones_-_Let_It_Bleed.png'),(4,'News of the World','1977-10-28','Queen_-_News_Of_The_World.png'),(5,'The Works','1984-02-27','Queen_-_The_Works.png'),(6,'1000 Forms of Fear','2014-07-04','Sia_-_1000_Forms_of_Fear.png'),(7,'21','2011-01-19','Adele_-_21.png'),(8,'25','2015-11-20','Adele_-_25.png'),(9,'What a Time to Be Alive','2015-09-20','Drake_Future_-_What_a_Time_to_Be_Alive.png'),(10,'Nothing Was the Same','2013-09-23','Drake_-_Nothing_Was_the_Same.png'),(11,'Under the Mistletoe','2011-11-01','Justin_Bieber_-_Under_the_Mistletoe.png'),(12,'Adventure (Deluxe)','2015-03-27','Madeon_-_Adventure_(Deluxe).png'),(13,'AT.LONG.LAST.A$AP','2015-05-26','A$AP_Rocky_-_AT.LONG.LAST.A$AP.jpg');
/*!40000 ALTER TABLE `album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `artist`
--

LOCK TABLES `artist` WRITE;
/*!40000 ALTER TABLE `artist` DISABLE KEYS */;
INSERT INTO `artist` VALUES ('A$AP Rocky'),('Acyde'),('Adele'),('Aquilo'),('Bones'),('Dan Smith'),('Drake'),('Future'),('James Fauntleroy'),('Joe Fox'),('Juicy J'),('Justin Bieber'),('Kanye West'),('Kyan'),('Lil Wayne'),('M.I.A.'),('Madeon'),('Mark Foster'),('Mark Ronson'),('Miguel'),('Mos Def'),('Nicholas Petricca'),('Passion Pit'),('Queen'),('Rod Stewart'),('Schoolboy Q'),('Sia'),('Taylor Swift'),('The Rolling Stones'),('UGK'),('Usher'),('Vancouver Sleep Clinic'),('Yams');
/*!40000 ALTER TABLE `artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `artists_of_albums`
--

LOCK TABLES `artists_of_albums` WRITE;
/*!40000 ALTER TABLE `artists_of_albums` DISABLE KEYS */;
INSERT INTO `artists_of_albums` VALUES (13,'A$AP Rocky'),(7,'Adele'),(8,'Adele'),(9,'Drake'),(10,'Drake'),(9,'Future'),(11,'Justin Bieber'),(12,'Madeon'),(4,'Queen'),(5,'Queen'),(6,'Sia'),(1,'Taylor Swift'),(2,'Taylor Swift'),(3,'The Rolling Stones');
/*!40000 ALTER TABLE `artists_of_albums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `artists_of_songs`
--

LOCK TABLES `artists_of_songs` WRITE;
/*!40000 ALTER TABLE `artists_of_songs` DISABLE KEYS */;
INSERT INTO `artists_of_songs` VALUES (26,'A$AP Rocky'),(27,'A$AP Rocky'),(28,'A$AP Rocky'),(29,'A$AP Rocky'),(30,'A$AP Rocky'),(31,'A$AP Rocky'),(32,'A$AP Rocky'),(33,'A$AP Rocky'),(34,'A$AP Rocky'),(35,'A$AP Rocky'),(36,'A$AP Rocky'),(37,'A$AP Rocky'),(38,'A$AP Rocky'),(39,'A$AP Rocky'),(40,'A$AP Rocky'),(41,'A$AP Rocky'),(42,'A$AP Rocky'),(43,'A$AP Rocky'),(6,'Adele'),(7,'Justin Bieber'),(8,'Madeon'),(9,'Madeon'),(10,'Madeon'),(11,'Madeon'),(12,'Madeon'),(13,'Madeon'),(14,'Madeon'),(15,'Madeon'),(16,'Madeon'),(17,'Madeon'),(18,'Madeon'),(19,'Madeon'),(20,'Madeon'),(21,'Madeon'),(22,'Madeon'),(23,'Madeon'),(24,'Madeon'),(25,'Madeon'),(5,'Queen'),(1,'Taylor Swift'),(2,'Taylor Swift'),(3,'Taylor Swift'),(4,'The Rolling Stones');
/*!40000 ALTER TABLE `artists_of_songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `featured_artists_of_songs`
--

LOCK TABLES `featured_artists_of_songs` WRITE;
/*!40000 ALTER TABLE `featured_artists_of_songs` DISABLE KEYS */;
INSERT INTO `featured_artists_of_songs` VALUES (43,'Acyde'),(17,'Aquilo'),(27,'Bones'),(11,'Dan Smith'),(28,'Future'),(38,'James Fauntleroy'),(26,'Joe Fox'),(28,'Joe Fox'),(34,'Joe Fox'),(35,'Joe Fox'),(36,'Joe Fox'),(37,'Juicy J'),(34,'Kanye West'),(9,'Kyan'),(40,'Lil Wayne'),(28,'M.I.A.'),(16,'Mark Foster'),(42,'Mark Ronson'),(42,'Miguel'),(43,'Mos Def'),(21,'Nicholas Petricca'),(12,'Passion Pit'),(42,'Rod Stewart'),(33,'Schoolboy Q'),(37,'UGK'),(7,'Usher'),(25,'Vancouver Sleep Clinic'),(43,'Yams');
/*!40000 ALTER TABLE `featured_artists_of_songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `format_of_album`
--

LOCK TABLES `format_of_album` WRITE;
/*!40000 ALTER TABLE `format_of_album` DISABLE KEYS */;
INSERT INTO `format_of_album` VALUES (2,1,'audio cd'),(1,1,'mp3'),(3,1,'vinyl'),(5,2,'audio cd'),(4,2,'mp3'),(7,12,'audio cd'),(6,12,'mp3'),(8,13,'mp3');
/*!40000 ALTER TABLE `format_of_album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `general_song`
--

LOCK TABLES `general_song` WRITE;
/*!40000 ALTER TABLE `general_song` DISABLE KEYS */;
INSERT INTO `general_song` VALUES (1,'Bad Blood','Taylor_Swift_-_Bad_Blood.txt','Taylor_Swift_-_Bad_Blood.mp3',211,1,8),(2,'Blank Space','Taylor_Swift_-_Blank_Space.txt','Taylor_Swift_-_Blank_Space.mp3',231,1,2),(3,'Treacherous','Taylor_Swift_-_Treacherous.txt','Taylor_Swift_-_Treacherous.mp3',241,2,3),(4,'Gimme Shelter','Rolling_Stones_-_Gimme_Shelter.txt','Rolling_Stones_-_Gimme_Shelter.mp3',277,3,1),(5,'We Will Rock You','Queen_-_We_Will_Rock_You.txt','Queen_-_We_Will_Rock_You.mp3',122,4,1),(6,'One and Only','Adele_-_One_and_Only.txt','Adele_-_One_and_Only.mp3',348,7,9),(7,'The Christmas Song (Chestnuts Roasting on an Open Fire)','Justin_Bieber_-_The_Christmas_Song.txt','Justin_Bieber_-_The_Christmas_Song.mp3',215,11,3),(8,'Isometric (Intro)',NULL,'Madeon_-_Isometric_(Intro).mp3',81,12,1),(9,'You\'re On',NULL,'Madeon_-_You\'re_On.mp3',193,12,2),(10,'OK',NULL,'Madeon_-_OK.mp3',182,12,3),(11,'La Lune',NULL,'Madeon_-_La_Lune.mp3',220,12,4),(12,'Pay No Mind','Madeon_-_Pay_No_Mind.txt','Madeon_-_Pay_No_Mind.mp3',249,12,5),(13,'Beings',NULL,'Madeon_-_Beings.mp3',215,12,6),(14,'Imperium',NULL,'Madeon_-_Imperium.mp3',199,12,7),(15,'Zephyr',NULL,'Madeon_-_Zephyr.mp3',220,12,8),(16,'Nonsense',NULL,'Madeon_-_Nonsense.mp3',226,12,9),(17,'Innocence',NULL,'Madeon_-_Innocence.mp3',224,12,10),(18,'Pixel Empire',NULL,'Madeon_-_Pixel Empire.mp3',245,12,11),(19,'Home',NULL,'Madeon_-_Home.mp3',225,12,12),(20,'Icarus',NULL,'Madeon_-_Icarus.mp3',215,12,13),(21,'Finale',NULL,'Madeon_-_Finale.mp3',205,12,14),(22,'The City',NULL,'Madeon_-_The City.mp3',234,12,15),(23,'Cut the Kid',NULL,'Madeon_-_Cut the Kid.mp3',198,12,16),(24,'Technicolor',NULL,'Madeon_-_Technicolor.mp3',385,12,17),(25,'Only Way Out',NULL,'Madeon_-_Only_Way_Out.mp3',227,12,18),(26,'Holy Ghost','A$AP_Rocky_-_Holy_Ghost.txt',NULL,191,13,1),(27,'Canal St.','A$AP_Rocky_-_Canal_St..txt',NULL,227,13,2),(28,'Fine Whine','A$AP_Rocky_-_Fine_Whine.txt',NULL,219,13,3),(29,'L$D','A$AP_Rocky_-_L$D.txt',NULL,238,13,4),(30,'Excuse Me','A$AP_Rocky_-_Excuse_Me.txt',NULL,238,13,5),(31,'JD','A$AP_Rocky_-_JD.txt',NULL,106,13,6),(32,'Lord Pretty Flacko Jodye 2 (LPFJ2)','A$AP_Rocky_-_Lord_Pretty_Flacko_Jodye_2_(LPFJ2).txt',NULL,127,13,7),(33,'Electric Body','A$AP_Rocky_-_Electric_Body.txt',NULL,255,13,8),(34,'Jukebox Joints','A$AP_Rocky_-_Jukebox_Joints.txt',NULL,324,13,9),(35,'Max B','A$AP_Rocky_-_Max_B.txt',NULL,241,13,10),(36,'Pharsyde','A$AP_Rocky_-_Pharcyde.txt',NULL,222,13,11),(37,'Wavybone','A$AP_Rocky_-_Wavybone.txt',NULL,303,13,12),(38,'West Side Highway','A$AP_Rocky_-_West_Side_Highway.txt',NULL,177,13,13),(39,'Better Things','A$AP_Rocky_-_Better_Things.txt',NULL,199,13,14),(40,'M\'$','A$AP_Rocky_-_M\'$.txt',NULL,233,13,15),(41,'Dreams (Interlude)','A$AP_Rocky_-_Dreams_(Interlude).txt',NULL,137,13,16),(42,'Everyday','A$AP_Rocky_-_Everyday.txt',NULL,261,13,17),(43,'Back Home','A$AP_Rocky_-_Back_Home.txt',NULL,278,13,18);
/*!40000 ALTER TABLE `general_song` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES ('arena rock'),('blues'),('country blues'),('dance'),('dance-pop'),('electro house'),('electropop'),('hard rock'),('hip hop'),('holiday'),('pop'),('pop rock'),('R&B'),('rock'),('soul'),('synthpop'),('teen pop'),('trap');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `genre_of_album`
--

LOCK TABLES `genre_of_album` WRITE;
/*!40000 ALTER TABLE `genre_of_album` DISABLE KEYS */;
INSERT INTO `genre_of_album` VALUES (4,'arena rock'),(3,'blues'),(3,'country blues'),(12,'dance'),(1,'dance-pop'),(12,'electro house'),(6,'electropop'),(3,'hard rock'),(9,'hip hop'),(10,'hip hop'),(13,'hip hop'),(11,'holiday'),(1,'pop'),(2,'pop'),(7,'pop'),(12,'pop'),(7,'R&B'),(11,'R&B'),(4,'rock'),(5,'rock'),(7,'soul'),(8,'soul'),(1,'synthpop'),(11,'teen pop'),(9,'trap'),(13,'trap');
/*!40000 ALTER TABLE `genre_of_album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `genre_of_song`
--

LOCK TABLES `genre_of_song` WRITE;
/*!40000 ALTER TABLE `genre_of_song` DISABLE KEYS */;
INSERT INTO `genre_of_song` VALUES (5,'arena rock'),(8,'electro house'),(9,'electro house'),(10,'electro house'),(11,'electro house'),(12,'electro house'),(13,'electro house'),(14,'electro house'),(15,'electro house'),(16,'electro house'),(17,'electro house'),(18,'electro house'),(19,'electro house'),(20,'electro house'),(21,'electro house'),(22,'electro house'),(23,'electro house'),(24,'electro house'),(25,'electro house'),(2,'electropop'),(4,'hard rock'),(1,'hip hop'),(26,'hip hop'),(27,'hip hop'),(28,'hip hop'),(29,'hip hop'),(30,'hip hop'),(31,'hip hop'),(32,'hip hop'),(33,'hip hop'),(34,'hip hop'),(35,'hip hop'),(36,'hip hop'),(37,'hip hop'),(38,'hip hop'),(39,'hip hop'),(40,'hip hop'),(41,'hip hop'),(42,'hip hop'),(43,'hip hop'),(7,'holiday'),(1,'pop'),(3,'pop'),(6,'pop rock');
/*!40000 ALTER TABLE `genre_of_song` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `music_format`
--

LOCK TABLES `music_format` WRITE;
/*!40000 ALTER TABLE `music_format` DISABLE KEYS */;
INSERT INTO `music_format` VALUES ('audio cd'),('cassette'),('mp3'),('vinyl');
/*!40000 ALTER TABLE `music_format` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `single_song`
--

LOCK TABLES `single_song` WRITE;
/*!40000 ALTER TABLE `single_song` DISABLE KEYS */;
INSERT INTO `single_song` VALUES (1,'2015-05-17','Taylor_Swift_-_Bad_Blood.png'),(2,'2014-11-10','Taylor_Swift_-_Blank_Space.png'),(4,'1969-12-05',NULL),(5,'1977-10-07','Queen_-_We_Will_Rock_You.png');
/*!40000 ALTER TABLE `single_song` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES ('Amazon'),('Barnes & Noble'),('iTunes'),('Walmart');
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `store_catalog`
--

LOCK TABLES `store_catalog` WRITE;
/*!40000 ALTER TABLE `store_catalog` DISABLE KEYS */;
INSERT INTO `store_catalog` VALUES ('Amazon',1,12.49),('Barnes & Noble',7,26.59),('iTunes',6,13.99),('iTunes',8,5.99);
/*!40000 ALTER TABLE `store_catalog` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-04 14:11:55
