#Crear un pago utilizando el cliente Java

El cliente se distribuye como el artefacto java [khipu-api-client](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.khipu%22%20AND%20a%3A%22khipu-api-client%22) y se recomienda utilizar gradle o maven.

**gradle**

```gradle
compile 'com.khipu:khipu-api-client:2.7.7'
```


**maven**

```xml
<dependency>
    <groupId>com.khipu</groupId>
    <artifactId>khipu-api-client</artifactId>
    <version>2.7.7</version>
</dependency>
```

Para crear un pago se utiliza el método paymentsPost de la clase PaymentsApi. Los campos motivo, monto y moneda son obligatorios según la API y recomendamos fuertemente usar el campo notifyUrl para especificar en que endpoint Khipu notificará al servidor del cobrador cuando el pago esté conciliado y validado.

```java
Long receiverId = <ID de cobrador>;
String secret = "<Llave secreta>";

ApiClient apiClient = new ApiClient();
apiClient.setKhipuCredentials(receiverId, secret);
PaymentsApi paymentsApi = new PaymentsApi();
paymentsApi.setApiClient(apiClient);

Map<String, Object> options = new HashMap<>();
options.put("notifyUrl", "http://mi-ecomerce.com/backend/notify");

PaymentsCreateResponse response = paymentsApi.paymentsPost("Pago de demo" //Motivo de la compra
        , "CLP" //Moneda
        , 100.0 //Monto
        , options
);

System.out.println("PAYMENT_ID: " + response.getPaymentId());
```

Se pueden ejecutar ejemplos usando gradle o maven


**Gradle**

```sh
> cd server/java/gradle  
> ./gradlew -PmainClass=CreatePayment run

Task :run  
PAYMENT_ID: xxxxyyyyzzzz
``` 

**Maven**

```sh
> cd server/java/maven  
> mvn compile && mvn exec:java -Dexec.mainClass="CreatePayment"

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
