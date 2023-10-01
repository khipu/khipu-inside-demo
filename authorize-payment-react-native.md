# Autorizar el pago en una aplicación react-native

## Agrega la librería a tu proyecto react-native

    npm install https://github.com/khipu/react-native-khenshin#1.3.1 --save

o

    yarn add https://github.com/khipu/react-native-khenshin#1.3.1

Seguramente vas a preferir hacer un fork del proyecto react-native-khenshin para poder personalizar la interfaz. En ese caso debes instalarlo desde tu repositorio forkeado.

    npm install https://<my-custom-repo> --save

o

    yarn add https://<my-custom-repo>

## Instalación y configuración

- [x] [react-native 0.60.x to 0.72.x](https://github.com/khipu/react-native-khenshin/blob/master/docs/INSTALL.0.60.x--0.71.x.md)

## Uso

```typescript
import React from 'react';
import {SafeAreaView, ScrollView, Button} from 'react-native';
import Khipu from 'react-native-khenshin';

function App(this: any): JSX.Element {
    const onStartPayment = () => {
        Khipu.startPaymentById(<ID DE PAGO>)
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
                <Button title={'pagar'} onPress={onStartPayment} />
            </ScrollView>
        </SafeAreaView>
);
}

export default App;

```
