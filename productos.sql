drop database if exists productos;

create database if not exists productos character set utf8;

use productos;


create table products (

id_producto tinyint(2) not null auto_increment primary key,

producto varchar(30) not null,

descripcion varchar(20) not null,

existencias int(4) not null
,
precio_compra int(8) not null,

precio_venta int(8) not null
)engine InnoDB;



insert into products(producto, descripcion, existencias, precio_compra,precio_venta) values
('Audifonos', 'Sony', '10', '180', '220'),
('Control', 'XboxOne', '2', '750', '900'),

('Bocina', 'JBL', '4', '2900', '2900'),

('Control', 'XboxOne', '2', '750', '900');