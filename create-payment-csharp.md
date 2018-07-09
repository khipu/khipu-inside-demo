#Crear un pago utilizando el cliente c#

El cliente se distribuye como el nuget [KhipuApiClient](https://www.nuget.org/packages/KhipuApiClient/).

Se puede instalar en un proyecto Visual Studio usando el Nuget Package Manager.

```
PM> Install-Package KhipuApiClient
```


Para crear un pago se utiliza el método PaymentsPost de la clase PaymentsApi. Los campos motivo, monto y moneda son obligatorios según la API y recomendamos fuertemente usar el campo **notifyUrl** para especificar en que endpoint Khipu notificará al servidor del cobrador cuando el pago esté conciliado y el campo **transactionId** para asociar el pago a un identificador propio del negocio, por ejemplo número de orden.

```csharp
Configuration.Secret = File.ReadAllText("../../../../../SECRET").Trim();
Configuration.ReceiverId = Int32.Parse(File.ReadAllText("../../../../../RECEIVER_ID").Trim());
PaymentsApi a = new PaymentsApi();

try
{
    PaymentsCreateResponse r = a.PaymentsPost("Pago de Demo", "CLP", 100, notifyUrl: "https://micomercio.com/notify", transactionId: "TX-1234");

    System.Console.WriteLine("PAYMENT_ID: " + r.PaymentId);
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
> KhipuInsideDemo.exe create

Task :run  
PAYMENT_ID: xxxxyyyyzzzz
``` 