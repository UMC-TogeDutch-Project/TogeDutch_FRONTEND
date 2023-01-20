package umc.mobile.project.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import umc.mobile.project.*
import umc.mobile.project.databinding.ActivityMainBinding

class MyProfileActivity : AppCompatActivity() {
    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        supportFragmentManager.beginTransaction()
            .replace(viewBinding.containerFragment.id, MyProfileFragment())
            .commitAllowingStateLoss()

    }

    fun replaceFragment(int: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when(int){
            1 -> transaction.replace(viewBinding.containerFragment.id, MyProfileReviseFragment())
            2 -> transaction.replace(viewBinding.containerFragment.id, MyPhoneNumReviseFragment())
        }
        transaction.commitAllowingStateLoss()
    }
}