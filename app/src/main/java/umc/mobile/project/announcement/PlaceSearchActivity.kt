package umc.mobile.project.announcement

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import umc.mobile.project.databinding.ActivityAnnounceListBinding
import umc.mobile.project.databinding.ActivityPlaceSearchBinding

class PlaceSearchActivity: AppCompatActivity() {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityPlaceSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}