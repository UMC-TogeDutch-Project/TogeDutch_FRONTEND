package umc.mobile.project.mypage.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import umc.mobile.project.*
import umc.mobile.project.databinding.ActivityMainBinding
import umc.mobile.project.databinding.ActivityMyprofileBinding



class MyProfileActivity : AppCompatActivity() {
    lateinit var bindingmain : ActivityMainBinding

    var name : String = ""
    var image : String = ""

    private val viewBinding: ActivityMyprofileBinding by lazy {
        ActivityMyprofileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        name = intent.getStringExtra("name").toString()
        image = intent.getStringExtra("image").toString()

        Log.d("Activity, name: ", name)
        Log.d("Activity, image: ", image)

        val bundle = Bundle()
        bundle.putString("name", name)
        bundle.putString("image", image)

        val myProfileFragment = MyProfileFragment()

        myProfileFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(viewBinding.containerFragment.id, myProfileFragment)
            .commitAllowingStateLoss()


    }

    fun replaceFragment(int: Int) {
        name = intent.getStringExtra("name").toString()
        image = intent.getStringExtra("image").toString()

        Log.d("Activity, name: ", name)
        Log.d("Activity, image: ", image)

        val bundle = Bundle()
        bundle.putString("name", name)
        bundle.putString("image", image)

        val myProfileReviseFragment = MyProfileReviseFragment()
        val myProfilePhoneNumReviseFragment = MyPhoneNumReviseFragment()

        val transaction = supportFragmentManager.beginTransaction()
        if (int == 1) {
            myProfileReviseFragment.arguments = bundle
            transaction.replace(
                viewBinding.containerFragment.id,
                myProfileReviseFragment
            )
        } else if(int == 2) {
            myProfilePhoneNumReviseFragment.arguments = bundle
             transaction.replace(
                viewBinding.containerFragment.id,
                 myProfilePhoneNumReviseFragment
            )
        }
        transaction.addToBackStack(null).commitAllowingStateLoss()
    }
}