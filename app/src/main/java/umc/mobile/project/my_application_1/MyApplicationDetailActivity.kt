package umc.mobile.project.my_application_1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityMyApplicationDetailBinding
import umc.mobile.project.my_application_1.current_application.CurrentApplicationActivity

class MyApplicationDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyApplicationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyApplicationDetailBinding.inflate(layoutInflater)
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

