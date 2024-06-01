import {
    IonButton,
    IonCol,
    IonContent,
    IonGrid,
    IonHeader,
    IonPage,
    IonRow,
    IonTitle,
    IonToolbar
} from '@ionic/react';
import './Tab1.css';
import {useState} from "react";
import {Khipu, KhipuOptions, KhipuResult} from "capacitor-khipu";

const Tab1: React.FC = () => {

    const [result, setResult] = useState({} as KhipuResult)

    const startOperation = () => {
        Khipu.startOperation({
            operationId: 'lev2r3ypv6gx',
            options: {
                title: 'KhipuIonicExample',
                skipExitPage: false,
                locale: 'es_CL',
                theme: 'light'
            } as KhipuOptions
        }).then(setResult)
    }
    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>Tab 1</IonTitle>
                </IonToolbar>
            </IonHeader>
            <IonContent fullscreen>
                <IonHeader collapse="condense">
                    <IonToolbar>
                        <IonTitle size="large">Tab 1</IonTitle>
                    </IonToolbar>
                </IonHeader>
                <IonButton onClick={startOperation}>Start Payment</IonButton>
                <IonGrid>
                    <IonRow>
                        <IonCol>operationId</IonCol>
                        <IonCol>{result.operationId}</IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>result</IonCol>
                        <IonCol>{result.result}</IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>exitTitle</IonCol>
                        <IonCol>{result.exitTitle}</IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>exitMessage</IonCol>
                        <IonCol>{result.exitMessage}</IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>exitUrl</IonCol>
                        <IonCol>{result.exitUrl}</IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>failureReason</IonCol>
                        <IonCol>{result.failureReason}</IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>continueUrl</IonCol>
                        <IonCol>{result.continueUrl}</IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>events</IonCol>
                        <IonCol>{JSON.stringify(result.events)}</IonCol>
                    </IonRow>
                </IonGrid>

            </IonContent>
        </IonPage>
    );
};

export default Tab1;
