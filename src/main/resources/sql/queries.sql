mysql -uroot -p

Ijse@1234

create database stClothing;

use stClothing;

create table User (
userName varchar(50) PRIMARY KEY,
password varchar(50)
);

desc User;

create table Employee (
employeeId varchar(50) PRIMARY KEY,
name varchar(50),
role varchar(50),
address varchar(100)
);

desc Employee;

create table Salary (
salaryId varchar(50) PRIMARY KEY,
employeeId varchar(50),
foreign key (employeeId) references Employee (employeeId) ON UPDATE CASCADE ON DELETE CASCADE
);

desc Salary;

create table Suppliers (
supplierId varchar(50) PRIMARY KEY,
name varchar(50),
address varchar(50),
tp varchar(50)
);

desc Suppliers;

create table Payments (
paymentId varchar(50) PRIMARY KEY,
supplierId varchar(50),
amount double(20,2),
foreign key (supplierId) references Suppliers(supplierId) ON UPDATE CASCADE ON DELETE CASCADE
);

desc Payments;

create table Items (
itemCode varchar(50) PRIMARY KEY,
qty int(8),
supplierId varchar(50),
description varchar(100),
FOREIGN KEY(supplierId) references Suppliers(supplierId) ON UPDATE CASCADE ON DELETE CASCADE
);

desc Items;

create table Customers (
customerId varchar(50) PRIMARY KEY,
name varchar(50),
address varchar(50),
tp varchar(50)
);

desc Customers;

create table Orders (
orderId varchar(50) PRIMARY KEY,
customerId varchar(50),
date date,
time varchar(50),
amount double(20,2),
type varchar(50),
FOREIGN KEY (customerId) references Customers(customerId) ON UPDATE CASCADE ON DELETE CASCADE
);

desc Orders;

create table OrderDetails (
itemCode varchar(50),
orderId varchar(50),
FOREIGN KEY(itemCode) references Items(itemCode) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(orderId) references Orders(orderId) ON UPDATE CASCADE ON DELETE CASCADE
);

desc OrderDetails;

create table Returns (
returnId varchar(50) PRIMARY KEY,
date date,
time varchar(50),
description varchar(50)
);

desc Returns;

create table ReturnDetails (
returnId varchar(50),
itemCode varchar(50),
FOREIGN KEY(returnId) references Returns(returnId) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(itemCode) references Items(itemCode) ON UPDATE CASCADE ON DELETE CASCADE
);

desc ReturnDetails;



