package umc.mobile.project.announcement

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.provider.SettingsSlicesContract.KEY_LOCATION
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import umc.mobile.project.R

import umc.mobile.project.databinding.ActivityPlaceSearchBinding

class PlaceSearchActivity : AppCompatActivity(), OnMapReadyCallback {
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.let{
            mCurrentLocatiion = it.getParcelable(KEY_LOCATION)
            mCameraPosition = it.getParcelable(KEY_CAMERA_POSITION)
        }

        mContext = this@PlaceSearchActivity

        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) // 정확도를 최우선적으로 고려
            .setInterval(UPDATE_INTERVAL_MS.toLong()) // 위치가 Update 되는 주기
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS.toLong()) // 위치 획득후 업데이트되는 주기
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Build the map.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.gps_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.gpsBackBtn.setOnClickListener{
            // 값 저장 및 반환

            finish()
        }

    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val GPS_ENABLE_REQUEST_CODE = 2001
        private const val UPDATE_INTERVAL_MS = 1000 * 60 * 15 // 1분 단위 시간 갱신
        private const val FASTEST_UPDATE_INTERVAL_MS = 1000 * 30 // 30초 단위로 화면 갱신
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }
}