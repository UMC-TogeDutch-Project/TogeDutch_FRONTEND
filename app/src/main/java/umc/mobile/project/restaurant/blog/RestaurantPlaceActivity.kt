package umc.mobile.project.restaurant.blog

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import umc.mobile.project.R
import umc.mobile.project.databinding.ActivityPlaceSearchBinding

class RestaurantPlaceActivity: AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityPlaceSearchBinding
    private lateinit var mMap: GoogleMap
    private var mLocationPermissionGranted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlaceSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Google Map API 초기화
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.gps_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // 주소를 위도, 경도로 변환하여 마커를 표시
        binding.gpsBackBtn.setOnClickListener {
            // 값 저장 및 반환
            finish()
        }


    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        locationPermission

        val geocoder = Geocoder(this)
        val address = intent.getStringExtra("addressData").toString()
        binding.gpsTextView11.text = intent.getStringExtra("titleData").toString()
        binding.gpsTextView12.text = intent.getStringExtra("addressData").toString()

        val locationList = geocoder.getFromLocationName(address, 1)

        if (locationList.isNotEmpty()) {
            val latitude = locationList[0].latitude
            val longitude = locationList[0].longitude
            val latLng = LatLng(latitude, longitude)

            Log.d("위도/경도","위도: $latitude 경도: $longitude")

            // Google Map에 마커 추가
            mMap.addMarker(MarkerOptions().position(latLng).title(address))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f))

        }
    }
    private val locationPermission: Unit
        private get() {
            if (ContextCompat.checkSelfPermission(this.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) {
                mLocationPermissionGranted = true
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                )
            }
        }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }
}