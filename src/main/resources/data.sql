-- role id 1 para Representante y role id 2 para Buyer

INSERT INTO warehouse.role (id,user_role) VALUES
(1,"REPRESENTANTE"),
(2,"BUYER");

INSERT INTO warehouse.user (id,password,user,role_id) VALUES
(1,"tomas123","Tomas",1),
(2,"ronald123","Ronald",2),
(3,"sebastian123","Sebastian",2);