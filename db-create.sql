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
VALUES (2,
        '1987 year. Jordan Belfort becomes a broker for a successful investment bank. The bank will close shortly after the sudden collapse of the Dow Jones. On the advice of his wife Teresa, Jordan gets a job in a small institution that deals with small shares. His persistent style of communication with clients and innate charisma quickly pays off. He meets Donnie''s housemate, a merchant, who immediately finds a common language with Jordan and decides to open his own company with him. They hire several of Belfort''s friends and his father Max as employees and call the company Stratton Oakmont. In his spare time, Jordan spends his life: maneuvering from one party to another, having sexual relations with prostitutes, using many drugs, including cocaine and quaalud. One day the moment comes when an FBI agent becomes interested in Belfort''s get-rich-quick',
        '1987 год. Джордан Белфорт становится брокером в успешном инвестиционном банке. Вскоре банк закрывается после внезапного обвала индекса Доу-Джонса. По совету жены Терезы Джордан устраивается в небольшое заведение, занимающееся мелкими акциями. Его настойчивый стиль общения с клиентами и врождённая харизма быстро даёт свои плоды. Он знакомится с соседом по дому Донни, торговцем, который сразу находит общий язык с Джорданом и решает открыть с ним собственную фирму. В качестве сотрудников они нанимают нескольких друзей Белфорта, его отца Макса и называют компанию «Стрэттон Оукмонт». В свободное от работы время Джордан прожигает жизнь: лавирует от одной вечеринки к другой, вступает в сексуальные отношения с проститутками, употребляет множество наркотических препаратов, в том числе кокаин и кваалюд. Однажды наступает момент, когда быстрым обогащением Белфорта начинает интересоваться агент ФБР');
INSERT INTO dictionary
VALUES (3, 'Avengers', 'Мстители');
INSERT INTO dictionary
VALUES (4,
        'Loki, Thor''s half-brother, returns, and this time he is not alone. The earth is on the brink of enslavement, and only the best of the best can save humanity. Head of the international organization SHIELD T. Nick Fury gathers outstanding champions of justice and goodness to repel the attack. Led by Captain America, Iron Man, Thor, The Incredible Hulk, Hawkeye and Black Widow go to war with the invader.',
        'Локи, сводный брат Тора, возвращается, и в этот раз он не один. Земля оказывается на грани порабощения, и только лучшие из лучших могут спасти человечество. Глава международной организации Щ.И.Т. Ник Фьюри собирает выдающихся поборников справедливости и добра, чтобы отразить атаку. Под предводительством Капитана Америки Железный Человек, Тор, Невероятный Халк, Соколиный Глаз и Чёрная Вдова вступают в войну с захватчиком.');
INSERT INTO dictionary
VALUES (5, 'Hall 1', 'Кинозал 1');
INSERT INTO dictionary
VALUES (6, 'In chaise of happiness', 'В погоне за счастьем');
INSERT INTO dictionary
VALUES (7,
        'Chris Gardner is a single father. While raising a five-year-old son, Chris tries his best to make the child grow up happy. Working as a seller, he cannot pay for the apartment, and they are evicted.
Finding himself on the street, but not wanting to give up, my father gets a job as an intern in a brokerage company, hoping to get a specialist position. Only during the internship he will not receive any money, and the internship lasts 6 months',
        'Крис Гарднер – отец-одиночка. Воспитывая пятилетнего сына, Крис изо всех сил старается сделать так, чтобы ребенок рос счастливым. Работая продавцом, он не может оплатить квартиру, и их выселяют.
Оказавшись на улице, но не желая сдаваться, отец устраивается стажером в брокерскую компанию, рассчитывая получить должность специалиста. Только на протяжении стажировки он не будет получать никаких денег, а стажировка длится 6 месяцев');



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
INSERT INTO films
VALUES (3, 6, 7,
        '/home/user/Desktop/FinalTAsk/SummaryTaskEPAM/src/resources/filmPhotos/5 2974681_ru_a0a157523602a5ac4bf91a9e0f941050.jpg');



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
INSERT INTO schedule
VALUES (8, 3, 1, '2020-10-31 18:00:00');
INSERT INTO schedule
VALUES (9, 3, 1, '2020-10-31 22:00:00');
INSERT INTO schedule
VALUES (10, 3, 1, '2020-10-31 19:00:00');
INSERT INTO schedule
VALUES (11, 3, 1, '2020-11-01 11:00:00');



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
