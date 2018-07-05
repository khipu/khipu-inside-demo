#Crear un pago utilizando el cliente Ruby

El cliente se distribuye como la Gema [khipu-api-client](https://rubygems.org/gems/khipu-api-client), se recomienda usar [Bundler](https://bundler.io/) para manejar las dependencias.

Para crear un pago se utiliza el método client.payments_post del objeto Khipu::PaymentsApi

```ruby
require 'khipu-api-client'

Khipu.configure do |c|
c.secret = <Llave secreta>
c.receiver_id = <ID de cobrador>
c.platform = 'demo-client'
c.platform_version = '2.0'
#c.debugging = true
end

client = Khipu::PaymentsApi.new

response = client.payments_post('Pago de demo', 'CLP', 100, {
})

print "PAYMENT_ID: " + response.payment_id + "\n"
```
En el respositorio se encuentra un ejemplo de uso.

**Instalación de la gema**

```sh
> cd server/ruby
> bundle install

Using bundler 1.16.2
Using ffi 1.9.25
Using ethon 0.11.0
Using json 2.1.0
Using typhoeus 1.3.0
Using khipu-api-client 2.7.2
Bundle complete! 1 Gemfile dependency, 6 gems now installed.
Use `bundle info [gemname]` to see where a bundled gem is installe
```

**Ejecución de la demo**

```sh
> cd server/ruby
> ruby demo.rb

PAYMENT_ID: xxxxyyyyzzzz
```

