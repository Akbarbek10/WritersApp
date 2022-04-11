package uz.mobiler.adiblar

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import uz.mobiler.adiblar.utils.MySharedPreference
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MySharedPreference.init(this)

        if (MySharedPreference.darkMode!!)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}

        val testDeviceIds = listOf("FC1A666C20BC789089825180CFE619C6")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)

        setLocale()

        Dexter.withContext(this).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
            }

        }).check()
    }

    override fun onResume() {
        super.onResume()
        setLocale()
    }

    private fun setLocale() {
        val locale = Locale(MySharedPreference.language!!)
        Locale.setDefault(locale)
        val config: Configuration = resources.configuration
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}