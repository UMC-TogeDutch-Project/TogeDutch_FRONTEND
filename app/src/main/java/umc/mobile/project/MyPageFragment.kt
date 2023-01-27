package umc.mobile.project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.mobile.project.commercial.CommercialListActivity
import umc.mobile.project.databinding.FragmentMypageBinding
import umc.mobile.project.ram.my_application_1.MyPostActivity
import umc.mobile.project.profile.MyProfileActivity

class MyPageFragment: Fragment() {
    private lateinit var viewBinding: FragmentMypageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMypageBinding.inflate(layoutInflater)

        viewBinding.btnProfile.setOnClickListener {
            val intent = Intent(context, MyProfileActivity::class.java)
            startActivity(intent)
        }

        viewBinding.btnLogout.setOnClickListener {

        }

        viewBinding.participation.setOnClickListener {
            val intent = Intent(context, MyPostActivity::class.java)
            startActivity(intent)
        }

        viewBinding.favorite.setOnClickListener {

        }

        viewBinding.advertise.setOnClickListener {
            val intent = Intent(context, CommercialListActivity::class.java)
            startActivity(intent)
        }

        viewBinding.notice.setOnClickListener {

        }

        viewBinding.alarmKeyword.setOnClickListener {

        }

        viewBinding.withdrawal.setOnClickListener {

        }

        return viewBinding.root
    }
}