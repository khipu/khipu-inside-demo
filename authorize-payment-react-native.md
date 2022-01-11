# Autorizar el pago en una aplicación react-native

> Esta librería está disponible sólo para Android momentáneamente.

## Agrega la librería a tu proyecto react-native

    npm install https://github.com/khipu/react-native-khenshin#1.1.0 --save

## Instalación y configuración

- [x] [react-native 0.59.x](https://github.com/khipu/react-native-khenshin/blob/master/docs/INSTALL.0.59.x.md)
- [x] [react-native 0.60.x to 0.66.x](https://github.com/khipu/react-native-khenshin/blob/master/docs/INSTALL.0.60.x--0.66.x.md)

## Uso

```javascript
import React from 'react';
import {TouchableOpacity, Text} from 'react-native';
const {RNKhenshin} = ReactNative.NativeModules;

export default class MyApp extends React.Component {
 
  onStartPayment = () => {
    RNKhenshin.startPaymentById('paymentId').then(({status, result}) => {
      if (status === 'CONCILIATING') {
         // khipu is conciliating the payment
      } else if (status === 'USER_CANCELED') {
        // The user cancelled the transaction
      } else {
        // Error!, see `result` for details
        console.log(result);
      }
    });
  };
 
  render() {
    return (
      <TouchableOpacity onPress={this.onStartPayment}>
        <Text>Start payment</Text>
      </TouchableOpacity>
    );
  }
}
```
