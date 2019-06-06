package com.khipu.khipuinsidedemo;

import android.app.Application;
import com.browser2app.khenshin.Khenshin;
import com.browser2app.khenshin.KhenshinApplication;
import com.browser2app.khenshin.KhenshinInterface;

public class Demo extends Application implements KhenshinApplication {


    private KhenshinInterface khenshin;

    public Demo(){
        khenshin = new Khenshin.KhenshinBuilder()
                .setApplication(this)
                .setAPIUrl("https://khipu.com/app/enc/")
                .build();
    }

    @Override
    public KhenshinInterface getKhenshin() {
        return khenshin;
    }
}
