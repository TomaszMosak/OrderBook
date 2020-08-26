#!/bin/bash

# Create database and dump data.
mysql -u"$1" -p"$2" < ./dbscripts/OrderBook.sql
mysql -u"$1" -p"$2" orderbook < ./dbscripts/OrderBookData.sql

# Run jar and react app
trap 'kill %1; kill %2' SIGINT
java -jar OrderBook-1.0.jar &
cd orderbook && npm start