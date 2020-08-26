#!/bin/bash

# Create database and dump data.
mysql -u"$1" -p"$2" < ./dbscripts/OrderBook.sql
mysql -u"$1" -p"$2" orderbook < ./dbscripts/OrderBookData.sql

# Run jar.