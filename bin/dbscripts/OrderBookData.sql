USE OrderBook;

ALTER TABLE Trade AUTO_INCREMENT = 1;
ALTER TABLE User AUTO_INCREMENT = 1;
ALTER TABLE OrderHistory AUTO_INCREMENT = 1;
ALTER TABLE `Order` AUTO_INCREMENT = 1;
ALTER TABLE Stock AUTO_INCREMENT = 1;
ALTER TABLE Party AUTO_INCREMENT = 1;

INSERT INTO Party (name, symbol) VALUES
    ('London Clearing House', 'LCH'),
    ('Lloyds Bank', 'LLY'),
    ('Royal Bank Scotland', 'RBS'),
    ('JP Morgan Chase', 'JPM'),
    ('Deutsche Bank', 'DBK');

INSERT INTO User (username, deleted) VALUES 
    ('TomB', false),
    ('BillyS', false),
    ('TomaszM', false);

INSERT INTO Stock (companyName, stockSymbol, stockExchange, centralPartyId, tickSize) VALUES
    ('Royal Dutch Shell', 'RDS-A', 'LSE', 1, 0.01);

INSERT INTO `Order` (partyId, stockId, orderStatus, side) VALUES
    (2, 1, 3, true),
    (3, 1, 3, false),
    (4, 1, 3, true),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, false),
    (4, 1, 3, true),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, false),
    (4, 1, 3, true),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, false),
    (4, 1, 3, true),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, false),
    (4, 1, 3, true),
    (4, 1, 3, true),
    (3, 1, 3, false),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, true),
    (4, 1, 3, false),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, true),
    (4, 1, 3, false),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, false),
    (4, 1, 3, true),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, false),
    (4, 1, 3, true),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, false),
    (4, 1, 3, true),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, false),
    (4, 1, 3, true),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, false),
    (4, 1, 3, true),
    (4, 1, 3, true),
    (3, 1, 3, false),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, true),
    (4, 1, 3, false),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, true),
    (4, 1, 3, false),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, false),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, true),
    (4, 1, 3, false),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, true),
    (3, 1, 3, true),
    (4, 1, 3, false),
    (4, 1, 3, false),
    (3, 1, 3, true),
    (2, 1, 3, false),
    (2, 1, 3, false);

INSERT INTO OrderHistory (orderId, price, currentSize, userId, timestamp) VALUES
    (1, 1236.12, 100, 1, '2017-01-01T14:55:27'),
    (2, 1234.13, 100, 1, '2017-01-02T14:55:28'),
    (3, 1234.14, 100, 1, '2017-02-03T15:55:29'),
    (4, 1234.15, 100, 1, '2017-02-04T12:55:30'),
    (5, 1234.16, 100, 1, '2017-03-05T11:55:31'),
    (6, 1234.17, 150, 1, '2017-03-06T10:55:34'),
    (7, 1234.18, 150, 1, '2017-04-07T11:55:56'),
    (8, 1234.19, 150, 1, '2017-04-08T12:55:28'),
    (9, 1234.20, 150, 1, '2017-05-09T11:55:27'),
    (10, 1234.21, 200, 1, '2017-05-10T13:55:28'),
    (11, 1234.12, 400, 1, '2017-06-11T14:55:27'),
    (12, 1234.13, 100, 1, '2017-06-12T15:55:28'),
    (13, 1234.14, 200, 1, '2017-07-13T16:55:27'),
    (14, 1234.15, 200, 1, '2017-07-14T11:55:28'),
    (15, 1239.16, 200, 1, '2017-08-15T11:55:27'),
    (16, 1234.17, 200, 1, '2017-08-16T12:55:28'),
    (17, 1234.18, 200, 1, '2017-09-17T10:55:27'),
    (18, 1234.19, 200, 1, '2017-09-18T10:55:28'),
    (19, 1234.20, 300, 1, '2017-10-19T11:55:27'),
    (20, 1234.21, 150, 1, '2017-10-20T12:55:28'),
    (21, 1234.12, 100, 1, '2017-11-21T11:55:27'),
    (22, 1265.13, 100, 1, '2017-11-22T12:55:28'),
    (23, 1234.14, 250, 1, '2017-12-23T11:55:27'),
    (24, 1234.15, 250, 1, '2017-12-24T12:55:28'),
    (25, 1234.16, 150, 1, '2017-12-27T11:55:27'),
    (26, 1276.17, 100, 1, '2018-01-01T11:55:28'),
    (27, 1274.18, 150, 1, '2018-01-02T11:55:27'),
    (28, 1334.19, 150, 1, '2018-02-03T11:55:28'),
    (29, 1234.20, 150, 1, '2018-02-04T11:55:27'),
    (30, 1234.21, 150, 1, '2018-03-05T11:55:28'),
    (31, 1234.12, 200, 1, '2018-03-06T11:55:27'),
    (32, 1234.13, 200, 1, '2018-04-07T11:55:28'),
    (33, 1234.14, 200, 1, '2018-04-08T11:55:27'),
    (34, 1254.15, 200, 1, '2018-05-09T12:55:28'),
    (35, 1234.16, 200, 1, '2018-05-10T12:55:27'),
    (36, 1234.17, 300, 1, '2018-06-11T12:55:28'),
    (37, 1234.18, 400, 1, '2018-06-12T13:55:27'),
    (38, 1234.19, 500, 1, '2018-07-13T13:55:28'),
    (39, 1234.20, 600, 1, '2018-07-14T13:55:27'),
    (40, 1234.21, 100, 1, '2018-08-15T13:55:28'),
    (41, 1234.12, 200, 1, '2018-08-16T14:55:27'),
    (42, 1234.13, 300, 1, '2018-09-17T14:55:28'),
    (43, 1234.14, 400, 1, '2018-09-18T14:55:27'),
    (44, 1234.15, 500, 1, '2018-10-19T14:55:28'),
    (45, 1244.16, 600, 1, '2018-10-20T15:55:27'),
    (46, 1234.17, 100, 1, '2018-11-21T15:55:28'),
    (47, 1234.18, 100, 1, '2018-11-22T15:55:27'),
    (48, 1234.19, 150, 1, '2018-12-23T15:55:28'),
    (49, 1234.20, 150, 1, '2018-12-25T15:55:27'),
    (50, 1234.21, 150, 1, '2018-12-27T16:55:28'),
    (51, 1234.12, 100, 1, '2019-01-01T16:55:27'),
    (52, 1234.13, 100, 1, '2019-01-02T16:55:28'),
    (53, 1234.14, 100, 1, '2019-02-03T16:55:27'),
    (54, 1234.15, 100, 1, '2019-02-04T16:55:28'),
    (55, 1234.16, 100, 1, '2019-03-05T16:55:27'),
    (56, 1234.17, 300, 1, '2019-03-06T15:55:28'),
    (57, 1234.33, 100, 1, '2019-04-07T14:55:27'),
    (58, 1234.19, 150, 1, '2019-04-08T13:55:28'),
    (59, 1234.70, 150, 1, '2019-05-09T12:55:27'),
    (60, 1234.21, 100, 1, '2019-05-10T16:55:28'),
    (61, 1234.12, 200, 1, '2019-06-11T15:55:27'),
    (62, 1234.13, 300, 1, '2019-06-12T14:55:28'),
    (63, 1234.14, 300, 1, '2019-07-13T13:55:27'),
    (64, 1234.15, 300, 1, '2019-07-14T12:55:28'),
    (65, 1234.16, 200, 1, '2019-08-15T11:55:27'),
    (66, 1234.17, 100, 1, '2019-08-16T11:55:28'),
    (67, 1234.98, 200, 1, '2019-09-17T11:55:27'),
    (68, 1234.19, 300, 1, '2019-09-18T11:55:28'),
    (69, 1234.20, 150, 1, '2019-10-19T11:55:27'),
    (70, 1234.21, 150, 1, '2019-10-20T11:55:28'),
    (71, 1234.12, 150, 1, '2019-11-21T10:55:27'),
    (72, 1234.13, 150, 1, '2019-11-22T10:55:28'),
    (73, 1234.14, 150, 1, '2019-12-23T10:55:27'),
    (74, 1234.15, 250, 1, '2019-12-24T10:55:28'),
    (75, 1234.16, 250, 1, '2019-12-27T09:55:27'),
    (76, 1235.89, 100, 1, '2020-01-01T09:55:28'),
    (77, 1236.18, 100, 1, '2020-01-02T09:55:27'),
    (78, 1237.19, 100, 1, '2020-02-03T14:55:28'),
    (79, 1231.65, 100, 1, '2020-02-04T13:55:27'),
    (80, 1232.20, 100, 1, '2020-03-05T16:55:27'),
    (81, 1233.12, 100, 1, '2020-03-06T16:55:27'),
    (82, 1234.13, 250, 1, '2020-04-07T15:55:28'),
    (83, 1235.14, 350, 1, '2020-04-08T14:55:27'),
    (84, 1236.15, 50, 1, '2020-04-09T13:55:28'),
    (85, 1237.16, 100, 1, '2020-05-10T09:55:27'),
    (86, 1238.17, 100, 1, '2020-05-11T09:55:28'),
    (87, 1239.22, 100, 1, '2020-06-12T09:55:27'),
    (88, 1230.19, 150, 1, '2020-06-13T09:55:28'),
    (89, 1231.26, 150, 1, '2020-07-14T09:55:27'),
    (90, 1234.20, 50, 1, '2020-07-15T09:55:27'),
    (91, 1234.12, 150, 1, '2020-07-16T09:55:27'),
    (92, 1234.53, 250, 1, '2020-07-17T09:55:28'),
    (93, 1234.14, 250, 1, '2020-07-18T09:55:27'),
    (94, 1234.15, 100, 1, '2020-07-19T09:55:28'),
    (95, 1234.96, 200, 1, '2020-07-20T09:55:27'),
    (96, 1234.17, 300, 1, '2020-07-21T09:55:28'),
    (97, 1234.18, 400, 1, '2020-07-22T09:55:27'),
    (98, 1234.49, 300, 1, '2020-07-23T09:55:28'),
    (99, 1234.50, 200, 1, '2020-07-24T09:55:27'),
    (100, 1234.60, 100, 1, '2020-07-27T09:55:27');