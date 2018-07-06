# Validar un pago utilizando el cliente Java

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

Para validar un pago se debe disponibilizar un endpoint web que se referencia como "notifyUrl" al momento de crear el pago. Ese endpoint va a ser consumido por khipu cuando un pago esté conciliado usando dos campos POST, **notification_token** y **notification_api_version**.

La versión actual de la api de notificación es la 1.3.

Con el **notification_token** se debe usar el método paymentsGet del PaymentsApiClient y usar el campo **transactionId** para asociarlo a un proceso de pago previamente iniciado, verificar el monto y el campo Status sea "done".

```java
Long receiverId = Long.parseLong(readSingleLineFile("../../RECEIVER_ID").trim()); //ID de cobrador
String secret = readSingleLineFile("../../SECRET").trim(); //llave secreta

String notificationToken = readSingleLineFile("../../NOTIFICATION_TOKEN").trim(); //token de un pago

ApiClient apiClient = new ApiClient();
apiClient.setKhipuCredentials(receiverId, secret);
PaymentsApi paymentsApi = new PaymentsApi();
paymentsApi.setApiClient(apiClient);

PaymentsResponse response = paymentsApi.paymentsGet(notificationToken);

System.out.println("PAYMENT_ID: " + response.getPaymentId());
System.out.println("TRANSACTION_ID: " + response.getTransactionId());
System.out.println("AMOUNT: " + response.getAmount());
System.out.println("CURRENCY: " + response.getCurrency());
System.out.println("STATUS: " + response.getStatus());
```

Se pueden ejecutar ejemplos usando gradle o maven


**Gradle**

```sh
> cd server/java/gradle  
> ./gradlew -PmainClass=ValidatePayment run

PAYMENT_ID: zsiace8tacgf
TRANSACTION_ID: TX-1234
AMOUNT: 100.0
CURRENCY: CLP
STATUS: done
``` 

**Maven**

```sh
> cd server/java/maven  
> mvn compile && mvn exec:java -Dexec.mainClass="ValidatePayment"
