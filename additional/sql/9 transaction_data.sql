-- -----------------------------------------------------
-- Transactions
-- -----------------------------------------------------
USE `bidbuy`;

INSERT INTO `transaction`(`lot_id`, `seller_id`, `customer_id`) 
	SELECT `lot`.`id`, `lot`.`owner_id`, `bid`.`client_id`  
		FROM `lot` JOIN `bid` 
		ON `bid`.`id` = `lot`.`leader_bid_id`;
	
