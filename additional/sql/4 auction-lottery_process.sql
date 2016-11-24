-- -----------------------------------------------------
-- Imitation of auction-lottery process
-- -----------------------------------------------------
USE `bidbuy`;
SET NAMES 'utf8';

INSERT INTO `lot`(`owner_id`, `auction_type`, `min_price`, `current_price`, `max_price`, `bid_step`, `duration_time`, `name`, `description`)
VALUES(
	3, -- user lolwo
    3, -- auction-lottery type
	100, -- fixed price
    100,
    100,
    20, -- maximum 5 partiсipants
    3600, -- one hour
    'Bonus code for tank "Type59"',
    'You сan win and type this code on https://ru.wargaming.net/shop/bonus/'
);

-- lot approved by administrator
UPDATE `lot` SET `status` = 1 WHERE `id` = 1; 

-- lot published by it's owner
UPDATE `lot` SET `status` = 2 WHERE `id` = 1; 

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    1, 
    5, -- user appleWow
    20 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- Imitation of delay bidween bids
SELECT sleep(5);

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    1, 
    7, -- user Nuclear
    20 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- Imitation of delay bidween bids
SELECT sleep(6);

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    1, 
    6, -- user NearYou
    20 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- Imitation of delay bidween bids
SELECT sleep(7);

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    1, 
    2, -- user Neskwi
    20 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- Imitation of delay bidween bids
SELECT sleep(8);

-- !user can make more than one bid to increase his chance to win
INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    1, 
    5, -- user appleWow 
    20 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- lot closed automatically after declared amount of bids (= lot.max_price/lot.bid_step)
-- 
-- ALSO lot.leader_bid_id may be updated to NULL if auction time will be expired
-- UPDATE `lot` SET `status` = -1 WHERE `id` = 1; 
UPDATE `lot` SET `status` = 3 WHERE `id` = 1; 

-- setting lot.last_bid_id (which determines winning bid) to bid seleced randomly from all bids 
-- (random generation in subquery just to show mechanism)
--
-- ALSO lot.leader_bid_id may be updated to NULL if auction time will be expired
-- UPDATE `lot` SET `last_bid_id` = NULL WHERE `id` = 1;  
UPDATE `lot` 
	SET `leader_bid_id` = 
		(SELECT `id` FROM `bid` 
			WHERE `bid`.`lot_id` = 1
            ORDER BY rand() 
            LIMIT 1)  -- I know this is slow
	WHERE `id` = 1; 

