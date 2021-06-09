-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema thieuhuyen
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema thieuhuyen
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `thieuhuyen` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `thieuhuyen` ;

-- -----------------------------------------------------
-- Table `thieuhuyen`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thieuhuyen`.`book` (
  `book_id` INT NOT NULL AUTO_INCREMENT,
  `book_name` VARCHAR(200) NULL DEFAULT NULL,
  `book_picture` VARCHAR(45) NULL DEFAULT NULL,
  `book_year` VARCHAR(45) NULL DEFAULT NULL,
  `book_description` TEXT NULL DEFAULT NULL,
  `book_author` VARCHAR(100) NULL DEFAULT NULL,
  `book_price` INT NULL DEFAULT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`book_id`),
  UNIQUE INDEX `book_id_UNIQUE` (`book_id` ASC) ,
  INDEX `fk_book_category1_idx` (`category_id` ASC) ,
  CONSTRAINT `fk_book_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `thieuhuyen`.`category` (`category_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 35
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `thieuhuyen`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thieuhuyen`.`category` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE INDEX `category_id_UNIQUE` (`category_id` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `thieuhuyen`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thieuhuyen`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(25) NOT NULL ,
  `user_phone` VARCHAR(45) NOT NULL ,
  `user_email` VARCHAR(45) NOT NULL ,
  `user_password` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `thieuhuyen`.`favorite_book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thieuhuyen`.`favorite_book` (
  `favorite_book_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `book_id` INT NOT NULL,
  `liked_time` TIMESTAMP(6) NOT NULL,
  PRIMARY KEY (`favorite_book_id`),
  UNIQUE INDEX `favorite_book_id_UNIQUE` (`favorite_book_id` ASC) ,
  INDEX `fk_favorite_book_user1_idx` (`user_id` ASC) ,
  INDEX `fk_favorite_book_book1_idx` (`book_id` ASC) ,
  CONSTRAINT `fk_favorite_book_book1`
    FOREIGN KEY (`book_id`)
    REFERENCES `thieuhuyen`.`book` (`book_id`),
  CONSTRAINT `fk_favorite_book_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `thieuhuyen`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `thieuhuyen`.`published_book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thieuhuyen`.`published_book` (
  `published_book_id` INT NOT NULL AUTO_INCREMENT,
  `book_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `posted_time` TIMESTAMP(6) NOT NULL,
  PRIMARY KEY (`published_book_id`),
  UNIQUE INDEX `user_book_id_UNIQUE` (`published_book_id` ASC) ,
  INDEX `fk_user_book_book_idx` (`book_id` ASC) ,
  INDEX `fk_user_book_user1_idx` (`user_id` ASC) ,
  CONSTRAINT `fk_user_book_book`
    FOREIGN KEY (`book_id`)
    REFERENCES `thieuhuyen`.`book` (`book_id`),
  CONSTRAINT `fk_user_book_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `thieuhuyen`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;