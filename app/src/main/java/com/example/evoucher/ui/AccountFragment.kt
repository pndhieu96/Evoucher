package com.example.evoucher.ui

import android.view.View.GONE
import android.view.View.VISIBLE
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.customView.TopBar
import com.example.evoucher.databinding.FragmentAccountBinding
import com.example.evoucher.model.UserResult
import com.example.evoucher.utils.ConstantUtils
import com.example.evoucher.utils.SharedPreferencesImp
import com.example.evoucher.utils.SharedPreferencesImp.Companion.TOKEN
import com.example.evoucher.utils.SharedPreferencesImp.Companion.USER_INFO
import com.example.evoucher.utils.Utils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {

    @Inject
    lateinit var sharedPreferencesImp : SharedPreferencesImp
    private var userInfo: UserResult? = null

    override fun initObserve() {

    }

    override fun initialize() {
        try {
            userInfo = Gson().fromJson(sharedPreferencesImp.getString(USER_INFO), UserResult::class.java)
        } catch (e: Exception) {
            userInfo = UserResult()
        }

        binding.tb.setTitle("Tài khoản")
        binding.tb.callBack = object : TopBar.CallBack{
            override fun onClick() {
                navController.popBackStack()
            }
        }

        userInfo?.let { userInfo ->
            binding.tvUser.text = userInfo.user?.hoTen
            binding.tvSdt.text = "SĐT: ${userInfo.moreInfo?.phone}"
            binding.tvAddress.text = "Địa chỉ: ${userInfo.moreInfo?.diaChi}"

            Glide.with(requireContext())
                .load(Utils.getImageUrl(userInfo.moreInfo?.imgUrl ?: "", ConstantUtils.TYPE_IMAGE_PARTNER))
                .apply(RequestOptions().placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .centerCrop())
                .into(binding.ivAvatar)
        }

        binding.btnLogout.setOnClickListener {
            sharedPreferencesImp.putString(TOKEN, "")
            context?.let {
                Utils.reload(it)
            }
        }

        binding.llEmpty.visibility = VISIBLE
        binding.rv.visibility = GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountFragment()
    }
}