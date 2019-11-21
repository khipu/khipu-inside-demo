//  Created by react-native-create-bridge

import { NativeModules } from 'react-native'

const { KhenshinBridge } = NativeModules

export default {
  exampleMethod () {
    return KhenshinBridge.exampleMethod()
  },

  EXAMPLE_CONSTANT: KhenshinBridge.EXAMPLE_CONSTANT
}
