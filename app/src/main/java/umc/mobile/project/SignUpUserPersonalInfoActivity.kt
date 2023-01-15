package umc.mobile.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import umc.mobile.project.databinding.ActivitySignUpUserPersonalInfoBinding

class SignUpUserPersonalInfoActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpUserPersonalInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding =ActivitySignUpUserPersonalInfoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnNext.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            finishAffinity()
            startActivity(intent)

        }
        viewBinding.btnBack.setOnClickListener {
            val intent = Intent(this, SignUpUserCategoryActivity::class.java)
            finish()
            startActivity(intent)
        }

    }
}