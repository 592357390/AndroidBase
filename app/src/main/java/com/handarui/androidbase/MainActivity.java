package com.handarui.androidbase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.handarui.androidbase.api.HomeApiService;
import com.handarui.androidbase.bean.AppVersionBean;
import com.handarui.baselib.net.RetrofitFactory;
import com.handarui.baselib.util.RxUtil;
import com.orhanobut.logger.Logger;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testBtn = (Button) findViewById(R.id.test_btn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeApiService homeService = RetrofitFactory.createRestService(HomeApiService.class);
                RxUtil.wrapRestCall(homeService.update_app()).subscribe(new Action1<AppVersionBean>() {
                    @Override
                    public void call(AppVersionBean appVersionBean) {
                        Logger.d(appVersionBean.versionCode);
                    }
                }, new DefaultErrorHandler());
            }
        });
    }
}
