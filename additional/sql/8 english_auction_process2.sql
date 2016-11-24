-- -----------------------------------------------------
-- Imitation of english auction process
-- -----------------------------------------------------
USE `bidbuy`;
SET NAMES 'utf8';

INSERT INTO `lot`(`owner_id`, `auction_type`, `min_price`, `current_price`, `max_price`, `bid_step`, `duration_time`, `name`, `description`)
VALUES(
	7, -- user Nuclear
    1, -- english auction type
	200, -- minimum price
    200, -- current price
    1000, -- maximum price
    50, -- increase of price
    86400, -- one day
    'Star Wars lightsaber',
    'It was used in the filming of Star Wars 7 '
);

-- lot approved by administrator
UPDATE `lot` SET `status` = 1 WHERE `id` = 5; 

-- lot published by it's owner
UPDATE `lot` SET `status` = 2 WHERE `id` = 5; 

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    5, 
    2, -- user Neskwi
    200 -- standard bid
);

-- Imitation of delay bidween bids
SELECT sleep(5);

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    5, 
    6, -- user NearYou
    250 -- standard bid
);

-- Imitation of delay bidween bids
SELECT sleep(6);

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    5, 
    5, -- user appleWow
    300 -- standard bid
);

-- Imitation of delay bidween bids
SELECT sleep(5);

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    5, 
    6, -- user NearYou
    350 -- standard bid
);

-- Imitation of delay bidween bids
SELECT sleep(6);

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    5, 
    5, -- user appleWow
    400 -- standard bid
);

-- Imitation of delay bidween bids
SELECT sleep(6);

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    5, 
    6, -- user NearYou
    450 -- standard bid
);


-- Imitation ofy bidween bids
SELECT sleep(6);

INSERT INTO `bid`(`lot_id`, `client_id`, `sum`) 
VALUES(
    5, 
    5, -- user appleWow
    1000 -- buyout bid
);

-- lot closed automatically if one of bid have sum equal to max_price
--
-- ALSO if auction time will be expired and leader_bid_id = NULL
-- UPDATE `lot` SET `status` = -1 WHERE `id` = 1; 
UPDATE `lot` SET `status` = 3 WHERE `id` = 5; 
