package com.khipu.client.example.java;

import static com.khipu.client.KhipuKt.getKhipuLauncherIntent;
import static com.khipu.client.KhipuKt.KHIPU_RESULT_EXTRA;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.khipu.client.KhipuHeader;
import com.khipu.client.KhipuOptions;
import com.khipu.client.KhipuResult;


public class MainActivity extends AppCompatActivity {
    private KhipuOptions.Theme theme = KhipuOptions.Theme.LIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        final Button button = findViewById(R.id.button);
        final Switch themeSwitch = findViewById(R.id.themeSwitch);
        final EditText editText = findViewById(R.id.edit_text);
        final TextView themeLabel = findViewById(R.id.switchLabel);
        final TextView resultText = findViewById(R.id.resultText);

        ActivityResultLauncher khipuLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback() {
                    @Override
                    public void onActivityResult(Object o) {
                        Log.v("Callback", "mensaje final recibido " + o.toString());
                        KhipuResult result = (KhipuResult) ((ActivityResult) o).getData().getExtras().getSerializable(KHIPU_RESULT_EXTRA);
                        resultText.setText(result.asJson());
                    }
                });
        themeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (themeSwitch.isChecked()) {
                    themeLabel.setText("Modo claro");
                    theme = KhipuOptions.Theme.LIGHT;
                } else {
                    themeLabel.setText("Modo oscuro");
                    theme = KhipuOptions.Theme.DARK;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty() || editText.getText().toString().equals("PAYMENT ID")) {
                    CharSequence text = "Ingrese un id de pago para iniciar";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getBaseContext() /* MyActivity */, text, duration);
                    toast.show();
                    return;
                }

//                String lightTopBarContainer = "#ffffff";
//                String lightOnTopBarContainer = "#3b9326";
//                String lightPrimary = "#3b9326";
//                String lightOnPrimary = "#ffffff";
//                String lightBackground = "#ffffff";
//                String lightOnBackground = "#333333";
//                String darkTopBarContainer = "#ffffff";
//                String darkOnTopBarContainer = "#3b9326";
//                String darkPrimary = "#3b9326";
//                String darkOnPrimary = "#ffffff";
//                String darkBackground = "#ffffff";
//                String darkOnBackground = "#333333";
                khipuLauncher.launch(getKhipuLauncherIntent(
                        getBaseContext(),
                        editText.getText().toString(),
                        new KhipuOptions.Builder()
//                                .header(
//                                        new KhipuHeader.Builder()
//                                                .headerLayoutId(R.layout.header_layout)
//                                                .merchantNameId(R.id.merchant_name)
//                                                .paymentMethodId(R.id.payment_method_value)
//                                                .amountId(R.id.amount_value)
//                                                .subjectId(R.id.subject_value)
//                                                .build()
//                                )
                                .topBarTitle("Khipu")
                                .theme(theme)
                                .skipExitPage(false)
                                .locale("es_CL")
//                                .colors(
//                                        new KhipuColors.Builder()
//                                                .lightTopBarContainer(lightTopBarContainer)
//                                                .lightOnTopBarContainer(lightOnTopBarContainer)
//                                                .lightPrimary(lightPrimary)
//                                                .lightOnPrimary(lightOnPrimary)
//                                                .lightBackground(lightBackground)
//                                                .lightOnBackground(lightOnBackground)
//                                                .darkTopBarContainer(darkTopBarContainer)
//                                                .darkOnTopBarContainer(darkOnTopBarContainer)
//                                                .darkPrimary(darkPrimary)
//                                                .darkOnPrimary(darkOnPrimary)
//                                                .darkBackground(darkBackground)
//                                                .darkOnBackground(darkOnBackground)
//                                                .build()
//                                )
                                .build()
                ));
            }
        });
    }
}