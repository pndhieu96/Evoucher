package com.example.evoucher.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.databinding.FragmentCouponDetailBinding

class CouponDetailFragment : BaseFragment<FragmentCouponDetailBinding>(FragmentCouponDetailBinding::inflate) {

    override fun initObserve() {

    }

    override fun initialize() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = CouponDetailFragment()
    }
}