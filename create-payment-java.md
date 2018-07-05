El cliente se distribuye como un .jar y se recomienda utilizar para proyectos basados en [Gradle](https://gradle.org/) o [Maven](https://maven.apache.org/).

**Gradle**

```
> cd server/java/gradle  
> ./gradlew run

Task :run  
PAYMENT_ID: xxxxyyyyzzzz
``` 

**Maven**

```
> cd server/java/maven  
> mvn exec:java

[INFO] Scanning for projects...
[INFO]
[INFO] --------------------< com.khipu:khipu-inside-demo >---------------------
[INFO] Building khipu-inside-demo 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] >>> exec-maven-plugin:1.2.1:java (default-cli) > validate @ khipu-inside-demo >>>
[INFO]
[INFO] <<< exec-maven-plugin:1.2.1:java (default-cli) < validate @ khipu-inside-demo <<<
[INFO]
[INFO]
[INFO] --- exec-maven-plugin:1.2.1:java (default-cli) @ khipu-inside-demo ---
PAYMENT_ID: xxxxyyyyzzzz
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 2.121 s
[INFO] Finished at: 2018-07-05T12:32:00-04:00
[INFO] ------------------------------------------------------------------------
``` 
