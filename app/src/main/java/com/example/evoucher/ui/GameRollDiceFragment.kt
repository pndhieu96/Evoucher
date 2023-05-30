package com.example.evoucher.ui

import android.hardware.Sensor
import android.hardware.SensorManager
import android.view.View
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat.getSystemService
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.databinding.FragmentRollDiceBinding
import com.example.evoucher.utils.ShakeDetector
import com.example.evoucher.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class GameRollDiceFragment : BaseFragment<FragmentRollDiceBinding>(FragmentRollDiceBinding::inflate) {

    private var delayTime = 80
    private var rollAnimations = 10
    private var isCanPlay = true

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

    }

    override fun initialize() {
        mSensorManager = getSystemService(requireContext(), SensorManager::class.java)
        mAccelerometer = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeDetector()
        mShakeDetector?.setOnShakeListener{ countShake ->
            uiScope.launch {
                if(isCanPlay) rollDice()
            }
        }

        Utils.enableButton(binding.btnReceiveGift, false)

        binding.diceContainer.setOnClickListener(View.OnClickListener {
            uiScope.launch {
                if(isCanPlay) rollDice()
            }
        })

        binding.btnReceiveGift.setOnClickListener {
            showNotification("Thông báo", "Chúc bạn may mắn lần sau")
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

    private suspend fun rollDice() {
        isCanPlay = false

        for (i in 0 until rollAnimations) {
            val dice1: Int = Utils.random(1,6)
            val dice2: Int = Utils.random(1,6)
            binding.die1.setImageResource(diceImages[dice1 - 1])
            binding.die2.setImageResource(diceImages[dice2 - 1])
            delay(delayTime.toLong())
        }
        var score = Utils.random(2,12)
        var score1 = 1;
        var score2 = 1;

        do {
            score1 = Utils.random(1,6)
            score2 = score - score1
        } while (score1 < 1 || score1 > 6
            || score2 < 1 || score2 > 6
            || (score1 + score2 != score))

        binding.die1.setImageResource(diceImages[score1 - 1])
        binding.die2.setImageResource(diceImages[score2 - 1])

        Utils.enableButton(binding.btnReceiveGift, true)

        binding.tvResult.text = "Bạn đã nhận được quà tặng"
        binding.tvDes.visibility = VISIBLE
        binding.tvLink.visibility = VISIBLE
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