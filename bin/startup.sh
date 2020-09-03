#!/bin/bash

# Alter application.properties
perl -pi -e "s'{HOST}'$1'g" application.properties
perl -pi -e "s'{USERNAME}'$2'g" application.properties
perl -pi -e "s'{PASSWORD}'$3'g" application.properties

# Create database and dump data.
mysql -h"$1" -u"$2" -p"$3" < ./dbscripts/OrderBook.sql
mysql -h"$1" -u"$2" -p"$3" OrderBook < ./dbscripts/OrderBookData.sql

# Run jar and react app
cd orderbook && npm install
cd ..
trap 'kill %1; kill %2' SIGINT
java -jar OrderBook-1.0.jar &
cd orderbook && npm start