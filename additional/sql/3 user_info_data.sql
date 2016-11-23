USE `bidbuy`;
SET NAMES 'utf8';

-- admin (Mikhail Snitavets, mksn13@gmail.com, Bogdanovicha 60-3, Minsk, Belarus, +375292312121)
INSERT INTO `user_info`(`user_id`, `address`, `first_name`, `last_name`, `phone_number`, `email`) 
VALUES(1, 'Bogdanovicha 60-3, Minsk, Belarus', 'Mikhail', 'Snitavets', '+375292312121', 'mksn13@gmail.com');

-- Владислав "Neskwi" Канаев (Россия)
INSERT INTO `user_info`(`user_id`, `address`, `first_name`, `last_name`) 
VALUES( 
	(SELECT `id` FROM `user` WHERE `username` = 'Neskwi'), 
    'Russia', 
    'Владислав', 
    'Канаев'
);

-- Андрей "lolwo" Денисенко (Беларусь)
INSERT INTO `user_info`(`user_id`, `address`, `first_name`, `last_name`) 
VALUES( 
	(SELECT `id` FROM `user` WHERE `username` = 'lolwo'), 
    'Belarus', 
    'Андрей', 
    'Денисенко'
);

-- Владимир "DYADOR" Друцкий (Украина)
INSERT INTO `user_info`(`user_id`, `address`, `first_name`, `last_name`) 
VALUES( 
	(SELECT `id` FROM `user` WHERE `username` = 'DYADOR'), 
    'Ukraine', 
    'Владимир', 
    'Друцкий'
);

-- Евгений "Grifon" Войтенко (Украина)
INSERT INTO `user_info`(`user_id`, `address`, `first_name`, `last_name`) 
VALUES( 
	(SELECT `id` FROM `user` WHERE `username` = 'Grifon'), 
    'Ukraine', 
    'Евгений', 
    'Войтенко'
);

-- Юрий "applewow" Ильин (Россия)
INSERT INTO `user_info`(`user_id`, `address`, `first_name`, `last_name`) 
VALUES( 
	(SELECT `id` FROM `user` WHERE `username` = 'appleWow'), 
    'Russia', 
    'Юрий', 
    'Ильин'
);

-- Алексей "NearYou" Кучкин (Беларусь)
INSERT INTO `user_info`(`user_id`, `address`, `first_name`, `last_name`) 
VALUES( 
	(SELECT `id` FROM `user` WHERE `username` = 'NearYou'), 
    'Belarus', 
    'Алексей', 
    'Кучкин'
);

-- Алексей "Nuclear" Морозов (Россия)
INSERT INTO `user_info`(`user_id`, `address`, `first_name`, `last_name`) 
VALUES( 
	(SELECT `id` FROM `user` WHERE `username` = 'Nuclear'), 
    'Russia', 
    'Алексей', 
    'Морозов'
);

