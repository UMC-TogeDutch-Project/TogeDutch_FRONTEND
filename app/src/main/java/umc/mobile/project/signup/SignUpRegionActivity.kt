package umc.mobile.project.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umc.mobile.project.databinding.ActivitySignUpRegionBinding

class SignUpRegionActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpRegionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUpRegionBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnNext.setOnClickListener {
            val intent = Intent(this, SignUpUserCategoryActivity::class.java)
            startActivity(intent)
        }

        viewBinding.btnBack.setOnClickListener {
            val intent = Intent(this, SignUpAlarmKeywordActivity::class.java)
            finish()
            startActivity(intent)

        }
    }
}