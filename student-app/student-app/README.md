# Student App: Spring Boot + JPA + Hibernate + MySQL

A complete, ready-to-run **Student CRUD REST API**. It is the finished project of the step-by-step tutorial at [rahmanziaur.github.io/spring.html](https://rahmanziaur.github.io/spring.html).

| | |
|---|---|
| **Framework** | Spring Boot 3.5 (Spring Web, Spring Data JPA, Validation) |
| **ORM** | Hibernate (through JPA) |
| **Database** | MySQL 8, database `student_db`, table `students` |
| **Build tool** | Maven |
| **Java** | JDK 17 or newer |
| **IDE** | IntelliJ IDEA (Community or Ultimate) |

## What you need installed

1. **JDK 17 or newer**. If you do not have one, IntelliJ can download it for you (see step 3 below).
2. **IntelliJ IDEA**. Maven is built in; you do not need to install Maven separately.
3. **MySQL Server 8** (and MySQL Workbench, recommended).
4. **Postman** or any REST client, for testing.

## How to run (5 steps)

### 1. Get the project

Either download the ZIP (**Code → Download ZIP** on GitHub) and unzip it, or clone it:

```bash
git clone <repository-url>
```

### 2. Create the database

Open **MySQL Workbench**, connect as `root`, open the file [`database/schema.sql`](database/schema.sql) (**File → Open SQL Script**) and run it with the ⚡ button.

It creates the database `student_db`, the table `students`, and three sample rows:

| Field | Type | Null | Key | Extra |
|---|---|---|---|---|
| id | int | NO | PRI | auto_increment |
| name | varchar(100) | YES | | |
| cgpa | float | YES | | |

### 3. Open the project in IntelliJ IDEA

1. **File → Open**, select the `student-app` folder (the one that contains `pom.xml`), click **OK**.
2. Choose **Trust Project**.
3. Wait for Maven to download the dependencies (progress bar at the bottom right). The first time needs an internet connection and can take a few minutes.
4. If IntelliJ says **"Project JDK is not defined"**: open **File → Project Structure → Project → SDK → Add SDK → Download JDK**, pick version 17 or newer, and click **OK**.

### 4. Set your MySQL password

Open `src/main/resources/application.properties` and change this line to your own MySQL `root` password:

```properties
spring.datasource.password=demo1234
```

> `demo1234` is a classroom placeholder. Do not commit your real password to GitHub.

### 5. Run

Open `src/main/java/com/example/studentapp/StudentAppApplication.java` and click the green ▶ arrow next to `main` (or press **Shift + F10**).

When the console shows `Started StudentAppApplication`, open <http://localhost:8080/hello> in a browser. You should see:

```
Hello World from Spring Boot!
```

Then open <http://localhost:8080/api/students> to see the students as JSON.

## API endpoints

Base URL: `http://localhost:8080`

| Method | URL | Body (JSON) | Result |
|---|---|---|---|
| GET | `/hello` | | Hello World text |
| GET | `/hello/{name}` | | Greeting with the name |
| GET | `/api/students` | | `200` list of all students |
| GET | `/api/students/{id}` | | `200` one student, or `404` |
| POST | `/api/students` | `{ "name": "Nusrat Jahan", "cgpa": 3.66 }` | `201` the created student |
| PUT | `/api/students/{id}` | `{ "name": "Rahim Uddin", "cgpa": 3.80 }` | `200` the updated student, or `404` |
| DELETE | `/api/students/{id}` | | `204`, or `404` |
| GET | `/api/students/search?name=ra` | | Students whose name contains the text |
| GET | `/api/students/top?minCgpa=3.5` | | Students with CGPA at or above the value, best first |

**Validation rules:** `name` is required and at most 100 characters; `cgpa` must be between 0.0 and 4.0. Invalid data returns `400` with messages, for example:

```json
{ "name": "Name is required", "cgpa": "CGPA cannot be above 4.0" }
```

### How to send the requests

- **Postman:** choose the method and URL from the table. For POST and PUT select **Body → raw → JSON**.
- **IntelliJ IDEA Ultimate:** open [`requests.http`](requests.http) and click the green ▶ next to any request.

Watch the IntelliJ console while you send requests: it prints the SQL that Hibernate generates.

## Project structure

```
student-app
├── pom.xml                         <- Maven dependencies
├── README.md
├── requests.http                   <- ready-made test requests
├── database
│   └── schema.sql                  <- creates the database, table and sample data
└── src/main
    ├── java/com/example/studentapp
    │   ├── StudentAppApplication.java   <- main class: run this
    │   ├── HelloController.java         <- Hello World endpoints
    │   ├── GreetingService.java         <- simple service (dependency injection demo)
    │   ├── Student.java                 <- JPA entity, mapped to the students table
    │   ├── StudentRepository.java       <- database access (Spring Data JPA)
    │   ├── StudentService.java          <- business logic
    │   ├── StudentController.java       <- REST API: /api/students
    │   └── GlobalExceptionHandler.java  <- turns validation errors into JSON
    └── resources
        └── application.properties      <- database connection and JPA settings
```

A request travels through the layers like this:

```
Client (browser / Postman)
   |  HTTP + JSON
   v
StudentController   (@RestController)
   v
StudentService      (@Service)
   v
StudentRepository   (@Repository, JpaRepository)
   |  SQL generated by Hibernate
   v
MySQL: student_db.students
```

## Troubleshooting

| Message in the console | Fix |
|---|---|
| `Access denied for user 'root'@'localhost'` | The password in `application.properties` is wrong (step 4). |
| `Unknown database 'student_db'` | Run `database/schema.sql` (step 2). |
| `Communications link failure` | MySQL Server is not running. Start the **MySQL80** service (Windows: Services app). |
| `Port 8080 was already in use` | Stop the other run (red ■), or uncomment `server.port=8081` in `application.properties` and use port 8081 in the URLs. |
| Red imports / `Cannot resolve symbol` | Open the **Maven** tool window (right edge) and click **Reload All Maven Projects**. Check your internet connection. |
| `Project JDK is not defined` or `release version 17 not supported` | Set a JDK 17+ in **File → Project Structure → Project → SDK**. |
| No green ▶ arrow next to `main` | You opened the wrong folder. Open the folder that directly contains `pom.xml`. |
| `415 Unsupported Media Type` | In Postman choose **Body → raw → JSON**. |

## Run without the IDE (optional)

If Maven is installed on your computer:

```bash
mvn spring-boot:run
```

## Courtesy

[rahmanziaur.github.io](https://rahmanziaur.github.io)
