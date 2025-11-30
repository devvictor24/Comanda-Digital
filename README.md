

# 📋 README — Comanda Digital

## 🍽️ Sobre o Projeto

O Comanda Digital é um sistema desenvolvido em Java Spring Boot para gerenciar pedidos, pratos e pagamentos em um restaurante.
Ele oferece uma API REST completa para cadastro de pratos, criação e acompanhamento dos pedidos.



## 🚀 Tecnologias Utilizadas

* Java 17+
* Spring Boot (Web, JPA, Validation)
* H2 Database
* Maven

---

## 📁 Estrutura do Projeto

```
Comanda-Digital/
 ├── controller/
 ├── model/
 ├── repository/
 ├── service/
 ├── config/
 └── ComandaDigitalApplication.java
```

---

## 🧩 Funcionalidades

### 🥘 Pratos

* Cadastrar
* Listar
* Editar
* Deletar

### 📦 Pedidos

* Criar pedido
* Adicionar itens
* Acompanhar status
* Finalizar

---

## 🌐 Endpoints da API

### **Pratos (`/api/dishes`)**

| Método | Endpoint           | Função        |
| ------ | ------------------ | ------------- |
| GET    | `/api/dishes`      | Listar pratos |
| POST   | `/api/dishes`      | Criar prato   |
| PUT    | `/api/dishes/{id}` | Atualizar     |
| DELETE | `/api/dishes/{id}` | Deletar       |

### **Pedidos (`/api/orders`)**

| Método | Endpoint                  | Função           |
| ------ | ------------------------- | ---------------- |
| POST   | `/api/orders`             | Criar pedido     |
| GET    | `/api/orders`             | Listar pedidos   |
| GET    | `/api/orders/{id}`        | Ver pedido       |
| PUT    | `/api/orders/{id}/status` | Atualizar status |

---

## ▶️ Como Rodar

```bash
mvn spring-boot:run
```

---

## 🗄️ Console H2

Acesso:

```
http://localhost:8080/h2-console
```

Credenciais padrão:

```
JDBC URL: jdbc:h2:mem:comanda
User: sa
Password: 
```

---

## 👨‍💻 Autor

Projeto desenvolvido para estudo e prática com Spring Boot.


