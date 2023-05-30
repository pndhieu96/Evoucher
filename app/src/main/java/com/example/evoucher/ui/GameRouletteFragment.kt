package com.example.evoucher.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.View
import androidx.fragment.app.FragmentManager
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.customView.wheelview.ItemDrawable
import com.example.evoucher.customView.wheelview.WheelView
import com.example.evoucher.customView.wheelview.adapter.WheelArrayAdapter
import com.example.evoucher.databinding.FragmentRouletteBinding
import com.example.evoucher.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameRouletteFragment : BaseFragment<FragmentRouletteBinding>(FragmentRouletteBinding::inflate) {

    private var mRunnable: Runnable? = null
    private var mHandler = Handler()
    private var currentPosition = 0;
    private var isCanPlay = true
    var listStrings: MutableList<String> = ArrayList()

    override fun initObserve() {

    }

    override fun initialize() {
        Utils.enableButton(binding.btnReceiveGift, false)
        Utils.enableButton(binding.btnRotary, true)

        setupData()

        binding.btnRotary.setOnClickListener{
            if(isCanPlay) {
                Utils.enableButton(binding.btnReceiveGift, false)
                Utils.enableButton(binding.btnRotary, false)
                currentPosition = Utils.random(0, 5)
                binding.wheelView.startRoulette(currentPosition)
                isCanPlay = false
            }

        }

        binding.btnReceiveGift.setOnClickListener {
            showNotification("Thông báo", "Chúc bạn may mắn lần sau")
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
                        binding.tvResult.text = "Bạn đã nhận được quà tặng"
                        binding.tvDes.visibility = View.VISIBLE
                        binding.tvLink.visibility = View.VISIBLE
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

    private fun showNotification(title: String, des: String) {
        var notificationFragment = NotificationFragment.newInstance(title, des, object : NotificationFragment.CallBack{
            override fun close() {

            }
        })
        notificationFragment.show(childFragmentManager, "NotificationFragment")
    }
}