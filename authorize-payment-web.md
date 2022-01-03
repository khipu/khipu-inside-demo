# Autorizar el pago en una aplicación Web

En este repositorio se encuentra una página web de demostración en la carpeta [app/web/js](https://github.com/khipu/khipu-inside-demo/tree/master/app/web/js). Para ejecutarla puedes abrir el archivo index.html con un navegador web cualquiera.

Los pasos necesarios para utilizar la biblioteca nativa android para B2app son:

1. [Incluir la biblioteca KWS](#incluir-la-biblioteca-kws)
2. [Agregar el elemento ancla](#agregar-el-elemento-ancla)
3. [Configurar Khipu](#configurar-khipu)
4. [Iniciar el pago](#iniciar-el-pago)

## Incluir la biblioteca KWS

Se debe incluir en la página la biblioteca kws.js dentro del tag <head> de la página.

```html
<head>
    ...
    <script src="https://js-scl.khipu.com/v1/kws.js"></script>
    ...
</head>
```

## Agregar el elemento ancla

Khipu embebido se puede utilizar en modo "modal" o "incrustado". En ambos casos se debe definir un elemento donde se anclará. Se recomienda usar un <div> con id khipu-web-root, pero puede ser cualquier id.

```html
<div id="khipu-web-root"></div>
```

## Configurar Khipu

Khipu Inside Web se puede incluir en dos modalidades, Modal o Incrustado.
- Modal: Se levantará una ventana modal con un overlay gris sobre la página.
  - Ancho máximo: Esta opción nos permite determinar el tamaño horizontal máximo de la modal ocupará
  - Alto máximo: Esta opción nos permite determinar el tamaño vertical máximo de la modal ocupará
- Incrustado: Se desplegará en el elemento ancla definido en la sección anterior.

El estilo gráfico de Khipu Inside Web se puede modificar con los siguiente parámetros:
- primaryColor: Color principal de la interfaz, se recomienda usar el color principal de la marca.
- textColor: Color de los textos utilizados en la interfaz.
- progressTextColor: Color de los textos de los mensajes de progreso.
- backgroundColor: Color de fondo de la interfaz, se recomienda alto contraste con los colores de texto.
- statusBarBackgroundColor: Color de fondo de la barra de status.

Por último, las páginas finales del proceso de pago (éxito, alerta o fracaso) pueden ser renderizadas por Khipu Web o por la página del comercio. En ambos casos se recibe el resultado en una función de callback.
En el caso de Khipu Web los links y botones de salida se configuran automáticamente con las urls enviadas al crear el pago a través de nuestra API. Con comportamientos especiales para la configuración modal.

Para definir todos estos comportamientos, se debe inicializar Khipu de la siguiente forma.

```js
    const prettyJson = (obj) => JSON.stringify(obj, null, 4)

    //callback que se llamará cuando el banco origen declare exitoso el pago
    const successHandler = (result) => {
      console.log(`Success handler: ${prettyJson(result)}`)
      alert(`Success handler: ${prettyJson(result)}`);
    };

    //callback que se llamará cuando el resultado del pago sea indeterminado
    const warningHandler = (warning) => {
      console.log(`Warning handler: ${prettyJson(warning)}`)
      alert(`Error handler: ${prettyJson(warning)}`);
    };

    //callback que se llamará cuando el resultado del pago sea un fracaso
    const errorHandler = (error) => {
      console.log(`Error handler: ${prettyJson(error)}`)
      alert(`Error handler: ${prettyJson(error)}`);
    };

    let khipu = new Khipu();
    const options = {
        mountElement: document.getElementById('khenshin-web-root'), //Elemento ancla
        modal: true, //false si se quiere incrustado
        modalOptions: {
            maxWidth: 750,
            maxHeight: 750,
        },
        options: {
          style: {
            primaryColor: '#8347ad',
            textColor: '#767E8D',
            progressTextColor: '#6E0380',
            backgroundColor: '#FFFFFF',
            statusBarBackgroundColor: '#F7F7F7',
          },
          skipExitPage: false, //true si se quiere que Khipu no pinte las páginas finales
        },
    }
    khipu.init(options, successHandler, warningHandler, errorHandler);
```

## Iniciar el pago

Finalmente y con un identificador de pago (paymentId) obtenido como se explica en [la documentación del proceso de pago](README.md) se puede iniciar un pago usando Khipu Inside Web.

```js
   khipu.start('ID DE PAGO DE 12 CARACTERES');
```

Recordar que siempre se debe esperar la notificación por API de khipu para considerar que un pago aprobado. Como se explican en [la documentación del proceso de pago](README.md).

## Cerrar ventana de pago

Si se desease cerrar la ventana de pago, por ejemplo, al capturar un mensaje de salida, basta con llamar el método

```js
   khipu.close();
```

Adicionalmente, para el caso de la ventana Modal, esta acción se realizará de forma interna en los links de:
- "Anular pago y volver" disponible durante el pago.
- "Finalizar y volver" que se muestra al finalizar el pago.

## Reiniciar el pago

Si se desease reiniciar el pago actual en caso de error u algún otro problema se puede utilizar el método

```js
   khipu.restart();
```

Adicionalmente, para el caso de la ventana Modal, esta acción se realizará de forma interna en los links de:
- "Volver a intentarlo"
