/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  StatusBar,
  Button,
  Alert,
  TextInput,
} from 'react-native';

import {Colors} from 'react-native/Libraries/NewAppScreen';

import {NativeModules} from 'react-native';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {text: ''};
  }

  startPaymentById = () => {
    console.log('payment');
    NativeModules.KhenshinBridge.startPaymentById(this.state.text, result => {
      Alert.alert('Result', result, [], {cancelable: false});
    });
  };

  render() {
    return (
      <>
        <StatusBar barStyle="dark-content" />
        <SafeAreaView style={{flexDirection: 'row', flex: 1, margin: 40}}>
          <TextInput
            style={{height: 40, flex: 1}}
            placeholder="PaymentId"
            onChangeText={text => this.setState({text})}
            value={this.state.text}
          />
          <Button title={'Pay'} onPress={this.startPaymentById} style={{flex: 1}} />
        </SafeAreaView>
      </>
    );
  }
}

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});

export default App;
