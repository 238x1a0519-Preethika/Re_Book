\# ReBook – Student Book Marketplace



ReBook is a student-focused book marketplace designed to help students donate, exchange, or sell books they no longer need. The project aims to make educational resources more accessible and encourage book reuse among students.



\## Features



\- \*\*User Registration and Login\*\* – Pages for user account access.

\- \*\*Dashboard\*\* – A central page to navigate the application.

\- \*\*Book Listings\*\* – Pages for listing and viewing books.

\- \*\*User Profile\*\* – A page for profile-related information.

\- \*\*Backend APIs\*\* – REST endpoints built using Spring Boot.

\- \*\*Database Integration\*\* – MySQL connectivity for storing application data.



\## Technologies Used



\- \*\*Backend:\*\* Java, Spring Boot

\- \*\*Frontend:\*\* HTML, CSS, JavaScript

\- \*\*Database:\*\* MySQL

\- \*\*Build Tool:\*\* Maven

\- \*\*Version Control:\*\* Git and GitHub



\## Project Structure



```text

ReBook/

├── src/

│   ├── main/

│   │   ├── java/com/rebook/rebook/

│   │   │   ├── config/

│   │   │   ├── controller/

│   │   │   ├── dto/

│   │   │   ├── entity/

│   │   │   └── repository/

│   │   └── resources/

│   │       ├── static/

│   │       └── application.properties

│   └── test/

├── pom.xml

├── mvnw

├── mvnw.cmd

└── README.md

```



\## Prerequisites



\- Java 21

\- MySQL

\- Git



\## Run Locally



1\. Clone this repository:



&#x20;  ```bash

&#x20;  git clone https://github.com/238x1a0519-Preethika/Re\_Book.git

&#x20;  cd Re\_Book

&#x20;  ```



2\. Create a MySQL database named `rebook\_db`.



3\. Set the `DB\_PASSWORD` environment variable to your MySQL password in your terminal. Do not commit your real database password.



&#x20;  \*\*PowerShell:\*\*



&#x20;  ```powershell

&#x20;  $env:DB\_PASSWORD = "your\_mysql\_password"

&#x20;  ```



4\. Start the application:



&#x20;  ```powershell

&#x20;  .\\mvnw.cmd spring-boot:run

&#x20;  ```



5\. Open the dashboard in your browser:



&#x20;  ```text

&#x20;  http://localhost:8080/dashboard.html

&#x20;  ```



&#x20;  If you configured a different server port, replace `8080` with that port.



\## Future Improvements



\- Complete and test book donation, exchange, and sale workflows.

\- Add search and filtering for book listings.

\- Improve input validation and security.

\- Deploy the application for public access.



\## Author



Developed as a BTech project.

