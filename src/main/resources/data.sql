-- role id 1 para Representante y role id 2 para Buyer

INSERT INTO role (id, user_role)
VALUES (1, "REPRESENTANTE"),
       (2, "BUYER");

INSERT INTO user (id, password, user, role_id)
VALUES (1, "tomas123", "Tomas", 1),
       (2, "ronald123", "Ronald", 2),
       (3, "sebastian123", "Sebastian", 2);

INSERT INTO category (id, code, name)
VALUES (1, "FS", "FRESCO"),
       (2, "RF", "REFRIGERADO"),
       (3, "FF", "CONGELADO");


INSERT INTO order_status (id, status)
VALUES (1, "Carrito");


INSERT INTO product (id, name, price)
VALUES (1, "FRESA", 2000.00),
       (2, "POLLO", 12000.00),
       (3, "PESCADO", 7000.00);

INSERT INTO warehouse (id, name, country, city, address, user_id)
VALUES (1, "warehouse1", "Colombia", "Bogota", "cll 185 #10-3", 1),
       (2, "warehouse2", "Colombia", "Medellin", "cll 18 #10-3", 1);

INSERT INTO section (id, warehouse_id, minimum_temperature, maximum_temperature, maximum_batch_quantity, category_id)
VALUES (1, 1, 10.0, 18.0, 10, 1),
       (2, 1, 0.0, 10.0, 10, 2),
       (3, 1, -10, 0.0, 10, 3),
       (4, 2, 10.0, 18.0, 10, 1),
       (5, 2, 0.0, 10.0, 10, 2),
       (6, 2, -10, 0.0, 10, 3);

INSERT INTO inbound_order (id, date)
VALUES (1, now()),
       (2, now()),
       (3, now());

INSERT INTO batch (id, initial_quantity, current_quantity, minimum_temperature, section_id, inbound_order_id,
                   product_id)
VALUES (1, 50, 50, 11.0, 1, 1, 1),
       (2, 50, 50, 5.0, 2, 1, 2),
       (3, 50, 50, -5, 3, 2, 3),
       (4, 50, 50, 11.0, 4, 2, 1),
       (5, 50, 50, 5.0, 5, 3, 2),
       (6, 50, 50, -5, 6, 3, 3);

INSERT INTO purchase_order (id, date, order_status_id, user_id)
VALUES (1, now(), 1, 2);

INSERT INTO purchase_order_has_product (id, quantity, product_id, purchase_order_id)
VALUES (1, 2, 1, 1),
       (2, 3, 2, 1);
