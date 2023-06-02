package com.example.evoucher.ui

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.adapter.CouponsAdapter
import com.example.evoucher.adapter.GamesAdapter
import com.example.evoucher.customView.TopBar
import com.example.evoucher.databinding.FragmentAccountBinding
import com.example.evoucher.model.CouponResult
import com.example.evoucher.model.Game
import com.example.evoucher.model.UserResult
import com.example.evoucher.utils.ConstantUtils
import com.example.evoucher.utils.SharedPreferencesImp
import com.example.evoucher.utils.SharedPreferencesImp.Companion.TOKEN
import com.example.evoucher.utils.SharedPreferencesImp.Companion.USER_INFO
import com.example.evoucher.utils.Utils
import com.example.evoucher.utils.Utils.Companion.observer
import com.example.evoucher.viewModel.AccountVm
import com.example.evoucher.viewModel.GamesVM
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {

    @Inject
    lateinit var sharedPreferencesImp : SharedPreferencesImp
    private var userInfo: UserResult? = null
    private val vm : AccountVm by viewModels()
    lateinit var adapter: CouponsAdapter

    override fun initObserve() {
        vm.coupons.observer(
            viewLifecycleOwner,
            onSuccess = {
                binding.pbLoading.visibility = View.GONE
                if(it.size > 0) {
                    adapter.list = it
                    adapter.notifyDataSetChanged()
                    binding.llEmpty.visibility = GONE
                    binding.rv.visibility = View.VISIBLE
                } else {
                    binding.llEmpty.visibility = VISIBLE
                    binding.rv.visibility = View.GONE
                }
            }, onError = {
                binding.pbLoading.visibility = View.GONE
                binding.llEmpty.visibility = VISIBLE
                binding.rv.visibility = View.GONE
            }, onLoading = {
                binding.pbLoading.visibility = View.VISIBLE
            }
        )
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

        adapter = CouponsAdapter(listOf())
        binding.rv.adapter = adapter
        adapter.callBack = object : CouponsAdapter.CallBack {

            override fun onClick(item: CouponResult) {

            }
        }


        userInfo?.let { userInfo ->
            vm.getCoupon(userInfo.user?.id ?: 0)

            binding.tvUser.text = userInfo.user?.hoTen
            binding.tvSdt.text = "SĐT: ${userInfo.moreInfo?.phone}"
            binding.tvAddress.text = "Địa chỉ: ${userInfo.moreInfo?.diaChi}"

            Glide.with(requireContext())
                .load(Utils.getImageUrl(userInfo.moreInfo?.imgUrl ?: "", ConstantUtils.TYPE_IMAGE_PARTNER))
                .apply(RequestOptions().placeholder(R.drawable.ic_user)
                    .centerCrop())
                .into(binding.ivAvatar)
        }

        binding.btnLogout.setOnClickListener {
            sharedPreferencesImp.putString(TOKEN, "")
            context?.let {
                Utils.reload(it)
            }
        }

        binding.llEmpty.visibility = GONE
        binding.rv.visibility = GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountFragment()
    }
}