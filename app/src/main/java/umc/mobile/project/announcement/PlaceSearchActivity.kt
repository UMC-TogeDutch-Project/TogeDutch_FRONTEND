package umc.mobile.project.announcement

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import umc.mobile.project.R
import umc.mobile.project.databinding.ActivityPlaceSearchBinding
import umc.mobile.project.latitude_var
import umc.mobile.project.longtitude_var
import java.io.IOException
import java.util.*

class PlaceSearchActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener {
    private lateinit var binding: ActivityPlaceSearchBinding

    private val TAG = this.javaClass.simpleName
    private lateinit var mContext: Context
    private lateinit var mMap: GoogleMap
    private var currentMarker: Marker? = null

    private lateinit var mFusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private var mCurrentLocatiion: Location? = null
    private var mCameraPosition: CameraPosition? = null
    private val mDefaultLocation = LatLng(37.56, 126.97)
    private var mLocationPermissionGranted = false

    var editTextadress : String = ""
    var latitude: Double = 37.56
    var longitude: Double = 126.97
    var address : String = ""
    lateinit var currentAddress : String

    lateinit var geocoder : Geocoder

    //위치 서비스가 gps를 사용해서 위치를 확인
    lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let {            // savedInstanceState가 null이 아닐때
            mCurrentLocatiion = it.getParcelable(KEY_LOCATION)
            mCameraPosition = it.getParcelable(KEY_CAMERA_POSITION)
        }

        binding = ActivityPlaceSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextadress = binding.gpsEditText.text.toString()

        mContext = this@PlaceSearchActivity

        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) // 정확도를 최우선적으로 고려
            .setInterval(UPDATE_INTERVAL_MS.toLong()) // 위치가 Update 되는 주기
            .setFastestInterval(UPDATE_INTERVAL_MS.toLong()) // 위치 획득후 업데이트되는 주기
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        //구글 맵을 준비하는 작업을 진행한다
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.gps_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnSearch.setOnClickListener {
            geocoder = Geocoder(this)

            var locationNameList: List<Address>? = null
            var locationList : List<Address>? = null

            address = binding.gpsEditText.text.toString()

            Log.d("editTextaddress: ", binding.gpsEditText.text.toString())
            Log.d("address: ", address)

            try {                                   //지역  , 읽을 개수
                locationNameList = geocoder.getFromLocationName(address, 10)
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생")
            }
            if (locationNameList != null) {
                if (locationNameList.isEmpty()) {
                    address = ""
                    Toast.makeText(this, "해당되는 주소 정보는 없습니다", Toast.LENGTH_LONG).show()
                } else {
                    latitude = locationNameList!![0].latitude
                    longitude = locationNameList!![0].longitude

                    // 위도,경도 입력 후 지도
                    //MasFragment.kt에서 불러온 함수
                    setLocation(latitude, longitude)

                    binding.gpsTextView11.text = address

                    try {
                        // 위도, 경도로 상세 주소 뽑기
                        locationList =
                            geocoder.getFromLocation(latitude, longitude, 1) //1개의 데이터를 얻어오겠다.
                    } catch (e: IOException) {
                        Log.d("위도/경도", "입출력 오류")
                    }

                    if (locationList != null) {
                        if (locationList.isEmpty()) {
                            binding.gpsTextView12.text = "해당되는 주소는 없습니다."
                        } else { //정상적으로 산출된 주소
                            binding.gpsTextView12.text = locationList[0].getAddressLine(0)

                        }
                    }

                    Log.d("위치정보", "주소: $address 상세 주소: ${binding.gpsTextView12.text} 위도: $latitude  경도: $longitude")
                }

            }
        }

        binding.gpsBackBtn.setOnClickListener {
            // 값 저장 및 반환
            val addressIntent = Intent()

            if(address == ""){
                Log.d("1: 위치정보",  "주소: $currentAddress 위도: $latitude  경도: $longitude")
                addressIntent.putExtra("address", currentAddress)
            } else {
                Log.d("2: 위치정보",  "주소: $address 위도: $latitude  경도: $longitude")
                addressIntent.putExtra("address", address)
            }
            addressIntent.putExtra("latitude", latitude)
            addressIntent.putExtra("longitude", longitude)

            Log.d("3: 위치정보",  "주소: $currentAddress 위도: $latitude  경도: $longitude")

            //// 여기에서 announcePostActivity에 있는 latitude_var, longtitude_var 전역변수에 담아주기!
            latitude_var = latitude
            longtitude_var = longitude

            setResult(Activity.RESULT_OK, addressIntent)
            finish()
        }


    }

    fun setLocation(latitude:Double, longitude:Double) {
        val LATLNG = LatLng(latitude, longitude)

        val makerOptions = MarkerOptions()
            .position(LATLNG)
            .title("Here")

        val cameraPosition = CameraPosition.Builder()
            .target(LATLNG)
            .zoom(15.0f)
            .build()

        mMap.clear()
        mMap.addMarker(makerOptions)
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }


    /**
     * Saves the state of the map when the activity is paused.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        mMap?.let{
            outState.putParcelable(KEY_CAMERA_POSITION, it.cameraPosition)
            outState.putParcelable(KEY_LOCATION, mCurrentLocatiion)
            super.onSaveInstanceState(outState)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.e(TAG, "onMapReady :")
        mMap = googleMap

        setDefaultLocation() // GPS를 찾지 못하는 장소에 있을 경우 지도의 초기 위치가 필요함.
        locationPermission
        updateLocationUI()
        deviceLocation

        mMap.setOnMyLocationButtonClickListener(this)
    }

    override fun onMyLocationButtonClick(): Boolean {
        onStart()

        return false
    }

    private fun updateLocationUI() {
        mMap?.let{      // map이 null이 아닐때
            try {
                if (mLocationPermissionGranted) {
                    it.isMyLocationEnabled = true
                    it.uiSettings.isMyLocationButtonEnabled = true
                } else {
                    it.isMyLocationEnabled = false
                    it.uiSettings.isMyLocationButtonEnabled = false
                    mCurrentLocatiion = null
                    locationPermission
                }
            } catch (e: SecurityException) {
                Log.e("Exception: %s", e.message!!)
            }
        }
    }

    private fun setDefaultLocation() {
        currentMarker?.let{     // 마커가 null이 아닐 때
            it.remove()
        }
        val markerOptions = MarkerOptions()
        markerOptions.position(mDefaultLocation)
        markerOptions.title("위치정보 가져올 수 없음")
        markerOptions.snippet("위치 퍼미션과 GPS 활성 여부 확인하세요")
        markerOptions.draggable(true)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        currentMarker = mMap!!.addMarker(markerOptions)

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 15f)
        mMap.moveCamera(cameraUpdate)
    }

    fun getCurrentAddress(latlng: LatLng): String {
        // 위치 정보와 지역으로부터 주소 문자열을 구한다.
        var addressList: List<Address>? = null
        geocoder =
            Geocoder(this, Locale.getDefault())

        // 지오코더를 이용하여 주소 리스트를 구한다.
        addressList = try {
            geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1)
        } catch (e: IOException) {
            Toast.makeText(
                this,
                "위치로부터 주소를 인식할 수 없습니다. 네트워크가 연결되어 있는지 확인해 주세요.",
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
            return "주소 인식 불가"
        }
        if (addressList != null) {
            if (addressList.size < 1) { // 주소 리스트가 비어있는지 비어 있으면
                return "해당 위치에 주소 없음"
            }
        }

        // 주소를 담는 문자열을 생성하고 리턴
        val address = addressList!![0]
        val addressStringBuilder = StringBuilder()
        for (i in 0..address.maxAddressLineIndex) {
            addressStringBuilder.append(address.getAddressLine(i))
            if (i < address.maxAddressLineIndex) addressStringBuilder.append("\n")
        }
        return addressStringBuilder.toString()
    }

    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            val locationList =
                locationResult.locations

            if (locationList.size > 0) {
                val location = locationList[locationList.size - 1]
                val currentPosition =
                    LatLng(location.latitude, location.longitude)
                val markerTitle = "현 위치"
                val markerSnippet = getCurrentAddress(currentPosition)

                Log.d("위치정보",  "위도: ${location.latitude} 경도: ${location.longitude}")

                binding.gpsTextView11.text = getCurrentAddress(currentPosition)
                binding.gpsTextView12.text = getCurrentAddress(currentPosition)

                currentAddress = getCurrentAddress(currentPosition)
                latitude = location.latitude
                longitude = location.longitude

                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(location, markerTitle, markerSnippet)
                mCurrentLocatiion = location
            }
        }
    }

    fun setCurrentLocation(location: Location, markerTitle: String?, markerSnippet: String?) {
        if (currentMarker != null) currentMarker!!.remove()

        val currentLatLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(currentLatLng)
        markerOptions.title(markerTitle)
        markerOptions.snippet(markerSnippet)
        markerOptions.draggable(true)

        currentMarker = mMap!!.addMarker(markerOptions)

        val cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng)
        mMap.moveCamera(cameraUpdate)
    }

    private val deviceLocation: Unit
        private get() {
            try {
                if (mLocationPermissionGranted) {
                    mFusedLocationProviderClient!!.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                }
            } catch (e: SecurityException) {
                Log.e("Exception: %s", e.message!!)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mLocationPermissionGranted = false

        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    @SuppressLint("MissingPermission") // 유저에게 Fragment가 보이도록 해준다.
    override fun onStart() {
        super.onStart()

        if (mLocationPermissionGranted) {
            Log.d(TAG, "onStart : requestLocationUpdates")
            mFusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
            mMap?.let{
                it.isMyLocationEnabled = true
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mFusedLocationProviderClient?.let{
            it!!.removeLocationUpdates(locationCallback)
            Log.d(TAG, "onStop : removeLocationUpdates")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mFusedLocationProviderClient?.let{
            it.removeLocationUpdates(locationCallback)
            Log.d(TAG, "onDestroy : removeLocationUpdates")
        }
    }


    companion object {
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val GPS_ENABLE_REQUEST_CODE = 2001
        private const val UPDATE_INTERVAL_MS = 1000 * 60 * 30 // 30분 단위 시간 갱신
        private const val FASTEST_UPDATE_INTERVAL_MS = 1000 * 30 // 30초 단위로 화면 갱신
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }

}
