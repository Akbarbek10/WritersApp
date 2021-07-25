package uz.mobiler.adiblar.ui.setting

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import uz.mobiler.adiblar.MainActivity
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.utils.MySharedPreference
import java.util.*


class SettingFragment : Fragment() {

    private lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_setting, container, false)

        MySharedPreference.init(root.context)

        val adRequest = AdRequest.Builder().build()
        root.adView.loadAd(adRequest)

        when (MySharedPreference.language) {
            "uz" -> {
                root.spinner.text = getString(R.string.lotin)
            }

            "ru" -> {
                root.spinner.text = getString(R.string.kril)
            }
        }
        setLocale()
        initUI()

        root.themeSwitch.isChecked = MySharedPreference.darkMode!!

        if (MySharedPreference.darkMode!!) {
            root.tv_darkTheme.text = getString(R.string.night)

        } else {
            root.tv_darkTheme.text = getString(R.string.day)
        }

        root.themeSwitch.addSwitchObserver { switchView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                MySharedPreference.darkMode = true
                root.tv_darkTheme.text = getString(R.string.night)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                MySharedPreference.darkMode = false
                root.tv_darkTheme.text = getString(R.string.day)
            }

        }

        root.spinner.setOnClickListener {
            val dialog = AlertDialog.Builder(root.context).create()
            val dialogView = layoutInflater.inflate(R.layout.dialog, null, false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setView(dialogView)

            dialogView.radio_group.check(MySharedPreference.spinnerPosition!!)

            if ((MySharedPreference.spinnerPosition!! == 0)) {
                dialogView.radio_group.check(R.id.radio_uz)
            }

            dialogView.btn_ok.setOnClickListener {
                initUI()
                when (dialogView.radio_group.checkedRadioButtonId) {
                    R.id.radio_uz -> {
                        MySharedPreference.language = "uz"
                    }
                    R.id.radio_ru -> {
                        MySharedPreference.language = "ru"
                    }
                }
                MySharedPreference.spinnerPosition = dialogView.radio_group.checkedRadioButtonId
                startActivity(Intent(root.context, MainActivity::class.java))
                activity?.finish()
            }
            dialogView.btn_cancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        root.card3.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody =
                "https://play.google.com/store/apps/details?id=uz.mobiler.adiblar"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Adiblar hayoti va ijodi");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            this.startActivity(sharingIntent)

        }

        root.card4.setOnClickListener {
            val alertDialog = AlertDialog.Builder(root.context).create()
            val dialogView = layoutInflater.inflate(R.layout.dialog_about, null, false)
            alertDialog.setView(dialogView)
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window?.setGravity(Gravity.CENTER)
            alertDialog.show()
        }

        root.btn_back.setOnClickListener {
            findNavController().popBackStack()
        }

        return root
    }

    private fun initUI() {
        root.tv_setting.text = getString(R.string.sozlamalar)
        root.tv_about.text = getString(R.string.dastur_haqida)
        root.tv_language.text = getString(R.string.dastur_tili)
        root.tv_share.text = getString(R.string.ulashish)
    }

    private fun setLocale() {
        val locale = Locale(MySharedPreference.language!!)
        Locale.setDefault(locale)
        val config: Configuration = resources.configuration
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }

}