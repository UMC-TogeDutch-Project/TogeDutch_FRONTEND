package umc.mobile.project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.mobile.project.databinding.FragmentHomeBinding
import umc.mobile.project.databinding.FragmentRestaurantBinding
import umc.mobile.project.notice.NoticeData
import umc.mobile.project.notice.NoticeListActivity
import umc.mobile.project.notice.NoticeRVAdapter

class HomeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        return FragmentRestaurantBinding.inflate(layoutInflater).root
        }


    }



