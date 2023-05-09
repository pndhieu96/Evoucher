package com.example.evoucher.ui

import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.databinding.FragmentLuckyNumberBinding
import com.example.evoucher.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class GameLuckyNumberFragment : BaseFragment<FragmentLuckyNumberBinding>(FragmentLuckyNumberBinding::inflate) {
    var count = 0;
    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)
    companion object {
        @JvmStatic
        fun newInstance() = GameLuckyNumberFragment()
    }

    override fun initObserve() {

    }

    override fun initialize() {
        binding.btnTakeNumber.isEnabled = true
        binding.btnTakeNumber.alpha = 1f
        binding.btnReceiveGift.isEnabled = false
        binding.btnReceiveGift.alpha = 0.5f

        binding.btnTakeNumber.setOnClickListener{
            uiScope.launch {
                if(count != 3) takeNumber()
            }
        }

        binding.btnReceiveGift.setOnClickListener {
            showNotification("Thông báo", "Chúc bạn may mắn lần sau")
        }
    }

    private suspend fun takeNumber() {
        count++;
        var luckyNumber = 0
        binding.btnTakeNumber.isClickable = false
        for(i in 1..10) {
            binding.tvLuckyNumber.text = Utils.random(1,100).toString()
            delay(100)
        }
        luckyNumber = Utils.random(1,100)
        binding.tvLuckyNumber.text = luckyNumber.toString()

        if(count == 1) {
            binding.tvNumber1.text = luckyNumber.toString()
        } else if(count == 2) {
            binding.tvNumber2.text = luckyNumber.toString()
        }

        if(count == 3){
            binding.btnTakeNumber.isEnabled = false
            binding.btnTakeNumber.alpha = 0.5f
            binding.btnReceiveGift.isEnabled = true
            binding.btnReceiveGift.alpha = 1f
            binding.tvNumber3.text = luckyNumber.toString()
        } else {
            binding.btnTakeNumber.isEnabled = true
            binding.btnTakeNumber.alpha = 1f
            binding.btnReceiveGift.isEnabled = false
            binding.btnReceiveGift.alpha = 0.5f
        }
        binding.btnTakeNumber.isClickable = true
    }

    private fun showNotification(title: String, des: String) {
        var notificationFragment = NotificationFragment.newInstance(title, des, object : NotificationFragment.CallBack{
            override fun close() {
                navController.popBackStack()
            }
        })
        notificationFragment.show(childFragmentManager, "NotificationFragment")
    }
}