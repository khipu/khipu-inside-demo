#Crear un pago utilizando el cliente PHP

El cliente se distribuye como una bibloteca PHP [khipu-api-client](https://packagist.org/packages/khipu/khipu-api-client) y se instala usando [Composer](https://getcomposer.org/).

Para crear un pago se utiliza el método paymentsPost del objeto Khipu\Client\PaymentsApi

```php
require __DIR__ . '/vendor/autoload.php';

$configuration = new Khipu\Configuration();
$configuration->setSecret(<Llave secreta>);
$configuration->setReceiverId(<ID de cobrador>);
$configuration->setPlatform('demo-client', '2.0');
# $configuration->setDebug(true);
$client = new Khipu\ApiClient($configuration);
$payments = new Khipu\Client\PaymentsApi($client);

try {
    $ops = array(
    );
    $response = $payments->paymentsPost('Pago de demo', 'CLP', 1000, $ops);

    print "PAYMENT_ID: " . $response->getPaymentId() . "\n";

} catch (Exception $e) {
    echo $e->getMessage();
}
```

Este repositorio incluye un ejemplo de creación de un pago:


**Instalación del cliente**

```
> cd server/php
> composer install

Loading composer repositories with package information
Updating dependencies (including require-dev)
Package operations: 1 install, 0 updates, 0 removals
  - Installing khipu/khipu-api-client (2.7.7): Loading from cache
Writing lock file
Generating autoload files
```

**Ejecución de la demo**

```
> cd server/php
> php demo.php

PAYMENT_ID: xxxxyyyyzzzz
```

