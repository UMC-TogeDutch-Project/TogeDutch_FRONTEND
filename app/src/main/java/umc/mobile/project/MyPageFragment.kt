package umc.mobile.project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.mobile.project.commercial.CommercialListActivity
import umc.mobile.project.databinding.FragmentMypageBinding
import umc.mobile.project.my_application_1.MyApplicationActivity
import umc.mobile.project.notice.NoticeActivity
import umc.mobile.project.profile.MyProfileActivity
import umc.mobile.project.wishlist.WishListActivity
import umc.mobile.project.withdrawal.WithdrawalActivity

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
            val intent = Intent(context, MyApplicationActivity::class.java)
            startActivity(intent)
        }

        viewBinding.favorite.setOnClickListener {
            val intent = Intent(context, WishListActivity::class.java)
            startActivity(intent)
        }

        viewBinding.advertise.setOnClickListener {
            val intent = Intent(context, CommercialListActivity::class.java)
            startActivity(intent)
        }

        viewBinding.notice.setOnClickListener {
            val intent = Intent(context, NoticeActivity::class.java)
            startActivity(intent)
        }

        viewBinding.alarmKeyword.setOnClickListener {

        }

        viewBinding.withdrawal.setOnClickListener {
            val intent = Intent(context, WithdrawalActivity::class.java)
            startActivity(intent)
        }

        return viewBinding.root
    }
}