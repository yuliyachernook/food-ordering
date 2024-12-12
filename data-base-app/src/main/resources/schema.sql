CREATE TABLE User (
    uuid UUID PRIMARY KEY,
    login VARCHAR(255),
    password VARCHAR(255),
    creationDateTime TIMESTAMP,
    userRoleEnum VARCHAR(255)
);

CREATE TABLE Product (
    uuid UUID PRIMARY KEY,
    itemName VARCHAR(255),
    price DOUBLE,
    description VARCHAR(255),
    category VARCHAR(255),
    discountPercentage INTEGER,
    image BYTEA,
    creationDateTime TIMESTAMP
);

CREATE TABLE Cart (
    uuid UUID PRIMARY KEY,
    creationDateTime TIMESTAMP
);

CREATE TABLE CartItem (
    uuid UUID PRIMARY KEY,
    cartUuid UUID,
    productUuid UUID,
    quantity INTEGER,
    FOREIGN KEY (cartUuid) REFERENCES Cart(uuid),
    FOREIGN KEY (productUuid) REFERENCES Product(uuid)
);

CREATE TABLE Coupon (
    uuid UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    code VARCHAR(255) NOT NULL UNIQUE,
    couponType VARCHAR(255) NOT NULL,
    discount INTEGER,
    availableUses INTEGER
);

CREATE TABLE Customer (
    uuid UUID PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    phoneNumber VARCHAR(255),
    email VARCHAR(255),
    creationDateTime TIMESTAMP,
    balance DOUBLE,
    userUuid UUID,
    cartUuid UUID,
    FOREIGN KEY (userUuid) REFERENCES User(uuid),
    FOREIGN KEY (cartUuid) REFERENCES Cart(uuid)
);

CREATE TABLE DeliveryAddress (
    uuid UUID PRIMARY KEY,
    city VARCHAR(255),
    street VARCHAR(255),
    house VARCHAR(255),
    apartment INTEGER,
    customerUuid UUID,
    FOREIGN KEY (customerUuid) REFERENCES Customer(uuid)
);

CREATE TABLE Orders (
    uuid UUID PRIMARY KEY,
    customerUuid UUID,
    deliveryAddressUuid UUID,
    totalPrice DOUBLE,
    orderStatus VARCHAR(255),
    comment VARCHAR(255),
    creationDateTime TIMESTAMP,
    FOREIGN KEY (customerUuid) REFERENCES Customer(uuid),
    FOREIGN KEY (deliveryAddressUuid) REFERENCES DeliveryAddress(uuid)
);

CREATE TABLE OrderItem (
    uuid UUID PRIMARY KEY,
    orderUuid UUID,
    productUuid UUID,
    price DOUBLE,
    quantity INTEGER,
    FOREIGN KEY (orderUuid) REFERENCES Orders(uuid),
    FOREIGN KEY (productUuid) REFERENCES Product(uuid)
);
