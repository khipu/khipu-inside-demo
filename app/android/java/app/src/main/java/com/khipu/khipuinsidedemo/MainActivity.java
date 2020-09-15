package com.khipu.khipuinsidedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.browser2app.khenshin.KhenshinApplication;
import com.browser2app.khenshin.KhenshinConstants;

public class MainActivity extends AppCompatActivity {


    int START_PAYMENT_REQUEST_CODE = 101;
    EditText paymentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paymentId = findViewById(R.id.paymentId);
    }

    public void doPay(View view) {

        Intent intent = ((KhenshinApplication)getApplication()).getKhenshin().getStartTaskIntent();
        intent.putExtra(KhenshinConstants.EXTRA_PAYMENT_ID, paymentId.getText().toString());  // ID DEL PAGO
        intent.putExtra(KhenshinConstants.EXTRA_FORCE_UPDATE_PAYMENT, false); // NO FORZAR LA ACTUALIZACION DE DATOS
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // LIMPIAR EL STACK DE ACTIVIDADES
        startActivityForResult(intent, START_PAYMENT_REQUEST_CODE); // INICIAR LA ACTIVIDAD ESPERANDO UNA RESPUESTA


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == START_PAYMENT_REQUEST_CODE) {
            String exitUrl = data.getStringExtra(KhenshinConstants.EXTRA_INTENT_URL);
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "PAYMENT OK, exit url: " + exitUrl,
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "PAYMENT FAILED, exit url: " + exitUrl,
                        Toast.LENGTH_LONG).show();
            }
        }

    }
}
