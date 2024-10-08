DROP TABLE IF EXISTS CUSTOMER;
CREATE TABLE CUSTOMER (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    FIRST_NAME VARCHAR(16) NOT NULL,
    SUR_NAME VARCHAR(16) NOT NULL,
    USER_NAME VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    TITLE VARCHAR(50) NOT NULL,
    AUTHOR VARCHAR(50) NOT NULL,
    ISBN VARCHAR(50) UNIQUE NOT NULL,
    PRICE DECIMAL(10, 2) NOT NULL,
    STOCK INT NOT NULL,
    CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS CART;
CREATE TABLE CART (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    CUSTOMER_ID BIGINT NOT NULL UNIQUE,
    CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(ID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS CART_ITEM;
CREATE TABLE CART_ITEM (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    CART_ID BIGINT,
    BOOK_ID BIGINT,
    QUANTITY INT NOT NULL,
    FOREIGN KEY (CART_ID) REFERENCES CART(ID) ON DELETE CASCADE,
    FOREIGN KEY (BOOK_ID) REFERENCES BOOK(ID)
);