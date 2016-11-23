USE `bidbuy`;

-- admin@12345 (administrator)
INSERT INTO `user`(`id`, `username`, `pass_hash`, `role`) 
VALUES(1, 'admin', '$2a$12$6BskHBTZfhDGOcYLZNOCZui5a7apSqHBzL9zidWANGax9ShKtM1nu', 1);

-- Neskwi@54321 (user)
INSERT INTO `user`(`username`, `pass_hash`) 
VALUES('Neskwi', '$2a$12$v.i376fH5F8NAtzoxKh3WOGl/U5K0tJnnCQsttEl2fugrL4.h0WHK');

-- lolwo@qwerty (user)
INSERT INTO `user`(`username`, `pass_hash`) 
VALUES('lolwo', '$2a$12$MBfnsQujCtfMot8rBbSAWuhhKEKNqUP5/QJm4EvpRxp9kH6dSzDFC');

-- DYADOR@asdfg (user, banned)
INSERT INTO `user`(`username`, `pass_hash`, `status`) 
VALUES('DYADOR', '$2a$12$brZ/7vgV/tO2YDtisFuj4uFn1stf9fbHDsHTIY14ZBlHla7fgGn/a', 0);

-- appleWow@54321 (user)
INSERT INTO `user`(`username`, `pass_hash`) 
VALUES('appleWow', '$2a$12$v.i376fH5F8NAtzoxKh3WOGl/U5K0tJnnCQsttEl2fugrL4.h0WHK');

-- NearYou@zxcvb (user)
INSERT INTO `user`(`username`, `pass_hash`) 
VALUES('NearYou', '$2a$12$eH7ubmvMyFQVo2NmFdfWlOBiekWmn/VuFkBJ1iDavLhEBLdiaIjGm');

-- Nuclear@Keepo (user)
INSERT INTO `user`(`username`, `pass_hash`) 
VALUES('Nuclear', '$2a$12$wtFOk1nXdw4fcJtc6i/V9eLMjNYzKuzdq3.KKviZSk0m63/TCz5NK');

-- Grifon@Kappa (user, deleted)
INSERT INTO `user`(`username`, `pass_hash`, `status`) 
VALUES('Grifon', '$2a$12$hnMr9iCQcPwxpfnNfWD48Ol99I4KlfTG4qz354MskJoSV9L7Mtt0C', -1);