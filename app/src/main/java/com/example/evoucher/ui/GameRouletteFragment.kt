package com.example.evoucher.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.customView.wheelview.ItemDrawable
import com.example.evoucher.customView.wheelview.WheelView
import com.example.evoucher.customView.wheelview.adapter.WheelArrayAdapter
import com.example.evoucher.databinding.FragmentRouletteBinding
import com.example.evoucher.model.*
import com.example.evoucher.utils.ConstantUtils
import com.example.evoucher.utils.SharedPreferencesImp
import com.example.evoucher.utils.Utils
import com.example.evoucher.utils.Utils.Companion.observer
import com.example.evoucher.viewModel.PlayGameVM
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.lang.Runnable
import javax.inject.Inject

@AndroidEntryPoint
class GameRouletteFragment : BaseFragment<FragmentRouletteBinding>(FragmentRouletteBinding::inflate) {
    @Inject
    lateinit var sharedPreferencesImp : SharedPreferencesImp

    private val vm: PlayGameVM by viewModels()
    private val args: GameLuckyNumberFragmentArgs by navArgs()
    private var campaign : Campaign? = null
    private var game: Game? = null
    private var partner: Partner? = null
    private var userResult: UserResult? = null
    private var gameResult : GameResult? = null
    private var isLoading = false
    private var isCanPlay = true

    private var mRunnable: Runnable? = null
    private var mHandler = Handler()
    private var currentPosition = 0;
    var listStrings: MutableList<String> = ArrayList()

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun initObserve() {
        vm.games.observer(
            viewLifecycleOwner,
            onSuccess = { gameResult ->
                this.gameResult = gameResult
                isLoading = false
                binding.pbLoading.visibility = GONE

                currentPosition = gameResult.randomNumber ?: -1

                if(currentPosition != -1) {
                    Log.d("GameRoulette", "initObserve: $currentPosition")
                    binding.wheelView.startRoulette(currentPosition)
                }
            },
            onError = {
                isLoading = false
                binding.pbLoading.visibility = GONE
                binding.tvResult.text = it.statusMessage[0]
            },
            onLoading = {
                binding.pbLoading.visibility = VISIBLE
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

        Utils.enableButton(binding.btnReceiveGift, false)
        Utils.enableButton(binding.btnRotary, true)

        setupData()

        binding.btnRotary.setOnClickListener{
            if(isCanPlay) {
                draw()
            }

        }

        binding.btnReceiveGift.setOnClickListener {
            if(gameResult?.nhanDuocVoucher == true) {
                vm.getCouponType(gameResult?.voucher?.loaiCouponId.toString())
            } else {
                showNotification("Thông báo", "Chúc bạn may mắn lần sau")
            }
        }
    }

    private fun draw() {
        if(campaign == null) return
        if(game == null) return
        if(userResult == null) return
        isCanPlay = false
        isLoading = true

        Utils.enableButton(binding.btnRotary, false)

        uiScope.launch {
            vm.playGames(
                userResult!!.user!!.id ?: 0, userResult!!.token, 1, 5,
                game!!.id ?: 0, campaign!!.id ?: 0, userResult!!.isBlockchain
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = GameRouletteFragment()
    }

    private fun setupData() {
        listStrings = ArrayList()
        listStrings.add("10")
        listStrings.add("30")
        listStrings.add("50")
        listStrings.add("80")
        listStrings.add("90")
        listStrings.add("100")
        binding.wheelView.setAdapter(RouletteAdapter(listStrings, requireContext()))
        binding.wheelView.setOnWheelItemSelectedListener(object : WheelView.OnWheelItemSelectListener {
            override fun onWheelItemSelected(parent: WheelView?, itemDrawable: Drawable?, position: Int) {}
        })
        binding.wheelView.setOnWheelItemClickListener(object : WheelView.OnWheelItemClickListener {
            override fun onWheelItemClick(parent: WheelView?, position: Int, isSelected: Boolean) {
                if (!isSelected) {
                    return
                }
                Utils.enableButton(binding.btnReceiveGift, false)
                Utils.enableButton(binding.btnRotary, false)
                if (mRunnable == null) {
                    mRunnable = Runnable {
                        Utils.enableButton(binding.btnReceiveGift, true)
                        Utils.enableButton(binding.btnRotary, false)

                        if(gameResult?.nhanDuocVoucher == true) {
                            binding.tvResult.text = "Bạn đã nhận được quà tặng"
                            Utils.enableButton(binding.btnReceiveGift, true)
                        } else {
                            binding.tvResult.text = "Chúc bạn may mắn lần sau"
                            Utils.enableButton(binding.btnReceiveGift, false)
                        }
                        binding.tvLink.setOnClickListener {
                            val action = GameRouletteFragmentDirections.actionRouletteFragmentToWebViewFragment(gameResult?.transectionLog ?: "")
                            navController.navigate(action)
                        }

                        binding.tvDes.visibility = VISIBLE
                        binding.tvLink.visibility = VISIBLE
                    }
                }
                if (mHandler == null) mHandler = Handler()
                mHandler.postDelayed(mRunnable!!, 500)
            }
        })
    }

    private class RouletteAdapter internal constructor(
        private val list: List<String>,
        private val mContext: Context
    ) :
        WheelArrayAdapter<String?>(list) {
        override fun getDrawable(position: Int): Drawable {
            val color: Int
            val text = list[position]
            color = when (position) {
                0 -> mContext.resources.getColor(R.color.item_roulette_1)
                1 -> mContext.resources.getColor(R.color.item_roulette_2)
                2 -> mContext.resources.getColor(R.color.item_roulette_3)
                3 -> mContext.resources.getColor(R.color.item_roulette_4)
                4 -> mContext.resources.getColor(R.color.item_roulette_5)
                5 -> mContext.resources.getColor(R.color.item_roulette_6)
                6 -> mContext.resources.getColor(R.color.deep_orange_a700)
                7 -> mContext.resources.getColor(R.color.green_800)
                8 -> mContext.resources.getColor(R.color.cyan_a700)
                9 -> mContext.resources.getColor(R.color.light_blue_a700)
                10 -> mContext.resources.getColor(R.color.indigo_a700)
                else -> mContext.resources.getColor(R.color.purple_a700)
            }
            return ItemDrawable(mContext, text, color)
        }
    }

    override fun onStop() {
        super.onStop()

        job.cancel()
    }

    private fun showNotification(title: String, des: String) {
        var notificationFragment = NotificationFragment.newInstance(title, des, object : NotificationFragment.CallBack{
            override fun close() {

            }
        })
        notificationFragment.show(childFragmentManager, "NotificationFragment")
    }
}