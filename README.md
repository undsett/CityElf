# CityElf  

Travis build: 
[![Build Status](https://travis-ci.org/undsett/CityElf.svg?branch=master)](https://travis-ci.org/undsett/CityElf)  

1. To build project in the console, you should:  
**./gradlew build**  
Next step:  
**java -jar build/libs/cityelf-0.0.1-SNAPSHOT.jar**  
2. For Spring to work correctly with Tomcat, make sure that the IDEA 2016 version is at least 3.6  
3. For Spring, you need to choose port 8088, with which tomcat is started.  
4. If you receive this error **java.lang.NoClassDefFoundError: org/springframework/boot/SpringApplication** then look at point 2.
5. Link for test http://localhost:8088/services/users/all 
6. For MailSenderService you need to create new system environment variable **CITYELF_EMAIL_PASSWORD** with email password as a value and reboot your PC.
  - for Windows: click right mouse button on your PC icon and choose "Properties" -> "Advanced system settings" -> "Advanced" -> "Environment Variables" -> new System Variable.
  Or run in command line (with administrator rules):<br> 
  **`setx -m CITYELF_EMAIL_PASSWORD _pass_`**
  - for Unix: open a terminal, type<br>
   **`sudo -H gedit /etc/environment`**<br> 
   and enter your password. Edit the text file just opened: write<br> 
   _`CITYELF_EMAIL_PASSWORD=pass`_ <br>
   in a new line and save it. Once saved, logout and login again.
7. If you start application from Idea by execution _CityElfApplication_ class you may just add an environment variable to run configuration:
 press Run -> Edit Configurations. Choose CityElfApplication and press edit "Environment variables" field. Press the button "..." and add new variable (look at point 6).
8. Also you need to introduce CITYELF_DB_PASSWORD and FIREBASE_SERVER_KEY variables. You can do it using instruction at point 6-7.
