# Autorizar el pago en una aplicación Web

En este repositorio se encuentra una página web de demostración en la carpeta [app/web/js](https://github.com/khipu/khipu-inside-demo/tree/master/app/web/js). Para ejecutarla puedes abrir el archivo index.html con un navegador web cualquiera.

Los pasos necesarios para utilizar la biblioteca nativa android para B2app son:

1. [Incluir la biblioteca KWS](#incluir-la-biblioteca-kws)
2. [Agregar el elemento ancla](#agregar-el-elemento-ancla)
3. [Configurar Khipu e iniciar el pago](#configurar-khipu-e-iniciar-el-pago)

## Incluir la biblioteca KWS

Se debe incluir en la página la biblioteca kws.js dentro del tag <head> de la página.

```html
<head>
    ...
    <script src="https://js.khipu.com/v1/kws.js"></script>
    ...
</head>
```

## Agregar el elemento ancla

Khipu embebido se puede utilizar en modo "modal" o "incrustado". En ambos casos se debe definir un elemento donde se anclará. Se recomienda usar un <div> con id khipu-web-root, pero puede ser cualquier id.

```html
<div id="khipu-web-root"></div>
```

## Configurar Khipu e iniciar el pago

Khipu Inside Web se puede incluir en dos modalidades, Modal o Embebido.
- Modal: Se levantará una ventana modal con un overlay gris sobre la página.
  - Ancho máximo: Esta opción nos permite determinar el tamaño horizontal máximo de la modal ocupará
  - Alto máximo: Esta opción nos permite determinar el tamaño vertical máximo de la modal ocupará
- Embebido: Se desplegará en el elemento ancla definido en la sección anterior.

El estilo gráfico de Khipu Inside Web se puede modificar con los siguiente parámetros:
- primaryColor: Color principal de la interfaz, se recomienda usar el color principal de la marca.
- fontFamily: La tipografía que se utilizará en todo el proceso, puede ser cualquier Google Font. Valor pre-determinado: Roboto.
- fontSizeMultiplier: Un multiplicador para aumentar o disminuir el tamaño de todos los textos. Valor pre-determinado: 1.0.

Las páginas finales del proceso de pago (éxito, alerta o fracaso) pueden ser renderizadas por Khipu Web o por la página del comercio. En ambos casos se recibe el resultado en una función de callback.

La función de callback será invocada con un objeto que tendrá los siguientes campos

```json
  {
    "operationId": <codigo unico de operacion>, // string
    "result": <OK | ERROR | WARNING>,
    "failureReason": <Motivo de la falla>,
    "exitTitle": <texto propuesto por Khipu para el titulo de la página de salida>, //string
    "exitMessage": <texto propuesto por Khipu para el cuerpo de la página de salida>, //string
    "exitUrl": <URL a la que redirigir al usuario luego de mostrar la página de salida>, //string
    "events": <arreglo con los pasos realizados para generar el pago, con sus estampas de tiempo>
  }
```

El campo result puede ser:

- `OK`: Se autorizó correctamente el pago en el banco de origen. El pago se está validando y se enviará una notificación por el servidor cuando este validado.
- `ERROR`: El pago no se autorizó correctamente, en `exitMessage` se encuentra el motivo a entregar al cliente y en `failureReason` el tipo de error.
- `WARNING`: El pago no se ha completado pero es posible que ocurra. Por ejemplo, el banco de origen tuvo un error al generar el comprobante de pago pero el dinero si se envió o faltan más firmantes en una transferencia de varios actores. Khipu comenzó el proceso de monitoreo de la cuenta de destino para validar el pago.

En el caso de Khipu Web los links y botones de salida se configuran automáticamente con las urls enviadas al crear el pago a través de nuestra API. Con comportamientos especiales para la configuración modal.

Finalmente y con un identificador de pago (paymentId) obtenido como se explica en [la documentación del proceso de pago](README.md) se puede iniciar un pago usando Khipu Inside Web.

```js

    const callback = (result) => {
      console.log(`calback invoked:`, result);
    };

    const options = {
    mountElement: document.getElementById('khenshin-web-root'), //Elemento ancla
    modal: true, //false si se quiere embebido
    modalOptions: {
      maxWidth: 450,
      maxHeight: 860,
    },
    options: {
        style: {
          primaryColor: '#8347AD',
          fontFamily: 'Roboto',
        },
        skipExitPage: false, //true si se quiere que Khipu no pinte las páginas finales
      },
    }


    khipu.startOperation('ID DE PAGO DE 12 CARACTERES', callback, options);
```

Recordar que siempre se debe esperar la notificación por API de khipu para considerar que un pago aprobado. Como se explican en [la documentación del proceso de pago](README.md).

## Cerrar ventana de pago

Si se desease cerrar la ventana de pago, por ejemplo, al capturar un mensaje de salida, basta con llamar el método

```js
   khipu.close();
```

Adicionalmente, para el caso de la ventana Modal, esta acción se realizará de forma interna en los links de:
- "Anular pago y volver" disponible durante el pago. Lo que llama al callback `errorHandler` con un mensaje 
del tipo: `OPERATION_FAILURE` con failureReason: `USER_CANCELED`
- "Finalizar y volver" que se muestra al finalizar el pago. Sin hacer llamadas adicionales a los callback, ya que
al completarse la transacción se envió una llamada al `successHandler`.

## Reiniciar el pago

Si se desease reiniciar el pago actual en caso de error u algún otro problema se puede utilizar el método

```js
   khipu.restart();
```

Adicionalmente, para el caso de la ventana Modal, esta acción se realizará de forma interna en los links de:
- "Volver a intentarlo"
