-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mentalaid
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mentalaid`;

-- -----------------------------------------------------
-- Schema mentalaid
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mentalaid`
  DEFAULT CHARACTER SET utf8;
USE `mentalaid`;

-- -----------------------------------------------------
-- Table `mentalaid`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mentalaid`.`user`;

CREATE TABLE IF NOT EXISTS `mentalaid`.`user` (
  `id`          INT UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT 'ID of a user',
  `email`       VARCHAR(255) NOT NULL,
  `username`    VARCHAR(45)  NOT NULL
  COMMENT 'Unique name of user, that will be shown on site, case-sensitive',
  `pass_hash`   CHAR(60)     NOT NULL
  COMMENT 'Hash of user password, created by bcrypt hash algorithm (fixed 60 char length)',
  `role`        TINYINT(0)   NOT NULL DEFAULT 0
  COMMENT 'Represents role of a user, can be one of specified values:\n0 - client\n1 - administrator\n(tinyint according this article http://komlenic.com/244/8-reasons-why-mysqls-enum-data-type-is-evil/ )',
  `created_at`  DATETIME              DEFAULT NULL
  COMMENT 'Time when current user was created',
  `modified_at` DATETIME              DEFAULT NULL
  COMMENT 'Time when current user credentials was updated',
  `status`      TINYINT(0)   NOT NULL DEFAULT 1
  COMMENT 'Represents current status of user, can be one of specified values:\n-1 - deleted\n 0 - banned\n 1 - active\n(tinyint according this article http://komlenic.com/244/8-reasons-why-mysqls-enum-data-type-is-evil/ )',
  `locale`      CHAR(2)               DEFAULT NULL
  COMMENT 'Time when current user credentials was updated',
  `image_url`   VARCHAR(255)          DEFAULT NULL
  COMMENT 'Avatar of an user',
  `website`     VARCHAR(255)          DEFAULT NULL
  COMMENT 'Personal website of a user, maybe facebook profile or smth like this',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  COMMENT = 'This table stores all users in system, including clients and administrators';

CREATE UNIQUE INDEX `username_UNIQUE`
  ON `mentalaid`.`user` (`username` ASC)
  COMMENT 'For searching users by username on site';

CREATE UNIQUE INDEX `email_UNIQUE`
  ON `mentalaid`.`user` (`email` ASC);

-- -----------------------------------------------------
-- Table `mentalaid`.`question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mentalaid`.`question`;

CREATE TABLE IF NOT EXISTS `mentalaid`.`question` (
  `id`          INT UNSIGNED  NOT NULL AUTO_INCREMENT
  COMMENT 'ID of a question',
  `creator_id`  INT UNSIGNED  NOT NULL
  COMMENT 'ID of a user, which created this question',
  `title`       VARCHAR(200)  NOT NULL
  COMMENT 'Short problem description',
  `description` VARCHAR(2000) NOT NULL
  COMMENT 'Detailed and complete description of a problem',
  `status`      TINYINT       NOT NULL DEFAULT 0
  COMMENT 'Represents current status of a question, can be one of specified values:\n-1 - deleted\n 0 - normal',
  `created_at`  DATETIME      NULL     DEFAULT NULL,
  `modified_at` DATETIME      NULL     DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_question_user1`
  FOREIGN KEY (`creator_id`)
  REFERENCES `mentalaid`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

CREATE INDEX `question_creator_id_INDEX`
  ON `mentalaid`.`question` (`creator_id` ASC);

-- -----------------------------------------------------
-- Table `mentalaid`.`answer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mentalaid`.`answer`;

CREATE TABLE IF NOT EXISTS `mentalaid`.`answer` (
  `id`          INT UNSIGNED  NOT NULL AUTO_INCREMENT
  COMMENT 'ID of an answer',
  `question_id` INT UNSIGNED  NOT NULL
  COMMENT 'ID of a question this answer for',
  `creator_id`  INT UNSIGNED  NOT NULL
  COMMENT 'ID of a user who created this answer',
  `text`        VARCHAR(2000) NOT NULL
  COMMENT 'text of an answer',
  `status`      TINYINT       NOT NULL DEFAULT 0
  COMMENT 'Represents current status of a question, can be one of specified values:\n-1 - deleted\n 0 - normal',
  `created_at`  DATETIME      NULL     DEFAULT NULL,
  `modified_at` DATETIME      NULL     DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_answer_question1`
  FOREIGN KEY (`question_id`)
  REFERENCES `mentalaid`.`question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_answer_user1`
  FOREIGN KEY (`creator_id`)
  REFERENCES `mentalaid`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

CREATE INDEX `answer_question_id_INDEX`
  ON `mentalaid`.`answer` (`question_id` ASC);

CREATE INDEX `answer_creator_id_INDEX`
  ON `mentalaid`.`answer` (`creator_id` ASC);

-- -----------------------------------------------------
-- Table `mentalaid`.`mark`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mentalaid`.`mark`;

CREATE TABLE IF NOT EXISTS `mentalaid`.`mark` (
  `id`          INT UNSIGNED              NOT NULL
  COMMENT 'id of a mark',
  `user_id`     INT UNSIGNED              NOT NULL
  COMMENT 'id of a user which created this mark',
  `answer_id`   INT UNSIGNED              NOT NULL
  COMMENT 'id of an answer this mark corresponds to',
  `value`       TINYINT UNSIGNED ZEROFILL NOT NULL
  COMMENT 'Mark value',
  `created_at`  DATETIME                  NULL DEFAULT NULL,
  `modified_at` DATETIME                  NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_mark_answer1`
  FOREIGN KEY (`answer_id`)
  REFERENCES `mentalaid`.`answer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_mark_user1`
  FOREIGN KEY (`user_id`)
  REFERENCES `mentalaid`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

CREATE INDEX `mark_answer_id_INDEX`
  ON `mentalaid`.`mark` (`answer_id` ASC);

CREATE INDEX `mark_user_id_INDEX`
  ON `mentalaid`.`mark` (`user_id` ASC);

USE `mentalaid`;

DELIMITER $$

DROP TRIGGER IF EXISTS `mentalaid`.`user_BEFORE_INSERT` $$

CREATE TRIGGER `mentalaid`.`user_BEFORE_INSERT`
BEFORE INSERT ON `user`
FOR EACH ROW
  BEGIN
    SET NEW.created_at = NOW();
    SET NEW.modified_at = NOW();
  END$$


DROP TRIGGER IF EXISTS `mentalaid`.`user_BEFORE_UPDATE` $$

CREATE TRIGGER `mentalaid`.`user_BEFORE_UPDATE`
BEFORE UPDATE ON `user`
FOR EACH ROW
  BEGIN
    SET NEW.modified_at = NOW();
  END$$


DROP TRIGGER IF EXISTS `mentalaid`.`question_BEFORE_INSERT` $$

CREATE TRIGGER `mentalaid`.`question_BEFORE_INSERT`
BEFORE INSERT ON `question`
FOR EACH ROW
  BEGIN
    SET NEW.created_at = NOW();
    SET NEW.modified_at = NOW();
  END$$


USE `mentalaid`$$
DROP TRIGGER IF EXISTS `mentalaid`.`question_BEFORE_UPDATE` $$
USE `mentalaid`$$
CREATE DEFINER = CURRENT_USER TRIGGER `mentalaid`.`question_BEFORE_UPDATE`
BEFORE UPDATE ON `question`
FOR EACH ROW
  BEGIN
    SET NEW.modified_at = NOW();
  END$$


DROP TRIGGER IF EXISTS `mentalaid`.`answer_BEFORE_INSERT` $$

CREATE TRIGGER `mentalaid`.`answer_BEFORE_INSERT`
BEFORE INSERT ON `answer`
FOR EACH ROW
  BEGIN
    SET NEW.created_at = NOW();
    SET NEW.modified_at = NOW();
  END$$


DROP TRIGGER IF EXISTS `mentalaid`.`answer_BEFORE_UPDATE` $$

CREATE TRIGGER `mentalaid`.`answer_BEFORE_UPDATE`
BEFORE UPDATE ON `answer`
FOR EACH ROW
  BEGIN
    SET NEW.modified_at = NOW();
  END$$


DROP TRIGGER IF EXISTS `mentalaid`.`mark_BEFORE_INSERT` $$

CREATE TRIGGER `mentalaid`.`mark_BEFORE_INSERT`
BEFORE INSERT ON `mark`
FOR EACH ROW
  BEGIN
    SET NEW.created_at = NOW();
    SET NEW.modified_at = NOW();
  END$$


DROP TRIGGER IF EXISTS `mentalaid`.`mark_BEFORE_UPDATE` $$

CREATE TRIGGER `mentalaid`.`mark_BEFORE_UPDATE`
BEFORE UPDATE ON `mark`
FOR EACH ROW
  BEGIN
    SET NEW.created_at = NOW();
    SET NEW.modified_at = NOW();
  END$$


DELIMITER ;

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
