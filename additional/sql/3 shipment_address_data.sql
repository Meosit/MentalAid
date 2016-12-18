USE `bidbuy`;
SET NAMES 'utf8';

-- admin (Mikhail Snitavets, mksn13@gmail.com, Bogdanovicha 60-3, Minsk, Minskaya, 220123 Belarus, +375292312121)
INSERT INTO `shipment_address` (`user_id`, `address`, `first_name`, `last_name`, `phone_number`)
VALUES (
  1,
  'Bogdanovicha 60-3, Minsk, Minskaya, 220123, Belarus',
  'Mikhail',
  'Snitavets',
  '+375292312121'
);

-- Владислав "Neskwi" Канаев (Россия)
INSERT INTO `shipment_address` (`user_id`, `address`, `first_name`, `last_name`, `phone_number`)
VALUES (
  (SELECT `id`
   FROM `user`
   WHERE `username` = 'Neskwi'),
  'Bogdanovicha 60-3, City, Region, 220123, Russia',
  'Владислав',
  'Канаев',
  '+375292312121'
);

-- Андрей "lolwo" Денисенко (Беларусь)
INSERT INTO `shipment_address` (`user_id`, `address`, `first_name`, `last_name`, `phone_number`)
VALUES (
  (SELECT `id`
   FROM `user`
   WHERE `username` = 'lolwo'),
  'Bogdanovicha 60-3, City, Region, 220123, Belarus',
  'Андрей',
  'Денисенко',
  '+375292312121'
);

-- Владимир "DYADOR" Друцкий (Украина)
INSERT INTO `shipment_address` (`user_id`, `address`, `first_name`, `last_name`, `phone_number`)
VALUES (
  (SELECT `id`
   FROM `user`
   WHERE `username` = 'DYADOR'),
  'Bogdanovicha 60-3, City, Region, 220123, Ukraine',
  'Владимир',
  'Друцкий',
  '+375292312121'
);

-- Евгений "Grifon" Войтенко (Украина)
INSERT INTO `shipment_address` (`user_id`, `address`, `first_name`, `last_name`, `phone_number`)
VALUES (
  (SELECT `id`
   FROM `user`
   WHERE `username` = 'Grifon'),
  'Bogdanovicha 60-3, City, Region, 220123, Ukraine',
  'Евгений',
  'Войтенко',
  '+375292312121'
);

-- Юрий "applewow" Ильин (Россия)
INSERT INTO `shipment_address` (`user_id`, `address`, `first_name`, `last_name`, `phone_number`)
VALUES (
  (SELECT `id`
   FROM `user`
   WHERE `username` = 'appleWow'),
  'Bogdanovicha 60-3, City, Region, 220123, Russia',
  'Юрий',
  'Ильин',
  '+375292312121'
);

-- Алексей "NearYou" Кучкин (Беларусь)
INSERT INTO `shipment_address` (`user_id`, `address`, `first_name`, `last_name`, `phone_number`)
VALUES (
  (SELECT `id`
   FROM `user`
   WHERE `username` = 'NearYou'),
  'Bogdanovicha 60-3, City, Region, 220123, Belarus',
  'Алексей',
  'Кучкин',
  '+375292312121'
);

-- Алексей "Nuclear" Морозов (Россия)
INSERT INTO `shipment_address` (`user_id`, `address`, `first_name`, `last_name`, `phone_number`)
VALUES (
  (SELECT `id`
   FROM `user`
   WHERE `username` = 'Nuclear'),
  'Bogdanovicha 60-3, City, Region, 220123, Russia',
  'Алексей',
  'Морозов',
  '+375292312121'
);

