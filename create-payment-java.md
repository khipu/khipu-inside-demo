#Crear un pago utilizando el cliente Java


Para crear un pago se utiliza el método paymentsPost de la clase PaymentsApi

```java
Long receiverId = <ID de cobrador>;
String secret = "<Llave secreta>";

ApiClient apiClient = new ApiClient();
apiClient.setKhipuCredentials(receiverId, secret);
PaymentsApi paymentsApi = new PaymentsApi();
paymentsApi.setApiClient(apiClient);

Map<String, Object> options = new HashMap<>();

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
> ./gradlew run

Task :run  
PAYMENT_ID: xxxxyyyyzzzz
``` 

**Maven**

```sh
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
