# Autorizar el pago en una aplicación react-native

## Agrega la librería a tu proyecto react-native

    npm install https://github.com/khipu/capacitor-khenshin#1.1.0 --save

o

    yarn add https://github.com/khipu/capacitor-khenshin#1.1.0

Seguramente vas a preferir hacer un fork del proyecto capacitor-khenshin para poder personalizar la interfaz. En ese caso debes instalarlo desde tu repositorio forkeado.

    npm install https://<my-custom-repo> --save

o

    yarn add https://<my-custom-repo>

## Instalación y configuración

- [Instalación en ionic](https://github.com/khipu/capacitor-khenshin/blob/main/README.md)

## Uso

```typescript
import {IonAlert, IonButton, IonPage} from '@ionic/react';
import {CapacitorKhenshin} from 'capacitor-khenshin';
import {useState} from "react";

const Home: React.FC = () => {

    const [alertMessage, setShowAlert] = useState('');


    return (
        <IonPage>
        <IonButton onClick={async () => {
                const result = await CapacitorKhenshin.startPaymentById({paymentId: 'gqkndtm8ujjb'});
                console.log(result);
                setShowAlert(JSON.stringify(result));
            }}>Pagar</IonButton>
            <IonAlert
            isOpen={alertMessage !== ''}
            onDidDismiss={() => setShowAlert('')}
            header="Alert"
            subHeader="Important message"
            message={alertMessage}
            buttons={['OK']}
            />
    </IonPage>
    );
};

export default Home;


```
