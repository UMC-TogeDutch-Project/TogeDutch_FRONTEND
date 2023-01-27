package umc.mobile.project.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityIntroBinding


class IntroActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2500)

    }

}