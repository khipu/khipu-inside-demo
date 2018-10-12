package com.browser2app.khipuinsidewebviewdemo;

import android.app.Application;
import com.browser2app.khenshin.Khenshin;
import com.browser2app.khenshin.KhenshinApplication;
import com.browser2app.khenshin.KhenshinInterface;

public class Demo extends Application implements KhenshinApplication {

    KhenshinInterface khenshin;

    public Demo() {
        khenshin = new Khenshin.KhenshinBuilder()
                .setApplication(this)
                .setTaskAPIUrl("https://khipu.com/app/2.0/")
                .setDumpAPIUrl("https://khipu.com/cerebro/")
                .build();
    }

    @Override
    public KhenshinInterface getKhenshin() {
        return khenshin;
    }
}
