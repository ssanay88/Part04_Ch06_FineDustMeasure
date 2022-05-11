package com.example.part04_ch06_finedustmeasure

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.part04_ch06_finedustmeasure.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient    // fusedLocationProviderClient 객체 생성
    private var cancellationTokenSource: CancellationTokenSource? = null

    private val scope = MainScope()    // 코루틴의 범위를 액티비티와 일치시켜 주기 위한 MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)


        requestLocationPermissions()    // 권한 요청 후 요청이 허용되지 않은 경우 액티비티 종료, 허용된 경우 다음 데이터 불러오는 단계 진행
        initVariable()    // 변수 객체들을 초기화
    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource?.cancel()
        scope.cancel()    // 코루틴 scope 종료 시켜준다.
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val locationPermissionGranted =
            requestCode == REQUEST_ACCESS_LOCATION_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED

        if (!locationPermissionGranted) {
            finish()    // 권한 요청이 안된 경우 종료
        } else {
            // fetchData 데이터 가져오기

            cancellationTokenSource = CancellationTokenSource()

            fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cancellationTokenSource!!.token).addOnSuccessListener { location ->
                mainBinding.textView.text = "${location.latitude} & ${location.longitude}"

            }
        }

    }

    // 권한 요청
    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_ACCESS_LOCATION_PERMISSIONS
        )

    }

    private fun initVariable() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)    // fusedLocationProviderClient 객체 초기화
    }


    companion object {
        private const val  REQUEST_ACCESS_LOCATION_PERMISSIONS = 100
    }

}

/*

 - 내 위치 정보를 가져올 수 있다. -> LocationManager
 - 오픈 API로부터 미세먼지 정보를 가져올 수 있다. -> Retrofit2, Coroutine
 - 홈 스크린에 미세먼지 위젯을 추가할 수 있다. -> App Widgets


 */