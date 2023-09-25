# Autorizar el pago en una aplicación react-native

## Agrega la librería a tu proyecto react-native

    npm install https://github.com/khipu/react-native-khenshin#1.2.1 --save

Seguramente vas a preferir hacer un fork del proyecto react-native-khenshin para poder personalizar la interfaz. En ese caso debes instalarlo desde tu repositorio forkeado.

    npm install https://<my-custom-repo> --save

## Instalación y configuración

- [x] [react-native 0.59.x](https://github.com/khipu/react-native-khenshin/blob/master/docs/INSTALL.0.59.x.md)
- [x] [react-native 0.60.x to 0.71.x](https://github.com/khipu/react-native-khenshin/blob/master/docs/INSTALL.0.60.x--0.71.x.md)

## Uso

```typescript
import React from 'react';
import {SafeAreaView, ScrollView, Header, Button} from 'react-native';
import Khipu from 'react-native-khenshin';

function App(this: any): JSX.Element {
    const onStartPayment = () => {
        Khipu.startPaymentById('paymentId')
            .then(({status, result}) => {
                if (status === 'CONCILIATING') {
                    // khipu is conciliating the payment
                } else if (status === 'USER_CANCELED') {
                    // The user cancelled the transaction
                } else {
                    // Error!, see `result` for details
                    console.log(result);
                }
            })
            .catch((err: any) => console.log({err}));
    };

    return (
        <SafeAreaView>
            <ScrollView>
                <Header />
                <Button title={'pagar'} onPress={onStartPayment}></Button>
            </ScrollView>
        </SafeAreaView>
    );
}
```
