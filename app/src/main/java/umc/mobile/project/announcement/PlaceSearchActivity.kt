package umc.mobile.project.announcement

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

import umc.mobile.project.databinding.ActivityPlaceSearchBinding

class PlaceSearchActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityPlaceSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gpsBackBtn.setOnClickListener{
            // 값 저장 및 반환


            finish()
        }


    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }

}