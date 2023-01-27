package umc.mobile.project.announcement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import umc.mobile.project.databinding.ActivityAnnounceDetailBinding


class AnnounceDetailActivity:AppCompatActivity() {
    lateinit var viewBinding: ActivityAnnounceDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAnnounceDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnSeeNow.setOnClickListener{
            finish()
        }
        viewBinding.backBtn.setOnClickListener{
            finish()
        }
    }

}