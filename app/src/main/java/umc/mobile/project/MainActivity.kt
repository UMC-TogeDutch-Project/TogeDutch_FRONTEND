package umc.mobile.project

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.containerFragment.id, HomeFragment())
            .commitAllowingStateLoss()

        viewBinding.navBottom.run {
            setOnItemSelectedListener {
                when (it.itemId){
                    R.id.menu_home -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id, HomeFragment())
                            .commitAllowingStateLoss()
                    }

                    R.id.menu_chat -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id, ChatFragment())
                            .commitAllowingStateLoss()
                    }

                    R.id.menu_restaurant -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id, RestaurantFragment())
                            .commitAllowingStateLoss()
                    }

                    R.id.menu_mypage -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id, MyPageFragment())
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            //처음 실행시 자동으로 home 화면을 가르키게 됨.
            selectedItemId = R.id.menu_home
        }
        // 공고 등록버튼 클릭시 post 화면으로 이동
        viewBinding.addBtn.setOnClickListener{
            val intent = Intent(this, AnnouncePostActivity::class.java)
            startActivity(intent)
        }
    }
}