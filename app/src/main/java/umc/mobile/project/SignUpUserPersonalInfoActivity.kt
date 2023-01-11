package umc.mobile.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umc.mobile.project.databinding.ActivitySignUpUserPersonalInfoBinding

class SignUpUserPersonalInfoActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpUserPersonalInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding =ActivitySignUpUserPersonalInfoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}