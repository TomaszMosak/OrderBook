DROP DATABASE IF EXISTS OrderBook;

CREATE DATABASE OrderBook;

USE OrderBook;

CREATE TABLE `Order` (
    id int AUTO_INCREMENT NOT NULL ,
    partyId int  NOT NULL ,
    stockId int  NOT NULL ,
    orderStatus int  NOT NULL ,
    side boolean  NOT NULL ,
    PRIMARY KEY (
        id
    )
);

CREATE TABLE OrderHistory (
    id int AUTO_INCREMENT NOT NULL ,
    orderId int NOT NULL ,
    Price Decimal(8,2)  NOT NULL ,
    currentSize int  NOT NULL ,
    userId int  NOT NULL ,
    timestamp timestamp  NOT NULL ,
    PRIMARY KEY (
        id
    )
);

CREATE TABLE Stock (
    id int AUTO_INCREMENT NOT NULL ,
    companyName VARCHAR(50)  NOT NULL ,
    stockSymbol VARCHAR(5)  NOT NULL ,
    stockExchange VARCHAR(6)  NOT NULL ,
    centralPartyId int  NOT NULL ,
    tickSize Decimal(6,3)  NOT NULL ,
    PRIMARY KEY (
        id
    )
);

CREATE TABLE Trade (
    id int AUTO_INCREMENT NOT NULL ,
    buyId int  NOT NULL ,
    sellId int  NOT NULL ,
    executionTime timestamp  NOT NULL ,
    PRIMARY KEY (
        id
    )
);

CREATE TABLE User (
    id int AUTO_INCREMENT NOT NULL ,
    userName VARCHAR(50)  NOT NULL ,
    deleted boolean  NOT NULL ,
    PRIMARY KEY (
        id
    )
);

CREATE TABLE Party (
    id int AUTO_INCREMENT NOT NULL ,
    name VARCHAR(50)  NOT NULL ,
    symbol VARCHAR(5)  NOT NULL ,
    PRIMARY KEY (
        id
    )
);

ALTER TABLE `Order` ADD CONSTRAINT fk_Order_partyId FOREIGN KEY(partyId)
REFERENCES Party (id);

ALTER TABLE `Order` ADD CONSTRAINT fk_Order_stockId FOREIGN KEY(stockId)
REFERENCES Stock (id);

ALTER TABLE OrderHistory ADD CONSTRAINT fk_OrderHistory_orderId FOREIGN KEY(orderId)
REFERENCES `Order` (id);

ALTER TABLE OrderHistory ADD CONSTRAINT fk_OrderHistory_userId FOREIGN KEY(userId)
REFERENCES User (id);

ALTER TABLE Stock ADD CONSTRAINT fk_Stock_centralPartyId FOREIGN KEY(centralPartyId)
REFERENCES Party (id);

ALTER TABLE Trade ADD CONSTRAINT fk_Trade_buyId FOREIGN KEY(buyId)
REFERENCES OrderHistory (id);

ALTER TABLE Trade ADD CONSTRAINT fk_Trade_sellId FOREIGN KEY(sellId)
REFERENCES OrderHistory (id);