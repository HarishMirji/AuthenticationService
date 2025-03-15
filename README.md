# 🔐 Authentication Service with Spring Security & JWT  

## 📌 Overview  
The **Authentication Service** provides a secure way to handle user authentication and authorization using **Spring Security** and **JWT (JSON Web Token)**. It ensures protected API access, user identity verification, and role-based authorization.  

## 🚀 Features  
✅ **User Authentication & Authorization** (Login & Register)  
✅ **JWT-based Token Generation & Validation**  
✅ **Role-Based Access Control (RBAC)**  
✅ **Spring Security Integration** for secure API endpoints  
✅ **Token Refresh Mechanism** for seamless authentication  

## 🏗️ System Components  

### **User Authentication**  
- Users can register and log in using a **username & password**.  
- Passwords are **securely hashed** using **BCrypt**.  

### **JWT Token Generation & Validation**  
- Generates **JWT tokens** upon successful authentication.  
- Tokens are **signed** and **validated** before granting API access.  

### **Spring Security Configuration**  
- Secures endpoints based on user **roles** and **permissions**.  
- Implements a **JWT filter** for validating requests.  

### **User Role-Based Authorization**  
- Supports **Admin, User, and other custom roles**.  
- Access control based on **granted authorities**.  

## ⚙️ Technologies Used  
- **Java**, **Spring Boot**  
- **Spring Security** for authentication & authorization  
- **JWT (JSON Web Token)** for secure token-based authentication  
- **BCrypt** for password hashing  
- **Spring Data JPA & MySQL** for user data storage  

## 📖 API Endpoints  

### **Authentication Endpoints**  
| Method | Endpoint        | Description          |
|--------|----------------|----------------------|
| POST   | `/auth/register`  | Register a new user |
| POST   | `/auth/login`     | Authenticate user & return JWT |
| GET    | `/auth/refresh`   | Refresh JWT token |

### **Protected Endpoints (Require Authentication)**  
| Method | Endpoint       | Access  |
|--------|---------------|---------|
| GET    | `/user/profile` | User   |
| GET    | `/admin/dashboard` | Admin |
