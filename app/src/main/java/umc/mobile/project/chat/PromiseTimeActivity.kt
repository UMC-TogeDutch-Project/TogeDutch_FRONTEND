package umc.mobile.project.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityPromiseTimeBinding

class PromiseTimeActivity: AppCompatActivity() {
    lateinit var binding: ActivityPromiseTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPromiseTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}