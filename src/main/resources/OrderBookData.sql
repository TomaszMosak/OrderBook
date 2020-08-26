USE OrderBook;

INSERT INTO Party (name, symbol) VALUES
    ("London Clearing House", "LCH"),
    ("Lloyds Bank", "LLY"),
    ("Royal Bank Scotland", "RBS"),
    ("JP Morgan Chase", "JPM"),
    ("Deutsche Bank", "DBK")

INSERT INTO User (username, deleted) VALUES 
    ("TomB", false),
    ("BillyS", false),
    ("TomaszM", false)

INSERT INTO Stock (companyName, stockSymbol, stockExchange, centralPartyId, tickSize) VALUES
    ("Royal Dutch Shell", "RDS-A", "LSE", 1, 0.01)

INSERT INTO `Order` (partyId, stockId, orderStatus, side) VALUES 
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, false),
    (4, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false),
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, false),
    (4, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false),
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, false),
    (4, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false,
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, false,
    (4, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false),
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, false),
    (4, 1, ACTIVE, true),
    (4, 1, ACTIVE, true),
    (3, 1, ACTIVE, false),
    (2, 1, ACTIVE, false),
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false,
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false,
    (2, 1, ACTIVE, false),
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, false),
    (4, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false),
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, false),
    (4, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false),
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, false),
    (4, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false,
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, false,
    (4, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false),
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, false),
    (4, 1, ACTIVE, true),
    (4, 1, ACTIVE, true),
    (3, 1, ACTIVE, false),
    (2, 1, ACTIVE, false),
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false,
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false,
    (2, 1, ACTIVE, false),
    (2, 1, ACTIVE, false),
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false,
    (2, 1, ACTIVE, true),
    (3, 1, ACTIVE, true),
    (4, 1, ACTIVE, false),
    (4, 1, ACTIVE, false),
    (3, 1, ACTIVE, true),
    (2, 1, ACTIVE, false,
    (2, 1, ACTIVE, false)

INSERT INTO OrderHistory (orderId, price, currentSize, userId, timestamp) VALUES
    (1, 1236.12, 101, 1, 2017-01-01 14:55:27),
    (2, 1234.13, 102, 1, 2017-01-02 14:55:28),
    (3, 1234.14, 103, 1, 2017-02-03 15:55:29),
    (4, 1234.15, 104, 1, 2017-02-04 12:55:30),
    (5, 1234.16, 105, 1, 2017-03-05 11:55:31),
    (6, 1234.17, 111, 1, 2017-03-06 10:55:34),
    (7, 1234.18, 112, 1, 2017-04-07 11:55:56),
    (8, 1234.19, 113, 1, 2017-04-08 12:55:28),
    (9, 1234.20, 150, 1, 2017-05-09 11:55:27),
    (10, 1234.21, 200, 1, 2017-05-10 13:55:28),
    (11, 1234.12, 400, 1, 2017-06-11 14:55:27),
    (12, 1234.13, 1000, 1, 2017-06-12 15:55:28),
    (13, 1234.14, 101, 1, 2017-07-13 16:55:27),
    (14, 1234.15, 102, 1, 2017-07-14 11:55:28),
    (15, 1239.16, 103, 1, 2017-08-15 11:55:27),
    (16, 1234.17, 104, 1, 2017-08-16 12:55:28),
    (17, 1234.18, 105, 1, 2017-09-17 10:55:27),
    (18, 1234.19, 106, 1, 2017-09-18 10:55:28),
    (19, 1234.20, 107, 1, 2017-10-19 11:55:27),
    (20, 1234.21, 108, 1, 2017-10-20 12:55:28),
    (21, 1234.12, 109, 1, 2017-11-21 11:55:27),
    (22, 1265.13, 100, 1, 2017-11-22 12:55:28),
    (23, 1234.14, 250, 1, 2017-12-23 11:55:27),
    (24, 1234.15, 225, 1, 2017-12-24 12:55:28),
    (25, 1234.16, 123, 1, 2017-12-27 11:55:27),

    (26, 1276.17, 110, 1, 2018-01-01 11:55:28),
    (27, 1274.18, 120, 1, 2018-01-02 11:55:27),
    (28, 1334.19, 130, 1, 2018-02-03 11:55:28),
    (29, 1234.20, 140, 1, 2018-02-04 11:55:27),
    (30, 1234.21, 150, 1, 2018-03-05 11:55:28),
    (31, 1234.12, 160, 1, 2018-03-06 11:55:27),
    (32, 1234.13, 170, 1, 2018-04-07 11:55:28),
    (33, 1234.14, 180, 1, 2018-04-08 11:55:27),
    (34, 1254.15, 190, 1, 2018-05-09 12:55:28),
    (35, 1234.16, 200, 1, 2018-05-10 12:55:27),
    (36, 1234.17, 300, 1, 2018-06-11 12:55:28),
    (37, 1234.18, 400, 1, 2018-06-12 13:55:27),
    (38, 1234.19, 500, 1, 2018-07-13 13:55:28),
    (39, 1234.20, 600, 1, 2018-07-14 13:55:27),
    (40, 1234.21, 100, 1, 2018-08-15 13:55:28),
    (41, 1234.12, 200, 1, 2018-08-16 14:55:27),
    (42, 1234.13, 300, 1, 2018-09-17 14:55:28),
    (43, 1234.14, 400, 1, 2018-09-18 14:55:27),
    (44, 1234.15, 500, 1, 2018-10-19 14:55:28),
    (45, 1244.16, 600, 1, 2018-10-20 15:55:27),
    (46, 1234.17, 1100, 1, 2018-11-21 15:55:28),
    (47, 1234.18, 1020, 1, 2018-11-22 15:55:27),
    (48, 1234.19, 150, 1, 2018-12-23 15:55:28),
    (49, 1234.20, 160, 1, 2018-12-25 15:55:27),
    (50, 1234.21, 170, 1, 2018-12-27 16:55:28),

    (51, 1234.12, 101, 1, 2019-01-01 16:55:27),
    (52, 1234.13, 102, 1, 2019-01-02 16:55:28),
    (53, 1234.14, 103, 1, 2019-02-03 16:55:27),
    (54, 1234.15, 104, 1, 2019-02-04 16:55:28),
    (55, 1234.16, 105, 1, 2019-03-05 16:55:27),
    (56, 1234.17, 106, 1, 2019-03-06 15:55:28),
    (57, 1234.33, 108, 1, 2019-04-07 14:55:27),
    (58, 1234.19, 109, 1, 2019-04-08 13:55:28),
    (59, 1234.70, 110, 1, 2019-05-09 12:55:27),
    (60, 1234.21, 111, 1, 2019-05-10 16:55:28),
    (61, 1234.12, 200, 1, 2019-06-11 15:55:27),
    (62, 1234.13, 300, 1, 2019-06-12 14:55:28),
    (63, 1234.14, 400, 1, 2019-07-13 13:55:27),
    (64, 1234.15, 500, 1, 2019-07-14 12:55:28),
    (65, 1234.16, 600, 1, 2019-08-15 11:55:27),
    (66, 1234.17, 700, 1, 2019-08-16 11:55:28),
    (67, 1234.98, 800, 1, 2019-09-17 11:55:27),
    (68, 1234.19, 900, 1, 2019-09-18 11:55:28),
    (69, 1234.20, 110, 1, 2019-10-19 11:55:27),
    (70, 1234.21, 120, 1, 2019-10-20 11:55:28),
    (71, 1234.12, 130, 1, 2019-11-21 10:55:27),
    (72, 1234.13, 140, 1, 2019-11-22 10:55:28),
    (73, 1234.14, 150, 1, 2019-12-23 10:55:27),
    (74, 1234.15, 160, 1, 2019-12-24 10:55:28),
    (75, 1234.16, 170, 1, 2019-12-27 09:55:27),

    (76, 1235.89, 101, 1, 2020-01-01 09:55:28),
    (77, 1236.18, 102, 1, 2020-01-02 09:55:27),
    (78, 1237.19, 103, 1, 2020-02-03 14:55:28),
    (79, 1231.65, 104, 1, 2020-02-04 13:55:27),
    (80, 1232.20, 105, 1, 2020-03-05 16:55:27),
    (81, 1233.12, 106, 1, 2020-03-06 16:55:27),
    (82, 1234.13, 107, 1, 2020-04-07 15:55:28),
    (83, 1235.14, 108, 1, 2020-04-08 14:55:27),
    (84, 1236.15, 109, 1, 2020-04-09 13:55:28),
    (85, 1237.16, 110, 1, 2020-05-10 09:55:27),
    (86, 1238.17, 120, 1, 2020-05-11 09:55:28),
    (87, 1239.22, 130, 1, 2020-06-12 09:55:27),
    (88, 1230.19, 140, 1, 2020-06-13 09:55:28),
    (89, 1231.26, 150, 1, 2020-07-14 09:55:27),
    (90, 1234.20, 160, 1, 2020-07-15 09:55:27),
    (91, 1234.12, 170, 1, 2020-08-16 09:55:27),
    (92, 1234.53, 180, 1, 2020-08-17 09:55:28),
    (93, 1234.14, 190, 1, 2020-09-18 09:55:27),
    (94, 1234.15, 100, 1, 2020-09-19 09:55:28),
    (95, 1234.96, 200, 1, 2020-10-20 09:55:27),
    (96, 1234.17, 300, 1, 2020-10-21 09:55:28),
    (97, 1234.18, 400, 1, 2020-11-22 09:55:27),
    (98, 1234.49, 500, 1, 2020-11-23 09:55:28),
    (99, 1234.50, 600, 1, 2020-12-24 09:55:27),
    (100, 1234.60, 1000, 1, 2020-12-27 09:55:27)