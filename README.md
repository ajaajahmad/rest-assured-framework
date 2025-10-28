# REST Assured Framework

A scalable and maintainable **REST Assured** API testing framework using Java and TestNG. This framework incorporates best practices and essential tools such as configuration management, logging, reporting with ExtentReports, and build automation via Maven.

![Java](https://img.shields.io/badge/Java-11%2B-orange)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue)
![REST Assured](https://img.shields.io/badge/REST%20Assured-5.3.0-green)
![TestNG](https://img.shields.io/badge/TestNG-7.7.1-red)

---

## Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Running Tests](#running-tests)
- [Reporting](#reporting)
- [Sample Test Cases](#sample-test-cases)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- ✅ **REST Assured** for API testing with fluent interface
- ✅ **TestNG** as the test framework with parallel execution support
- ✅ **Maven** for dependency management and build automation
- ✅ **Configuration management** via property files for multiple environments
- ✅ **Logging** with SLF4J + Logback for detailed test execution logs
- ✅ **HTML reporting** with ExtentReports for rich visual reports
- ✅ **Data-driven testing** support with JSON/CSV files
- ✅ **Custom listeners** for enhanced test lifecycle management
- ✅ **Request/Response specifications** for reusability
- ✅ **POJO serialization/deserialization** using Jackson
- ✅ **Sample test cases** demonstrating GET, POST, PUT, DELETE API calls
- ✅ **JSON Schema validation**
- ✅ **Authentication support** (Basic, OAuth2, API Key)

---

## Project Structure

```
rest-assured-framework/
├── src/
│   ├── main/
│   │   └── java/com/automation/
│   │       ├── api/
│   │       │   ├── ApiClient.java              # Base API client
│   │       │   ├── RequestSpecBuilder.java     # Request spec builder
│   │       │   └── ResponseSpecBuilder.java    # Response spec builder
│   │       ├── config/
│   │       │   ├── ConfigReader.java           # Property file reader
│   │       │   └── EndpointConfig.java         # API endpoints
│   │       ├── models/
│   │       │   ├── User.java                   # User POJO
│   │       │   ├── CreateUserRequest.java      # Request POJOs
│   │       │   └── UserResponse.java           # Response POJOs
│   │       └── utils/
│   │           ├── JsonUtil.java               # JSON utilities
│   │           ├── DateUtil.java               # Date utilities
│   │           └── DataProviderUtil.java       # TestNG data providers
│   └── test/
│       ├── java/com/automation/
│       │   ├── base/
│       │   │   └── BaseTest.java               # Base test class
│       │   ├── listeners/
│       │   │   ├── ExtentReportListener.java   # ExtentReports listener
│       │   │   └── TestListener.java           # Custom TestNG listener
│       │   └── tests/
│       │       ├── UserApiTest.java            # User API tests
│       │       ├── ProductApiTest.java         # Product API tests
│       │       └── AuthenticationTest.java     # Auth tests
│       └── resources/
│           ├── configs/
│           │   ├── dev.properties              # Development environment
│           │   ├── qa.properties               # QA environment
│           │   └── prod.properties             # Production environment
│           ├── testdata/
│           │   ├── users.json                  # Test data files
│           │   └── products.csv
│           ├── schemas/
│           │   └── user-schema.json            # JSON schemas
│           ├── extent-config.xml               # ExtentReports config
│           ├── logback.xml                     # Logging configuration
│           └── testng.xml                      # TestNG suite configuration
├── logs/                                        # Generated log files
├── reports/                                     # Generated test reports
├── target/                                      # Maven build output
├── .gitignore
├── pom.xml                                      # Maven dependencies
└── README.md
```

---

## Prerequisites

Before setting up the framework, ensure you have:

- **Java JDK 11** or above ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6** or above ([Download](https://maven.apache.org/download.cgi))
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code with Java extensions)
- **Git** (optional, for cloning the repository)
- Internet connection for downloading dependencies

### Verify Installation

```bash
java -version
mvn -version
```

---

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/rest-assured-framework.git
cd rest-assured-framework
```

### 2. Install Dependencies

```bash
mvn clean install
```

This command will:
- Download all required dependencies
- Compile the source code
- Run tests (use `mvn clean install -DskipTests` to skip tests)

### 3. Import Project into IDE

**IntelliJ IDEA:**
1. Open IntelliJ IDEA
2. File → Open → Select `pom.xml`
3. Choose "Open as Project"
4. Wait for Maven to download dependencies

**Eclipse:**
1. File → Import → Maven → Existing Maven Projects
2. Browse to project directory
3. Finish

---

## Configuration

### Environment Configuration

Edit the property files in `src/test/resources/configs/`:

**dev.properties:**
```properties
base.url=https://api.dev.example.com
api.key=your_dev_api_key
timeout=30
environment=DEV
```

**qa.properties:**
```properties
base.url=https://api.qa.example.com
api.key=your_qa_api_key
timeout=30
environment=qa
```

### Logging Configuration

Edit `src/test/resources/logback.xml`:

```xml
<configuration>
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>logs/test-execution.log</file>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    <root level="INFO">
        <appender-ref ref="FILE" />
    </root>
</configuration>
```

### TestNG Configuration

Edit `src/test/resources/testng.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="API Test Suite" parallel="tests" thread-count="3">
    <listeners>
        <listener class-name="com.example.listeners.ExtentReportListener"/>
        <listener class-name="com.example.listeners.TestListener"/>
    </listeners>
    
    <test name="User API Tests">
        <classes>
            <class name="com.example.tests.UserApiTest"/>
        </classes>
    </test>
    
    <test name="Product API Tests">
        <classes>
            <class name="com.example.tests.ProductApiTest"/>
        </classes>
    </test>
</suite>
```

---

## Usage

### Basic API Test Example

```java
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends BaseTest {
    
    @Test
    public void testGetUser() {
        given()
            .baseUri(configReader.getBaseUrl())
            .header("Authorization", "Bearer " + configReader.getApiKey())
            .pathParam("id", 1)
        .when()
            .get("/users/{id}")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("name", notNullValue());
    }
    
    @Test
    public void testCreateUser() {
        User user = new User("John Doe", "john@example.com");
        
        given()
            .baseUri(configReader.getBaseUrl())
            .header("Content-Type", "application/json")
            .body(user)
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            .body("name", equalTo("John Doe"));
    }
}
```

---

## Running Tests

### Run All Tests

```bash
mvn clean test
```

### Run Specific Test Suite

```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### Run Specific Test Class

```bash
mvn clean test -Dtest=UserApiTest
```

### Run Specific Test Method

```bash
mvn clean test -Dtest=UserApiTest#testGetUser
```

### Run Tests with Environment

```bash
mvn clean test -Denvironment=qa
```

### Run Tests in Parallel

```bash
mvn clean test -DthreadCount=5
```

### Run Tests with Custom Properties

```bash
mvn clean test -Dbase.url=https://custom-api.com -Dapi.key=custom_key
```

---

## Reporting

### ExtentReports

After test execution, HTML reports are generated in:
```
reports/ExtentReport_<timestamp>.html
```

**Features:**
- Interactive dashboard with pass/fail statistics
- Detailed test steps with request/response logs
- Screenshots for failures
- Test execution timeline
- Environment details

**Opening Reports:**
```bash
# On Windows
start reports/ExtentReport_<timestamp>.html

# On Mac/Linux
open reports/ExtentReport_<timestamp>.html
```

### TestNG Reports

Default TestNG reports are generated in:
```
target/surefire-reports/index.html
```

### Log Files

Detailed execution logs are available in:
```
logs/test-execution.log
```

---

## Sample Test Cases

### GET Request Example

```java
@Test(description = "Verify GET request returns user details")
public void testGetUserById() {
    Response response = given()
        .spec(requestSpec)
        .pathParam("id", 1)
    .when()
        .get("/users/{id}")
    .then()
        .spec(responseSpec)
        .statusCode(200)
        .extract().response();
    
    User user = response.as(User.class);
    Assert.assertNotNull(user.getName());
    logger.info("User retrieved: " + user.getName());
}
```

### POST Request Example

```java
@Test(description = "Verify POST request creates new user")
public void testCreateUser() {
    CreateUserRequest request = CreateUserRequest.builder()
        .name("Jane Doe")
        .email("jane@example.com")
        .build();
    
    Response response = given()
        .spec(requestSpec)
        .body(request)
    .when()
        .post("/users")
    .then()
        .statusCode(201)
        .extract().response();
    
    UserResponse userResponse = response.as(UserResponse.class);
    Assert.assertEquals(userResponse.getName(), "Jane Doe");
}
```

### Data-Driven Test Example

```java
@Test(dataProvider = "userData", dataProviderClass = DataProviderUtil.class)
public void testCreateMultipleUsers(String name, String email) {
    given()
        .spec(requestSpec)
        .body(new User(name, email))
    .when()
        .post("/users")
    .then()
        .statusCode(201)
        .body("name", equalTo(name));
}
```

### JSON Schema Validation

```java
@Test(description = "Verify response matches JSON schema")
public void testUserResponseSchema() {
    given()
        .spec(requestSpec)
    .when()
        .get("/users/1")
    .then()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
}
```

---

## Troubleshooting

### Common Issues

**1. Dependencies Not Downloading**
```bash
# Clear Maven cache and reinstall
mvn dependency:purge-local-repository
mvn clean install
```

**2. Tests Not Running**
```bash
# Verify TestNG suite file
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

**3. Connection Timeout**
- Check base URL in properties file
- Verify network connectivity
- Increase timeout in configuration

**4. Authentication Failures**
- Verify API key is correct
- Check token expiration
- Ensure proper header format

**5. JSON Parsing Errors**
- Validate JSON structure
- Check POJO field mappings
- Enable request/response logging

### Enable Debug Logging

Add to `logback.xml`:
```xml
<root level="DEBUG">
    <appender-ref ref="FILE" />
    <appender-ref ref="CONSOLE" />
</root>
```

---

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -m 'Add YourFeature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

### Coding Standards
- Follow Java naming conventions
- Write meaningful test descriptions
- Add comments for complex logic
- Update documentation for new features
- Ensure all tests pass before submitting

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Contact & Support

- **Issues:** [GitHub Issues](https://github.com/yourusername/rest-assured-framework/issues)
- **Documentation:** [Wiki](https://github.com/yourusername/rest-assured-framework/wiki)

---

## Acknowledgments

- [REST Assured](https://rest-assured.io/) - REST API testing library
- [TestNG](https://testng.org/) - Testing framework
- [ExtentReports](https://www.extentreports.com/) - Reporting library
- [Maven](https://maven.apache.org/) - Build automation tool

---

**Happy Testing! 🚀**
