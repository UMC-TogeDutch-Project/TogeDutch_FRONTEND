package umc.mobile.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umc.mobile.project.databinding.ActivitySignUpRegionBinding

class SignUpRegionActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpRegionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUpRegionBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}