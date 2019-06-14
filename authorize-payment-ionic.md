# Autorizar el pago en una aplicación Ionic

Para Android, la biblioteca de Khipu Inside se llama **khenshin** y se distribuye como un artefacto android.

Para iOS, la biblioteca de Khipu Inside se llama **khenshin** y se distribuye como un Cocoapod.

En este repositorio se encuentra una App de demostración en la carpeta [app/multi/ionic](https://github.com/khipu/khipu-inside-demo/tree/master/app/multi/ionic) . Para ejecutarla debes tener ionic instalado.

Los pasos necesarios para utilizar el plugin cordova para Ionic en android e iOS para Browser2app son:

0. [Agregar las credenciales para usar Khipu Inside](#credenciales)
1. [Crear un proyecto Ionic](#proyecto)
2. [Agregar las plataformas](#plataformas)
3. [Agregar el plugin cordova](#plugin)
4. [Invocar khipu desde tu app](#invocación)

#Credenciales

##Android

Debes [solicitar en Khipu](mailto:soporte@khipu.com) tu usuario y contraseña para el repositorio de artefactos java. Con estos debes crear un archivo gradle.properties que debes alojar en ~/.gradle/gradle.properties donde definas las variables khenshinRepoUsername y khenshinRepoPassword.

```bash
PROMPT> cat ~/.gradle/gradle.properties

khenshinRepoUsername=<Nombre de usuario entregado>
khenshinRepoPassword=<Password entregado>
```
   
##iOS

Debes tener un usuario bitbucket.org y [solicitar en Khipu](mailto:soporte@khipu.com) que le den acceso a ese usuario al repositorio de los pods de Khipu Inside.

Luego debes crear un par de llaves ssh publica y privada que se alojen en ~/.ssh/ y debes confiurar tu cuenta bitbucket.org para poder acceder con tu llave ssh.
    
    
## Proyecto

Para crear un proyecto Ionic debes ejecutar "ionic start" y constestar las preguntas que te haga:

```bash
PROMPT> ionic start

Every great app needs a name! 😍

Please enter the full name of your app. You can change this at any time. To bypass this prompt next time, supply name,
the first argument to ionic start.

? Project name: Khipu Inside Demo

Let's pick the perfect starter template! 💪

Starter templates are ready-to-go Ionic apps that come packed with everything you need to build your app. To bypass this
prompt next time, supply template, the second argument to ionic start.

? Starter template: blank
[INFO] Existing git project found (/Users/edavis/git/khipu-inside-demo). Git operations are disabled.
✔ Preparing directory ./khipu-inside-demo - done!
✔ Downloading and extracting blank starter - done!

Installing dependencies may take several minutes.

...
```


## Plataformas

Puedes agregar las plataformas ios y android a tu proyecto. (Recomendamos usar, al menos, cordova-ionic 5.0.1)

```bash
PROMPT> ionic cordova platform add ios@5.0.1


> ionic integrations enable cordova
[INFO] Downloading integration cordova
[INFO] Copying integrations files to project
CREATE resources
...
```

Al 7 de Junio de 2019, es necesario sobreescribir el parámetro target -> build settings -> "User Header Search Paths" con "${PODS_ROOT}"/** para que al compiliar con linea de comandos se encuentren los archivos de encabezado.


```bash
PROMPT> ionic cordova platform add android


> cordova platform add android --save
Using cordova-fetch for cordova-android@~7.1.1
Adding android project...
Creating Cordova project for the Android platform:
	Path: platforms/android
	Package: io.ionic.starter
	Name: MyApp
	Activity: MainActivity
	Android target: android-27
Android project created with cordova-android@7.1.4
...
```

## Plugin

A continuación debemos agregar el plugin cordova-khenshin

```bash
PROMPT> ionic cordova plugin add https://github.com/khipu/cordova-khenshin 
> cordova plugin add https://github.com/khipu/cordova-khenshin --save
Installing "cordova-khenshin" for android
Android Studio project detected
Subproject Path: CordovaLib
Subproject Path: app
Installing "cordova-khenshin" for ios
Installing "cordova-plugin-cocoapod-support" for ios
Adding cordova-khenshin to package.json
Saved plugin info for "cordova-khenshin" to config.xml
```

## Invocación

Se debe importar el modulo khenshin

```typescript
import khenshin from 'cordova-khenshin/www/khenshin';
```

Y luego invocarlo:

```typescript
   khenshin.startByPaymentId('<ID DEL PAGO>', success => console.log(success), err => console.log(err));
```

Los parametros "success" y "err" son funciones de callback que se invocan si el pago termino correctamente en el banco autorizador o con error, respectivamente.

Recordar que siempre se debe esperar la notificación por API de khipu para considerar que un pago aprobado. Como se explican en [la documentación del proceso de pago](README.md).
