-- CHANGELOG
-- -----------------------------------------------------
-- UPDATED FOR MySQL 5.5.50MariaDB
-- Added triggers for DATETIME values
-- In table `lot` column `last_bid_id` renamed to `leader_bid_id`
-- To table `lot` added three new fields: `current_price`, `created_at`, `modified_at`
-- For table `bid` added trigger (after insert) which updates leader_bid_id
-- -----------------------------------------------------

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema bidbuy
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `bidbuy` ;

CREATE SCHEMA IF NOT EXISTS `bidbuy` ;

-- -----------------------------------------------------
-- Table `bidbuy`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bidbuy`.`user` ;

CREATE TABLE IF NOT EXISTS `bidbuy`.`user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID of a user',
  `username` VARCHAR(45) NOT NULL COMMENT 'Unique name of user, that will be shown on site, case-sensitive',
  `pass_hash` CHAR(60) NOT NULL COMMENT 'Hash of user password, created by bcrypt hash algorithm (fixed 60 char length)',
  `role` TINYINT(0) NOT NULL DEFAULT 0 COMMENT 'Represents role of a user, can be one of specified values:\n0 - client\n1 - administrator\n(tinyint according this article http://komlenic.com/244/8-reasons-why-mysqls-enum-data-type-is-evil/ )',
  `created_at` DATETIME NOT NULL COMMENT 'Time when current user was created',
  `modified_at` DATETIME NOT NULL COMMENT 'Time when current user credentials was updated',
  `status` TINYINT(0) NOT NULL DEFAULT 1 COMMENT 'Represents current status of user, can be one of specified values:\n-1 - deleted\n 0 - banned\n 1 - active\n(tinyint according this article http://komlenic.com/244/8-reasons-why-mysqls-enum-data-type-is-evil/ )',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = 'This table stores all users in system, including clients and administrators';

CREATE UNIQUE INDEX `username_UNIQUE` ON `bidbuy`.`user` (`username` ASC);


-- -----------------------------------------------------
-- Table `bidbuy`.`user_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bidbuy`.`user_info` ;

CREATE TABLE IF NOT EXISTS `bidbuy`.`user_info` (
  `user_id` INT UNSIGNED NOT NULL COMMENT 'ID of a user,  ',
  `modified_at` DATETIME NOT NULL COMMENT 'Time when user profile was modified last time',
  `address` VARCHAR(500) NULL DEFAULT NULL COMMENT 'Live address of user, used for shipment',
  `first_name` VARCHAR(100) NULL DEFAULT NULL COMMENT 'First name of a user',
  `last_name` VARCHAR(100) NULL DEFAULT NULL COMMENT 'Last name of a user',
  `phone_number` VARCHAR(10) NULL DEFAULT NULL COMMENT 'phone number of a user',
  `email` VARCHAR(255) NULL DEFAULT NULL COMMENT 'email of a user',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `bidbuy`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Represents user information, what is not necessary for basic system working';


-- -----------------------------------------------------
-- Table `bidbuy`.`bid`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bidbuy`.`bid` ;

CREATE TABLE IF NOT EXISTS `bidbuy`.`bid` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID of a bid',
  `lot_id` INT UNSIGNED NOT NULL COMMENT 'ID of a lot this bid corresponds to',
  `client_id` INT UNSIGNED NOT NULL COMMENT 'ID of a user, who make this bid',
  `sum` DECIMAL(13,4) UNSIGNED NOT NULL COMMENT 'Sum of a bid',
  `time` DATETIME NOT NULL COMMENT 'Time moment when this bid occurs',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_client_id`
    FOREIGN KEY (`client_id`)
    REFERENCES `bidbuy`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_lot_id`
    FOREIGN KEY (`lot_id`)
    REFERENCES `bidbuy`.`lot` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Represents bid through which users can affect on auction lot';

CREATE INDEX `client_id_FK_INDEX` ON `bidbuy`.`bid` (`client_id` DESC);

CREATE INDEX `lot_id_FK_INDEX` ON `bidbuy`.`bid` (`lot_id` DESC);


-- -----------------------------------------------------
-- Table `bidbuy`.`lot`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bidbuy`.`lot` ;

CREATE TABLE IF NOT EXISTS `bidbuy`.`lot` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID of a lot',
  `owner_id` INT UNSIGNED NOT NULL COMMENT 'ID of the user, who created this lot',
  `leader_bid_id` INT UNSIGNED NULL DEFAULT NULL COMMENT 'Last bid for this lot.\nThis approach longer in bid inserting, but faster while selecting winner of the lot. \nAlso lot can determine transaction record itself.',
  `auction_type` TINYINT(0) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'Represents type of auction, at the current moment there are three types: \n1 - english auction\n2 - reversive auction\n3 - auction-lottery\n(tinyint according this article http://komlenic.com/244/8-reasons-why-mysqls-enum-data-type-is-evil/ )',
  `min_price` DECIMAL(13,4) UNSIGNED ZEROFILL NOT NULL COMMENT 'Represents minimum price of a lot, which setting up by user',
  `current_price` DECIMAL(13,4) UNSIGNED ZEROFILL NOT NULL COMMENT 'Represents price of lot at the current moment',
  `max_price` DECIMAL(13,4) UNSIGNED ZEROFILL NOT NULL COMMENT 'Represents maximum price of a lot, which setting up by user',
  `bid_step` DECIMAL(13,4) UNSIGNED NOT NULL COMMENT 'Sum delta bidween two nearest bids, or fixed bid for auction-lottery',
  `duration_time` INT UNSIGNED NOT NULL COMMENT 'Represents time bidween auction will be opened and closed, in seconds',
  `name` VARCHAR(200) NOT NULL COMMENT 'Short name of a lot which will be shown in lot list',
  `description` VARCHAR(1000) NOT NULL COMMENT 'Detaied description of a lot',
  `image_path` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Path of the lot image on server hdd, optional',
  `status` TINYINT(0) NOT NULL DEFAULT 0 COMMENT 'Represents current status of lot, and can  be one of specified values:\n-1 - lot deleted, but not sold\n 0 - lot created\n 1 - lot approved and can be published\n 2 - lot published and opened\n 3 - lot closed\n(tinyint according this article http://komlenic.com/244/8-reasons-why-mysqls-enum-data-type-is-evil/ )',
  `status_changed_at` DATETIME NOT NULL COMMENT 'Represents time when status of lot was changed',
  `modified_at` DATETIME NOT NULL COMMENT 'Represents time when status of lot was changed',
  `created_at` DATETIME NOT NULL COMMENT 'Represents time when status of lot was changed',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_owner_id`
    FOREIGN KEY (`owner_id`)
    REFERENCES `bidbuy`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_leader_bid_id`
    FOREIGN KEY (`leader_bid_id`)
    REFERENCES `bidbuy`.`bid` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Represents auction lot';

CREATE INDEX `owner_id_FK_INDEX` ON `bidbuy`.`lot` (`owner_id` ASC);

CREATE INDEX `name_INDEX` ON `bidbuy`.`lot` (`name` ASC);

-- -----------------------------------------------------
-- Table `bidbuy`.`transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bidbuy`.`transaction` ;

CREATE TABLE IF NOT EXISTS `bidbuy`.`transaction` (
  `lot_id` INT UNSIGNED NOT NULL COMMENT 'ID of the lot that is transferred',
  `time` DATETIME NOT NULL COMMENT 'Time when this transaction occured',
  `comment` VARCHAR(300) NULL DEFAULT NULL COMMENT 'Additional information of transaction, optional',
  `seller_id` INT UNSIGNED NOT NULL COMMENT 'ID of owner of the lot that is transferred',
  `customer_id` INT UNSIGNED NOT NULL COMMENT 'ID of client who have winning bid on lot that is transferred',
  PRIMARY KEY (`lot_id`),
  CONSTRAINT `fk_seller_id`
    FOREIGN KEY (`seller_id`)
    REFERENCES `bidbuy`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_customer_id`
    FOREIGN KEY (`customer_id`)
    REFERENCES `bidbuy`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_transaction_lot_id`
    FOREIGN KEY (`lot_id`)
    REFERENCES `bidbuy`.`lot` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Represents transaction bidween two clients: one of them sells lot, another wins it by bid';

CREATE INDEX `seller_id_FK_INDEX` ON `bidbuy`.`transaction` (`seller_id` ASC);

CREATE INDEX `customer_id_FK_INDEX` ON `bidbuy`.`transaction` (`customer_id` ASC);

USE `bidbuy`;

-- -----------------------------------------------------
-- Triggers for DATETIME values
-- -----------------------------------------------------
DELIMITER $$

DROP TRIGGER IF EXISTS `bidbuy`.`user_BEFORE_INSERT` $$

CREATE TRIGGER `bidbuy`.`user_BEFORE_INSERT` BEFORE INSERT ON `user` FOR EACH ROW
BEGIN
	SET NEW.`created_at` = NOW();
    SET NEW.`modified_at` = NOW();
END$$


DROP TRIGGER IF EXISTS `bidbuy`.`user_BEFORE_UPDATE`$$

CREATE TRIGGER `bidbuy`.`user_BEFORE_UPDATE` BEFORE UPDATE ON `user` FOR EACH ROW
BEGIN
	SET NEW.`modified_at` = NOW();
END$$


DROP TRIGGER IF EXISTS `bidbuy`.`user_info_BEFORE_INSERT`$$

CREATE TRIGGER `bidbuy`.`user_info_BEFORE_INSERT` BEFORE INSERT ON `user_info` FOR EACH ROW
BEGIN
	SET NEW.`modified_at` = NOW();
END$$


DROP TRIGGER IF EXISTS `bidbuy`.`user_info_BEFORE_UPDATE`$$

CREATE TRIGGER `bidbuy`.`user_info_BEFORE_UPDATE` BEFORE UPDATE ON `user_info` FOR EACH ROW
BEGIN
	SET NEW.`modified_at` = NOW();
END$$


DROP TRIGGER IF EXISTS `bidbuy`.`bid_BEFORE_INSERT`$$

CREATE TRIGGER `bidbuy`.`bid_BEFORE_INSERT` BEFORE INSERT ON `bid` FOR EACH ROW
BEGIN
	SET NEW.`time` = NOW();
END$$


DROP TRIGGER IF EXISTS `bidbuy`.`lot_BEFORE_INSERT`$$

CREATE TRIGGER `bidbuy`.`lot_BEFORE_INSERT` BEFORE INSERT ON `lot` FOR EACH ROW
BEGIN
	SET NEW.`created_at` = NOW();
	SET NEW.`modified_at` = NOW();
	SET NEW.`status_changed_at` = NOW();
END$$


DROP TRIGGER IF EXISTS `bidbuy`.`lot_BEFORE_UPDATE`$$

CREATE TRIGGER `bidbuy`.`lot_BEFORE_UPDATE` BEFORE UPDATE ON `lot` FOR EACH ROW
BEGIN
	SET NEW.`modified_at` = NOW();
	IF OLD.`status` <> NEW.`status` THEN
		SET NEW.`status_changed_at` = NOW();
    END IF;
END$$


DROP TRIGGER IF EXISTS `bidbuy`.`transaction_BEFORE_INSERT`$$

CREATE TRIGGER `bidbuy`.`transaction_BEFORE_INSERT` BEFORE INSERT ON `transaction` FOR EACH ROW
BEGIN
	SET NEW.time = NOW();
END$$

-- -----------------------------------------------------
-- Trigger for autoupdating leader_bid_id and current_price
-- -----------------------------------------------------
DROP TRIGGER IF EXISTS `bidbuy`.`bid_AFTER_INSERT`$$

CREATE TRIGGER `bidbuy`.`bid_AFTER_INSERT` AFTER INSERT ON `bid` FOR EACH ROW
BEGIN
	UPDATE `lot` SET 
		`leader_bid_id` = NEW.`id`,
        `current_price` = NEW.`sum`
    WHERE `id` = NEW.`lot_id`;
END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
