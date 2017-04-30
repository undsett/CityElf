# CityElf

1. To build project in the console, you should:  
**./gradlew build**  
Next step:  
**java -jar build/libs/cityelf-0.0.1-SNAPSHOT.jar**  
2. For Spring to work correctly with Tomcat, make sure that the IDEA 2016 version is at least 3.6  
3. For Spring, you need to choose port 8088, with which tomcat is started.  
4. If you receive this error **java.lang.NoClassDefFoundError: org/springframework/boot/SpringApplication** then look at point 2.
5. Link for test http://localhost:8088/services/users/all  
