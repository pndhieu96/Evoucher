package com.example.evoucher.ui

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.view.View
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.databinding.FragmentRollDiceBinding
import com.example.evoucher.model.*
import com.example.evoucher.utils.ConstantUtils
import com.example.evoucher.utils.ShakeDetector
import com.example.evoucher.utils.SharedPreferencesImp
import com.example.evoucher.utils.Utils
import com.example.evoucher.utils.Utils.Companion.observer
import com.example.evoucher.viewModel.PlayGameVM
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class GameRollDiceFragment : BaseFragment<FragmentRollDiceBinding>(FragmentRollDiceBinding::inflate) {
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

    private var delayTime = 100
    private var minLoadingTime = 800

    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private var mShakeDetector: ShakeDetector? = null

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private var diceImages = intArrayOf(
        R.drawable.d1,
        R.drawable.d2,
        R.drawable.d3,
        R.drawable.d4,
        R.drawable.d5,
        R.drawable.d6
    )

    override fun initObserve() {
        vm.games.observer(
            viewLifecycleOwner,
            onSuccess = { gameResult ->
                this.gameResult = gameResult
                isLoading = false
                var score = gameResult.randomNumber ?: 0
                var score1 = 0;
                var score2 = 0;
                if(score >= 2) {
                    do {
                        score1 = Utils.random(1, 6)
                        score2 = score - score1
                    } while (score1 < 1 || score1 > 6
                        || score2 < 1 || score2 > 6
                        || (score1 + score2 != score)
                    )
                }
                binding.die1.setImageResource(diceImages[score1 - 1])
                binding.die2.setImageResource(diceImages[score2 - 1])

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
                    val action = GameRollDiceFragmentDirections.actionRollDiceFragmentToWebViewFragment(gameResult?.transectionLog ?: "")
                    navController.navigate(action)
                }
            },
            onError = {
                isLoading = false
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

        Utils.enableButton(binding.btnReceiveGift, false)

        mSensorManager = getSystemService(requireContext(), SensorManager::class.java)
        mAccelerometer = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeDetector()
        mShakeDetector?.setOnShakeListener{ countShake ->
            if(isCanPlay) rollDice()
        }

        binding.diceContainer.setOnClickListener(View.OnClickListener {
            if(isCanPlay) rollDice()
        })

        binding.btnReceiveGift.setOnClickListener {
            if(gameResult?.nhanDuocVoucher == true) {
                vm.getCouponType(gameResult?.voucher?.loaiCouponId.toString())
            } else {
                showNotification("Thông báo", "Chúc bạn may mắn lần sau")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager?.registerListener(mShakeDetector, mAccelerometer,SensorManager.SENSOR_DELAY_UI);
    }

    override fun onPause() {
        mSensorManager?.unregisterListener(mShakeDetector);
        super.onPause()
    }

    override fun onStop() {
        super.onStop()

        job.cancel()
    }

    private fun rollDice() {
        if(campaign == null) return
        if(game == null) return
        if(userResult == null) return
        isCanPlay = false
        isLoading = true

        uiScope.launch {
            launch {
                while (isLoading && job.isActive) {
                    val dice1: Int = Utils.random(1,6)
                    val dice2: Int = Utils.random(1,6)
                    binding.die1.setImageResource(diceImages[dice1 - 1])
                    binding.die2.setImageResource(diceImages[dice2 - 1])
                    delay(delayTime.toLong())
                }
            }

            launch {
                delay(minLoadingTime.toLong())
                vm.playGames(
                    userResult!!.user!!.id ?: 0, userResult!!.token, 2, 12,
                    game!!.id ?: 0, campaign!!.id ?: 0, userResult!!.isBlockchain
                )
            }
        }
    }

    private fun showNotification(title: String, des: String) {
        var notificationFragment = NotificationFragment.newInstance(title, des, object : NotificationFragment.CallBack{
            override fun close() {
            }
        })
        notificationFragment.show(childFragmentManager, "NotificationFragment")
    }

    companion object {
        @JvmStatic
        fun newInstance() = GameRollDiceFragment()
    }
}