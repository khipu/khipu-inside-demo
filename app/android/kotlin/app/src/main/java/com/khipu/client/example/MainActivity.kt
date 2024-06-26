package com.khipu.client.example

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khipu.client.KHIPU_RESULT_EXTRA
import com.khipu.client.KhipuOptions
import com.khipu.client.KhipuResult
import com.khipu.client.example.ui.theme.KhipuClientExampleTheme
import com.khipu.client.getKhipuLauncherIntent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KhipuClientExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    paymentStart()
                }
            }
        }
    }
}

@Composable
fun paymentStart() {
    val context = LocalContext.current
    //val text = remember { mutableStateOf("wfg954bkelcu") }
    val text = remember {
        mutableStateOf("PAYMENT ID")
    }
    var lightTopBarContainer by remember{ mutableStateOf("#ffffff") }
    var lightOnTopBarContainer by remember{ mutableStateOf("#3b9326") }
    var lightPrimary by remember{ mutableStateOf("#3b9326") }
    var lightOnPrimary by remember{ mutableStateOf("#ffffff") }
    var lightBackground by remember{ mutableStateOf("#ffffff") }
    var lightOnBackground by remember{ mutableStateOf("#333333") }
    var darkTopBarContainer by remember{ mutableStateOf("#ffffff") }
    var darkOnTopBarContainer by remember{ mutableStateOf("#3b9326") }
    var darkPrimary by remember{ mutableStateOf("#3b9326") }
    var darkOnPrimary by remember{ mutableStateOf("#ffffff") }
    var darkBackground by remember{ mutableStateOf("#ffffff") }
    var darkOnBackground by remember{ mutableStateOf("#333333") }
    var resultText by remember{ mutableStateOf("") }
    var themeSelected by remember{ mutableStateOf(KhipuOptions.Theme.LIGHT) }

    val khipuLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val metadata = intent?.getSerializableExtra(KHIPU_RESULT_EXTRA) as KhipuResult
                resultText = metadata.asJson()
                Log.v("TAG", metadata.asJson())
            }
        }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            singleLine = true,
            value = text.value,
            onValueChange = { text.value = it },
            label = { Text("OperationID") }
        )

        Switch(
            checked = KhipuOptions.Theme.LIGHT.equals(themeSelected),
            onCheckedChange = {
                if(it) {
                    themeSelected = KhipuOptions.Theme.LIGHT
                } else {
                    themeSelected = KhipuOptions.Theme.DARK
                }
            }
        )

        if(KhipuOptions.Theme.LIGHT.equals(themeSelected)) {
            Text(text = "Modo claro")

        } else {
            Text(text = "Modo Oscuro")

        }



        Button(
            onClick = {
                if(text.value == null || text.value.equals("") || text.value.equals("PAYMENT ID")) {
                    val text = "Ingrese un id de pago para iniciar"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(context, text, duration) // in Activity
                    toast.show()
                } else {
                    khipuLauncher.launch(
                        getKhipuLauncherIntent(
                            context = context,
                            operationId = text.value,
                            options = KhipuOptions.Builder()
//                            .header(
//                                KhipuHeader.Builder()
//                                    .headerLayoutId(processHeaderLayoutId)
//                                    .merchantNameId(merchantNameResourceId)
//                                    .paymentMethodId(paymentMethodResourceId)
//                                    .amountId(amountResourceId)
//                                    .subjectId(subjectResourceId)
//                                    .build()
//                            )
                                .topBarTitle("Khipu")
//                            .topBarImageResourceId(R.drawable.merchant_logo)
                                .skipExitPage(false)
                                .theme(themeSelected)
                                .locale("es_CL")
//                            .colors(
//                                KhipuColors.Builder()
//                                    .lightTopBarContainer(lightTopBarContainer)
//                                    .lightOnTopBarContainer(lightOnTopBarContainer)
//                                    .lightPrimary(lightPrimary)
//                                    .lightOnPrimary(lightOnPrimary)
//                                    .lightBackground(lightBackground)
//                                    .lightOnBackground(lightOnBackground)
//                                    .darkTopBarContainer(darkTopBarContainer)
//                                    .darkOnTopBarContainer(darkOnTopBarContainer)
//                                    .darkPrimary(darkPrimary)
//                                    .darkOnPrimary(darkOnPrimary)
//                                    .darkBackground(darkBackground)
//                                    .darkOnBackground(darkOnBackground)
//                                    .build()
//                            )
                                .build()
                        )
                    )
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Transferir")
        }
        Text(text = "Resultado de la operación")
        Text(text = resultText)

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KhipuClientExampleTheme {
        paymentStart()
    }
}