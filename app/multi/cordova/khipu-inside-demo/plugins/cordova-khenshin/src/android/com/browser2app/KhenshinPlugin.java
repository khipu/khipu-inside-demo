package com.browser2app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import com.browser2app.khenshin.KhenshinConstants;
import com.browser2app.khenshin.Khenshin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import android.util.Log;

public class KhenshinPlugin extends CordovaPlugin {

	private static final String TAG = KhenshinPlugin.class.getSimpleName();

	private static final int START_PAYMENT_REQUEST_CODE = 101;

	private CallbackContext currentCallbackContext;

	@Override
	protected void pluginInitialize() {
		if(!Khenshin.isInitialized()) {
			new Khenshin.KhenshinBuilder()
					.setApplication((Application)(cordova.getContext().getApplicationContext()))
					.setAPIUrl("https://khipu.com/app/enc/")
					.setMainButtonStyle(Khenshin.CONTINUE_BUTTON_IN_FORM)
					.setAllowCredentialsSaving(true)
					.setHideWebAddressInformationInForm(true)
					.setAutomatonTimeout(90)
					.setSkipExitPage(false)
					.build();
		}
	}

	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		Log.d(TAG, "EXECUTE " + action + " callback " + callbackContext);
		CordovaArgs cordovaArgs = new CordovaArgs(args);
		currentCallbackContext = callbackContext;
		if("startByPaymentId".equals(action)) {
			startByPaymentId(cordovaArgs.getString(0));
			return true;
		} else if ("startByAutomatonId".equals(action)) {
			Map<String, String> params = new HashMap<>();
			for(int i = 1; i < args.length(); i++) {
				String[] kv = cordovaArgs.getString(i).split(":");
				params.put(kv[0], kv[1]);
			}
			startByAutomatonId(cordovaArgs.getString(0), params);
			return true;
		}
		return false;
	}

	void startByPaymentId(String paymentId) {
		Intent intent = Khenshin.getInstance().getStartTaskIntent();
		intent.putExtra(KhenshinConstants.EXTRA_PAYMENT_ID, paymentId);
		intent.putExtra(KhenshinConstants.EXTRA_FORCE_UPDATE_PAYMENT, false);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		cordova.setActivityResultCallback(this);
		cordova.getActivity().startActivityForResult(intent, START_PAYMENT_REQUEST_CODE);
	}

	void startByAutomatonId(String automatonId, Map<String, String> map) {
		Intent intent = Khenshin.getInstance().getStartTaskIntent();
		intent.putExtra(KhenshinConstants.EXTRA_AUTOMATON_ID, automatonId);
		Bundle params = new Bundle();

		for (Map.Entry<String, String> entry : map.entrySet())
		{
			params.putString(entry.getKey(), entry.getValue());
		}

		intent.putExtra(KhenshinConstants.EXTRA_AUTOMATON_PARAMETERS, params);
		intent.putExtra(KhenshinConstants.EXTRA_FORCE_UPDATE_PAYMENT, false);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		cordova.setActivityResultCallback(this);
		cordova.getActivity().startActivityForResult(intent, START_PAYMENT_REQUEST_CODE);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		Log.d(TAG, "onActivityResult callback " + currentCallbackContext);
		if(requestCode == START_PAYMENT_REQUEST_CODE){
			if(resultCode == Activity.RESULT_OK){
				currentCallbackContext.success("OK");
			} else {
				currentCallbackContext.error("ERROR");
			}
		}
	}

	@Override
	public void onRestoreStateForActivityResult(Bundle state, CallbackContext callbackContext) {
		Log.d(TAG, "onRestoreStateForActivityResult callback " + callbackContext);
		currentCallbackContext = callbackContext;
	}

	@Override
	public Bundle onSaveInstanceState() {
		Log.d(TAG, "onSaveInstanceState");
		Bundle toSave = new Bundle();
		toSave.putString("save", "this");
		return toSave;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
	}
}
