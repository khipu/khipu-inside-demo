# Autorizar el pago en una aplicación Cordova

Para Android, la biblioteca de Khipu Inside se llama **khenshin** y se distribuye como un artefacto android.

Para iOS, la biblioteca de Khipu Inside se llama **khenshin** y se distribuye como un Cocoapod.

En este repositorio se encuentra una App de demostración en la carpeta [app/multi/cordova](https://github.com/khipu/khipu-inside-demo/tree/master/app/multi/cordova). Para ejecutarla debes tener cordova instalado.

Los pasos necesarios para utilizar el plugin cordova en android e iOS para son:

1. [Crear un proyecto Cordova](#proyecto)
2. [Agregar las plataformas](#plataformas)
3. [Agregar el plugin cordova](#plugin)
4. [Invocar khipu desde tu app](#invocación)

## Proyecto

Para crear un proyecto Apache Cordova debes ejecutar "cordova create khipu-inside-demo cl.khipu KhipuInsideDemo".

```bash
PROMPT> cordova create khipu-inside-demo cl.khipu KhipuInsideDemo
Creating a new cordova project.
```

## Plataformas

Puedes agregar las plataformas ios y android a tu proyecto.

```bash
PROMPT> cordova platform add ios

Using cordova-fetch for cordova-ios@^6.2.0
Adding ios project...
Creating Cordova project for the iOS platform:
        Path: platforms/ios
        Package: cl.khipu
        Name: KhipuInsideDemo
iOS project created with cordova-ios@6.3.0
```

Al 7 de Junio de 2019, es necesario sobreescribir el parámetro target -> build settings -> "User Header Search Paths" con "${PODS_ROOT}"/** para que al compiliar con linea de comandos se encuentren los archivos de encabezado.

```bash
PROMPT> cordova platform add android

Using cordova-fetch for cordova-android@^10.1.1
Adding android project...
Creating Cordova project for the Android platform:
        Path: platforms/android
        Package: cl.khipu
        Name: KhipuInsideDemo
        Activity: MainActivity
        Android target: android-30
Subproject Path: CordovaLib
Subproject Path: app
Android project created with cordova-android@10.1.2
```

## Plugin

A continuación debemos agregar el plugin cordova-khenshin

```bash
PROMPT>cordova plugin add https://github.com/khipu/cordova-khenshin 
Installing "cordova-khenshin" for android
Subproject Path: CordovaLib
Subproject Path: app
Installing "cordova-khenshin" for ios
Running command: pod install --verbose

[!] The `KhipuInsideDemo [Debug]` target overrides the `LD_RUNPATH_SEARCH_PATHS` build setting defined in `Pods/Target Support Files/Pods-KhipuInsideDemo/Pods-KhipuInsideDemo.debug.xcconfig'. This can lead to problems with the CocoaPods installation

[!] The `KhipuInsideDemo [Release]` target overrides the `LD_RUNPATH_SEARCH_PATHS` build setting defined in `Pods/Target Support Files/Pods-KhipuInsideDemo/Pods-KhipuInsideDemo.release.xcconfig'. This can lead to problems with the CocoaPods installation

Adding cordova-khenshin to package.json
```

## Invocación

Se debe invocar en los JS el modulo khenshin

```typescript
   khenshin.startByPaymentId('<ID DEL PAGO>', success => console.log(success), err => console.log(err));
```

Los parametros "success" y "err" son funciones de callback que se invocan si el pago termino correctamente en el banco autorizador o con error, respectivamente.

Recordar que siempre se debe esperar la notificación por API de khipu para considerar que un pago aprobado. Como se explican en [la documentación del proceso de pago](README.md).
