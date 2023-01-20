package umc.mobile.project.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.mobile.project.databinding.FragmentMypagePhonenumberReviseBinding

class MyPhoneNumReviseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMypagePhonenumberReviseBinding.inflate(layoutInflater).root
    }
}