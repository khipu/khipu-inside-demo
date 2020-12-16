package com.khipu.khipuinsidedemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.browser2app.khenshin.Khenshin
import com.browser2app.khenshin.KhenshinConstants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(!Khenshin.isInitialized()) {
            Khenshin.KhenshinBuilder()
                .setApplication(application)
                .setAPIUrl("https://khipu.com/app/enc/")
                .build()
        }
    }

    private val _startPaymentRequestCode: Int = 1001

    fun doPay(v: View) {
        val intent: Intent = Khenshin.getInstance().startTaskIntent
        intent.putExtra(KhenshinConstants.EXTRA_PAYMENT_ID, paymentId.text.toString())
        intent.putExtra(KhenshinConstants.EXTRA_FORCE_UPDATE_PAYMENT, false)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivityForResult(intent, _startPaymentRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == _startPaymentRequestCode && data != null) {
            val exitUrl: String = data.getStringExtra(KhenshinConstants.EXTRA_INTENT_URL)
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this@MainActivity, "PAYMENT OK, exit url: $exitUrl", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MainActivity, "PAYMENT FAILED, exit url: $exitUrl", Toast.LENGTH_LONG).show()
            }
        }
    }
}
