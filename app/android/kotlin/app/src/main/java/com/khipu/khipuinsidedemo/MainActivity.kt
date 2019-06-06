package com.khipu.khipuinsidedemo

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.browser2app.khenshin.KhenshinApplication
import com.browser2app.khenshin.KhenshinConstants
import com.browser2app.khenshin.LogWrapper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private val START_PAYMENT_REQUEST_CODE: Int = 1001

    fun doPay(v: View) {
        val intent: Intent = (application as KhenshinApplication).khenshin.startTaskIntent
        intent.putExtra(KhenshinConstants.EXTRA_PAYMENT_ID, paymentId.text.toString())
        intent.putExtra(KhenshinConstants.EXTRA_FORCE_UPDATE_PAYMENT, false)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivityForResult(intent, START_PAYMENT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == START_PAYMENT_REQUEST_CODE && data != null) {
            val exitUrl: String = data.getStringExtra(KhenshinConstants.EXTRA_INTENT_URL)
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this@MainActivity, "PAYMENT OK, exit url: $exitUrl", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MainActivity, "PAYMENT FAILED, exit url: $exitUrl", Toast.LENGTH_LONG).show()
            }
        }
    }
}
