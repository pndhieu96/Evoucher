package com.example.evoucher.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.View
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.customView.wheelview.ItemDrawable
import com.example.evoucher.customView.wheelview.WheelView
import com.example.evoucher.customView.wheelview.adapter.WheelArrayAdapter
import com.example.evoucher.databinding.FragmentRouletteBinding
import com.example.evoucher.utils.Utils

class RouletteFragment : BaseFragment<FragmentRouletteBinding>(FragmentRouletteBinding::inflate) {

    private var mRunnable: Runnable? = null
    private var mHandler = Handler()
    private var count = 0;
    private var total = 0;
    private var currentPosition = 0;
    var listStrings: MutableList<String> = ArrayList()

    override fun initObserve() {

    }

    override fun initialize() {
        binding.btnRotary.isEnabled = true
        binding.btnRotary.alpha = 1f
        binding.btnReceiveGift.isEnabled = false
        binding.btnReceiveGift.alpha = 0.5f

        setupData()

        binding.btnRotary.setOnClickListener{
            count++
            currentPosition = Utils.Companion.random(0,5)
            binding.wheelView.startRoulette(currentPosition)
            total += listStrings.get(currentPosition).toInt()

            binding.tvTotal.text = "Tổng điểm: ${total}"
            binding.tvCount.text = "Quay lần: ${count}"

            if(count == 3){
                binding.btnRotary.isEnabled = false
                binding.btnRotary.alpha = 0.5f
                binding.btnReceiveGift.isEnabled = true
                binding.btnReceiveGift.alpha = 1f
            } else {
                binding.btnRotary.isEnabled = true
                binding.btnRotary.alpha = 1f
                binding.btnReceiveGift.isEnabled = false
                binding.btnReceiveGift.alpha = 0.5f
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RouletteFragment()
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
                if (mRunnable == null) {
                    mRunnable = Runnable {
                        //Xu ly sau khi quay xong
                    }
                }
                if (mHandler == null) mHandler = Handler()
                mHandler.postDelayed(mRunnable!!, 1000)
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
}