package umc.mobile.project.announcement

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import umc.mobile.project.databinding.ActivityPlaceSearchBinding

class PlaceSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaceSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gpsBackBtn.setOnClickListener{
            finish()
        }

    }

}