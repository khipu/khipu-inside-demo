<?php
require __DIR__ . '/vendor/autoload.php';

$configuration = new Khipu\Configuration();
$configuration->setSecret(trim(file_get_contents("../SECRET")));
$configuration->setReceiverId(trim(file_get_contents("../RECEIVER_ID")));
$configuration->setPlatform('demo-client', '2.0');
# $configuration->setDebug(true);
$client = new Khipu\ApiClient($configuration);
$payments = new Khipu\Client\PaymentsApi($client);

try {
    $ops = array(
        "notify_url" => "http://mi-ecomerce.com/backend/notify",
        "transaction_id" => "TX-1234",
    );
    $response = $payments->paymentsPost('Pago de demo', 'CLP', 1000, $ops);

    print "PAYMENT_ID: " . $response->getPaymentId() . "\n";

} catch (Exception $e) {
    echo $e->getMessage();
}
