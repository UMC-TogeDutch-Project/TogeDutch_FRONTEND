package umc.mobile.project.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.mobile.project.databinding.FragmentMypagePhonenumberReviseBinding

class MyPhoneNumReviseFragment : Fragment() {
    private lateinit var viewBinding: FragmentMypagePhonenumberReviseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMypagePhonenumberReviseBinding.inflate(layoutInflater)

        initActionBar()

        return viewBinding.root
    }

    private fun initActionBar() {
        viewBinding.include.appbarPageNameLeftTv.text = "내 프로필 수정"

        viewBinding.include.appbarBackBtn.setOnClickListener {
            (context as MyProfileActivity).supportFragmentManager.beginTransaction().remove(this).commit()
            (context as MyProfileActivity).supportFragmentManager
                .popBackStack()
        }
    }
}