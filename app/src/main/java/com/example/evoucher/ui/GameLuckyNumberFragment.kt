package com.example.evoucher.ui

import android.content.Intent
import android.net.Uri
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.databinding.FragmentLuckyNumberBinding
import com.example.evoucher.model.*
import com.example.evoucher.utils.ConstantUtils.Companion.XXX
import com.example.evoucher.utils.SharedPreferencesImp
import com.example.evoucher.utils.Utils
import com.example.evoucher.utils.Utils.Companion.observer
import com.example.evoucher.viewModel.PlayGameVM
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


@AndroidEntryPoint
class GameLuckyNumberFragment : BaseFragment<FragmentLuckyNumberBinding>(FragmentLuckyNumberBinding::inflate) {
    private val vm: PlayGameVM by viewModels()
    private val args: GameLuckyNumberFragmentArgs by navArgs()
    private var campaign : Campaign? = null
    private var game: Game? = null
    private var partner: Partner? = null
    private var userResult: UserResult? = null
    private var gameResult : GameResult? = null
    private var isLoading = false
    private var isCanPlay = true

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    @Inject
    lateinit var sharedPreferencesImp : SharedPreferencesImp

    companion object {
        @JvmStatic
        fun newInstance() = GameLuckyNumberFragment()
    }

    override fun initObserve() {
        vm.games.observer(
            viewLifecycleOwner,
            onSuccess = { gameResult ->
                this.gameResult = gameResult
                isLoading = false
                val luckyNumber = gameResult.randomNumber
                binding.tvLuckyNumber.text = luckyNumber.toString()

                if(gameResult.nhanDuocVoucher) {
                    binding.tvResult.text = "Bạn đã nhận được quà tặng"
                    Utils.enableButton(binding.btnReceiveGift, true)
                } else {
                    binding.tvResult.text = "Chúc bạn may mắn lần sau"
                    Utils.enableButton(binding.btnReceiveGift, false)
                }
                binding.tvDes.visibility = VISIBLE
                binding.tvLink.visibility = VISIBLE
                binding.tvLink.text = "https://mumbai.polygonscan.com/tx"
                binding.tvLink.setOnClickListener {
                    val action = GameLuckyNumberFragmentDirections.actionLuckyNumberFragmentToWebViewFragment(gameResult?.transectionLog ?: "")
                    navController.navigate(action)
                }
            },
            onError = {
                isLoading = false
                binding.tvLuckyNumber.text = "??"
                binding.tvResult.text = it.statusMessage[0]
            },
            onLoading = {
                gameResult = null
            }
        )

        vm.coupon.observer(
            viewLifecycleOwner,
            onSuccess = {
                val des = "\nBạn đã nhận được voucher\n" +
                        "${it.ten}\n" +
                        "của đối tác ${partner?.tenDoiTac}\n"
                showNotification("Thông báo", des)
            }
        )
    }

    override fun initialize() {
        campaign = args.campaignArg
        game = args.gamesArg
        partner = args.partnerArg
        try {
            userResult = Gson().fromJson(sharedPreferencesImp.getString(SharedPreferencesImp.USER_INFO), UserResult::class.java)
        } catch (e: Exception) {
            userResult = null
        }

        Utils.enableButton(binding.btnTakeNumber, true)
        Utils.enableButton(binding.btnReceiveGift, false)

        binding.btnTakeNumber.setOnClickListener{
            if(isCanPlay) takeNumber()
        }

        binding.btnReceiveGift.setOnClickListener {
            if(gameResult?.nhanDuocVoucher == true) {
                vm.getCouponType(gameResult?.voucher?.loaiCouponId.toString())
            } else {
                showNotification("Thông báo", "Chúc bạn may mắn lần sau")
            }
        }
    }

    private fun takeNumber() {
        if(campaign == null) return
        if(game == null) return
        if(userResult == null) return
        isCanPlay = false
        isLoading = true

        binding.btnTakeNumber.isClickable = false
        Utils.enableButton(binding.btnTakeNumber, false)

        uiScope.launch {
            launch {
                while (isLoading && job.isActive) {
                    binding.tvLuckyNumber.text = Utils.random(1, 100).toString()
                    delay(100)
                }
            }
            launch {
                delay(800)
                vm.playGames(
                    userResult!!.user!!.id ?: 0, userResult!!.token, 1, 100,
                    game!!.id ?: 0, campaign!!.id ?: 0, userResult!!.isBlockchain
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()

        job.cancel()
    }

    private var notificationFragment : NotificationFragment? = null
    private fun showNotification(title: String, des: String) {
        notificationFragment?.dismiss()
        notificationFragment = NotificationFragment.newInstance(title, des, object : NotificationFragment.CallBack{
            override fun close() {

            }
        })
        notificationFragment?.show(childFragmentManager, "NotificationFragment")
    }
}