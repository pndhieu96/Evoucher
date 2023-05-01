package com.example.evoucher.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.databinding.FragmentSplashBinding
import com.example.evoucher.utils.SharedPreferencesImp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    @Inject lateinit var sPreferences: SharedPreferencesImp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initObserve() {

    }

    override fun initialize() {
        Handler().postDelayed({
            Log.d("SplashFragment", sPreferences.getString(SharedPreferencesImp.TOKEN))
            if(sPreferences.getString(SharedPreferencesImp.TOKEN).isNullOrEmpty()) {
                navController.navigate(R.id.action_splashFragment_to_loginFragment)
            } else {
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
            }
        },2000)
    }

    companion object {
        fun newInstance() = SplashFragment()
    }
}