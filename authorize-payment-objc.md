# Autorizar el pago en una aplicación iOS (Objective C)

La biblioteca de Khipu Inside se llama **khenshin** y se distribuye como un Cocoapod. Para poder utilizarlo en tu App debes contar con acceso a nuestro repositorio privado: https://bitbucket.org/khipu/khenshin-pod.git

En este repositorio se encuentra una App de demostración en la carpeta [app/ios/objc](https://github.com/khipu/khipu-inside-demo/tree/master/app/ios/objc) . Para ejecutarla se debe abrir con Android Studio o IntelliJ

Los pasos necesarios para utilizar la biblioteca nativa iOS para Browser2app son:


1. [Agregar cocoapod khenshin](#agregar-cocoapod-khenshin)
2. [Flags de compilación](#flags-de-compilación)
3. [Inicialización de la biblioteca](#inicialización-de-la-biblioteca)
4. [Invocar Khipu desde tu app](#invocar-khipu-desde-tu-app)
 
## Agregar cocoapod khenshin
Para instalar khenshin en tu proyecto es necesario utilizar cocoapods, para eso se debe crear o modificar un archivo Podfile para que contenga la referencia al pod de khenshin.

```
target 'Khipu Inside Demo' do
    # Uncomment this line if you're using Swift or would like to use dynamic frameworks
    use_frameworks!

    # Pods for Khipu Inside Demo
    pod 'khenshin', :git => 'https://bitbucket.org/khipu/khenshin-pod.git', :tag => '1.298'
end
```

Luego, para instalar el Pod se debe ejecutar *pod install* que descargará el pod de khenshin, todas sus dependencias y modificará el proyecto Xcode para incluirlas.

```
> pod install
Analyzing dependencies
Downloading dependencies
Using AFNetworking (3.1.0)
Using ActionSheetPicker-3.0 (2.3.0)
Using BEMCheckBox (1.4.1)
Using IQKeyboardManager (5.0.3)
Using JSONModel (1.7.0)
Using JVFloatLabeledTextField (1.2.1)
Using PPTopMostController (0.0.1)
Using RaptureXML (1.0.1)
Using TTTAttributedLabel (2.0.0)
Using Toast (3.1.0)
Using khenshin (1.298)
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

En el archivo *AppDelegate.m* debes inicializar **khenshin** en el selector *didFinishLaunchingWithOptions* 

```objc
- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    [KhenshinInterface initWithBuilderBlock:^(KhenshinBuilder *builder) {
        builder.cerebroAPIURL = @"https://khipu.com/cerebro/";
        builder.automatonAPIURL = @"https://khipu.com/app/2.0";
        builder.mainButtonStyle = KHMainButtonFatOnForm;
        builder.barLeftSideLogo = [[UIImage alloc] init];
    }];
    return YES;
}
```

## Invocar Khipu desde tu app

Para invocar Khipu se utiliza el método *startEngineWithPaymentExternalId* de la clase *KhenshinInterface*, para esto es necesario contar con el ID de pago generado en pasos anteriores.


```objc
    [KhenshinInterface startEngineWithPaymentExternalId:<ID de pago>
                                         userIdentifier:@""
                                      isExternalPayment:YES
                                                success:^(NSURL *returnURL){
        NSLog(@"SUCCESS");
    }
                                                failure:^(NSURL *returnURL){
        NSLog(@"FAILURE");
    }
                                               animated:NO];
```
