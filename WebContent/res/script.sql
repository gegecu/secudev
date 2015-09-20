SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `secudev` DEFAULT CHARACTER SET utf8 ;
USE `secudev` ;

-- -----------------------------------------------------
-- Table `secudev`.`salutation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `secudev`.`salutation` ;

CREATE TABLE IF NOT EXISTS `secudev`.`salutation` (
  `salutationid` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`salutationid`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `secudev`.`sex`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `secudev`.`sex` ;

CREATE TABLE IF NOT EXISTS `secudev`.`sex` (
  `sexid` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`sexid`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `secudev`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `secudev`.`user` ;

CREATE TABLE IF NOT EXISTS `secudev`.`user` (
  `firstname` VARCHAR(50) NOT NULL,
  `lastname` VARCHAR(50) NOT NULL,
  `sexid` INT(11) NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  `salutationid` INT(11) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `birthdate` DATE NOT NULL,
  `joindate` DATE NOT NULL,
  PRIMARY KEY (`username`),
  INDEX `sexid_idx` (`sexid` ASC),
  INDEX `salutationid_idx` (`salutationid` ASC),
  CONSTRAINT `salutationid`
    FOREIGN KEY (`salutationid`)
    REFERENCES `secudev`.`salutation` (`salutationid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sexid`
    FOREIGN KEY (`sexid`)
    REFERENCES `secudev`.`sex` (`sexid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `secudev`.`admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `secudev`.`admin` ;

CREATE TABLE IF NOT EXISTS `secudev`.`admin` (
  `username` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `username`
    FOREIGN KEY (`username`)
    REFERENCES `secudev`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `secudev`.`log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `secudev`.`log` ;

CREATE TABLE IF NOT EXISTS `secudev`.`log` (
  `logid` INT(11) NOT NULL AUTO_INCREMENT,
  `loginfo` VARCHAR(100) NOT NULL,
  `ipaddress` VARCHAR(100) NOT NULL,
  `datetime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`logid`))
ENGINE = InnoDB
AUTO_INCREMENT = 81
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `secudev`.`post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `secudev`.`post` ;

CREATE TABLE IF NOT EXISTS `secudev`.`post` (
  `postid` INT(11) NOT NULL AUTO_INCREMENT,
  `post` TEXT NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `postdate` DATE NOT NULL,
  `editeddate` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`postid`),
  INDEX `username_idx` (`username` ASC),
  CONSTRAINT `username_fk`
    FOREIGN KEY (`username`)
    REFERENCES `secudev`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 69
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
