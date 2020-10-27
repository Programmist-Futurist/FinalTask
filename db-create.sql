# Colors ENUM('RED','GREEN','BLUE'),
SET NAMES utf8;

DROP DATABASE IF EXISTS final_project_db;

CREATE DATABASE final_project_db CHARACTER SET utf8 COLLATE utf8_bin;

USE final_project_db;

# tables creating
CREATE TABLE dictionary
(
    id  INT primary key AUTO_INCREMENT,
    eng VARCHAR(1000) NOT NULL,
    rus VARCHAR(1000) NOT NULL
);



CREATE TABLE users
(
    id       INT primary key AUTO_INCREMENT,
    name     VARCHAR(225),
    login    VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    role     ENUM ('ADMIN','USER') NOT NULL,
    email    VARCHAR(50),
    phone    VARCHAR(20),
    logged   BOOLEAN               NOT NULL DEFAULT FALSE,
    language ENUM ('RU','ENG')     NOT NULL
);


CREATE TABLE films
(
    id            INT primary key AUTO_INCREMENT,
    nameId        INT NOT NULL,
    descriptionId INT NOT NULL,
    image         VARCHAR(200),
    UNIQUE (nameId, descriptionId)
);



CREATE TABLE halls
(
    id           INT primary key AUTO_INCREMENT,
    nameId       INT UNIQUE NOT NULL,
    placesAmount INT        NOT NULL
);


CREATE TABLE schedule
(
    id        INT primary key AUTO_INCREMENT,
    filmId    INT      NOT NULL,
    hallId    INT      NOT NULL,
    timeStart DATETIME NOT NULL,
    UNIQUE (filmId, hallId, timeStart)
);



CREATE TABLE reservations
(
    id          INT primary key AUTO_INCREMENT,
    scheduleId  INT NOT NULL,
    userId      INT NOT NULL,
    placeNumber INT NOT NULL,
    UNIQUE (scheduleId, placeNumber)
);


# relations config
ALTER TABLE reservations
    ADD CONSTRAINT FOREIGN KEY (scheduleId)
        REFERENCES schedule (id) ON DELETE CASCADE ON UPDATE RESTRICT;
ALTER TABLE reservations
    ADD CONSTRAINT FOREIGN KEY (userId)
        REFERENCES users (id) ON DELETE CASCADE ON UPDATE RESTRICT;


ALTER TABLE schedule
    ADD CONSTRAINT FOREIGN KEY (filmId)
        REFERENCES films (id) ON DELETE CASCADE ON UPDATE RESTRICT;
ALTER TABLE schedule
    ADD CONSTRAINT FOREIGN KEY (hallId)
        REFERENCES halls (id) ON DELETE CASCADE ON UPDATE RESTRICT;


ALTER TABLE films
    ADD CONSTRAINT FOREIGN KEY (nameId)
        REFERENCES dictionary (id) ON DELETE CASCADE ON UPDATE RESTRICT;
ALTER TABLE films
    ADD CONSTRAINT FOREIGN KEY (descriptionId)
        REFERENCES dictionary (id) ON DELETE CASCADE ON UPDATE RESTRICT;


ALTER TABLE halls
    ADD CONSTRAINT FOREIGN KEY (nameId)
        REFERENCES dictionary (id) ON DELETE CASCADE ON UPDATE RESTRICT;


# creating entities
INSERT INTO dictionary
VALUES (1, 'Wolf from Wall street', 'Волк с Уолл стрит');
INSERT INTO dictionary
VALUES (2, 'Film about a guy who become very successful in not leagal way. But his life was really delightful',
        'Фильм показывает невероятную жизнь парня (полную измен, путешествий, наркотиков, адреналина и тестостерана), который открыл свою компанию с нуля.');
INSERT INTO dictionary
VALUES (3, 'Avengers', 'Avengers');
INSERT INTO dictionary
VALUES (4, 'Film about heroes from Earth, who protect our planet from different catastrophes',
        'Фильм повествует о героях Земли, которые защищают человечество от разного рода опасностей');
INSERT INTO dictionary
VALUES (5, 'Hall 1', 'Кинозал 1');



INSERT INTO users
VALUES (default, 'Kirill', 'login1@gmail.com', 'password1',
        'ADMIN', 'kirilllevchenko33@gmail.com', '+38 095 48 23 727',
        false, 'ENG');
INSERT INTO users
VALUES (default, 'Diablo', 'login2@gmail.com', 'password2',
        'USER', 'diablo@gmail.com', '+38 (066) 576 43 12',
        false, 'ENG');
INSERT INTO users
VALUES (default, 'Antonio', 'login3@gmail.com', 'password3',
        'USER', 'login3@gmail.com', '380971234343',
        false, 'ENG');



INSERT INTO films
VALUES (1, 1, 2,
        '/home/user/Desktop/FinalTAsk/SummaryTaskEPAM/src/resources/filmPhotos/Постер_фильма_«Волк_с_Уолл-стрит».jpg');
INSERT INTO films
VALUES (2, 3, 4, '/home/user/Desktop/FinalTAsk/SummaryTaskEPAM/src/resources/filmPhotos/1388943046_1.jpg');



INSERT INTO halls
VALUES (1, 5, 100);


INSERT INTO schedule
VALUES (1, 1, 1, '2020-10-29 09:00:00');
INSERT INTO schedule
VALUES (2, 2, 1, '2020-10-29 12:00:00');
INSERT INTO schedule
VALUES (3, 1, 1, '2020-10-29 17:00:00');
INSERT INTO schedule
VALUES (4, 2, 1, '2020-10-29 20:00:00');
INSERT INTO schedule
VALUES (5, 1, 1, '2020-10-29 10:00:00');
INSERT INTO schedule
VALUES (6, 2, 1, '2020-10-30 18:00:00');
INSERT INTO schedule
VALUES (7, 1, 1, '2020-10-30 21:00:00');



INSERT INTO reservations
VALUES (1, 1, 2, 24);
INSERT INTO reservations
VALUES (2, 1, 3, 37);
INSERT INTO reservations
VALUES (3, 4, 2, 15);
INSERT INTO reservations
VALUES (4, 5, 3, 43);
INSERT INTO reservations
VALUES (5, 5, 3, 44);
