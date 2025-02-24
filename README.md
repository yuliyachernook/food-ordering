# Food Ordering Web Application

## Description

"Food Ordering" is a web application that allows users to browse the menu and place orders using a coupon discount system. The platform is designed for two user roles: administrators and customers.

## Application features

- Adminstrators can:
  - manage products, including setting discounts;
  - manage coupons;
  - manage orders.

- Customers can:
  - browse and select products;
  - manage shopping cart;
  - place orders while applying discount coupons;
  - edit delivery addresses and other personal information in their accounts;
  - view available coupons.

One of the key features of the app is that customers can browse and add items to the shopping cart without the need to log in. Once they authenticate, the items previously added to the cart are transferred to the cart of the customer account, ensuring a convenient shopping experience.

## Technologies

- **Backend**:
  - **Programming Language**: Java
  - **Frameworks**: Spring, Hibernate
  - **Database**: PostgreSQL
  - **Migration Management**: Liquibase

- **Frontend**:
  - Thymeleaf, HTML, CSS

- **Testing**:
  - **Frameworks**: JUnit, Mockito

- **Build and Deployment**:
  - **Tools**: Maven, Docker, Nginx
