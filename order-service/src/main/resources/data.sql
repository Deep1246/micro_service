-- Insert into orders
INSERT INTO order_master (status, total_price) VALUES ('CREATED', 1000.00);
INSERT INTO order_master (status, total_price) VALUES ('CONFIRMED', 2000.00);

-- Insert into order_items (link with order_id)
INSERT INTO order_items (product_id, qty, order_id) VALUES (1, 2, 1);
INSERT INTO order_items (product_id, qty, order_id) VALUES (2, 1, 1);
INSERT INTO order_items (product_id, qty, order_id) VALUES (3, 5, 2);
INSERT INTO order_items (product_id, qty, order_id) VALUES (4, 3, 2);