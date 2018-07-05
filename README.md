# Khipu Inside

## Introducción

Khipu Chile es un medio de pago que opera en Chile. Permite a comercios cobrar electrónicamente y a clientes pagar con sus cuentas bancarias (Corrientes, Vistas, Chequeras electrónicas, etc.).

El uso estándar de Khipu requiere que el cliente pagador instale una aplicación móvil, el terminal de pagos Khipu.

Khipu Inside permite, a comercios que cuenten con una App (Android y/o iOS), embeber el proceso de autorización del pago directmente en su App, bajando, con esto, la fricción en el proceso de compra y, por lo tanto, aumentando la conversión del proceso completo.

Este repositorio es la documentación y ejemplo de como realizar una integración Khipu Inside. Se cubren los casos de aplicaciones Android e iOS y de servidores con tecnología Java, PHP, Ruby y .NET.

## Esquema general

## Pre-requisitos

1. Tener una cuenta de cobrador en [khipu.com](khipu.com). Existen cuentas de cobro regulares y en "modo desarrollador", en ambas el ciclo de creación, autorización y conciliación del pago es idéntica. La diferencia es que en las cuentas regulares se utilizan Bancos y dinero real mientras en las en "modo desarrollador" ficticios. Recomendamos usar una cuenta en "modo desarrollador" en el proceso de desarrollo y pruebas de la integración y en producción cambiar las credenciales de acceso a la API por las de una cuenta regular.
2. Un servidor capaz de conectarse por HTTPS a khipu.com y capaz de disponibilizar un endpoint web para recibir las notificaciones de conciliación de los pagos recibidos.
3. Una aplicación móvil para Android o IOS. En este documento se asumirá que la aplicación es nativa, pero existen plugins y bridges para otras tecnologías como Cordova, React Native, etc.


## Creación de la solicitud de pago (en el servidor del cobrador)

Para crear un solicitud de pago se utiliza la "[API de khipu para crear cobros y recibir pagos (v 2.0)](https://khipu.com/page/api)", esta es una API REST y Khipu disponibiliza clientes para esa API en Java, Ruby, PHP y .NET.

En la carpeta "server" de este repositorio se encuentra el código fuente de ejemplo implementado en los diferentes lenguajes.

En la misma carpeta estan los archivos **RECEIVER_ID** y **SECRET**, esos archivos contienen las credenciales de un comercio de pruebas que permite ejecutar los ejemplos. 

En todos los lenguajes el proceso es el mismo, primero se obtienen las credenciales del comercio (RECEIVER_ID y SECRET), luego se utiliza el endpoint [POST /payments](https://khipu.com/page/api-referencia#paymentsPost) para crear una solicitud de pago y finalmente se imprime en la consola el identificador único del pago (paymentId) que se utilizará en la aplicación móvil.

Al momento de crear el pago se definen los parámetros obligatorios:

- Motivo del pago
- Monto
- Moneda
- URL de notificación

Además de parámetros opcionales, como por ejemplo, el correo electrónico del pagador o asociar una imágen al cobro. Puedes ver [los ejemplos de uso avanzado de API](https://khipu.com/page/api-usos-avanzados) para conocer más sobre las oportunidades que plantea.

En este proyecto se encuentran ejemplos de implementaciones en:

- Java (Gradle [ver documentación]() y Maven [ver documentación]())
- Ruby [ver documentación]()
- PHP [ver documentación]()

### Java

El cliente se distribuye como un .jar y se recomienda utilizar para proyectos basados en [Gradle](https://gradle.org/) o [Maven](https://maven.apache.org/).

**Gradle**

```
> cd server/java/gradle  
> ./gradlew run

Task :run  
PAYMENT_ID: xxxxyyyyzzzz
``` 

**Maven**

```
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

### Ruby

El cliente se distribuye como una Gema, se recomienda usar [Bundler](https://bundler.io/) para manejar las dependencias.

**Instalación de la gema**

```
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

```
> cd server/ruby
> ruby demo.rb

PAYMENT_ID: xxxxyyyyzzzz
```

### PHP

El cliente se distribuye usando [Composer](https://getcomposer.org/).

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


## Autorización del pago en la App (en el dispositivo móvil del usuario)

Una vez que ya existe la solicitud de pago y se cuenta con el identificador único del pago (una cadena alfanumérica de 12 caracteres) se debe traspasar a la App. El mecanismo exácto de como llega el paymentId a la App queda fuera del alcance de este documento porque depende de la implementación.

Embeber khipu dentro de la App tiene tres etapas.

1. Agregar la biblioteca al proyecto de la App.
2. Inicializar la biblioteca al iniciar la App.
3. Invocar la biblioteca al momento de querer autorizar un pago.

En este repositorio se encuentran proyectos de ejemplo en:

- Android (Java [ver documentación]())
- iOS (ObjC [ver documentación]() y Swift [ver documentación]()).


## Recepción de la notificación de conciliación (en el servidor del cobrador)

Una vez que el cliente pagador ya autorizó el pago. Khipu comenzará el proceso de conciliación y verificación de éste. Al finalizar ese proceso (pocos segundos), Khipu invocará la URL de notificación asociada al pago entregando el parámetro **notification_token**.

Con el **notification_token** se debe utilizar el método [GET /payments](https://khipu.com/page/api-referencia#paymentsGet) para obtener el detalle del pago a partir del token y verificar que se encuentre en estado **done**.

Finalmente, 

En este repositorio se encuentran proyectos de ejemplo en:

- Java (Gradle [ver documentación]() y Maven [ver documentación]())
- Ruby [ver documentación]()
- PHP [ver documentación]()
