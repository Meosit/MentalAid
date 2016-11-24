-- -----------------------------------------------------
-- Imitation of english auction process
-- -----------------------------------------------------
USE `bidbuy`;
SET NAMES 'utf8';

INSERT INTO `lot`(`owner_id`, `auction_type`, `min_price`, `current_price`, `max_price`, `bid_step`, `duration_time`, `name`, `description`)
VALUES(
	6, -- user NearYou
    1, -- english auction type
	50, -- minimum price
    50, -- current price
    100, -- maximum price
    2, -- increase of price
    86400, -- one day
    'Bonus code for tank "Lowe"',
    'You сan win and enter this code on https://ru.wargaming.net/shop/bonus/'
);

-- lot approved by administrator
UPDATE `lot` SET `status` = 1 WHERE `id` = 2; 

-- lot published by it's owner
UPDATE `lot` SET `status` = 2 WHERE `id` = 2; 

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    2, 
    2, -- user Neskwi
    50 -- standard bid
);

-- Imitation of delay bidween bids
SELECT sleep(5);

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    2, 
    7, -- user Nuclear
    52 -- standard bid
);

-- Imitation of delay bidween bids
SELECT sleep(6);

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    1, 
    5, -- user appleWow
    100 -- buyout bid
);

-- lot closed automatically if one of bid have sum equal to max_price
--
-- ALSO if auction time will be expired and leader_bid_id = NULL
-- UPDATE `lot` SET `status` = -1 WHERE `id` = 1; 
UPDATE `lot` SET `status` = 3 WHERE `id` = 1; 
