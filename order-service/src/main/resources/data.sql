-- Insert into orders
INSERT INTO orders (status, total_price) VALUES ('CREATED', 1000.00)
    ,('CONFIRMED', 2000.00);

-- Insert into order_items (link with order_id)
INSERT INTO order_items (product_id, qty, order_id)
VALUES (1, 2, 1)
, (2, 1, 1),
(3, 5, 2),
(4, 3, 2);