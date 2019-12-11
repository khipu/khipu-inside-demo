# Autorizar el pago en una aplicación iOS (Swift)

La biblioteca de Khipu Inside se llama **khenshin** y se distribuye como un Cocoapod.

En este repositorio se encuentra una App de demostración en la carpeta [app/ios/swift](https://github.com/khipu/khipu-inside-demo/tree/master/app/ios/swift) . Para ejecutarla se debe abrir con Android Studio o IntelliJ

Los pasos necesarios para utilizar la biblioteca nativa iOS para B2app son:


1. [Agregar cocoapod khenshin](#agregar-cocoapod-khenshin)
2. [Flags de compilación](#flags-de-compilación)
3. [Inicialización de la biblioteca](#inicialización-de-la-biblioteca)
4. [Invocar Khipu desde tu app](#invocar-khipu-desde-tu-app)
 
## Tamaño de la biblioteca (cuanto afecta a tu aplicación)

La biblioteca khenshin se distribuye como un Cocoapod Universal, es decir, que incluye versiones en las cuatro arquitecturas soportadas por iOS, tanto para simuladores como para dispositivos físicos.

El tamaño de la biblioteca se detalla según la siguiente tabla dependiendo de la arquitectura en que se utilice.

| Arquitectura | Tamaño |
|--------------|--------:|
|Arm64|12MB|
|Armv7|11MB|
|i386|11MB|
|x86_64|11MB|

khenshin utiliza, además, otras bibliotecas como dependencia, el tamaño final que agregará a tu aplicación depende del grado de duplicidad de esas bibliotecas. A modo de ejemplo. Una applicación vacía, que sólo utiliza khenshin y en una arquitectura determinada es de 32MB, es decir, el tamaño que khenshin agregará a tu aplicación varía entre 11 y 32MB dependiendo del grado de intersección de las bibliotecas utilizadas.

Las bibliotecas que khenshin utiliza como dependencia son las siguientes:

```
PODS:
  - ActionSheetPicker-3.0 (2.3.0)
  - AFNetworkActivityLogger (3.0.0):
    - AFNetworking/NSURLSession (~> 3.0)
  - AFNetworking (3.1.0):
    - AFNetworking/NSURLSession (= 3.1.0)
    - AFNetworking/Reachability (= 3.1.0)
    - AFNetworking/Security (= 3.1.0)
    - AFNetworking/Serialization (= 3.1.0)
    - AFNetworking/UIKit (= 3.1.0)
  - AFNetworking-Synchronous/3.x (1.1.0):
    - AFNetworking (~> 3.0)
  - AFNetworking/NSURLSession (3.1.0):
    - AFNetworking/Reachability
    - AFNetworking/Security
    - AFNetworking/Serialization
  - AFNetworking/Reachability (3.1.0)
  - AFNetworking/Security (3.1.0)
  - AFNetworking/Serialization (3.1.0)
  - AFNetworking/UIKit (3.1.0):
    - AFNetworking/NSURLSession
  - BEMCheckBox (1.4.1)
  - FMDB (2.7.5):
    - FMDB/standard (= 2.7.5)
  - FMDB/standard (2.7.5)
  - IQKeyboardManager (5.0.3)
  - JSONModel (1.7.0)
  - khenshin (1.548):
    - ActionSheetPicker-3.0 (= 2.3.0)
    - AFNetworkActivityLogger (= 3.0.0)
    - AFNetworking (= 3.1.0)
    - AFNetworking-Synchronous/3.x (= 1.1.0)
    - BEMCheckBox (= 1.4.1)
    - FMDB (= 2.7.5)
    - IQKeyboardManager (= 5.0.3)
    - JSONModel (= 1.7.0)
    - PPTopMostController (= 0.0.1)
    - RaptureXML (= 1.0.1)
    - TTTAttributedLabel (= 2.0.0)
  - PPTopMostController (0.0.1)
  - RaptureXML (1.0.1)
  - Toast (3.1.0)
  - TTTAttributedLabel (2.0.0)
``` 
 
Es importante mencionar que el tamaño al que nos referimos en este documento corresponde al tamaño de la aplicación instalada, lo que el usuario debe descargar de Apple Store es siempre menor y depende de optimizaciones que haga Apple con la aplicación. 
 
## Agregar cocoapod khenshin
Para instalar khenshin en tu proyecto es necesario utilizar cocoapods, para eso se debe crear o modificar un archivo Podfile para que contenga la referencia al pod de khenshin.

```
target 'Khipu Inside Demo' do
    # Uncomment this line if you're using Swift or would like to use dynamic frameworks
    use_frameworks!

    # Pods for Khipu Inside Demo
    pod 'khenshin' #Fija la versión antes de pasar a producción
end
```

Luego, para instalar el Pod se debe ejecutar *pod install* que descargará el pod de khenshin, todas sus dependencias y modificará el proyecto Xcode para incluirlas.

```
> pod install
Analyzing dependencies
Downloading dependencies
Using AFNetworkActivityLogger (3.0.0)
Using AFNetworking (3.1.0)
Using AFNetworking-Synchronous (1.1.0)
Using ActionSheetPicker-3.0 (2.3.0)
Using BEMCheckBox (1.4.1)
Using FMDB (2.7.5)
Using IQKeyboardManager (5.0.3)
Using JSONModel (1.7.0)
Using PPTopMostController (0.0.1)
Using RaptureXML (1.0.1)
Using TTTAttributedLabel (2.0.0)
Using Toast (3.1.0)
Using khenshin (1.599)
Generating Pods project
Integrating client project
Sending stats
Pod installation complete! There is 1 dependency from the Podfile and 11 total pods installed.
```

Si tu proyecto era del tipo .xcodeproj, ahora tendrás un proyecto del tipo .xcworkspace, de ahora en adelante debes abrir éste con Xcode.


## Flags de compilación  
Valida que tu proyecto tenga configurada la banderas -ObjC y -l"xml2" 

> Project -> Build Settings -> Other Linker Flags -> *-Objc* *-l"xml2"*

# Inicialización de la biblioteca 

En el archivo *AppDelegate.swift* debes inicializar **khenshin** en la función *application* 

```swift
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        
        KhenshinInterface.initWithBuilderBlock {(builder: KhenshinBuilder?) -> Void in
            builder?.APIUrl = "https://khipu.com/app/enc/"
        }
        
        return true
    }
```

## Parámetros de inicialización de khenshin

En la sección anterior se utilizó la construcción minimal de khenshin, a continuación veremos todos los parámetros opcionales.

```objc

    KhenshinInterface.initWithBuilderBlock {(builder: KhenshinBuilder?) -> Void in
        # Obligatorios
        builder?.APIUrl = "https://khipu.com/app/enc/"

        # Los siguientes parámetros son opcionales

        # Permitir guardar credenciales bancarias en el dispositivo, pre-determinado no
        builder?.allowCredentialsSaving = true

        # Estilo del botón continuar, puede ser:
        # - KHMainButtonDefault: Botón en la barra de herramientas, pre-determinado
        # - KHMainButtonFatOnForm: Botón en los formularios con fondo sólido
        # - KHMainButtonSkinnyOnForm: Botón en los formularios sin fondo
        builder?.mainButtonStyle = Int(KHMainButtonFatOnForm.rawValue)

        # Esconder la barra de navegación estilo browser, pre-determinado: no
        builder?.hideWebAddressInformationInForm = true

        # Usar el logo de la app en la barra centrar del toolbar, pre-determinado: no
        builder?.useBarCenteredLogoInForm = true

        # Logo para usar en la barra (UIImage), pre-determinado: nil
        builder?.barCenteredLogo = UIImage.init()

        # Logo para usar en la izquierda de la barra (UIImage), pre-determinado: nil
        builder?.barLeftSideLogo = UIImage.init()

        # Color principal de la app, barra principal y botones principales (UIColor)
        builder?.principalColor = UIColor.blue

        # Color principal oscuro, para barra de status (UIColor)
        builder?.darkerPrincipalColor = UIColor.black

        # Color secundario (UIColor)
        builder?.secondaryColor = UIColor.red

        # Color de fondo de los formularios (UIColor)
        builder?.backgroundColor = UIColor.white

        # Tinte para la barra principal
        builder?.barTintColor = UIColor.white

        # Color para el indicador de progreso
        builder?.progressColor = UIColor.red

        # Tamaño del font de la barra de navegación
        builder?.navigationFontSize = 10.0

        # Color del texto de la barra de navegación
        builder?.navigationBarTextTint = UIColor.white

        # Color del texto en formularios
        builder?.textColorHex = "#000000"

        # Color de los controles en formularios
        builder?.normalControlColorHex = "#333333"

        # Font normal a usar
        builder?.font = UIFont.systemFont(ofSize: 16.0)

        # Font bold a usar
        builder?.fontBold = UIFont.boldSystemFont(ofSize: 16.0)

        # Font light a usar
        builder?.fontLight = UIFont.lightSystemFont(ofSize: 16.0)

        # No desplegar las páginas de salida, en vez, retornar directamente a la aplicación host.
        builder?.skipExitPage = true

        # Mantener cookies entre una ejecución y otra
        builder?.keepCookies = true

        # Imagen para poner en el fondo de los formularios
        builder?.backgroundImage = UIImage.init()

        # Imagen para usar como spinner de progreso
        builder?.spinnerImage = UIImage.init()
        
        # Separador de decimales
        builder?.decimalSeparator = ","

        # Separador de miles
        builder?.groupingSeparator = "."

        # Vista que se usa en el encabezado de los formularios khenshin, debe ser del tipo UIView<ProcessHeader>
        builder?.processHeader = (Bundle.main.loadNibNamed("PaymentProcessHeader", owner: self, options: nil)?[0] as! UIView & ProcessHeader)

        # Vista que se usar al terminar un pago exitoso, debe ser del tipo UIViewController<ProcessExit>
        builder?.processSuccess = (Bundle.main.loadNibNamed("PaymentProcessSuccess", owner: self, options: nil)?[0] as! UIViewController & ProcessExit)    

        # Vista que se usar al terminar un pago con error, debe ser del tipo UIViewController<ProcessExit>
        builder?.processFailure = (Bundle.main.loadNibNamed("PaymentProcessFailure", owner: self, options: nil)?[0] as! UIViewController & ProcessExit)    

        # Vista que se usar al terminar un pago con dudas sobre el resultado, debe ser del tipo UIViewController<ProcessExit>
        builder?.processWarning = (Bundle.main.loadNibNamed("PaymentProcessWarning", owner: self, options: nil)?[0] as! UIViewController & ProcessExit)   
        

    }];

}
```


## Invocar Khipu desde tu app

Para invocar Khipu se utiliza el método *startEngine* de la clase *KhenshinInterface*, para esto es necesario contar con el ID de pago generado en pasos anteriores.

```swift
        KhenshinInterface.startEngine(withPaymentExternalId: paymentId.text, userIdentifier: "", isExternalPayment: true, success: { (exitURL: URL?) in
            NSLog("SUCCESS")
        }, failure: { (exitURL: URL?) in
            NSLog("FAILURE")
        }, animated: false)
```


Recordar que siempre se debe esperar la notificación por API de khipu para considerar que un pago aprobado. Como se explican en [la documentación del proceso de pago](README.md).
