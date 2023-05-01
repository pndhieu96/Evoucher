package com.example.evoucher.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.evoucher.model.ApiError
import com.example.evoucher.model.Resource
import com.example.evoucher.model.ResourceStatus
import java.util.*


class Utils {
    companion object {
        fun <T> LiveData<Resource<T>>.observer(
            owner: LifecycleOwner,
            onSuccess: ((T) -> Unit)? = null,
            onError: ((ApiError) -> Unit)? = null,
            onLoading: (() -> Unit)? = null
        ) {
            observe(owner) {
                when (it.status) {
                    ResourceStatus.SUCCESS -> it.getContentIfNotHandled()?.let { data ->
                        onSuccess?.invoke(data)
                    }
                    ResourceStatus.ERROR -> it.getErrorIfNotHandled()?.let { error ->
                        onError?.invoke(error)
                    }
                    ResourceStatus.LOADING -> it.isLoadingIfNotHandled()?.let { loading ->
                        onLoading?.invoke()
                    }
                }
            }
        }

        fun reload(context: Context) {
            val packageManager: PackageManager = context.getPackageManager()
            val intent = packageManager.getLaunchIntentForPackage(context.getPackageName())
            val componentName = intent!!.component
            val mainIntent = Intent.makeRestartActivityTask(componentName)
            context.startActivity(mainIntent)
            Runtime.getRuntime().exit(0)
        }

        fun hideKeyboard(activity: Activity) {
            val imm: InputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view: View? = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }

        fun spToPixel(context: Context, sp: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics()
            ).toInt()
        }

        fun random(min: Int, max: Int) : Int {
            val random: Int = Random().nextInt(max - min + 1) + min
            return random;
        }
    }
}