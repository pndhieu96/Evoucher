package com.example.evoucher.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.example.evoucher.databinding.CvTopBarBinding

class TopBar : FrameLayout {
    private lateinit var binding: CvTopBarBinding
    var callBack : CallBack? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle){
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val inflate = LayoutInflater.from(context)
        binding = CvTopBarBinding.inflate(inflate)
        addView(binding.root)
        binding.ivBack.setOnClickListener {
            callBack?.onClick()
        }
    }

    fun setTitle(title: String) {
        binding.tvTitle.text = title
    }

    interface CallBack {
        fun onClick()
    }
}