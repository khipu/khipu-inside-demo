# Validar un pago utilizando el cliente c#

El cliente se distribuye como el nuget [KhipuApiClient](https://www.nuget.org/packages/KhipuApiClient/).

Se puede instalar en un proyecto Visual Studio usando el Nuget Package Manager.

```
PM> Install-Package KhipuApiClient
```


Para validar un pago se debe disponibilizar un endpoint web que se referencia como "notifyUrl" al momento de crear el pago. Ese endpoint va a ser consumido por khipu cuando un pago esté conciliado usando dos campos POST, **notification_token** y **notification_api_version**.

La versión actual de la api de notificación es la 1.3.

Con el **notification_token** se debe usar el método PaymentsGet del PaymentsApi y usar el campo **transactionId** para asociarlo a un proceso de pago previamente iniciado, verificar el monto y el campo Status sea "done".

```csharp
Configuration.Secret = File.ReadAllText("../../../../../SECRET").Trim();
Configuration.ReceiverId = Int32.Parse(File.ReadAllText("../../../../../RECEIVER_ID").Trim());
String notificationToken = File.ReadAllText("../../../../../NOTIFICATION_TOKEN").Trim();

PaymentsApi a = new PaymentsApi();

try
{
    PaymentsResponse r = a.PaymentsGet(notificationToken);

    System.Console.WriteLine("PAYMENT_ID: " + r.PaymentId);
    System.Console.WriteLine("TRANSACTION_ID: " + r.TransactionId);
    System.Console.WriteLine("AMOUNT: " + r.Amount);
    System.Console.WriteLine("CURRENCY: " + r.Currency);
    System.Console.WriteLine("STATUS: " + r.Status);

}
catch (Exception e)
{
    Console.WriteLine(e);
}
System.Console.Read();
```

Se pueden ejecutar ejemplos compilando el proyecto usando Visual Studio y luego ejecutando:


```sh
> cd server/csharp/KhipuInsideDemo/KhipuInsideDemo/bin/Debug  
> KhipuInsideDemo.exe validate

PAYMENT_ID: zsiace8tacgf
TRANSACTION_ID: TX-1234
AMOUNT: 100.0
CURRENCY: CLP
STATUS: done
``` 