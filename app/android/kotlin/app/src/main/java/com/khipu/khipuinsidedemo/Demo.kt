package com.khipu.khipuinsidedemo

import android.app.Application
import com.browser2app.khenshin.Khenshin
import com.browser2app.khenshin.KhenshinApplication
import com.browser2app.khenshin.KhenshinInterface

class Demo: Application(), KhenshinApplication {

    private val khenshinInterface: KhenshinInterface = Khenshin.KhenshinBuilder()
        .setApplication(this)
        .setAPIUrl("https://khipu.com/app/enc/")
        .build()

    override fun getKhenshin(): KhenshinInterface {
        return khenshinInterface
    }

}