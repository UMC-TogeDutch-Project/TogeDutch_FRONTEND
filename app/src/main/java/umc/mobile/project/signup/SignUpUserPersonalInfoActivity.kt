package umc.mobile.project.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.mobile.project.databinding.ActivitySignUpUserPersonalInfoBinding
import umc.mobile.project.login.LoginActivity

class SignUpUserPersonalInfoActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpUserPersonalInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding =ActivitySignUpUserPersonalInfoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        viewBinding.btnNext.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            finishAffinity()
            startActivity(intent)

        }
        viewBinding.btnBack.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }

    }
}