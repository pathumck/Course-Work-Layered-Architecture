mysql -uroot -p

Ijse@1234

create database stClothing;

use stClothing;

create table User (
userName varchar(50) PRIMARY KEY,
password varchar(50) not null
);

desc User;

create table Employee (
employeeId varchar(50) PRIMARY KEY,
name varchar(50) not null ,
role varchar(50) not null ,
address varchar(100) not null
);

desc Employee;

create table Salary (
salaryId varchar(50) PRIMARY KEY,
employeeId varchar(50) not null ,
foreign key (employeeId) references Employee (employeeId) ON UPDATE CASCADE ON DELETE CASCADE
);

desc Salary;

create table Suppliers (
supplierId varchar(50) PRIMARY KEY,
name varchar(50) not null ,
address varchar(50) not null ,
tp varchar(50) not null
);

desc Suppliers;

create table Payments (
paymentId varchar(50) PRIMARY KEY,
supplierId varchar(50) not null ,
amount double(20,2) not null,
foreign key (supplierId) references Suppliers(supplierId) ON UPDATE CASCADE ON DELETE CASCADE
);

desc Payments;

create table Items (
itemCode varchar(50) PRIMARY KEY,
qty int(8) not null,
supplierId varchar(50) not null ,
description varchar(100) not null ,
FOREIGN KEY(supplierId) references Suppliers(supplierId) ON UPDATE CASCADE ON DELETE CASCADE
);

desc Items;

create table Customers (
customerId varchar(50) PRIMARY KEY,
name varchar(50) not null ,
address varchar(50) not null ,
tp varchar(50) not null,
date varchar(50) not null
);

desc Customers;

create table Orders (
orderId varchar(50) PRIMARY KEY,
customerId varchar(50) not null ,
date date not null ,
time varchar(50) not null ,
amount double(20,2) not null,
type varchar(50) not null ,
FOREIGN KEY (customerId) references Customers(customerId) ON UPDATE CASCADE ON DELETE CASCADE
);

desc Orders;

create table OrderDetails (
itemCode varchar(50) not null ,
orderId varchar(50) not null ,
FOREIGN KEY(itemCode) references Items(itemCode) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(orderId) references Orders(orderId) ON UPDATE CASCADE ON DELETE CASCADE
);

desc OrderDetails;

create table Returns (
returnId varchar(50) PRIMARY KEY,
date date not null ,
time varchar(50) not null ,
description varchar(50) not null
);

desc Returns;

create table ReturnDetails (
returnId varchar(50) not null ,
itemCode varchar(50) not null ,
FOREIGN KEY(returnId) references Returns(returnId) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(itemCode) references Items(itemCode) ON UPDATE CASCADE ON DELETE CASCADE
);

desc ReturnDetails;





