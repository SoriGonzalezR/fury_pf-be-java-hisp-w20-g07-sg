# MERCADO LIBRE - FRESCOS

# Proyecto Final - Grupo 7

## Roadmap

![Logo](https://assets.digitalhouse.com/content/AR/CTD/DESAFIO%20FINAL_Mesa%20de%20trabajo%201.jpg)

## Miembros del Proyecto

- 👨🏼‍💻 [@manueldiazmeli](https://github.com/manueldiazmeli)
- 👨🏻‍💻 [@TomasGonzalezDev](https://github.com/TomasGonzalezDev)
- 👨🏻‍💻 [@santtury](https://github.com/santtury)
- 👩🏻‍💻 [@NathamgML](https://github.com/NathamgML)
- 👨🏻‍💻 [@JuanSHenao](https://github.com/JuanSHenao)
- 👨🏻‍💻 [@RonaldRosero](https://github.com/RonaldRosero)
- 👩🏻‍💻 [@SoriGonzalezR](https://github.com/SoriGonzalezR)
- 👨🏻‍💻 [@steveng45](https://github.com/steveng45)

## Objetivo

El objetivo de este proyecto final es implementar una API REST en el marco de la consigna y aplicando los contenidos
trabajados durante todo el BOOTCAMP MELI. (Git, Java, Spring, Bases de datos y calidad de software).

## Pautas para la planificación y dinámica de trabajo

#### A. Metodología de trabajo

Para llevar adelante el proyecto les proponemos trabajar con la metodología de trabajo SCRUM aplicando de manera regular
un conjunto de buenas prácticas para trabajar colaborativamente en equipo y así obtener el mejor resultado posible.

Tengamos en cuenta que el Sprint 3 se divide en 2 momentos:

3.1 Primera parte: Se desarrollarán 5 requerimientos de forma grupal, entre el Miércoles 08 de Febrero y el Lunes 13 de
Febrero al mediodía.

3.2 Segunda parte: Se desarrollará un sexto requerimiento de forma individual, entre 13 y 15 de Febrero (a última hora).

#### Daily - Planning

Al momento de presentar el proyecto y al inicio de cada Sprint el Product Owner define los requisitos liberados y se
revisan las tareas, se realizan las consultas y la estimación de requerimientos.
Será importante organizar salas en Meet para tener la daily del equipo con el fin de compartir el trabajo qué hicimos
ayer, qué impedimentos tuvimos y qué vamos a hacer hoy. De esta forma podremos chequear los avances en los
requerimientos y escuchar a otros colegas teniendo en cuenta cuál es el objetivo, los obstáculos, las posibles
soluciones y cómo puedo ayudar.

#### Revisión Diaria

Al finalizar la jornada, es una oportunidad para chequear los avances, si hay algo para destacar o revisar (esto es
opcional, ya que no es parte del proceso de desarrollo de software en sí, sino más bien, una sugerencia para una
práctica de gestión interna. Tampoco es necesario realizar una reunión con tus compañeros de equipo para el chequeo
mencionado).

#### Documentación de entregas parciales

1. Realizar el commit inicial “Inicio de Requerimiento XX”, para saber en qué momento se empezó con el requerimiento.
2. Realizar el commit final “finalización de Requerimiento XX”, indicando que se terminó con un requerimiento, dando
   aviso en la planilla.
3. Al terminar el requerimiento versionarlo. Por ej. Versión 0.0.1

#### B. Peer Review (Revisión de pares)

Se propone a lo largo del Sprint 3 - Proyecto Final, trabajar con la modalidad de revisión de pares propuesta por el
proceso de Integración continua (C.I.) empleado en Mercado Libre.
Este proceso implica, que en el momento en que cada desarrollador se encuentre trabajando en una nueva funcionalidad
sobre una rama “feature” personal y desee aportar esos cambios a la rama principal de desarrollo del equipo (develop),
deberá hacerlo a través de la solicitud de un Pull Request (PR) en donde deberá designar a un colega de equipo como
revisor (review). El o la colega asignada, deberá a su vez aprobar los cambios en el código para que se puedan
integrar (merge) en la rama principal.

## Tecnologías Utilizadas

Git, Java, Spring, Bases de datos, Testing y calidad de software

## Decisiones de Equipo

- En sección la capacidad maxima es por lotes independientemente del lote.
- Tener en cuenta los batch number para hacer los POST.

## Requerimientos técnicos funcionales

## Requerimiento 1

**Responsables:** Manuel Alejandro Diaz Isaza, Ronald Esteban Rosero Montana, Tomas Giovanny Gonzalez Romero y Juan Sebastian Henao Ramirez.

**Requerimiento US: ml-insert-batch-in-fulfillment-warehouse-01**

**Importante:** Las historias de usuario están narradas desde el punto de vista del Representante del warehouse en
función a sus necesidades. Los servicios son expuestos desde el warehouse de fulfillment. Los contratos hacen referencia
a la User Story.

Sinónimos de Representante: supervisor, líder.

**Ingresar lote en warehouse de fulfillment**

### User Story

| User Story Code: ml-insert-batch-in-fulfillment-warehouse-01 <br/> User Story Name: Ingresar lote en warehouse de fulfillment                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **COMO** Representante del warehouse de fulfillment **QUIERO** poder consultar un producto en stock en el warehouse **PARA** conocer su ubicación en un sector y los diferentes lotes en donde se encuentre.                                                                                                                                                                                                                                                                                                                                                                        | 
| **ESCENARIO 1:** producto de un Seller está registrado. <br/> **DADO QUE**  el producto de un Sellers está registrado <br/> **Y** que el warehouse es válido <br/> **Y** que el representante pertenece al  warehouse <br/> **Y** que el sector es válido <br/> **Y** que el sector corresponde al tipo de producto <br/> **Y** que el sector tiene espacio disponible <br/> **CUANDO** el representante ingresa lote <br/> **ENTONCES** el registro de compra es creado <br/> **Y** el lote es asignado a un sector <br/> **Y** el representante es asociado al registro de compra | 
| **VALIDACIÓN** <br/> - Autenticarse como representante y acceder a los endpoints. <br/> - Registrar lote en el sector correspondiente. <br/> - Probar que se esté registrando correctamente el sector del depósito.                                                                                                                                                                                                                                                                                                                                                                 | 

**Nota:** Tener la información necesaria para entender en qué sector debe almacenarse esa mercadería para que se
mantenga en
buen estado mientras se encuentre en el depósito y para que se le pueda mostrar al colaborador que va a ir a buscar el
producto (picking) donde se encuentra.

### Contratos referentes a la User Story

| HTTP   | Plantilla URI                       | Descripción                                                                                                                     |
|:-------|:------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------|
| `POST` | /api/v1/fresh-products/inboundorder | Dar de alta un lote con el stock de productos que la compone. Devolver el nuevo lote con status code “201 CREATED”.             |
| `PUT`  | /api/v1/fresh-products/inboundorder | En el caso de que el lote ya exista  y deba actualizarse el mismo. Devolver el stock actualizado con status code “201 CREATED”. |

**Nota General:**

- Contemplar otros tipos de errores.
- Utilizar script de carga.
- Trabajar con Access Token para el pedido como cliente autenticado.

### Representación JSON

`Request`

```http
{
    "inbound_order": {
        "order_number": "Integer",
        "order_date": "dd-MM-yyyy",
        "section": {
            "section_code": "Integer",
            "warehouse_code": "Integer"
        },
        "batch_stock": [
            {
                "batch_number": "Integer",
                "product_id": "Integer",
                "current_temperature": "Double",
                "minimum_temperature": "Double",
                "initial_quantity": "Integer",
                "current_quantity": "Integer",
                "manufacturing_date": "dd-MM-yyyy",
                "manufacturing_time": "dd-MM-yyyy HH:mm:ss",
                "due_date": "dd-MM-yyyy"
            }
        ]
    }
}
```

`Response`

```http
{
    "batch_stock": [
        {
            "batch_number": "Integer",
            "product_id": "Integer",
            "current_temperature": "Double",
            "minimum_temperature": "Double",
            "initial_quantity": "Integer",
            "current_quantity": "Integer",
            "manufacturing_date": "dd-MM-yyyy",
            "manufacturing_time": "dd-MM-yyyy HH:mm:ss",
            "due_date": "dd-MM-yyyy"
        }
    ]
}
```

## Requerimiento 2

**Responsables:** Nathalia Montero Gomez, Soraya Carolina Gonzalez Ramirez, Sebastian Camilo Anttury Sanchez y Manuel Stiven Gonzalez Mahecha.

**Requerimiento US: ml-add-products-to-cart-01**

**Importante:** Las historias de usuario están narradas desde el punto de vista del buyer en función a sus necesidades.
Los servicios son expuestos desde Marketplace para ser consumidos por buyer que lo solicita. Los contratos hacen
referencia a la User Story.

**Registrar Venta: Agregar producto al carrito de compras**

### User Story

| User Story Code: ml-add-products-to-cart-01 <br/> User Story Name: Agregar producto al carrito de compras                                                                                                                                                                                                                                                                                                                                                                             |
|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **COMO** buyer **QUIERO** agregar productos al carrito de compras del Marketplace **PARA** comprarlos si lo deseo.                                                                                                                                                                                                                                                                                                                                                                    | 
| **ESCENARIO 1:** producto de un Seller está registrado. <br/> **DADO QUE**  el producto de un Sellers está registrado <br/> **Y** que el buyer esté registrado <br/> **Y** que el producto tenga stock <br/> **Y** que el vencimiento del producto no sea inferior a 3 semanas <br/> **CUANDO** el buyer agrega el producto con la cantidad al carrito <br/> **ENTONCES** se agrega un producto al carrito de compra <br/> **Y** agrega cantidad de producto inferior al stock actual | 
| **VALIDACIÓN** <br/> - Autenticarse como buyer y acceder a los endpoints. <br/> - Consultar producto.  <br/> - Agregar un producto al carrito de buyer.                                                                                                                                                                                                                                                                                                                               | 

### Contratos referentes a la User Story

| HTTP   | Plantilla URI                                     | Descripción                                                                                                                                                                                                                                                                 |
|:-------|:--------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `GET`  | /api/v1/fresh-products/list                       | Ver una lista de productos completa.Si la lista no existe, debe devolver un “404 Not Found”.                                                                                                                                                                                |
| `GET`  | /api/v1/fresh-products/list?category={FS, RF, FF} | Ver una lista de productos según categoría. category: FS=Fresco, RF=Refrigerado, FF=Congelado .Si la lista no existe, debe devolver un “404 Not Found”.                                                                                                                     |
| `POST` | /api/v1/fresh-products/orders                     | Dar de alta una orden con la lista de productos que componen la PurchaseOrder. Calcular el precio final,  y devolverlo junto a un status code “201 CREATED”. Si no hay stock de un producto notificar la situación devolviendo un error por producto, no a nivel de orden . |
| `GET`  | /api/v1/fresh-products/orders/{idOrder}           | Mostrar productos en la orden.                                                                                                                                                                                                                                              |
| `PUT`  | /api/v1/fresh-products/orders/{idOrder}           | Modificar la orden existente. que sea de tipo carrito para modificar.                                                                                                                                                                                                       |

**Nota General:**

- Contemplar otros tipos de errores.
- Utilizar script de carga.
- Trabajar con Access Token para el pedido como cliente autenticado.

### Representación JSON

`Request`

```http
{
    "purchase_order": {
        "date": "dd-MM-yyyy",
        "buyer_id": "Integer",
        "order_status": {
            "status_code": "string"
        },
        "products": [
            {
                "product_id": "Integer",
                "quantity": "Integer"
            }
        ]
    }
}
```

**Nota:** Las órdenes de compra (purchaseOrder) que realiza el buyer únicamente tendrán el
estado de Orden (OrderStatus) con valor: carrito .

`Response`

```http
{
  "total_price": "double"
}
```

## Requerimiento 3

**Responsables:** Ronald Esteban Rosero Montana, Manuel Stiven Gonzalez Mahecha y Sebastian Camilo Anttury Sanchez.

**Requerimiento US: ml-check-product-location-in-warehouse-01**

**Importante:** Las historias de usuario están narradas desde el punto de vista del representante del warehouse en
función a sus necesidades. Los servicios son expuestos desde el warehouse de fulfillment. Los contratos hacen referencia
a la User Story.

Sinónimos de Representante: supervisor, líder.

**Consultar ubicación de un producto en el warehouse**

### User Story

| User Story Code: ml-check-product-location-in-warehouse-01 <br/> User Story Name: Consultar ubicación de un producto en warehouse                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **COMO** Representante **QUIERO** poder consultar un producto en stock en el warehouse **PARA** conocer su ubicación en un sector y los diferentes lotes en donde se encuentre.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               | 
| **ESCENARIO 1:** producto de un Seller está registrado. <br/> **DADO QUE**  el producto de un Sellers está registrado <br/> **Y** que el warehouse es válido <br/> **Y** que el representante pertenece al  warehouse <br/> **Y** que el sector es válido <br/> **Y** que el sector corresponde al tipo de producto <br/> **Y** que el sector posea el lote <br/> **Y** que el lote posea el producto. <br/> **CUANDO** el representante ingresa el código de producto <br/> **ENTONCES** se muestra la ubicación del producto en un sector <br/> **Y** que el producto posea un número de lote <br/> **Y** que el producto se filtre por número de lote <br/> **Y** que el producto se filtre por la cantidad actual del lote (menor a mayor) <br/> **Y** que el producto se filtre por fecha de vencimiento | 
| **VALIDACIÓN** <br/> - Autenticarse como representante y acceder a los endpoints. <br/> - El producto no debe de aparecer en un sector incorrecto. <br/> - El producto debe de aparecer en diferentes lotes. <br/> - El producto no debe de estar vencido o próximo a vencer (mínimo 3 semanas)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               | 

**Nota:** Tener la información necesaria para entender en qué sector debe almacenarse esa mercadería para que se
mantenga en
buen estado mientras se encuentre en el depósito y para que se le pueda mostrar al colaborador que va a ir a buscar el
producto (picking) donde se encuentra.

### Contratos referentes a la User Story

| HTTP  | Plantilla URI                                                 | Descripción                                                                                                                                                                      |
|:------|:--------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `GET` | /api/v1/fresh-products/{idProduct}/batch/list                 | Ver una lista de productos con todos los lotes en donde aparezca. Si la lista no existe, debe devolver un “404 Not Found”.                                                       |
| `GET` | /api/v1/fresh-products/{idProduct}/batch/list?order={L, C, F} | Ver una lista de productos con todos los lotes en donde aparezca. Ordenados por por: L = ordenado por lote, C = ordenado por cantidad actual, F = ordenado por fecha vencimiento |

**Nota General:**

- Contemplar otros tipos de errores.
- Utilizar script de carga.
- Trabajar con Access Token para el pedido como cliente autenticado.

### Representación JSON

`Response`

```http
{
    "section": {
        "section_code": "int",
        "warehouse_code": "int"
    },
    "product_id": "int",
    "batch_stock": [
        {
            "batch_number": "int",
            "current_quantity": "int",
            "due_date": "dd-MM-yyyy"
        }
    ]
}
```

## Requerimiento 4

**Responsables:** Soraya Carolina Gonzalez Ramirez.

**Requerimiento US: ml-check-product-stock-in-warehouses-04**

**Importante:** Las historias de usuario están narradas desde el punto de vista del representante y en función a sus
necesidades. Los servicios son expuestos desde el warehouse de fulfillment. Los contratos hacen referencia a la User
Story.

Sinónimos de Representante: supervisor, líder.

**Consultar el stock de un producto en todos los warehouses**

### User Story

| User Story Code: ml-check-product-stock-in-warehouses-01 <br/> User Story Name: Consultar el stock de un producto en todos los warehouses                                                                                                                                                             |
|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **COMO** Representante **QUIERO** poder consultar un producto en todos los warehouse **PARA** conocer el stock del mismo en cada warehouse.                                                                                                                                                           | 
| **ESCENARIO 1:** producto de un Seller está registrado. <br/> **DADO QUE**  el producto de un Sellers está registrado <br/> **Y** que el warehouse es válido <br/> **CUANDO** el representante ingresa el código de producto <br/> **ENTONCES** se muestra la cantidad del producto en cada warehouse | 
| **VALIDACIÓN** <br/> - Autenticarse como representante y acceder a los endpoints.                                                                                                                                                                                                                     | 

### Contratos referentes a la User Story

| HTTP  | Plantilla URI                                     | Descripción                                                                                                                                          |
|:------|:--------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------|
| `GET` | /api/v1/fresh-products/{idProduct}/warehouse/list | Obtener la cantidad total de stock de un producto por cada warehouse.Si el producto no existe en ningún warehouse, debe devolver un “404 Not Found”. |

**Nota General:**

- Contemplar otros tipos de errores.
- Utilizar script de carga.
- Trabajar con Access Token para el pedido como cliente autenticado.

### Representación JSON

`Response`

```http
{
    "product_id": "int",
    "warehouses": [
        {
            "warehouse_code": "int",
            "total_quantity": "int"
        }
    ]
}
```

## Requerimiento 5

**Responsables:** Todo el equipo.

**Requerimiento US: ml-check-batch-stock-due-date-01**

**Importante:** Las historias de usuario están narradas desde el punto de vista del representante del warehouse en
función a sus necesidades. Los servicios son expuestos desde el warehouse de fulfillment. Los contratos hacen referencia
a la User Story.

Sinónimos de Representante: supervisor, líder.

**Consultar fecha de vencimiento por lote**

### User Story

| User Story Code: ml-check-batch-stock-due-date-01 <br/> User Story Name: Consultar fecha de vencimiento por lote                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **COMO** Representante **QUIERO** poder consultar los productos en stock próximos a vencer en un determinado warehouse **PARA** poder aplicar alguna acción comercial con los mismos.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | 
| **ESCENARIO 1:** producto de un Seller está registrado. <br/> **DADO QUE**  el producto de un Sellers está registrado <br/> **Y** que el warehouse es válido <br/> **Y** que el representante pertenece al  warehouse **CUANDO** el representante ingresa el número de días <br/> **ENTONCES** se muestra una lista de productos con fecha de vencimiento entre la fecha actual y la fecha futura (fecha actual + días ingresados) <br/> **Y** que el producto posea un número de lote <br/> **Y** que el producto se filtre por número de lote <br/> **Y** que el producto se filtre por fecha de vencimiento <br/> **Y** que el producto se filtre según categoría de los productos (frescos, congelados, refrigerados) | 
| **VALIDACIÓN** <br/> - Autenticarse como representante y acceder a los endpoints. <br/> - El producto no debe de aparecer en un sector incorrecto. <br/> - El producto debe de aparecer en diferentes lotes. <br/> - la fecha de vencimiento debe de estar dentro del rango ingresado.                                                                                                                                                                                                                                                                                                                                                                                                                                    | 

### Contratos referentes a la User Story

| HTTP  | Plantilla URI                                                                                                | Descripción                                                                                                                                                                                                                         |
|:------|:-------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `GET` | /api/v1/fresh-products/batch/list/due-date/{cantDays}                                                        | Obtener todos los lotes próximos a vencer entre hoy y la cantidad de días posteriores ordenados por fecha de vencimiento.                                                                                                           |
| `GET` | /api/v1/fresh-products/batch/list/due-date/{cantDays}?category = {FS, RF, FF}& order = {date_asc, date_desc} | Obtener todos los lotes próximos a vencer entre hoy y la cantidad de días posteriores ordenados por fecha de vencimiento. Que pertenecen a una determinada categoría de producto. category: FS=Fresco, RF=Refrigerado, FF=Congelado |

**Nota General:**

- Contemplar otros tipos de errores.
- Utilizar script de carga.
- Trabajar con Access Token para el pedido como cliente autenticado.

### Representación JSON

`Response`

```http
{
    "batch_stock": [
        {
            "batch_number": "Integer",
            "product_id": "Integer",
            "product_type_id": "Integer",
            "due_date": "LocalDate",
            "current_quantity": "Integer"
        },
        {
            "batch_number": "Integer",
            "product_id": "Integer",
            "product_type_id": "Integer",
            "due_date": "LocalDate",
            "current_quantity": "Integer"
        }
    ]
}
```


## Modelo Relacional 
![modelo relacional](https://res.cloudinary.com/dainl1ric/image/upload/v1676414369/image_qewsyp.png)
#### Agradecimientos: A nuestros queridos Scrums Masters Carlos Arroyo  por antojarnos de comida y a nuestros instructores Joy por sus kahoots ❤️, Martin, Jean y Adrian por compartir su conocimiento!!
