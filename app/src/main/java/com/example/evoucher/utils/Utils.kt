package com.example.evoucher.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.evoucher.model.ApiError
import com.example.evoucher.model.Resource
import com.example.evoucher.model.ResourceStatus
import com.example.evoucher.utils.ConstantUtils.Companion.TYPE_IMAGE_BRANCH
import com.example.evoucher.utils.ConstantUtils.Companion.TYPE_IMAGE_CAMPAIGN
import com.example.evoucher.utils.ConstantUtils.Companion.TYPE_IMAGE_GAMES
import com.example.evoucher.utils.ConstantUtils.Companion.TYPE_IMAGE_PARTNER
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.Normalizer
import java.text.ParseException
import java.text.SimpleDateFormat
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

        fun getImageUrl(name: String, type: Int) : String {
            if(type == TYPE_IMAGE_CAMPAIGN) {
                return "https://ptc3.ngoinhaso.vn/images/AnhChienDich/$name";
            } else if(type == TYPE_IMAGE_PARTNER) {
                return "https://ptc3.ngoinhaso.vn/images/LogoDoiTac/$name";
            } else if(type == TYPE_IMAGE_BRANCH){
                return "https://ptc3.ngoinhaso.vn/images/AnhChiNhanh/$name";
            } else if(type == TYPE_IMAGE_GAMES){
                return "https://ptc3.ngoinhaso.vn/images/AnhTroChoi/$name";
            }
            return ""
        }

        fun stringToDate(dateStr: String) : Date {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            try {
                val date: Date = format.parse(dateStr)
                return date
            } catch (e: ParseException) {
                return Date()
            }
        }

        fun Date.plus(days: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.time = this
            calendar.add(Calendar.DAY_OF_YEAR, days)
            return calendar.time
        }

        fun String.removeNonSpacingMarks() =
            Normalizer.normalize(this, Normalizer.Form.NFD)
                .replace("\\p{Mn}+".toRegex(), "")

        fun convertToMD5(input: String): String {
            try {
                // Create MD5 Hash
                val digest: MessageDigest = MessageDigest.getInstance("MD5")
                digest.update(input.toByteArray())
                val messageDigest: ByteArray = digest.digest()

                // Create Hex String
                val hexString = StringBuilder()
                for (b in messageDigest) {
                    val hex = Integer.toHexString(0xFF and b.toInt())
                    if (hex.length == 1) {
                        hexString.append("0")
                    }
                    hexString.append(hex)
                }
                return hexString.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return ""
        }

        fun enableButton(btn : Button, enable: Boolean) {
            if(enable) {
                btn.isEnabled = true
                btn.alpha = 1f
            } else {
                btn.isEnabled = false
                btn.alpha = 0.5f
            }
        }
    }
}