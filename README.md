# OrderBook

It works sometimes.

## How to run

We assume there is a MySQL distribution running on port 3306 or some other port you know about, and you know the username and password of an administrator for this distribution (like the root user).

1. Click the green "Code" button in the top right and then "Download ZIP" in the drop down menu that shows to download this repository to your downloads folder (or probably somewhere else, depending on your system/browser).

2. Unzip the downloaded `OrderBook-master.zip` file, if it is not already unzipped. We assume you know how to unzip files :)

3. In the `bin` folder in the (newly created) `OrderBook-master` directory, open `application.properties` in a text editor. There will be four lines that look something like
```
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/OrderBook?serverTimezone=BST
spring.datasource.username=root
spring.datasource.password=password
```
Change `root` and `password` right of the equals signs to the username and password of a MySQL admin. This usually means the root user. Save the document and close it.

N.B. If you know your MySQL is not running on port 3306, you may need to change `3306` in `jdbc:mysql://localhost:3306/OrderBook?serverTimezone=BST` to match this port. Similarly, if your database is not running on `localhost`, change `localhost` to the URL of your database.

4. Open a terminal in `bin`. Run
```
$ chmod 755 startup.sh
```
to allow the script `startup.sh` to. be executed, and then
```
$ ./startup.sh <host-url> <username> <password>
```
where `<host-url>`, `<username>` and `<password>` should be replaced by the url of your database, username and password of a MySQL admin like before.

The website will be available at `http://localhost:3000`.

Shut down the application by typing ctrl-c in the terminal.

## Rollback plan

Shut down the application by typing ctrl-c in the terminal.

Ignore the application for the rest of your life.
