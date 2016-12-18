-- -----------------------------------------------------
-- Imitation of auction-lottery process
-- -----------------------------------------------------
USE `bidbuy`;
SET NAMES 'utf8';

INSERT INTO `lot` (`owner_id`, `auction_type`, `min_price`, `current_price`, `max_price`, `bid_step`, `duration_time`, `name`, `description`)
VALUES (
  2, -- user Neskwi
  1, -- auction-lottery type
  30, -- fixed price
  30,
  30,
  3, -- maximum 10 partiсipants
  43200, -- 12 hours
  'Zippo light',
  'Original zippo of release 1944'
);

-- lot approved by administrator
UPDATE `lot`
SET `status` = 1
WHERE `id` = 4;

-- lot published by it's owner
UPDATE `lot`
SET `status` = 2
WHERE `id` = 4;

INSERT INTO `bid` (`lot_id`, `client_id`, `sum`)
VALUES (
  4,
  3, -- user lolwo
  3 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- Imitation of delay bidween bids
SELECT sleep(5);

INSERT INTO `bid` (`lot_id`, `client_id`, `sum`)
VALUES (
  4,
  7, -- user Nuclear
  3 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- Imitation of delay bidween bids
SELECT sleep(6);

INSERT INTO `bid` (`lot_id`, `client_id`, `sum`)
VALUES (
  4,
  6, -- user NearYou
  3 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- Imitation of delay bidween bids
SELECT sleep(7);

INSERT INTO `bid` (`lot_id`, `client_id`, `sum`)
VALUES (
  4,
  3, -- user lolwo
  3 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- Imitation of delay bidween bids
SELECT sleep(8);

INSERT INTO `bid` (`lot_id`, `client_id`, `sum`)
VALUES (
  4,
  5, -- user appleWow
  3 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);


INSERT INTO `bid` (`lot_id`, `client_id`, `sum`)
VALUES (
  4,
  3, -- user lolwo
  3 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- Imitation of delay bidween bids
SELECT sleep(5);

INSERT INTO `bid` (`lot_id`, `client_id`, `sum`)
VALUES (
  4,
  7, -- user Nuclear
  3 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- Imitation of delay bidween bids
SELECT sleep(6);

INSERT INTO `bid` (`lot_id`, `client_id`, `sum`)
VALUES (
  4,
  6, -- user NearYou
  3 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- Imitation of delay bidween bids
SELECT sleep(7);

INSERT INTO `bid` (`lot_id`, `client_id`, `sum`)
VALUES (
  4,
  3, -- user lolwo
  3 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- Imitation of delay bidween bids
SELECT sleep(8);

INSERT INTO `bid` (`lot_id`, `client_id`, `sum`)
VALUES (
  4,
  5, -- user appleWow
  3 -- in auction-lottery this value must be equal to bid_step in appropriate lot
);

-- lot closed automatically after declared amount of bids (= lot.max_price/lot.bid_step)
-- 
-- ALSO lot.leader_bid_id may be updated to NULL if auction time will be expired
-- UPDATE `lot` SET `status` = -1 WHERE `id` = 1; 
UPDATE `lot`
SET `status` = 3
WHERE `id` = 4;

-- setting lot.last_bid_id (which determines winning bid) to bid seleced randomly from all bids 
-- (random generation in subquery just to show mechanism)
--
-- ALSO lot.leader_bid_id may be updated to NULL if auction time will be expired
-- UPDATE `lot` SET `last_bid_id` = NULL WHERE `id` = 1;  
UPDATE `lot`
SET `leader_bid_id` =
(SELECT `id`
 FROM `bid`
 WHERE `bid`.`lot_id` = 4
 ORDER BY rand()
 LIMIT 1) -- I know this is slow
WHERE `id` = 4;

