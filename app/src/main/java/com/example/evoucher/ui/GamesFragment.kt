package com.example.evoucher.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.adapter.GamesAdapter
import com.example.evoucher.adapter.SearchAdapter
import com.example.evoucher.customView.TopBar
import com.example.evoucher.databinding.FragmentGamesBinding
import com.example.evoucher.model.Campaign
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesFragment : BaseFragment<FragmentGamesBinding>(FragmentGamesBinding::inflate) {

    lateinit var adapter: GamesAdapter

    override fun initObserve() {

    }

    override fun initialize() {
        binding.tb.setTitle("Danh sách trò chơi")
        binding.tb.callBack = object : TopBar.CallBack {
            override fun onClick() {
                navController.popBackStack()
            }

        }

        adapter = GamesAdapter(listOf("Con số may mắn","Đổ xí ngầu","Vòng quay kì diệu"))
        binding.rv.adapter = adapter
        adapter.callBack = object : GamesAdapter.CallBack {

            override fun onClick(item: Int) {
                if(item == 0) {
                    navController.navigate(R.id.action_gamesFragment_to_luckyNumberFragment)
                } else if(item == 1) {
                    navController.navigate(R.id.action_gamesFragment_to_rollDiceFragment)
                } else if(item == 2) {
                    navController.navigate(R.id.action_gamesFragment_to_rouletteFragment)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = GamesFragment()
    }
}