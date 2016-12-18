-- -----------------------------------------------------
-- Imitation of auction process
-- -----------------------------------------------------
USE `bidbuy`;
SET NAMES 'utf8';

INSERT INTO `lot` (`owner_id`, `auction_type`, `min_price`, `current_price`, `max_price`, `bid_step`, `duration_time`, `name`, `description`)
VALUES (
  2, -- user Neskwi
  2, -- reversive auction type
  50, -- minimum price
  300, -- current price
  300, -- maximum price
  20, -- decrease of price
  300, -- 5 minutes
  'Bonus code for tank "T34"',
  'You сan win and enter this code on https://ru.wargaming.net/shop/bonus/'
);

-- lot approved by administrator
UPDATE `lot`
SET `status` = 1
WHERE `id` = 3;

-- lot published by it's owner
UPDATE `lot`
SET `status` = 2
WHERE `id` = 3;

-- After some time system decreases current price
SELECT sleep(5); -- Time calculates based on 
UPDATE `lot`
SET `current_price` = 280
WHERE `id` = 3;

-- After some time system decreases current price
SELECT sleep(5); -- Time calculates based on 
UPDATE `lot`
SET `current_price` = 260
WHERE `id` = 3;

-- After some time system decreases current price
SELECT sleep(5); -- Time calculates based on 
UPDATE `lot`
SET `current_price` = 240
WHERE `id` = 3;

-- First bid on this type of lot is winning
INSERT INTO `bid` (`lot_id`, `client_id`, `sum`)
VALUES (
  3,
  6, -- user NearYou
  240 -- current price
);

-- lot closed automatically if current_price lower than min_price
--
-- ALSO if auction time will be expired and leader_bid_id = NULL
-- UPDATE `lot` SET `status` = -1 WHERE `id` = 1; 
UPDATE `lot`
SET `status` = 3
WHERE `id` = 1;
