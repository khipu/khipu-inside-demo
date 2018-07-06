<?php
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
