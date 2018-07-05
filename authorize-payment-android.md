# Autorizar el pago en una aplicación Android

La biblioteca de Khipu Inside se llama **khenshin** y se distribuye como un artefacto android privado. Para poder utilizarlo en tu App debes contar con acceso a nuestro repositorio privado: https://dev.khipu.com/nexus/content/repositories/browser2app

Los pasos necesarios para utilizar la biblioteca nativa android para Browser2app son:

1. [Agregar los repositorios](#repositorios)
2. [Agregar las dependencias](#dependencias)
3. [Modificar la clase base de tu app](#clase-de-tu-aplicación)
4. [Configurar colores](#colores) y [vistas del proceso](#vistas)
5. [Invocar browser2app desde tu app](#invocación)
6. [Recibir la respuesta en tu app](#respuesta)


## Repositorios

Se debe incluir el [repositorio maven de khenshin](https://dev.khipu.com/nexus/content/repositories/browser2app) así como jcenter

```gradle
allprojects {
	repositories {
		jcenter()
		maven {
			url 'https://dev.khipu.com/nexus/content/repositories/browser2app'
			credentials {
				username khenshinRepoUsername
				password khenshinRepoPassword
			}
		}
	}
}
```	

Los campos khenshinRepoUsername y khenshinRepoPassword te serán proporcionados por tu ejecutivo Khipu, se deben incluir en el archivo gradle.properties en la raiz del proyecto y sin incluir al sistema de control de versiones.

## Dependencias

Con los repositorios agregados puedes agregar el paquete khenshin a tu proyecto.

```gradle
compile 'com.browser2app:khenshin:3.5.3'
```   
    
## Clase de tu aplicación

La clase principal de tu aplicación (la definida en el atributo android:name dentro del tag application en el AndroidManifest.xml) debe implementar la interfaz KhenshinApplication y en el constructor debe inicializar a Khenshin

```java
public class Demo extends Application implements KhenshinApplication {

    KhenshinInterface khenshin;

    public Demo(){
        khenshin = new Khenshin.KhenshinBuilder()
                .setApplication(this)
                .setTaskAPIUrl("https://khipu.com/app/2.0/")
                .setDumpAPIUrl("https://khipu.com/cerebro/")
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

    <color name="khenshin_primary">#ca0814</color> <!-- Color de la barra de navegación y botón principal-->
    <color name="khenshin_primary_dark">#580409</color> <!-- Color del status bar superior -->
    <color name="khenshin_primary_text">#ffffff</color> <!-- Color del texto en la barra de navegación -->
    <color name="khenshin_accent">#ca0814</color> <!-- Color de las decoraciones, por ejemplo barras de progreso -->
    
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