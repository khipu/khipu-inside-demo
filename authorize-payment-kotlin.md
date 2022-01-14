# Autorizar el pago en una aplicación Android

La biblioteca de Khipu Inside se llama **khenshin**.

En este repositorio se encuentra una App de demostración en la carpeta [app/android/kotlin](https://github.com/khipu/khipu-inside-demo/tree/master/app/android/kotlin) . Para ejecutarla se debe abrir con XCode.

Los pasos necesarios para utilizar la biblioteca nativa android para B2app son:

1. [Agregar los repositorios](#repositorios)
2. [Agregar las dependencias](#dependencias)
3. [Inicializar khenshin](#inicializar-khenshin)
4. [Parámetros de inicialización de khenshin](#parámetros-de-inicialización-de-khenshin)
5. [Configurar colores](#colores) y [vistas del proceso](#vistas)
6. [Invocar b2app desde tu app](#invocación)
7. [Recibir la respuesta en tu app](#respuesta)


## Tamaño de la biblioteca (cuanto afecta a tu aplicación)

La biblioteca khenshin se distribuye como un artefacto android (extensión .aar). En la versión actual (5.0.3) pesa 806 KB y referencia una lista de bibliotecas externas que en la medida que ya se estén utilizando no afectarían el tamaño de la aplicación.

A modo de ejemplo, una aplicación vacía, que sólo utiliza khenshin pesa 5.8MB

El tamaño final que agregará khenshin a tu aplicación quedará determinado por la cantidad de bibliotecas repetidas e irá entre 800 KB y 9 MB en el caso que construyas un APK por arquitectura (recomendado) y entre 800 KB y 20 MB si sólo construyes un APK con todas las arquitecturas incluidas.

Para que puedas estimar el grado de repetición de bibliotecas con las que ya usas en tu aplicación. Estas son todas las bibliotecas que khenshin usa:

```
+--- com.google.guava:guava:27.1-android
|    +--- com.google.guava:failureaccess:1.0.1
|    +--- com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava
|    +--- com.google.code.findbugs:jsr305:3.0.2
|    +--- org.checkerframework:checker-compat-qual:2.5.2
|    +--- com.google.errorprone:error_prone_annotations:2.2.0
|    +--- com.google.j2objc:j2objc-annotations:1.1
|    \--- org.codehaus.mojo:animal-sniffer-annotations:1.17
+--- com.android.support:exifinterface:27.1.1
|    \--- com.android.support:support-annotations:27.1.1
+--- com.android.support:appcompat-v7:27.1.1
|    +--- com.android.support:support-annotations:27.1.1
|    +--- com.android.support:support-core-utils:27.1.1
|    |    +--- com.android.support:support-annotations:27.1.1
|    |    \--- com.android.support:support-compat:27.1.1
|    |         +--- com.android.support:support-annotations:27.1.1
|    |         \--- android.arch.lifecycle:runtime:1.1.0
|    |              +--- android.arch.lifecycle:common:1.1.0
|    |              \--- android.arch.core:common:1.1.0
|    +--- com.android.support:support-fragment:27.1.1
|    |    +--- com.android.support:support-compat:27.1.1 (*)
|    |    +--- com.android.support:support-core-ui:27.1.1
|    |    |    +--- com.android.support:support-annotations:27.1.1
|    |    |    +--- com.android.support:support-compat:27.1.1 (*)
|    |    |    \--- com.android.support:support-core-utils:27.1.1 (*)
|    |    +--- com.android.support:support-core-utils:27.1.1 (*)
|    |    +--- com.android.support:support-annotations:27.1.1
|    |    +--- android.arch.lifecycle:livedata-core:1.1.0
|    |    |    +--- android.arch.lifecycle:common:1.1.0
|    |    |    +--- android.arch.core:common:1.1.0
|    |    |    \--- android.arch.core:runtime:1.1.0
|    |    |         \--- android.arch.core:common:1.1.0
|    |    \--- android.arch.lifecycle:viewmodel:1.1.0
|    +--- com.android.support:support-vector-drawable:27.1.1
|    |    +--- com.android.support:support-annotations:27.1.1
|    |    \--- com.android.support:support-compat:27.1.1 (*)
|    \--- com.android.support:animated-vector-drawable:27.1.1
|         +--- com.android.support:support-vector-drawable:27.1.1 (*)
|         \--- com.android.support:support-core-ui:27.1.1 (*)
+--- com.android.support:design:27.1.1
|    +--- com.android.support:support-v4:27.1.1
|    |    +--- com.android.support:support-compat:27.1.1 (*)
|    |    +--- com.android.support:support-media-compat:27.1.1
|    |    |    +--- com.android.support:support-annotations:27.1.1
|    |    |    \--- com.android.support:support-compat:27.1.1 (*)
|    |    +--- com.android.support:support-core-utils:27.1.1 (*)
|    |    +--- com.android.support:support-core-ui:27.1.1 (*)
|    |    \--- com.android.support:support-fragment:27.1.1 (*)
|    +--- com.android.support:appcompat-v7:27.1.1 (*)
|    +--- com.android.support:recyclerview-v7:27.1.1
|    |    +--- com.android.support:support-annotations:27.1.1
|    |    +--- com.android.support:support-compat:27.1.1 (*)
|    |    \--- com.android.support:support-core-ui:27.1.1 (*)
|    \--- com.android.support:transition:27.1.1
|         +--- com.android.support:support-annotations:27.1.1
|         \--- com.android.support:support-compat:27.1.1 (*)
+--- com.google.code.gson:gson:2.8.2
+--- com.squareup.retrofit2:retrofit:2.3.0
|    \--- com.squareup.okhttp3:okhttp:3.8.0 -> 3.10.0
|         \--- com.squareup.okio:okio:1.14.0
+--- com.squareup.picasso:picasso:2.71828
|    +--- com.squareup.okhttp3:okhttp:3.10.0 (*)
|    +--- com.android.support:support-annotations:27.1.0 -> 27.1.1
|    \--- com.android.support:exifinterface:27.1.0 -> 27.1.1 (*)
+--- com.jakewharton.picasso:picasso2-okhttp3-downloader:1.1.0
|    +--- com.squareup.picasso:picasso:2.5.2 -> 2.71828 (*)
|    \--- com.squareup.okhttp3:okhttp:3.4.1 -> 3.10.0 (*)
+--- com.squareup.retrofit2:converter-gson:2.3.0
|    +--- com.squareup.retrofit2:retrofit:2.3.0 (*)
|    \--- com.google.code.gson:gson:2.7 -> 2.8.2
+--- com.squareup.retrofit2:converter-scalars:2.3.0
|    \--- com.squareup.retrofit2:retrofit:2.3.0 (*)
+--- com.squareup.okhttp3:okhttp-urlconnection:3.8.0
|    \--- com.squareup.okhttp3:okhttp:3.8.0 -> 3.10.0 (*)
+--- pub.devrel:easypermissions:0.4.0
|    \--- com.android.support:appcompat-v7:25.3.1 -> 27.1.1 (*)
+--- com.squareup.okhttp3:logging-interceptor:3.8.0
|    \--- com.squareup.okhttp3:okhttp:3.8.0 -> 3.10.0 (*)
+--- com.bottlerocketstudios:vault:1.4.2
+--- com.sjl:dsl4xml:0.1.7
+--- com.github.franmontiel:PersistentCookieJar:v1.0.1
|    \--- com.squareup.okhttp3:okhttp:3.1.2 -> 3.10.0 (*)
+--- info.hoang8f:android-segmented:1.0.6
+--- com.facebook.fresco:animated-webp:1.9.0
|    +--- com.parse.bolts:bolts-tasks:1.4.0
|    +--- com.facebook.fresco:webpsupport:1.9.0
|    |    +--- com.facebook.soloader:soloader:0.3.0
|    |    +--- com.parse.bolts:bolts-tasks:1.4.0
|    |    +--- com.facebook.fresco:fbcore:1.9.0
|    |    \--- com.facebook.fresco:imagepipeline-base:1.9.0
|    |         +--- com.facebook.soloader:soloader:0.3.0
|    |         +--- com.parse.bolts:bolts-tasks:1.4.0
|    |         \--- com.facebook.fresco:fbcore:1.9.0
|    \--- com.facebook.fresco:animated-base:1.9.0
|         +--- com.facebook.fresco:fbcore:1.9.0
|         +--- com.facebook.fresco:imagepipeline-base:1.9.0 (*)
|         +--- com.facebook.fresco:imagepipeline:1.9.0
|         |    +--- com.facebook.fresco:imagepipeline-base:1.9.0 (*)
|         |    +--- com.facebook.soloader:soloader:0.3.0
|         |    +--- com.parse.bolts:bolts-tasks:1.4.0
|         |    \--- com.facebook.fresco:fbcore:1.9.0
|         +--- com.facebook.fresco:animated-drawable:1.9.0
|         |    +--- com.facebook.fresco:imagepipeline:1.9.0 (*)
|         |    +--- com.facebook.fresco:drawee:1.9.0
|         |    |    +--- com.facebook.fresco:fbcore:1.9.0
|         |    |    \--- com.facebook.fresco:imagepipeline:1.9.0 (*)
|         |    \--- com.facebook.fresco:fbcore:1.9.0
|         \--- com.parse.bolts:bolts-tasks:1.4.0
+--- com.facebook.fresco:webpsupport:1.9.0 (*)
+--- com.facebook.fresco:fresco:1.9.0
|    +--- com.facebook.fresco:fbcore:1.9.0
|    +--- com.facebook.fresco:drawee:1.9.0 (*)
|    +--- com.facebook.fresco:imagepipeline:1.9.0 (*)
|    \--- com.facebook.soloader:soloader:0.3.0
+--- commons-codec:commons-codec:1.10
+--- com.google.guava:guava:27.1-android (*)
+--- com.google.guava:failureaccess:1.0.1
+--- com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava
+--- com.google.code.findbugs:jsr305:3.0.2
+--- org.checkerframework:checker-compat-qual:2.5.2
+--- com.google.errorprone:error_prone_annotations:2.2.0
+--- com.google.j2objc:j2objc-annotations:1.1
+--- org.codehaus.mojo:animal-sniffer-annotations:1.17
+--- com.android.support:exifinterface:27.1.1 (*)
+--- com.android.support:support-annotations:27.1.1
+--- com.android.support:appcompat-v7:27.1.1 (*)
+--- com.android.support:support-core-utils:27.1.1 (*)
+--- com.android.support:support-compat:27.1.1 (*)
+--- android.arch.lifecycle:runtime:1.1.0 (*)
+--- android.arch.lifecycle:common:1.1.0
+--- android.arch.core:common:1.1.0
+--- com.android.support:support-fragment:27.1.1 (*)
+--- com.android.support:support-core-ui:27.1.1 (*)
+--- android.arch.lifecycle:livedata-core:1.1.0 (*)
+--- android.arch.core:runtime:1.1.0 (*)
+--- android.arch.lifecycle:viewmodel:1.1.0
+--- com.android.support:support-vector-drawable:27.1.1 (*)
+--- com.android.support:animated-vector-drawable:27.1.1 (*)
+--- com.android.support:design:27.1.1 (*)
+--- com.android.support:support-v4:27.1.1 (*)
+--- com.android.support:support-media-compat:27.1.1 (*)
+--- com.android.support:recyclerview-v7:27.1.1 (*)
+--- com.android.support:transition:27.1.1 (*)
+--- com.google.code.gson:gson:2.8.2
+--- com.squareup.retrofit2:retrofit:2.3.0 (*)
+--- com.squareup.okhttp3:okhttp:3.10.0 (*)
+--- com.squareup.okio:okio:1.14.0
+--- com.squareup.picasso:picasso:2.71828 (*)
+--- com.jakewharton.picasso:picasso2-okhttp3-downloader:1.1.0 (*)
+--- com.squareup.retrofit2:converter-gson:2.3.0 (*)
+--- com.squareup.retrofit2:converter-scalars:2.3.0 (*)
+--- com.squareup.okhttp3:okhttp-urlconnection:3.8.0 (*)
+--- pub.devrel:easypermissions:0.4.0 (*)
+--- com.squareup.okhttp3:logging-interceptor:3.8.0 (*)
+--- com.bottlerocketstudios:vault:1.4.2
+--- com.sjl:dsl4xml:0.1.7
+--- com.github.franmontiel:PersistentCookieJar:v1.0.1 (*)
+--- info.hoang8f:android-segmented:1.0.6
+--- com.facebook.fresco:animated-webp:1.9.0 (*)
+--- com.parse.bolts:bolts-tasks:1.4.0
+--- com.facebook.fresco:webpsupport:1.9.0 (*)
+--- com.facebook.soloader:soloader:0.3.0
+--- com.facebook.fresco:fbcore:1.9.0
+--- com.facebook.fresco:imagepipeline-base:1.9.0 (*)
+--- com.facebook.fresco:animated-base:1.9.0 (*)
+--- com.facebook.fresco:imagepipeline:1.9.0 (*)
+--- com.facebook.fresco:animated-drawable:1.9.0 (*)
+--- com.facebook.fresco:drawee:1.9.0 (*)
+--- com.facebook.fresco:fresco:1.9.0 (*)
\--- commons-codec:commons-codec:1.10
```

Es importante mencionar que el tamaño al que nos referimos en este documento corresponde al tamaño de la aplicación instalada, lo que el usuario debe descargar de Google Play es siempre menor y depende de optimizaciones que haga Google con el .apk


## Repositorios

Se debe incluir el [repositorio maven de khenshin](https://dev.khipu.com/nexus/content/repositories/khenshin) así como jcenter

```gradle
allprojects {
	repositories {
		google()
		mavenCentral()
		maven { url 'https://dev.khipu.com/nexus/content/repositories/khenshin' }
	}
}
```	

## Dependencias

Con los repositorios agregados puedes agregar el paquete khenshin a tu proyecto.

```gradle
implementation 'com.browser2app:khenshin:+' //Fija la versión antes de pasar a producción
```   
    
## Inicializar khenshin

Debes inicializar khenshin antes de utilizarlo en los procesos de pago de tu app. Esto puede ser en el método onCreate de tu aplicación o en alguna otra parte del ciclo de vida de tu app. En este ejemplo lo haremos en el método onCreate de la actividad que posteriormente lanzará un pago.

```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khenshin es un singleton, debes inicializarlo sólo una vez
        if(!Khenshin.isInitialized()) {
            Khenshin.KhenshinBuilder()
                .setApplication(application)
                .setAPIUrl("https://khipu.com/app/enc/")
                .build()
        }
    }
```

## Parámetros de inicialización de khenshin

En la sección anterior se utilizó la construcción minimal de khenshin, a continuación veremos todos los parámetros opcionales.

```java

            khenshin = new Khenshin.KhenshinBuilder()
                    // Obligatorios
                    .setApplication(application) 
                    .setAPIUrl("https://khipu.com/app/enc/")

                    // Todas los siguientes parametros son opcionales

                    // Estilo del botón continuar, puede ser:
                    // - Khenshin.CONTINUE_BUTTON_IN_FORM: Dentro de cada formulario, 
                    //  al final de todos los campos
                    // - Khenshin.CONTINUE_BUTTON_IN_TOOLBAR: En la barra de herramientas 
                    //  (arriba a la derecha), opción pre-determinada
                    // - Khenshin.CONTINUE_BUTTON_IN_KEYBOARD: Incluido en el 
                    //  teclado del teléfono
                    .setMainButtonStyle(Khenshin.CONTINUE_BUTTON_IN_FORM)
    
                    // Permitir guardar credenciales bancarias en el dispositivo móvil, pre-determinado: no
                    .setAllowCredentialsSaving(true)
        
                    // Esconder la barra de navegación tipo browser en las páginas de pago, pre-determinado: no
                    .setHideWebAddressInformationInForm(true)
                
                    // Esconder la última página (éxito o fracaso del proceso de pago)
                    // con esta opción se retorna el control a la aplicación sin mostrar
                    // la última página, pre-determinado: no
                    .setSkipExitPage(true)

                    // Esconder los mensajes de progreso entre etapas, se recomienda 
                    // usar con un indicador de progeso personalizado, pre-determinado: no
                    .setHideProgressDialogInTransition(true)

                    // Indicador de progreso personalizado, puede ser un GIF animado o un Webp animado,
                    // pre-determinado: nulo
                    .setProgressAnimationResourceId(R.id.id_de_recurso)

                    // Separador de decimales a utilizar, pre-determinado: el del locale del dispositivo
                    .setDecimalSeparator(',')

                    // Separador de miles a utilizar, pre-determinado: el del locale del dispositivo
                    .setGroupingSeparator('.')

                    // Limpiar las cookies del nagevador antes de comenzar un pago, pre-determinado: no
                    .setClearCookiesBeforeStart(true)

                    // Enviar automáticamente los formularios para los que ya están definidos todos los 
                    // valores, pre-determinado: no
                    .setAutoSubmitIfComplete(true)

                    // Tipo de letra personalizado para usar en todos los formularios, 
                    // ver: https://developer.android.com/guide/topics/ui/look-and-feel/fonts-in-xml
                    .setFontResourceId(R.font.mi_fuente_de_letra)

                    .build();

```

## Colores

En tu proyecto puedes determinar los colores que usará Khenshin en las pantallas de pago sobreescribiendo los siguiente parámetros en los recursos de tu proyecto (por ejemplo en un archivo colors.xml dentro de res/values)

```xml
<color name="khenshin_primary">#ca0814</color> <!-- Color de la barra de navegación y botón principal-->
<color name="khenshin_primary_dark">#580409</color> <!-- Color del status bar superior -->
<color name="khenshin_primary_text">#ffffff</color> <!-- Color del texto en la barra de navegación -->
<color name="khenshin_accent">#ca0814</color> <!-- Color de las decoraciones, por ejemplo barras de progreso -->
```
    
## Vistas

Para personalizar más aún la visualización de Khenshin puedes sobreescribir archivos de layout que se utilizan en el proceso de pago.

Una vez importado khenshin al proyecto se pueden buscar los archivos khenshin_*.xml dentro de res/src/layout de la biblioteca khenshin-&lt;Version&gt;.aar, todos esos layout se pueden sobre escribir en el proyecto final y para esto basta crear un archivo con el mismo nombre en res/src/layout.  

    
## Invocación

Para iniciar un pago con Khenshin debes iniciar la actividad StartPaymentActivity.

```kotlin
private val START_PAYMENT_REQUEST_CODE: Int = 1001
	
...
	
        val intent: Intent = Khenshin.getInstance().startTaskIntent
        intent.putExtra(KhenshinConstants.EXTRA_PAYMENT_ID, paymentId.text.toString())
        intent.putExtra(KhenshinConstants.EXTRA_FORCE_UPDATE_PAYMENT, false)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivityForResult(intent, START_PAYMENT_REQUEST_CODE)
```

## Respuesta

En la actividad de tu aplicación que inició la actividad de pago se debe implementar el método onActivityResult

```kotlin
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == START_PAYMENT_REQUEST_CODE && data != null) {
            val exitUrl: String = data.getStringExtra(KhenshinConstants.EXTRA_INTENT_URL)
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this@MainActivity, "PAYMENT OK, exit url: $exitUrl", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MainActivity, "PAYMENT FAILED, exit url: $exitUrl", Toast.LENGTH_LONG).show()
            }
        }
    }
```
	
El parámetro requestCode debe ser el mismo que se envió al iniciar la actividad.

El parámetro resultCode será RESULT_OK si el pago terminó exitósamente o RESULT_CANCEL si el usuario no completó el pago.

Recordar que siempre se debe esperar la notificación por API de khipu para considerar que un pago aprobado. Como se explican en [la documentación del proceso de pago](README.md).
