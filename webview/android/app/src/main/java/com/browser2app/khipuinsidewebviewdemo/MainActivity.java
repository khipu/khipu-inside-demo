package com.browser2app.khipuinsidewebviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.browser2app.khenshin.KhenshinApplication;
import com.browser2app.khenshin.KhenshinConstants;

public class MainActivity extends AppCompatActivity {

    private static final int START_PAYMENT_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void go(View view) {

        Intent intent = ((KhenshinApplication) getApplication()).getKhenshin().getStartTaskIntent();
        intent.putExtra(KhenshinConstants.EXTRA_PAYMENT_ID, "yjwlqvcfbrmp");
        intent.putExtra(KhenshinConstants.EXTRA_FORCE_UPDATE_PAYMENT, false); // NO FORZAR LA ACTUALIZACION DE DATOS
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // LIMPIAR EL STACK DE ACTIVIDADES
        startActivityForResult(intent, START_PAYMENT_REQUEST_CODE); // INICIAR LA ACTIVIDAD E
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == START_PAYMENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "PAYMENT OK", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "PAYMENT FAILED", Toast.LENGTH_LONG).show();
            }
        }

    }
}
