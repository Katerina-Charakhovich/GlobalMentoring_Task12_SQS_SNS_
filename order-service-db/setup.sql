CREATE DATABASE schema_product;

USE schema_product;

CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user` varchar(255) NOT NULL,
  `type` enum('LIQUIDS','COUNTABLE') NOT NULL DEFAULT 'COUNTABLE',
  `volume` int DEFAULT NULL,
  `number` int DEFAULT NULL,
  `total` int DEFAULT NULL,
  `state` enum('REJECT','ACCEPTED','NEW') NOT NULL DEFAULT 'NEW',
  `comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
