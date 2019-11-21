//  Created by react-native-create-bridge

package com.khipuinsidedemo.khenshinbridge;

import android.app.Activity;
import android.content.Intent;
import com.browser2app.khenshin.KhenshinConstants;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.khipuinsidedemo.MainApplication;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class KhenshinBridgeModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    public static final String REACT_CLASS = "KhenshinBridge";
    private static final int START_PAYMENT_REQUEST_CODE = 1001;
    private static ReactApplicationContext reactContext = null;
    private static final String TAG = KhenshinBridgeModule.class.getSimpleName();
    private Callback callback;

    public KhenshinBridgeModule(ReactApplicationContext context) {
        // Pass in the context to the constructor and save it so you can emit events
        // https://facebook.github.io/react-native/docs/native-modules-android.html#the-toast-module
        super(context);
        reactContext = context;
        reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        // Tell React the name of the module
        // https://facebook.github.io/react-native/docs/native-modules-android.html#the-toast-module
        return REACT_CLASS;
    }

    @Override
    public Map<String, Object> getConstants() {
        // Export any constants to be used in your native module
        // https://facebook.github.io/react-native/docs/native-modules-android.html#the-toast-module
        final Map<String, Object> constants = new HashMap<>();
        constants.put("EXAMPLE_CONSTANT", "example");

        return constants;
    }

    @ReactMethod
    public void startPaymentById(String paymentId, Callback callback) {
        this.callback = callback;
        Intent intent = ((MainApplication) getReactApplicationContext().getApplicationContext()).getKhenshin().getStartTaskIntent();
        intent.putExtra(KhenshinConstants.EXTRA_PAYMENT_ID, paymentId);
        intent.putExtra(KhenshinConstants.EXTRA_FORCE_UPDATE_PAYMENT, true);
        intent.putExtra("EXTRA_EXTERNAL_PAYMENT", false);
        intent.putExtra("EXTRA_IGNORE_RETURN_URL", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getCurrentActivity().startActivityForResult(intent, START_PAYMENT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == START_PAYMENT_REQUEST_CODE && data != null) {
            String exitStatus = resultCode == RESULT_OK ? "OK" : "FAILURE";
            callback.invoke(exitStatus);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
