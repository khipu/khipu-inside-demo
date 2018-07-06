# Validar un pago utilizando el cliente PHP

El cliente se distribuye como la bibloteca PHP [khipu-api-client](https://packagist.org/packages/khipu/khipu-api-client) y se instala usando [Composer](https://getcomposer.org/).

Para validar un pago se debe disponibilizar un endpoint web que se referencia como "notify_url" al momento de crear el pago. Ese endpoint va a ser consumido por khipu cuando un pago esté conciliado usando dos campos POST, **notification_token** y **notification_api_version**.

La versión actual de la api de notificación es la 1.3.

Con el **notification_token** se debe usar el método paymentsGet del PaymentsApiClient y usar el campo **transactionId** para asociarlo a un proceso de pago previamente iniciado, verificar el monto y el campo Status sea "done".

```php
require __DIR__ . '/vendor/autoload.php';

$configuration = new Khipu\Configuration();
$configuration->setSecret(trim(file_get_contents("../SECRET")));
$configuration->setReceiverId(trim(file_get_contents("../RECEIVER_ID")));
$configuration->setPlatform('demo-client', '2.0');
$notificationToken = trim(file_get_contents("../NOTIFICATION_TOKEN"));
# $configuration->setDebug(true);
$client = new Khipu\ApiClient($configuration);
$payments = new Khipu\Client\PaymentsApi($client);

try {
    $response = $payments->paymentsGet($notificationToken);

    print "PAYMENT_ID: " . $response->getPaymentId() . "\n";
    print "TRANSACTION_ID: " . $response->getTransactionId() . "\n";
    print "AMOUNT: " . $response->getAmount() . "\n";
    print "CURRENCY: " . $response->getCurrency() . "\n";
    print "STATUS: " . $response->getStatus() . "\n";

} catch (Exception $e) {
    echo $e->getMessage();
}
```

Este repositorio incluye un ejemplo de validación de un pago:



```
> cd server/php
> php validate_payment.php
PAYMENT_ID: kht7s9utffa6
TRANSACTION_ID: TX-1234
AMOUNT: 100
CURRENCY: CLP
STATUS: done
```
