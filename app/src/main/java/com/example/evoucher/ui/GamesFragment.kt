package com.example.evoucher.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.databinding.FragmentGamesBinding


class GamesFragment : BaseFragment<FragmentGamesBinding>(FragmentGamesBinding::inflate) {

    override fun initObserve() {

    }

    override fun initialize() {
        binding.btnGameLuckyNumber.setOnClickListener {
            navController.navigate(R.id.action_gamesFragment_to_luckyNumberFragment)
        }

        binding.btnGameRollDice.setOnClickListener {
            navController.navigate(R.id.action_gamesFragment_to_rollDiceFragment)
        }

        binding.btnGameRoulette.setOnClickListener {
            navController.navigate(R.id.action_gamesFragment_to_rouletteFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = GamesFragment()
    }
}