package umc.mobile.project.ram.my_application_1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityMyPostDetailBinding
import umc.mobile.project.ram.my_application_1.current_application.CurrentApplicationActivity

class MyPostDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSeeCurrent.setOnClickListener {
            val intent = Intent(this, CurrentApplicationActivity::class.java)
            startActivity(intent)
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}

