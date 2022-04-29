package com.example.part04_ch06_finedustmeasure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

/*

 - 내 위치 정보를 가져올 수 있다. -> LocationManager
 - 오픈 API로부터 미세먼지 정보를 가져올 수 있다. -> Retrofit2, Coroutine
 - 홈 스크린에 미세먼지 위젯을 추가할 수 있다. -> App Widgets


 */