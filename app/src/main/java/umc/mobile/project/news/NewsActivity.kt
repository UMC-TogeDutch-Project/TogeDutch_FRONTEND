package umc.mobile.project.news

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityNewsBinding


class NewsActivity : AppCompatActivity(){
    private lateinit var viewBinding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setflag(0)

        viewBinding.btnUpLoad.setOnClickListener {
            setflag(0)
        }

        viewBinding.btnMate.setOnClickListener {
            setflag(1)
        }



    }
    private fun setflag(fragNum: Int){

        val ft = supportFragmentManager.beginTransaction()
        when(fragNum){
            0 -> {
                viewBinding.btnUpLoad.setTextColor(Color.parseColor("#557AFF"))
                viewBinding.btnMate.setTextColor(Color.parseColor("#66000000"))
                viewBinding.ivUnderBarUpLoad.setBackgroundColor(Color.parseColor("#557AFF"))
                viewBinding.ivUnderBarMate.setBackgroundColor(Color.parseColor("#FFFFFF"))
                ft.replace(viewBinding.containerFragment.id, NewsUpLoadFragment()).commit()
            }
            1 -> {
                viewBinding.btnMate.setTextColor(Color.parseColor("#557AFF"))
                viewBinding.btnUpLoad.setTextColor(Color.parseColor("#66000000"))
                viewBinding.ivUnderBarUpLoad.setBackgroundColor(Color.parseColor("#FFFFFF"))
                viewBinding.ivUnderBarMate.setBackgroundColor(Color.parseColor("#557AFF"))
                ft.replace(viewBinding.containerFragment.id, NewsMateFragment()).commit()
            }
        }
    }
}