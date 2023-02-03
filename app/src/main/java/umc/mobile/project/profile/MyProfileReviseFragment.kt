package umc.mobile.project.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import umc.mobile.project.MainActivity
import umc.mobile.project.databinding.FragmentMypageProfileReviseBinding


class MyProfileReviseFragment : Fragment() {
    private lateinit var viewBinding: FragmentMypageProfileReviseBinding

    var name : String = ""
    var image : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMypageProfileReviseBinding.inflate(layoutInflater)

        initActionBar()

        Log.d("name: ", arguments?.getString("name").toString())
        Log.d("image: ", arguments?.getString("image").toString())

        name = arguments?.getString("name").toString()
        image = arguments?.getString("image").toString()

        viewBinding.nickname.text = name

        Log.d("name: ", name)
        Log.d("image: ", image)

        if(image != "null"){
            Glide.with(this).load(arguments?.getString("image")).into(viewBinding.profileImage)
        }

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