INSERT INTO customer (first_name,sur_name,user_name,password) VALUES ('john','doe', 'john@email.com','1234');
INSERT INTO book (title, author,isbn, price, stock)
VALUES
('Spring Action', 'Jack','123456' ,10.99, 50),
('1984', 'George Orwell','666777545',8.99 ,100),
('To Kill a Mockingbird', 'Harper Lee','44888990011', 12.50, 30);
INSERT INTO cart (customer_id)
VALUES ((SELECT id FROM customer WHERE user_name = 'john@email.com'));
INSERT INTO cart_item (cart_id, book_id, quantity)
VALUES
(
    (SELECT id FROM cart WHERE customer_id = (SELECT id FROM customer WHERE user_name = 'john@email.com')),
    (SELECT id FROM book WHERE title = '1984'),
    2
),
(
    (SELECT id FROM cart WHERE customer_id = (SELECT id FROM customer WHERE user_name = 'john@email.com')),
    (SELECT id FROM book WHERE title = 'Spring Action'),
    1
);
