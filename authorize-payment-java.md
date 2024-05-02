# Autorizar el pago en una aplicación Android

Para realizar un pago con Khipu se utiliza el SDK de Khipu.

En este repositorio se encuentra una App de demostración en la carpeta [app/android/java](https://github.com/khipu/khipu-inside-demo/tree/master/app/android/java) . Para ejecutarla se debe abrir con Android Studio.

Los pasos necesarios para utilizar la biblioteca nativa android para realizar pagos con Khipu son:

1. [Agregar los repositorios](#repositorios)
2. [Agregar las dependencias](#dependencias)
3. [Agregar plugin de Kotlin](#agregar-plugin-de-kotlin)
3. [Invocar Khenshin](#invocar-khenshin)
4. [Parámetros de inicialización](#parámetros-de-inicialización-de-khenshin)
5. [Configurar colores](#colores) y [vistas del proceso](#vistas)
6. [Recibir la respuesta en tu app](#respuesta)



## Tamaño de la biblioteca (cuanto afecta a tu aplicación)

La biblioteca khenshin se distribuye como un artefacto android (extensión .aar). En la versión actual (1.2) pesa 1.73 MB y referencia una lista de bibliotecas externas que en la medida que ya se estén utilizando no afectarían el tamaño de la aplicación.

El tamaño final que agregará khenshin a tu aplicación quedará determinado por la cantidad de bibliotecas repetidas e irá entre 800 KB y 9 MB en el caso que construyas un APK por arquitectura (recomendado) y entre 800 KB y 20 MB si sólo construyes un APK con todas las arquitecturas incluidas.

Para que puedas estimar el grado de repetición de bibliotecas con las que ya usas en tu aplicación. Estas son todas las bibliotecas que khenshin usa:

```
+--- org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0
|    +--- org.jetbrains.kotlin:kotlin-stdlib:1.9.0 -> 1.9.22
|    |    +--- org.jetbrains:annotations:13.0 -> 23.0.0
|    |    +--- org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0 -> 1.9.0 (c)
|    |    +--- org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0 -> 1.9.0 (c)
|    |    \--- org.jetbrains.kotlin:kotlin-stdlib-common:1.9.22 (c)
|    \--- org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.0
|         \--- org.jetbrains.kotlin:kotlin-stdlib:1.9.0 -> 1.9.22 (*)
+--- androidx.core:core-ktx:1.13.0
|    +--- androidx.annotation:annotation:1.1.0 -> 1.7.0
|    |    \--- androidx.annotation:annotation-jvm:1.7.0
|    |         \--- org.jetbrains.kotlin:kotlin-stdlib:1.7.10 -> 1.9.22 (*)
|    +--- androidx.core:core:1.13.0
|    |    +--- androidx.annotation:annotation:1.6.0 -> 1.7.0 (*)
|    |    +--- androidx.annotation:annotation-experimental:1.4.0
|    |    |    \--- org.jetbrains.kotlin:kotlin-stdlib:1.7.10 -> 1.9.22 (*)
|    |    +--- androidx.lifecycle:lifecycle-runtime:2.6.2 -> 2.7.0
|    |    |    +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |    |    +--- androidx.arch.core:core-common:2.2.0
|    |    |    |    \--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |    |    +--- androidx.lifecycle:lifecycle-common:2.7.0
|    |    |    |    +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |    |    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|    |    |    |    +--- org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1 -> 1.7.3
|    |    |    |    |    +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3
|    |    |    |    |    |    \--- org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.3
|    |    |    |    |    |         +--- org.jetbrains:annotations:23.0.0
|    |    |    |    |    |         +--- org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.7.3
|    |    |    |    |    |         |    +--- org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3 (c)
|    |    |    |    |    |         |    +--- org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.3 (c)
|    |    |    |    |    |         |    \--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3 (c)
|    |    |    |    |    |         +--- org.jetbrains.kotlin:kotlin-stdlib-common:1.8.20 -> 1.9.22
|    |    |    |    |    |         |    \--- org.jetbrains.kotlin:kotlin-stdlib:1.9.22 (*)
|    |    |    |    |    |         \--- org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.20 -> 1.9.0 (*)
|    |    |    |    |    +--- org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.7.3 (*)
|    |    |    |    |    \--- org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.20 -> 1.9.0 (*)
|    |    |    |    +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1 -> 1.7.3 (*)
|    |    |    |    +--- androidx.lifecycle:lifecycle-common-java8:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-livedata:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-livedata-core:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-runtime:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-runtime-ktx:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0 (c)
|    |    |    |    \--- androidx.lifecycle:lifecycle-livedata-core-ktx:2.7.0 (c)
|    |    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|    |    |    +--- androidx.lifecycle:lifecycle-common:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-common-java8:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-livedata-core:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-runtime-ktx:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-livedata:2.7.0 (c)
|    |    |    \--- androidx.lifecycle:lifecycle-livedata-core-ktx:2.7.0 (c)
|    |    +--- androidx.versionedparcelable:versionedparcelable:1.1.1
|    |    |    +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |    |    \--- androidx.collection:collection:1.0.0 -> 1.4.0
|    |    |         \--- androidx.collection:collection-jvm:1.4.0
|    |    |              +--- androidx.annotation:annotation:1.7.0 (*)
|    |    |              \--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|    |    \--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|    \--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
+--- androidx.appcompat:appcompat:1.6.1
|    +--- androidx.activity:activity:1.6.0 -> 1.9.0
|    |    +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |    +--- androidx.core:core:1.13.0 (*)
|    |    +--- androidx.lifecycle:lifecycle-runtime:2.6.1 -> 2.7.0 (*)
|    |    +--- androidx.lifecycle:lifecycle-viewmodel:2.6.1 -> 2.7.0
|    |    |    +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|    |    |    +--- androidx.lifecycle:lifecycle-common:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-common-java8:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-livedata-core:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-runtime:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-runtime-ktx:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-livedata:2.7.0 (c)
|    |    |    \--- androidx.lifecycle:lifecycle-livedata-core-ktx:2.7.0 (c)
|    |    +--- androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.1 -> 2.7.0
|    |    |    +--- androidx.annotation:annotation:1.0.0 -> 1.7.0 (*)
|    |    |    +--- androidx.core:core-ktx:1.2.0 -> 1.13.0 (*)
|    |    |    +--- androidx.lifecycle:lifecycle-livedata-core:2.7.0
|    |    |    |    +--- androidx.lifecycle:lifecycle-common:2.7.0 (*)
|    |    |    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|    |    |    |    +--- androidx.lifecycle:lifecycle-common:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-common-java8:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-runtime:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-runtime-ktx:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-livedata:2.7.0 (c)
|    |    |    |    \--- androidx.lifecycle:lifecycle-livedata-core-ktx:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel:2.7.0 (*)
|    |    |    +--- androidx.savedstate:savedstate:1.2.1
|    |    |    |    +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |    |    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.10 -> 1.9.22 (*)
|    |    |    |    \--- androidx.savedstate:savedstate-ktx:1.2.1 (c)
|    |    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|    |    |    +--- org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1 -> 1.7.3 (*)
|    |    |    +--- androidx.lifecycle:lifecycle-common:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-common-java8:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-livedata-core:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-runtime:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-runtime-ktx:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-livedata:2.7.0 (c)
|    |    |    \--- androidx.lifecycle:lifecycle-livedata-core-ktx:2.7.0 (c)
|    |    +--- androidx.savedstate:savedstate:1.2.1 (*)
|    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|    |    +--- androidx.activity:activity-compose:1.9.0 (c)
|    |    \--- androidx.activity:activity-ktx:1.9.0 (c)
|    +--- androidx.annotation:annotation:1.3.0 -> 1.7.0 (*)
|    +--- androidx.appcompat:appcompat-resources:1.6.1
|    |    +--- androidx.annotation:annotation:1.2.0 -> 1.7.0 (*)
|    |    +--- androidx.core:core:1.6.0 -> 1.13.0 (*)
|    |    +--- androidx.vectordrawable:vectordrawable:1.1.0
|    |    |    +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |    |    +--- androidx.core:core:1.1.0 -> 1.13.0 (*)
|    |    |    \--- androidx.collection:collection:1.1.0 -> 1.4.0 (*)
|    |    \--- androidx.vectordrawable:vectordrawable-animated:1.1.0
|    |         +--- androidx.vectordrawable:vectordrawable:1.1.0 (*)
|    |         +--- androidx.interpolator:interpolator:1.0.0
|    |         |    \--- androidx.annotation:annotation:1.0.0 -> 1.7.0 (*)
|    |         \--- androidx.collection:collection:1.1.0 -> 1.4.0 (*)
|    +--- androidx.core:core:1.9.0 -> 1.13.0 (*)
|    +--- androidx.cursoradapter:cursoradapter:1.0.0
|    |    \--- androidx.annotation:annotation:1.0.0 -> 1.7.0 (*)
|    +--- androidx.drawerlayout:drawerlayout:1.0.0
|    |    +--- androidx.annotation:annotation:1.0.0 -> 1.7.0 (*)
|    |    +--- androidx.core:core:1.0.0 -> 1.13.0 (*)
|    |    \--- androidx.customview:customview:1.0.0
|    |         +--- androidx.annotation:annotation:1.0.0 -> 1.7.0 (*)
|    |         \--- androidx.core:core:1.0.0 -> 1.13.0 (*)
|    +--- androidx.fragment:fragment:1.3.6
|    |    +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |    +--- androidx.core:core:1.2.0 -> 1.13.0 (*)
|    |    +--- androidx.collection:collection:1.1.0 -> 1.4.0 (*)
|    |    +--- androidx.viewpager:viewpager:1.0.0
|    |    |    +--- androidx.annotation:annotation:1.0.0 -> 1.7.0 (*)
|    |    |    +--- androidx.core:core:1.0.0 -> 1.13.0 (*)
|    |    |    \--- androidx.customview:customview:1.0.0 (*)
|    |    +--- androidx.loader:loader:1.0.0
|    |    |    +--- androidx.annotation:annotation:1.0.0 -> 1.7.0 (*)
|    |    |    +--- androidx.core:core:1.0.0 -> 1.13.0 (*)
|    |    |    +--- androidx.lifecycle:lifecycle-livedata:2.0.0 -> 2.7.0
|    |    |    |    +--- androidx.arch.core:core-runtime:2.2.0
|    |    |    |    |    +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |    |    |    |    \--- androidx.arch.core:core-common:2.2.0 (*)
|    |    |    |    +--- androidx.lifecycle:lifecycle-livedata-core:2.7.0 (*)
|    |    |    |    +--- androidx.lifecycle:lifecycle-livedata-core-ktx:2.7.0
|    |    |    |    |    +--- androidx.lifecycle:lifecycle-livedata-core:2.7.0 (*)
|    |    |    |    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|    |    |    |    |    +--- androidx.lifecycle:lifecycle-common:2.7.0 (c)
|    |    |    |    |    +--- androidx.lifecycle:lifecycle-common-java8:2.7.0 (c)
|    |    |    |    |    +--- androidx.lifecycle:lifecycle-livedata:2.7.0 (c)
|    |    |    |    |    +--- androidx.lifecycle:lifecycle-livedata-core:2.7.0 (c)
|    |    |    |    |    +--- androidx.lifecycle:lifecycle-runtime:2.7.0 (c)
|    |    |    |    |    +--- androidx.lifecycle:lifecycle-runtime-ktx:2.7.0 (c)
|    |    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel:2.7.0 (c)
|    |    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0 (c)
|    |    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0 (c)
|    |    |    |    |    \--- androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0 (c)
|    |    |    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|    |    |    |    +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1 -> 1.7.3 (*)
|    |    |    |    +--- androidx.lifecycle:lifecycle-common:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-common-java8:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-livedata-core:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-livedata-core-ktx:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-runtime:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-runtime-ktx:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0 (c)
|    |    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0 (c)
|    |    |    |    \--- androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0 (c)
|    |    |    \--- androidx.lifecycle:lifecycle-viewmodel:2.0.0 -> 2.7.0 (*)
|    |    +--- androidx.activity:activity:1.2.4 -> 1.9.0 (*)
|    |    +--- androidx.lifecycle:lifecycle-livedata-core:2.3.1 -> 2.7.0 (*)
|    |    +--- androidx.lifecycle:lifecycle-viewmodel:2.3.1 -> 2.7.0 (*)
|    |    +--- androidx.lifecycle:lifecycle-viewmodel-savedstate:2.3.1 -> 2.7.0 (*)
|    |    +--- androidx.savedstate:savedstate:1.1.0 -> 1.2.1 (*)
|    |    \--- androidx.annotation:annotation-experimental:1.0.0 -> 1.4.0 (*)
|    \--- androidx.savedstate:savedstate:1.2.0 -> 1.2.1 (*)
+--- androidx.lifecycle:lifecycle-runtime-ktx:2.7.0
|    +--- androidx.annotation:annotation:1.0.0 -> 1.7.0 (*)
|    +--- androidx.lifecycle:lifecycle-runtime:2.7.0 (*)
|    +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|    +--- org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1 -> 1.7.3 (*)
|    +--- androidx.lifecycle:lifecycle-runtime:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-viewmodel:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-common-java8:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-livedata-core:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-common:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-livedata:2.7.0 (c)
|    \--- androidx.lifecycle:lifecycle-livedata-core-ktx:2.7.0 (c)
+--- androidx.activity:activity-compose:1.9.0
|    +--- androidx.activity:activity-ktx:1.9.0
|    |    +--- androidx.activity:activity:1.9.0 (*)
|    |    +--- androidx.core:core-ktx:1.13.0 (*)
|    |    +--- androidx.lifecycle:lifecycle-runtime-ktx:2.6.1 -> 2.7.0 (*)
|    |    +--- androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1 -> 2.7.0
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel:2.7.0 (*)
|    |    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|    |    |    +--- org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1 -> 1.7.3 (*)
|    |    |    +--- androidx.lifecycle:lifecycle-common:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-common-java8:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-livedata-core:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-runtime:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-runtime-ktx:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0 (c)
|    |    |    +--- androidx.lifecycle:lifecycle-livedata:2.7.0 (c)
|    |    |    \--- androidx.lifecycle:lifecycle-livedata-core-ktx:2.7.0 (c)
|    |    +--- androidx.savedstate:savedstate-ktx:1.2.1
|    |    |    +--- androidx.savedstate:savedstate:1.2.1 (*)
|    |    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.10 -> 1.9.22 (*)
|    |    |    \--- androidx.savedstate:savedstate:1.2.1 (c)
|    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|    |    +--- androidx.activity:activity:1.9.0 (c)
|    |    \--- androidx.activity:activity-compose:1.9.0 (c)
|    +--- androidx.compose.runtime:runtime:1.0.1 -> 1.6.6
|    |    \--- androidx.compose.runtime:runtime-android:1.6.6
|    |         +--- org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1 -> 1.7.3 (*)
|    |         +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1 -> 1.7.3 (*)
|    |         \--- androidx.compose.runtime:runtime-saveable:1.6.6 (c)
|    +--- androidx.compose.runtime:runtime-saveable:1.0.1 -> 1.6.6
|    |    \--- androidx.compose.runtime:runtime-saveable-android:1.6.6
|    |         +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |         +--- androidx.compose.runtime:runtime:1.6.6 (*)
|    |         \--- androidx.compose.runtime:runtime:1.6.6 (c)
|    +--- androidx.compose.ui:ui:1.0.1 -> 1.6.6
|    |    \--- androidx.compose.ui:ui-android:1.6.6
|    |         +--- androidx.annotation:annotation:1.6.0 -> 1.7.0 (*)
|    |         +--- androidx.compose.runtime:runtime-saveable:1.6.6 (*)
|    |         +--- androidx.compose.ui:ui-geometry:1.6.6
|    |         |    \--- androidx.compose.ui:ui-geometry-android:1.6.6
|    |         |         +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |         |         +--- androidx.compose.ui:ui:1.6.6 (c)
|    |         |         +--- androidx.compose.ui:ui-graphics:1.6.6 (c)
|    |         |         +--- androidx.compose.ui:ui-text:1.6.6 (c)
|    |         |         +--- androidx.compose.ui:ui-unit:1.6.6 (c)
|    |         |         \--- androidx.compose.ui:ui-util:1.6.6 (c)
|    |         +--- androidx.compose.ui:ui-graphics:1.6.6
|    |         |    \--- androidx.compose.ui:ui-graphics-android:1.6.6
|    |         |         +--- androidx.annotation:annotation:1.7.0 (*)
|    |         |         +--- androidx.compose.ui:ui-unit:1.6.6
|    |         |         |    \--- androidx.compose.ui:ui-unit-android:1.6.6
|    |         |         |         +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |         |         |         +--- androidx.compose.ui:ui-geometry:1.6.6 (*)
|    |         |         |         +--- androidx.compose.ui:ui:1.6.6 (c)
|    |         |         |         +--- androidx.compose.ui:ui-geometry:1.6.6 (c)
|    |         |         |         +--- androidx.compose.ui:ui-graphics:1.6.6 (c)
|    |         |         |         +--- androidx.compose.ui:ui-text:1.6.6 (c)
|    |         |         |         \--- androidx.compose.ui:ui-util:1.6.6 (c)
|    |         |         +--- androidx.compose.ui:ui:1.6.6 (c)
|    |         |         +--- androidx.compose.ui:ui-geometry:1.6.6 (c)
|    |         |         +--- androidx.compose.ui:ui-text:1.6.6 (c)
|    |         |         +--- androidx.compose.ui:ui-unit:1.6.6 (c)
|    |         |         \--- androidx.compose.ui:ui-util:1.6.6 (c)
|    |         +--- androidx.compose.ui:ui-text:1.6.6
|    |         |    \--- androidx.compose.ui:ui-text-android:1.6.6
|    |         |         +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |         |         +--- androidx.compose.ui:ui-graphics:1.6.6 (*)
|    |         |         +--- androidx.compose.ui:ui-unit:1.6.6 (*)
|    |         |         +--- androidx.compose.ui:ui:1.6.6 (c)
|    |         |         +--- androidx.compose.ui:ui-geometry:1.6.6 (c)
|    |         |         +--- androidx.compose.ui:ui-graphics:1.6.6 (c)
|    |         |         +--- androidx.compose.ui:ui-unit:1.6.6 (c)
|    |         |         \--- androidx.compose.ui:ui-util:1.6.6 (c)
|    |         +--- androidx.compose.ui:ui-unit:1.6.6 (*)
|    |         +--- androidx.compose.ui:ui-util:1.6.6
|    |         |    \--- androidx.compose.ui:ui-util-android:1.6.6
|    |         |         +--- androidx.compose.ui:ui:1.6.6 (c)
|    |         |         +--- androidx.compose.ui:ui-geometry:1.6.6 (c)
|    |         |         +--- androidx.compose.ui:ui-graphics:1.6.6 (c)
|    |         |         +--- androidx.compose.ui:ui-text:1.6.6 (c)
|    |         |         \--- androidx.compose.ui:ui-unit:1.6.6 (c)
|    |         +--- androidx.compose.ui:ui-geometry:1.6.6 (c)
|    |         +--- androidx.compose.ui:ui-graphics:1.6.6 (c)
|    |         +--- androidx.compose.ui:ui-text:1.6.6 (c)
|    |         +--- androidx.compose.ui:ui-unit:1.6.6 (c)
|    |         \--- androidx.compose.ui:ui-util:1.6.6 (c)
|    +--- androidx.lifecycle:lifecycle-viewmodel:2.6.1 -> 2.7.0 (*)
|    +--- androidx.activity:activity:1.9.0 (c)
|    \--- androidx.activity:activity-ktx:1.9.0 (c)
+--- androidx.compose:compose-bom:2024.04.01
|    +--- androidx.compose.material:material-icons-extended-android:1.6.6 (c)
|    +--- androidx.compose.material3:material3-android:1.2.1 (c)
|    +--- androidx.compose.runtime:runtime:1.6.6 (c)
|    +--- androidx.compose.runtime:runtime-saveable:1.6.6 (c)
|    +--- androidx.compose.ui:ui:1.6.6 (c)
|    +--- androidx.compose.ui:ui-android:1.6.6 (c)
|    +--- androidx.compose.ui:ui-graphics-android:1.6.6 (c)
|    +--- androidx.compose.ui:ui-tooling-preview-android:1.6.6 (c)
|    +--- androidx.compose.ui:ui-geometry:1.6.6 (c)
|    +--- androidx.compose.ui:ui-graphics:1.6.6 (c)
|    +--- androidx.compose.ui:ui-text:1.6.6 (c)
|    +--- androidx.compose.ui:ui-unit:1.6.6 (c)
|    +--- androidx.compose.ui:ui-util:1.6.6 (c)
|    +--- androidx.compose.foundation:foundation:1.6.6 (c)
|    +--- androidx.compose.foundation:foundation-layout:1.6.6 (c)
|    +--- androidx.compose.material:material-icons-core:1.6.6 (c)
|    +--- androidx.compose.material:material-ripple:1.6.6 (c)
|    +--- androidx.compose.runtime:runtime-android:1.6.6 (c)
|    +--- androidx.compose.runtime:runtime-saveable-android:1.6.6 (c)
|    +--- androidx.compose.ui:ui-geometry-android:1.6.6 (c)
|    +--- androidx.compose.ui:ui-text-android:1.6.6 (c)
|    +--- androidx.compose.ui:ui-unit-android:1.6.6 (c)
|    +--- androidx.compose.ui:ui-util-android:1.6.6 (c)
|    +--- androidx.compose.foundation:foundation-android:1.6.6 (c)
|    +--- androidx.compose.foundation:foundation-layout-android:1.6.6 (c)
|    +--- androidx.compose.material:material-icons-core-android:1.6.6 (c)
|    +--- androidx.compose.material:material-ripple-android:1.6.6 (c)
|    +--- androidx.compose.animation:animation:1.6.6 (c)
|    +--- androidx.compose.animation:animation-android:1.6.6 (c)
|    +--- androidx.compose.animation:animation-core:1.6.6 (c)
|    \--- androidx.compose.animation:animation-core-android:1.6.6 (c)
+--- androidx.compose.ui:ui-android -> 1.6.6 (*)
+--- androidx.compose.ui:ui-graphics-android -> 1.6.6 (*)
+--- androidx.compose.ui:ui-tooling-preview-android -> 1.6.6
|    +--- androidx.annotation:annotation:1.2.0 -> 1.7.0 (*)
|    +--- androidx.compose.runtime:runtime:1.6.6 (*)
|    +--- androidx.compose.ui:ui:1.6.6 (c)
|    +--- androidx.compose.ui:ui-geometry:1.6.6 (c)
|    +--- androidx.compose.ui:ui-graphics:1.6.6 (c)
|    +--- androidx.compose.ui:ui-text:1.6.6 (c)
|    +--- androidx.compose.ui:ui-unit:1.6.6 (c)
|    \--- androidx.compose.ui:ui-util:1.6.6 (c)
+--- androidx.compose.material3:material3-android -> 1.2.1
|    +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    +--- androidx.annotation:annotation-experimental:1.4.0 (*)
|    +--- androidx.compose.foundation:foundation:1.6.0 -> 1.6.6
|    |    \--- androidx.compose.foundation:foundation-android:1.6.6
|    |         +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |         +--- androidx.collection:collection:1.4.0 (*)
|    |         +--- androidx.compose.animation:animation:1.6.6
|    |         |    \--- androidx.compose.animation:animation-android:1.6.6
|    |         |         +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |         |         +--- androidx.compose.animation:animation-core:1.6.6
|    |         |         |    \--- androidx.compose.animation:animation-core-android:1.6.6
|    |         |         |         +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |         |         |         +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1 -> 1.7.3 (*)
|    |         |         |         \--- androidx.compose.animation:animation:1.6.6 (c)
|    |         |         +--- androidx.compose.foundation:foundation-layout:1.6.6
|    |         |         |    \--- androidx.compose.foundation:foundation-layout-android:1.6.6
|    |         |         |         +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |         |         |         +--- androidx.compose.ui:ui:1.6.6 (*)
|    |         |         |         \--- androidx.compose.foundation:foundation:1.6.6 (c)
|    |         |         +--- androidx.compose.runtime:runtime:1.6.6 (*)
|    |         |         +--- androidx.compose.ui:ui:1.6.6 (*)
|    |         |         +--- androidx.compose.ui:ui-geometry:1.6.6 (*)
|    |         |         \--- androidx.compose.animation:animation-core:1.6.6 (c)
|    |         +--- androidx.compose.runtime:runtime:1.6.6 (*)
|    |         +--- androidx.compose.ui:ui:1.6.6 (*)
|    |         \--- androidx.compose.foundation:foundation-layout:1.6.6 (c)
|    +--- androidx.compose.foundation:foundation-layout:1.6.0 -> 1.6.6 (*)
|    +--- androidx.compose.material:material-icons-core:1.6.0 -> 1.6.6
|    |    \--- androidx.compose.material:material-icons-core-android:1.6.6
|    |         +--- androidx.compose.ui:ui:1.6.6 (*)
|    |         \--- androidx.compose.material:material-ripple:1.6.6 (c)
|    +--- androidx.compose.material:material-ripple:1.6.0 -> 1.6.6
|    |    \--- androidx.compose.material:material-ripple-android:1.6.6
|    |         +--- androidx.compose.foundation:foundation:1.6.6 (*)
|    |         +--- androidx.compose.runtime:runtime:1.6.6 (*)
|    |         \--- androidx.compose.material:material-icons-core:1.6.6 (c)
|    +--- androidx.compose.runtime:runtime:1.6.0 -> 1.6.6 (*)
|    +--- androidx.compose.ui:ui-graphics:1.6.0 -> 1.6.6 (*)
|    \--- androidx.compose.ui:ui-text:1.6.0 -> 1.6.6 (*)
+--- androidx.compose.material:material-icons-extended-android -> 1.6.6
|    +--- androidx.compose.material:material-icons-core:1.6.6 (*)
|    +--- androidx.compose.material:material-icons-core:1.6.6 (c)
|    \--- androidx.compose.material:material-ripple:1.6.6 (c)
+--- io.socket:socket.io-client:2.1.0
|    \--- io.socket:engine.io-client:2.1.0
|         \--- com.squareup.okhttp3:okhttp:3.12.12 -> 4.9.3
|              +--- com.squareup.okio:okio:2.8.0 -> 3.4.0
|              |    \--- com.squareup.okio:okio-jvm:3.4.0
|              |         +--- org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0 -> 1.9.0 (*)
|              |         \--- org.jetbrains.kotlin:kotlin-stdlib-common:1.8.0 -> 1.9.22 (*)
|              \--- org.jetbrains.kotlin:kotlin-stdlib:1.4.10 -> 1.9.22 (*)
+--- com.browser2app:khenshin-java-securemessage:4.0.0.32
+--- com.khipu.khenshin:protocol:1.0.41
+--- org.purejava:tweetnacl-java:1.1.2
+--- androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0
|    +--- androidx.annotation:annotation-experimental:1.1.0 -> 1.4.0 (*)
|    +--- androidx.compose.runtime:runtime:1.0.1 -> 1.6.6 (*)
|    +--- androidx.compose.ui:ui:1.0.1 -> 1.6.6 (*)
|    +--- androidx.lifecycle:lifecycle-common-java8:2.7.0
|    |    +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    |    +--- androidx.lifecycle:lifecycle-common:2.7.0 (*)
|    |    +--- androidx.lifecycle:lifecycle-common:2.7.0 (c)
|    |    +--- androidx.lifecycle:lifecycle-livedata-core:2.7.0 (c)
|    |    +--- androidx.lifecycle:lifecycle-runtime:2.7.0 (c)
|    |    +--- androidx.lifecycle:lifecycle-runtime-ktx:2.7.0 (c)
|    |    +--- androidx.lifecycle:lifecycle-viewmodel:2.7.0 (c)
|    |    +--- androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0 (c)
|    |    +--- androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0 (c)
|    |    +--- androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0 (c)
|    |    +--- androidx.lifecycle:lifecycle-livedata:2.7.0 (c)
|    |    \--- androidx.lifecycle:lifecycle-livedata-core-ktx:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0 (*)
|    +--- androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0 (*)
|    +--- androidx.lifecycle:lifecycle-common-java8:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-runtime:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-runtime-ktx:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-viewmodel:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-livedata-core:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-common:2.7.0 (c)
|    +--- androidx.lifecycle:lifecycle-livedata:2.7.0 (c)
|    \--- androidx.lifecycle:lifecycle-livedata-core-ktx:2.7.0 (c)
+--- io.coil-kt:coil-compose:2.1.0
|    +--- io.coil-kt:coil-compose-base:2.1.0
|    |    +--- io.coil-kt:coil-base:2.1.0
|    |    |    +--- androidx.lifecycle:lifecycle-runtime:2.4.1 -> 2.7.0 (*)
|    |    |    +--- org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1 -> 1.7.3 (*)
|    |    |    +--- com.squareup.okhttp3:okhttp:4.9.3 (*)
|    |    |    \--- com.squareup.okio:okio:3.0.0 -> 3.4.0 (*)
|    |    \--- androidx.compose.foundation:foundation:1.1.1 -> 1.6.6 (*)
|    \--- io.coil-kt:coil:2.1.0
|         \--- io.coil-kt:coil-base:2.1.0 (*)
+--- org.apache.commons:commons-lang3:3.14.0
+--- com.squareup.okhttp3:okhttp:4.9.3 (*)
+--- com.google.code.gson:gson:2.10.1
+--- androidx.security:security-crypto:1.1.0-alpha06
|    \--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
+--- androidx.biometric:biometric:1.2.0-alpha05
|    +--- androidx.annotation:annotation:1.1.0 -> 1.7.0 (*)
|    +--- androidx.core:core:1.3.2 -> 1.13.0 (*)
|    \--- androidx.fragment:fragment:1.2.5 -> 1.3.6 (*)
+--- androidx.datastore:datastore-preferences:1.1.0
|    \--- androidx.datastore:datastore-preferences-android:1.1.0
|         +--- androidx.datastore:datastore:1.1.0
|         |    \--- androidx.datastore:datastore-android:1.1.0
|         |         +--- androidx.annotation:annotation:1.2.0 -> 1.7.0 (*)
|         |         +--- androidx.datastore:datastore-core:1.1.0
|         |         |    \--- androidx.datastore:datastore-core-android:1.1.0
|         |         |         +--- androidx.annotation:annotation:1.7.0 (*)
|         |         |         +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|         |         |         +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3 (*)
|         |         |         +--- androidx.datastore:datastore:1.1.0 (c)
|         |         |         +--- androidx.datastore:datastore-core-okio:1.1.0 (c)
|         |         |         +--- androidx.datastore:datastore-preferences:1.1.0 (c)
|         |         |         \--- androidx.datastore:datastore-preferences-core:1.1.0 (c)
|         |         +--- androidx.datastore:datastore-core-okio:1.1.0
|         |         |    \--- androidx.datastore:datastore-core-okio-jvm:1.1.0
|         |         |         +--- androidx.datastore:datastore-core:1.1.0 (*)
|         |         |         +--- com.squareup.okio:okio:3.4.0 (*)
|         |         |         +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|         |         |         +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3 (*)
|         |         |         +--- androidx.datastore:datastore:1.1.0 (c)
|         |         |         +--- androidx.datastore:datastore-core:1.1.0 (c)
|         |         |         +--- androidx.datastore:datastore-preferences:1.1.0 (c)
|         |         |         \--- androidx.datastore:datastore-preferences-core:1.1.0 (c)
|         |         +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|         |         +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3 (*)
|         |         +--- androidx.datastore:datastore-core:1.1.0 (c)
|         |         +--- androidx.datastore:datastore-core-okio:1.1.0 (c)
|         |         +--- androidx.datastore:datastore-preferences:1.1.0 (c)
|         |         \--- androidx.datastore:datastore-preferences-core:1.1.0 (c)
|         +--- androidx.datastore:datastore-preferences-core:1.1.0
|         |    \--- androidx.datastore:datastore-preferences-core-jvm:1.1.0
|         |         +--- androidx.datastore:datastore-core:1.1.0 (*)
|         |         +--- androidx.datastore:datastore-core-okio:1.1.0 (*)
|         |         +--- com.squareup.okio:okio:3.4.0 (*)
|         |         +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|         |         +--- androidx.datastore:datastore:1.1.0 (c)
|         |         +--- androidx.datastore:datastore-core:1.1.0 (c)
|         |         +--- androidx.datastore:datastore-core-okio:1.1.0 (c)
|         |         \--- androidx.datastore:datastore-preferences:1.1.0 (c)
|         +--- org.jetbrains.kotlin:kotlin-stdlib:1.8.22 -> 1.9.22 (*)
|         +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3 (*)
|         +--- androidx.datastore:datastore:1.1.0 (c)
|         +--- androidx.datastore:datastore-preferences-core:1.1.0 (c)
|         +--- androidx.datastore:datastore-core:1.1.0 (c)
|         \--- androidx.datastore:datastore-core-okio:1.1.0 (c)
+--- com.github.penfeizhou.android.animation:apng:3.0.1
|    \--- com.github.penfeizhou.android.animation:frameanimation:3.0.1
+--- org.jetbrains.kotlin:kotlin-stdlib-jdk8:{strictly 1.9.0} -> 1.9.0 (c)
+--- androidx.core:core-ktx:{strictly 1.13.0} -> 1.13.0 (c)
+--- androidx.lifecycle:lifecycle-runtime-ktx:{strictly 2.7.0} -> 2.7.0 (c)
+--- androidx.lifecycle:lifecycle-viewmodel-compose:{strictly 2.7.0} -> 2.7.0 (c)
+--- androidx.compose.ui:ui-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.activity:activity-compose:{strictly 1.9.0} -> 1.9.0 (c)
+--- androidx.compose.ui:ui-graphics-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.appcompat:appcompat:{strictly 1.6.1} -> 1.6.1 (c)
+--- androidx.compose:compose-bom:{strictly 2024.04.01} -> 2024.04.01 (c)
+--- androidx.compose.material:material-icons-extended-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.material3:material3-android:{strictly 1.2.1} -> 1.2.1 (c)
+--- androidx.compose.ui:ui-tooling-preview-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- io.socket:socket.io-client:{strictly 2.1.0} -> 2.1.0 (c)
+--- com.squareup.okhttp3:okhttp:{strictly 4.9.3} -> 4.9.3 (c)
+--- com.browser2app:khenshin-java-securemessage:{strictly 4.0.0.32} -> 4.0.0.32 (c)
+--- com.khipu.khenshin:protocol:{strictly 1.0.41} -> 1.0.41 (c)
+--- org.purejava:tweetnacl-java:{strictly 1.1.2} -> 1.1.2 (c)
+--- io.coil-kt:coil-compose:{strictly 2.1.0} -> 2.1.0 (c)
+--- org.apache.commons:commons-lang3:{strictly 3.14.0} -> 3.14.0 (c)
+--- com.google.code.gson:gson:{strictly 2.10.1} -> 2.10.1 (c)
+--- androidx.security:security-crypto:{strictly 1.1.0-alpha06} -> 1.1.0-alpha06 (c)
+--- androidx.biometric:biometric:{strictly 1.2.0-alpha05} -> 1.2.0-alpha05 (c)
+--- androidx.datastore:datastore-preferences:{strictly 1.1.0} -> 1.1.0 (c)
+--- com.github.penfeizhou.android.animation:apng:{strictly 3.0.1} -> 3.0.1 (c)
+--- org.jetbrains.kotlin:kotlin-stdlib:{strictly 1.9.22} -> 1.9.22 (c)
+--- org.jetbrains.kotlin:kotlin-stdlib-jdk7:{strictly 1.9.0} -> 1.9.0 (c)
+--- androidx.annotation:annotation:{strictly 1.7.0} -> 1.7.0 (c)
+--- androidx.core:core:{strictly 1.13.0} -> 1.13.0 (c)
+--- androidx.activity:activity:{strictly 1.9.0} -> 1.9.0 (c)
+--- androidx.appcompat:appcompat-resources:{strictly 1.6.1} -> 1.6.1 (c)
+--- androidx.cursoradapter:cursoradapter:{strictly 1.0.0} -> 1.0.0 (c)
+--- androidx.drawerlayout:drawerlayout:{strictly 1.0.0} -> 1.0.0 (c)
+--- androidx.fragment:fragment:{strictly 1.3.6} -> 1.3.6 (c)
+--- androidx.savedstate:savedstate:{strictly 1.2.1} -> 1.2.1 (c)
+--- androidx.lifecycle:lifecycle-runtime:{strictly 2.7.0} -> 2.7.0 (c)
+--- org.jetbrains.kotlinx:kotlinx-coroutines-android:{strictly 1.7.3} -> 1.7.3 (c)
+--- androidx.activity:activity-ktx:{strictly 1.9.0} -> 1.9.0 (c)
+--- androidx.compose.runtime:runtime:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.runtime:runtime-saveable:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.ui:ui:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.lifecycle:lifecycle-viewmodel:{strictly 2.7.0} -> 2.7.0 (c)
+--- androidx.compose.ui:ui-geometry:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.ui:ui-graphics:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.ui:ui-text:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.ui:ui-unit:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.ui:ui-util:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.annotation:annotation-experimental:{strictly 1.4.0} -> 1.4.0 (c)
+--- androidx.compose.foundation:foundation:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.foundation:foundation-layout:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.material:material-icons-core:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.material:material-ripple:{strictly 1.6.6} -> 1.6.6 (c)
+--- io.socket:engine.io-client:{strictly 2.1.0} -> 2.1.0 (c)
+--- androidx.lifecycle:lifecycle-common-java8:{strictly 2.7.0} -> 2.7.0 (c)
+--- androidx.lifecycle:lifecycle-viewmodel-ktx:{strictly 2.7.0} -> 2.7.0 (c)
+--- androidx.lifecycle:lifecycle-viewmodel-savedstate:{strictly 2.7.0} -> 2.7.0 (c)
+--- io.coil-kt:coil-compose-base:{strictly 2.1.0} -> 2.1.0 (c)
+--- io.coil-kt:coil:{strictly 2.1.0} -> 2.1.0 (c)
+--- com.squareup.okio:okio:{strictly 3.4.0} -> 3.4.0 (c)
+--- androidx.datastore:datastore-preferences-android:{strictly 1.1.0} -> 1.1.0 (c)
+--- com.github.penfeizhou.android.animation:frameanimation:{strictly 3.0.1} -> 3.0.1 (c)
+--- org.jetbrains:annotations:{strictly 23.0.0} -> 23.0.0 (c)
+--- androidx.annotation:annotation-jvm:{strictly 1.7.0} -> 1.7.0 (c)
+--- androidx.versionedparcelable:versionedparcelable:{strictly 1.1.1} -> 1.1.1 (c)
+--- androidx.vectordrawable:vectordrawable:{strictly 1.1.0} -> 1.1.0 (c)
+--- androidx.vectordrawable:vectordrawable-animated:{strictly 1.1.0} -> 1.1.0 (c)
+--- androidx.customview:customview:{strictly 1.0.0} -> 1.0.0 (c)
+--- androidx.collection:collection:{strictly 1.4.0} -> 1.4.0 (c)
+--- androidx.viewpager:viewpager:{strictly 1.0.0} -> 1.0.0 (c)
+--- androidx.loader:loader:{strictly 1.0.0} -> 1.0.0 (c)
+--- androidx.lifecycle:lifecycle-livedata-core:{strictly 2.7.0} -> 2.7.0 (c)
+--- androidx.arch.core:core-common:{strictly 2.2.0} -> 2.2.0 (c)
+--- androidx.lifecycle:lifecycle-common:{strictly 2.7.0} -> 2.7.0 (c)
+--- org.jetbrains.kotlinx:kotlinx-coroutines-core:{strictly 1.7.3} -> 1.7.3 (c)
+--- org.jetbrains.kotlinx:kotlinx-coroutines-bom:{strictly 1.7.3} -> 1.7.3 (c)
+--- androidx.savedstate:savedstate-ktx:{strictly 1.2.1} -> 1.2.1 (c)
+--- androidx.compose.runtime:runtime-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.runtime:runtime-saveable-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.ui:ui-geometry-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.ui:ui-text-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.ui:ui-unit-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.ui:ui-util-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.foundation:foundation-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.foundation:foundation-layout-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.material:material-icons-core-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.compose.material:material-ripple-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- io.coil-kt:coil-base:{strictly 2.1.0} -> 2.1.0 (c)
+--- com.squareup.okio:okio-jvm:{strictly 3.4.0} -> 3.4.0 (c)
+--- androidx.datastore:datastore:{strictly 1.1.0} -> 1.1.0 (c)
+--- androidx.datastore:datastore-preferences-core:{strictly 1.1.0} -> 1.1.0 (c)
+--- androidx.interpolator:interpolator:{strictly 1.0.0} -> 1.0.0 (c)
+--- androidx.collection:collection-jvm:{strictly 1.4.0} -> 1.4.0 (c)
+--- androidx.lifecycle:lifecycle-livedata:{strictly 2.7.0} -> 2.7.0 (c)
+--- org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:{strictly 1.7.3} -> 1.7.3 (c)
+--- androidx.compose.animation:animation:{strictly 1.6.6} -> 1.6.6 (c)
+--- org.jetbrains.kotlin:kotlin-stdlib-common:{strictly 1.9.22} -> 1.9.22 (c)
+--- androidx.datastore:datastore-android:{strictly 1.1.0} -> 1.1.0 (c)
+--- androidx.datastore:datastore-preferences-core-jvm:{strictly 1.1.0} -> 1.1.0 (c)
+--- androidx.arch.core:core-runtime:{strictly 2.2.0} -> 2.2.0 (c)
+--- androidx.lifecycle:lifecycle-livedata-core-ktx:{strictly 2.7.0} -> 2.7.0 (c)
+--- androidx.compose.animation:animation-android:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.datastore:datastore-core:{strictly 1.1.0} -> 1.1.0 (c)
+--- androidx.datastore:datastore-core-okio:{strictly 1.1.0} -> 1.1.0 (c)
+--- androidx.compose.animation:animation-core:{strictly 1.6.6} -> 1.6.6 (c)
+--- androidx.datastore:datastore-core-android:{strictly 1.1.0} -> 1.1.0 (c)
+--- androidx.datastore:datastore-core-okio-jvm:{strictly 1.1.0} -> 1.1.0 (c)
\--- androidx.compose.animation:animation-core-android:{strictly 1.6.6} -> 1.6.6 (c)
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
implementation 'com.khipu:khenshin-client-android:+' //Fija la versión antes de pasar a producción
```   

## Agregar plugin de Kotlin

Para poder usar la biblioteca es necesario agregar el plugin de kotlin-android al proyecto. 

```build.gradle.kts
plugins {
  id("org.jetbrains.kotlin.android") version "2.0.0-RC2"
}
```

## Invocar khenshin

```java
    //Creamos un lanzador de la actividad de khenshin y definimos un callback para mostrar la respuesta
    ActivityResultLauncher khipuLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback() {
              @Override
              public void onActivityResult(Object o) {
                      Log.v("Callback", "mensaje final recibido " + o.toString());
                      KhenshinResult result = (KhenshinResult) ((ActivityResult) o).getData().getExtras().getSerializable(KHENSHIN_RESULT_EXTRA);
                      resultText.setText(result.toString());
                      }
        });
    ...
    
    //Mas adelante, iniciamos la actividad
        khipuLauncher.launch(getKhenshinLauncherIntent(
          getBaseContext(),
          operationId,
          new KhenshinOptions.Builder()
            .build()
        ));
```

## Parámetros de inicialización de khenshin

En la sección anterior se utilizó la construcción minimal de khenshin, a continuación veremos todos los parámetros opcionales.

```java

    getKhenshinLauncherIntent(
        //Obligatorios
        getBaseContext(),
        operationId,
        //Constructor de opciones
        new KhenshinOptions.Builder()
            //Configura la cabecera de la actividad de pago con elementos como títulos y logos, usando el objeto KhenshinHeader.
            .header(
              new KhenshinHeader.Builder()
                //Identificador de cabecera personalizada
                .headerLayoutId(R.layout.header_layout)
                //Identificador de contenedor de nombre del comercio
                .merchantNameId(R.id.merchant_name)
                //Identificador de contenedor de medio de pago
                .paymentMethodId(R.id.payment_method_value)
                //Identificador de contenedor de monto de transacción
                .amountId(R.id.amount_value)
                //Identificador de contenedor de asunto del pago
                .subjectId(R.id.subject_value)
                .build()
            )
            //Título para la barra superior durante el proceso de pago.
            .topBarTitle("Khipu")
            //Permite seleccionar el tema, soporta KhenshinOptions.Theme.LIGHT y KhenshinOptions.Theme.DARK
            .theme(theme)
            //Si es verdadero, se omite la página de resumen al final del proceso de pago.
            .skipExitPage(false)
            //Configuración regional para el idioma de la interfaz. El formato estándar que combina un código de idioma ISO 639-1 y un código de país ISO 3166. Por ejemplo, `"es_CL"` para español de Chile.
            .locale("es_CL")
            //Permite personalizar los colores de la interfaz de usuario.
            .colors(
                new KhenshinColors.Builder()
                    //Color de fondo para la barra superior en modo claro.
                    .lightTopBarContainer(lightTopBarContainer)
                    //Color de los elementos en la barra superior en modo claro.
                    .lightOnTopBarContainer(lightOnTopBarContainer)
                    //Color primario en modo claro.
                    .lightPrimary(lightPrimary)
                    //Color de los elementos sobre el color primario en modo claro.
                    .lightOnPrimary(lightOnPrimary)
                    //Color de fondo general en modo claro.
                    .lightBackground(lightBackground)
                    //Color de los elementos sobre el fondo general en modo claro.
                    .lightOnBackground(lightOnBackground)
                    //Color de fondo para la barra superior en modo oscuro.
                    .darkTopBarContainer(darkTopBarContainer)
                    //Color de los elementos en la barra superior en modo oscuro.
                    .darkOnTopBarContainer(darkOnTopBarContainer)
                    //Color primario en modo oscuro.
                    .darkPrimary(darkPrimary)
                    //Color de los elementos sobre el color primario en modo oscuro.
                    .darkOnPrimary(darkOnPrimary)
                    //Color de fondo general en modo oscuro.
                    .darkBackground(darkBackground)
                    //Color de los elementos sobre el fondo general en modo oscuro.
                    .darkOnBackground(darkOnBackground)
                    .build()
            )
            .build()

```

## Colores

En tu proyecto puedes determinar los colores que usará Khenshin en las pantallas de pago usando el constructor de KhenshinColors. cada uno de los campos recibe un string que representa un color en hexadecimal.

## Vistas

Para personalizar aún más la visualización de Khenshin puedes definir tu propia cabecera.

Tu cabecera personalizada debe estar en formato xml y normalmente estará ubicado en res/layout. La cabecera debe tener definidos campos para mostrar el asunto del pago, el nombre del comercio, el medio de pago y el monto. Una vez definidos, se pasan al constructor de cabecera KhenshinHeader y se agregan al campo header del constructor de opciones KhenshinOptions:

```java
    new KhenshinHeader.Builder()
        //Identificador de cabecera personalizada
        .headerLayoutId(R.layout.header_layout)
        //Identificador de contenedor de nombre del comercio
        .merchantNameId(R.id.merchant_name)
        //Identificador de contenedor de medio de pago
        .paymentMethodId(R.id.payment_method_value)
        //Identificador de contenedor de monto de transacción
        .amountId(R.id.amount_value)
        //Identificador de contenedor de asunto del pago
        .subjectId(R.id.subject_value)
        .build()

```

## Respuesta

Al momento de crear el lanzador de Khenshin, se define el callback con las acciones necesarias para terminar el proceso

```java
    ActivityResultLauncher khipuLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback() {
        @Override
        public void onActivityResult(Object o) {
                KhenshinResult result = (KhenshinResult) ((ActivityResult) o).getData().getExtras().getSerializable(KHENSHIN_RESULT_EXTRA);
                resultText.setText(result.toString());
                }
        });
```

El objeto KhenshinResult contiene la información del resultado del proceso. Contiene los siguientes campos:

- **operationId**:`String`
  El identificador único de la intención de pago
- **exitTitle**: `String`
  Título que se mostrará al usuario en la pantalla de salida, reflejando el resultado de la operación.
- **exitMessage**:`String`
  Mensaje que se mostrará al usuario, proporcionando detalles adicionales sobre el resultado de la operación.
- **exitUrl**: `String`
  URL a la que retornará la aplicación al terminar el proceso.
- **result**: `String`
  Resultado general de la operación, los valores posibles son:
    - **OK**: Éxito
    - **ERROR**: Error
    - **WARNING**: Advertencias
    - **CONTINUE**: Operación necesita más pasos
- **failureReason**: `String` (Opcional)
  Describe la razón del fallo, si la operación no fue exitosa.
- **continueUrl**: `String`(Opcional)
  Disponible solo cuando el resultado es "CONTINUE", que indica la URL a seguir para continuar la operación.

