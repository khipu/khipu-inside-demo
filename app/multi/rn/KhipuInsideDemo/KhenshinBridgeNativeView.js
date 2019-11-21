//  Created by react-native-create-bridge

import React, { Component } from 'react'
import { requireNativeComponent } from 'react-native'

const KhenshinBridge = requireNativeComponent('KhenshinBridge', KhenshinBridgeView)

export default class KhenshinBridgeView extends Component {
  render () {
    return <KhenshinBridge {...this.props} />
  }
}

KhenshinBridgeView.propTypes = {
  exampleProp: React.PropTypes.any
}
