package umc.mobile.project.restaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityRestaurantPageBinding

class RestaurantPageActivity: AppCompatActivity() {
    lateinit var  binding: ActivityRestaurantPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backBtn.setOnClickListener{
            finish()
        }

    }


}