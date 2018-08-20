# real-estate-scraper
In order to build the project you need to have Maven and JDK1.8 or higher installed.

1. Build command: mvn clean package
2. Go to /target directory
3. Run command: java -jar <NAME_OF_JAR>
4. Open Chrome (not tested on other browsers) and hit http://localhost:8080
5. When done: Ctrl+c or kill -9 <PID> to kill process

Steps 1 to 3, can be replaced with mvn spring-boot:run but it performs whole build cycle each time.

Notes: H2 embedded database is used for persistance. The database is by default persisted in user's home directory.
If you want to set the database file path of your choice, go to application.properties and edit the following line:
spring.datasource.url=jdbc:h2:file:<YOUR_PATH/DB_FILE_NAME>;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE.
