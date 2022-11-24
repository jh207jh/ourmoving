package com.example.edusungshinourmoving;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlabalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 네이티브 앱 키로 초기화
        KakaoSdk.init(this, "0643f73ba8e6f1946ca51617d2fd899b");
    }
}
