-- role id 1 para Representante y role id 2 para Buyer
INSERT INTO role (id, user_role)
VALUES (1, 'REPRESENTANTE'),
       (2, 'BUYER');

INSERT INTO usuarios (id, password, username, role_id)
VALUES (1, 'tomas123', 'Tomas', 1),
       (2, 'ronald123', 'Ronald', 1),
       (3, 'sebastian123', 'Sebastian', 2);

INSERT INTO category (id, code, name)
VALUES (1, 'FS', 'FRESCO'),
       (2, 'RF', 'REFRIGERADO'),
       (3, 'FF', 'CONGELADO');

INSERT INTO order_status (id, status)
VALUES (1, 'Carrito');

INSERT INTO product (id, name, price)
VALUES (1, 'FRESA', 2000.00),
       (2, 'POLLO', 12000.00),
       (3, 'PESCADO', 7000.00),
       (4, 'CARNE', 18000.00);

INSERT INTO warehouse (id, name, country, city, address, user_id)

VALUES (1, 'warehouse1', 'Colombia', 'Bogota', 'cll 185 #10-3', 1),
       (2, 'warehouse2', 'Colombia', 'Medellin', 'cll 18 #10-3', 2);


INSERT INTO section (id, warehouse_id, minimum_temperature, maximum_temperature, maximum_batch_quantity, category_id)
VALUES (1, 1, 10.0, 18.0, 10, 1),
       (2, 1, 0.0, 10.0, 10, 2),
       (3, 1, -10, 0.0, 10, 3),
       (4, 2, 10.0, 18.0, 10, 1),
       (5, 2, 0.0, 10.0, 10, 2),
       (6, 2, -10, 0.0, 10, 3);

INSERT INTO inbound_order (date)
VALUES (now()),
       (now()),
       (now());


INSERT INTO batch (initial_quantity, current_quantity, minimum_temperature, section_id, inbound_order_id,
                   product_id, manufacturing_date, manufacturing_time,due_date)
VALUES (50, 50, 11.0, 1, 1, 1,'2023-02-10','0001-01-10 12:30:00','2023-02-20'),
       (50, 50, 5.0, 2, 1, 2,'2023-02-9','0001-01-10 12:30:00','2023-02-20'),
       (50, 50, -5, 3, 2, 3,'2023-02-10','0001-01-10 12:30:00','2023-02-20'),
       (50, 50, 11.0, 4, 2, 4,'2023-02-10','0001-01-10 12:30:00','2023-02-20'),
       (50, 50, 5.0, 5, 3, 2,'2023-02-10','0001-01-10 12:30:00','2023-02-20'),
       (50, 50, -5, 6, 3, 3, '2023-02-10','0001-01-10 12:30:00','2023-02-20'),
       (50, 50, 11.0, 1, 1, 1,'2023-02-10','0001-01-10 12:30:00','2023-02-16'),
       (50, 50, 11.0, 1, 1, 1,'2023-02-10','0001-01-10 12:30:00','2023-02-15'),
       (50, 50, 11.0, 1, 1, 1,'2023-02-10','0001-01-10 12:30:00','2023-02-17'),
       (50, 50, -5, 1, 3, 1, '2023-02-10','0001-01-10 12:30:00','2023-05-20');
       
INSERT INTO purchase_order ( date, order_status_id, user_id)
VALUES (now(), 1, 2),( now(), 1, 2);

INSERT INTO purchase_order_has_product (quantity, product_id, purchase_order_id)
VALUES ( 2, 1, 1),
       ( 3, 2, 1),
       ( 2, 2, 2);
