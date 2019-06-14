# Autorizar el pago en una aplicación Android

La biblioteca de Khipu Inside se llama **khenshin**.

En este repositorio se encuentra una App de demostración en la carpeta [app/android/java](https://github.com/khipu/khipu-inside-demo/tree/master/app/android/java) . Para ejecutarla se debe abrir con Android Studio o IntelliJ

Los pasos necesarios para utilizar la biblioteca nativa android para Browser2app son:

1. [Agregar los repositorios](#repositorios)
2. [Agregar las dependencias](#dependencias)
3. [Modificar la clase base de tu app](#clase-de-tu-aplicación)
4. [Configurar colores](#colores) y [vistas del proceso](#vistas)
5. [Invocar browser2app desde tu app](#invocación)
6. [Recibir la respuesta en tu app](#respuesta)


## Tamaño de la biblioteca (cuanto afecta a tu aplicación)

La biblioteca khenshin se distribuye como un artefacto android (extensión .aar). En la versión actual (5.0.5) pesa 806 KB y referencia una lista de bibliotecas externas que en la medida que ya se estén utilizando no afectarían el tamaño de la aplicación.

A modo de ejemplo, una aplicación vacía, que sólo utiliza khenshin pesa 5.8MB.

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
		jcenter()
		maven { url 'https://dev.khipu.com/nexus/content/repositories/khenshin' }
	}
}
```	

## Dependencias

Con los repositorios agregados puedes agregar el paquete khenshin a tu proyecto.

```gradle
compile 'com.browser2app:khenshin:5.0.5'
```   
    
## Clase de tu aplicación

La clase principal de tu aplicación (la definida en el atributo android:name dentro del tag application en el AndroidManifest.xml) debe implementar la interfaz KhenshinApplication y en el constructor debe inicializar a Khenshin

```java
public class Demo extends Application implements KhenshinApplication {

    KhenshinInterface khenshin;

    public Demo(){
        khenshin = new Khenshin.KhenshinBuilder()
                .setApplication(this)
                .setAPIUrl("https://khipu.com/app/enc/")
                .build();
    }

    @Override
    public KhenshinInterface getKhenshin() {
        return khenshin;
    }
}
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

    
## Invocación

Para iniciar un pago con Khenshin debes iniciar la actividad StartPaymentActivity.

```java
int START_PAYMENT_REQUEST_CODE = 101;
	
...
	
Intent intent = ((KhenshinApplication)getApplication()).getKhenshin().getStartTaskIntent();
intent.putExtra(KhenshinConstants.EXTRA_PAYMENT_ID, <ID DEL PAGO>); intent.putExtra(KhenshinConstants.EXTRA_FORCE_UPDATE_PAYMENT, false); // NO FORZAR LA ACTUALIZACION DE DATOS
intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // LIMPIAR EL STACK DE ACTIVIDADES
startActivityForResult(intent, START_PAYMENT_REQUEST_CODE); // INICIAR LA ACTIVIDAD ESPERANDO UNA RESPUESTA
```

## Respuesta

En la actividad de tu aplicación que inició la actividad de pago se debe implementar el método onActivityResult

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (requestCode == START_PAYMENT_REQUEST_CODE) {
		String exitUrl = data.getStringExtra(KhenshinConstants.EXTRA_INTENT_URL);
		if (resultCode == RESULT_OK) {
			Toast.makeText(MainActivity.this, "PAYMENT OK, exit url: " + exitUrl,
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(MainActivity.this, "PAYMENT FAILED, exit url: " + exitUrl,
					Toast.LENGTH_LONG).show();
		}
	}
	
}
```
	
El parámetro requestCode debe ser el mismo que se envió al iniciar la actividad.

El parámetro resultCode será RESULT_OK si el pago terminó exitósamente o RESULT_CANCEL si el usuario no completó el pago.

Recordar que siempre se debe esperar la notificación por API de khipu para considerar que un pago aprobado. Como se explican en [la documentación del proceso de pago](README.md).
