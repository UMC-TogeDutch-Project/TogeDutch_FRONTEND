package umc.mobile.project.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umc.mobile.project.MainActivity
import umc.mobile.project.signup.SignUpActivity
import umc.mobile.project.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), MyCustomDialogInterface {
    private lateinit var viewBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnLogin.setOnClickListener {
            val myCustomDialog = MyCustomDialog(this, this)
            myCustomDialog.show()
        }

        viewBinding.tbSginUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }




    }

    override fun onbtnGotoMainClicked() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}