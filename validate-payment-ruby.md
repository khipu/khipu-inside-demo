# Validar un pago utilizando el cliente Ruby

El cliente se distribuye como la Gema [khipu-api-client](https://rubygems.org/gems/khipu-api-client), se recomienda usar [Bundler](https://bundler.io/) para manejar las dependencias.

Para validar un pago se debe disponibilizar un endpoint web que se referencia como "notify_url" al momento de crear el pago. Ese endpoint va a ser consumido por khipu cuando un pago esté conciliado usando dos campos POST, **notification_token** y **notification_api_version**.

La versión actual de la api de notificación es la 1.3.

Con el **notification_token** se debe usar el método payments_get del Khipu::PaymentsApi y usar el campo **transaction_id** para asociarlo a un proceso de pago previamente iniciado, verificar el monto y el campo **status** sea "done".

```ruby
require 'khipu-api-client'

Khipu.configure do |c|
  c.secret = File.read("../SECRET").strip
  c.receiver_id = File.read("../RECEIVER_ID").strip
  c.platform = 'demo-client'
  c.platform_version = '2.0'
  #c.debugging = true
end

client = Khipu::PaymentsApi.new

response = client.payments_get(File.read("../NOTIFICATION_TOKEN").strip)

print "PAYMENT_ID: " + response.payment_id + "\n"
print "TRANSACTION_ID: " + response.transaction_id + "\n"
print "AMOUNT: " +  response.amount.to_s + "\n"
print "CURRENCY: " + response.currency + "\n"
print "STATUS: " + response.status + "\n"
```

Este repositorio incluye un ejemplo de validación de un pago:



```
> cd server/ruby
> ruby validate_payment.rb
PAYMENT_ID: kht7s9utffa6
TRANSACTION_ID: TX-1234
AMOUNT: 100
CURRENCY: CLP
STATUS: done
```
