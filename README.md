# Bank-management-springboot-app

### Bank Management System

#### Overview
The Bank Management System is a comprehensive web-based application designed to facilitate and streamline the management and operations of a bank. This system provides various functionalities for administrators, managers, and users, allowing them to perform different tasks based on their roles. The application leverages modern technologies such as Java, Spring Boot, Thymeleaf, and MySQL to deliver a robust, scalable, and secure solution.

#### Features

- **User Module**
  - User Registration: Allows users to create new accounts.
  - User Login: Authenticated users can log in to the system.
  - Profile Management: Users can view and update their profile information.
  - Account Management: Users can create, view, and delete bank accounts.

- **Admin Module**
  - Branch Management: Admins can create, update, and delete branches.
  - Role Management: Admins can assign and remove user roles.
  - User Management: Admins can view and manage all user profiles.

- **Manager Module**
  - Branch Operations: Managers can handle branch-specific operations.
  - Pending Requests: Managers can view and approve or reject pending requests from users.
  - Account Management: Managers can view and manage all bank accounts under their branch.

- **Security Module**
  - Authentication and Authorization: Managed using Spring Security.
  - Data Encryption: Ensures secure handling of sensitive data.

#### Technologies Used
- **Backend**: Java, Spring Boot
- **Frontend**: Thymeleaf, Bootstrap, CSS, JavaScript
- **Database**: MySQL
- **Tools**: Maven, JPA (Java Persistence API)
- **Server**: Apache Tomcat (embedded in Spring Boot)

#### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/bank-management-system.git
   cd bank-management-system
   ```

2. **Configure the database:**
   Update the `application.properties` file with your MySQL database credentials.

3. **Build the project:**
   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application:**
   Open your browser and navigate to `http://localhost:9191`.

#### Sample Code

- **User Profile Data HTML**
  ```html
  <!DOCTYPE html>
  <html lang="en" xmlns:th="http://www.thymeleaf.org">
  <head>
      <meta charset="UTF-8">
      <meta content="width=device-width, initial-scale=1.0" name="viewport">
      <title>User Profile Data</title>
      <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>
      <div class="container mt-5">
          <h2 class="text-uppercase fs-1 fw-bold text-primary mb-4">User Profile Information</h2>
          <div class="row mb-3">
              <label class="col-sm-3 col-form-label fw-bold">User ID:</label>
              <div class="col-sm-9">
                  <span class="text-uppercase" th:text="${user.getId()}">1</span>
              </div>
          </div>
          <!-- Other profile fields here -->
      </div>
  </body>
  </html>
  ```

- **Application Properties**
  ```properties
  spring.application.name=bankapplication
  spring.jpa.hibernate.ddl-auto=update
  spring.datasource.url=jdbc:mysql://localhost:3306/bankmanagement
  spring.datasource.username=root
  spring.datasource.password=root
  spring.thymeleaf.prefix=classpath:/templates/
  spring.thymeleaf.suffix=.html
  server.port=9191
  logging.file.path=logs
  logging.file=logs/spring-boot-logging.log
  logging.level.org.springframework.security=DEBUG
  ```

#### Contributing

Contributions are welcome! Please fork this repository and submit pull requests.

#### License

This project is licensed under the MIT License - see the `LICENSE` file for details.

---

Feel free to use this repository description in your GitHub project. If you need any modifications or additional information, let me know! 😊
