package com.example.evoucher.ui

import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.customView.CustomToast
import com.example.evoucher.databinding.FragmentLoginBinding
import com.example.evoucher.model.Campaign
import com.example.evoucher.model.UserResult
import com.example.evoucher.utils.SharedPreferencesImp
import com.example.evoucher.utils.Utils
import com.example.evoucher.utils.Utils.Companion.observer
import com.example.evoucher.viewModel.LoginVM
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val LoginVM : LoginVM by viewModels()
    @Inject
    lateinit var sPregerences: SharedPreferencesImp
    private var pass : String = ""
    override fun initObserve() {
        LoginVM.userResult.observer(
            viewLifecycleOwner,
            onSuccess = {
                binding.pbLoading.visibility = View.GONE

                sPregerences.putString(SharedPreferencesImp.TOKEN, it.token ?: "")
                sPregerences.putBoolean(SharedPreferencesImp.IS_SAVE_ACCOUNT,
                    binding.cbSaveAccount.isChecked
                )
                it.user?.password = pass
                sPregerences.putString(
                    SharedPreferencesImp.USER_INFO,
                    Gson().toJson(it)
                )

                navController.navigate(R.id.action_loginFragment_to_homeFragment)
                CustomToast.makeText(requireContext(),"Đăng nhập thành công",
                    CustomToast.LENGTH_LONG,CustomToast.SUCCESS,false).show();
            }, onError = {
                binding.pbLoading.visibility = View.GONE
                CustomToast.makeText(requireContext(),"Đăng nhập không công",
                    CustomToast.LENGTH_LONG,CustomToast.ERROR,false).show();
            }, onLoading = {
                binding.pbLoading.visibility = View.VISIBLE
            }
        )
    }

    override fun initialize() {
        if(sPregerences.getBoolean(SharedPreferencesImp.IS_SAVE_ACCOUNT)) {
            try {
                var userResult = Gson().fromJson(
                    sPregerences.getString(SharedPreferencesImp.USER_INFO),
                    UserResult::class.java
                )
                userResult?.let {
                    binding.edtAccount.setText(userResult.user?.username)
                    binding.edtPassword.setText(userResult.user?.password)
                    binding.cbSaveAccount.isChecked = true
                }
            } catch (e: Exception) {}
        }

        binding.btnLogin.setOnClickListener {
            pass = binding.edtPassword.text.toString()
            LoginVM.login(
                binding.edtAccount.text.toString(),
                pass
            )
            Utils.hideKeyboard(requireActivity())
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finishAffinity();
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}