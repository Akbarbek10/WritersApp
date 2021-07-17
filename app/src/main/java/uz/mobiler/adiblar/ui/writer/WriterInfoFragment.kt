package uz.mobiler.adiblar.ui.writer

import android.content.res.Configuration
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.material.appbar.AppBarLayout
import com.like.LikeButton
import com.like.OnLikeListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_scrolling.view.*
import kotlinx.android.synthetic.main.fragment_writer_info.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.database.MyDBHelper
import uz.mobiler.adiblar.models.Writer
import uz.mobiler.adiblar.utils.LotinKrilService
import uz.mobiler.adiblar.utils.MySharedPreference
import java.util.*
import kotlin.math.abs


class WriterInfoFragment : Fragment() {

    private lateinit var root: View
    lateinit var writer: Writer
    private var temp = false
    private var desc = ""
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_writer_info, container, false)
        MySharedPreference.init(root.context)
        setLocale()
        val myDBHelper = MyDBHelper(root.context)

        if (arguments != null) {
            writer = arguments?.getSerializable("writer") as Writer
        }

        mInterstitialAd = InterstitialAd(root.context)
        mInterstitialAd.adUnitId = getString(R.string.interstitial)
        mInterstitialAd.loadAd(AdRequest.Builder().build())


        val toolbar: androidx.appcompat.widget.Toolbar = root.findViewById(R.id.toolbar)
        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(toolbar)

        var name = ""

        when (MySharedPreference.language) {
            "uz" -> {
                name = writer.writer
                root.toolbar_layout.title = writer.writer
                desc = writer.description
                root.tv_info.text = writer.description
            }
            "ru" -> {
                name = LotinKrilService.convert(writer.writer)
                root.toolbar_layout.title = LotinKrilService.convert(writer.writer)
                root.tv_info.text = LotinKrilService.convert(writer.description)
                desc = LotinKrilService.convert(writer.description)
            }
        }

        root.toolbar_layout.setCollapsedTitleTextColor(root.context.resources.getColor(R.color.grey))
        root.toolbar_layout.setExpandedTitleColor(root.context.resources.getColor(R.color.grey))

        root.like_btn1.isLiked = myDBHelper.getWriterById(writer)

        if (MySharedPreference.darkMode!!) {
            root.like_btn1.setUnlikeDrawableRes(R.drawable.ic_vector_white)

        }
        if (myDBHelper.getWriterById(writer)) {
            root.like_back.setBackgroundResource(R.drawable.unlike_background)
        } else {
            root.like_back.setBackgroundResource(R.drawable.like_background_white)
        }

        root.like_btn1.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                myDBHelper.addWriter(writer)
                root.like_back.setBackgroundResource(R.drawable.unlike_background)
                root.like_btn1.setLikeDrawableRes(R.drawable.ic_bookmark)
            }

            override fun unLiked(likeButton: LikeButton?) {
                myDBHelper.deleteWriter(writer)
                root.like_back.setBackgroundResource(R.drawable.like_background_white)
                root.like_btn1.setLikeDrawableRes(R.drawable.ic_vector2)

            }

        })

        Picasso.get().load(writer.imgUrl).placeholder(R.drawable.place_holder)
            .fit().error(R.drawable.error_image).into(root.iv_image)

        root.tv_birthDeath.text = ("(${writer.birthDead})")

        root.btn_back.setOnClickListener {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
                mInterstitialAd.adListener = object : AdListener() {
                    override fun onAdClosed() {
                        super.onAdClosed()
                        findNavController().popBackStack()
                    }
                }
            } else {
                findNavController().popBackStack()
            }
        }

        root.layout_search.setOnClickListener {
            root.linear_search.visibility = View.VISIBLE
            root.layout_search.visibility = View.GONE
            root.btn_back.visibility = View.GONE
            root.like_back.visibility = View.GONE
            temp = true
        }

        root.iv_cancel.setOnClickListener {
            if (root.et_search.text.toString().isEmpty()) {
                temp = false
                root.linear_search.visibility = View.GONE
                root.layout_search.visibility = View.VISIBLE
                root.btn_back.visibility = View.VISIBLE
                root.like_back.visibility = View.VISIBLE
            } else {
                root.et_search.setText("")

            }
        }

        root.app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { p0, p1 ->
            var isShow = false
            var scrollRange = -1

            if (scrollRange == -1) {
                scrollRange = p0.totalScrollRange;
            }

            if (abs(scrollRange + p1) == 0 && temp) {
                root.toolbar_layout.title = ""
                isShow = false
            } else if (abs(scrollRange + p1) == 0) {
                isShow = false
                root.like_back.setBackgroundResource(R.drawable.like_background_white)
                root.like_btn1.setLikeDrawableRes(R.drawable.ic_bookmark_green)

            } else if (!isShow) {
                if (root.like_btn1.isLiked) {
                    root.like_back.setBackgroundResource(R.drawable.unlike_background)
                    root.like_btn1.setLikeDrawableRes(R.drawable.ic_bookmark)
                } else {
                    root.like_back.setBackgroundResource(R.drawable.like_background_white)
                    root.like_btn1.setLikeDrawableRes(R.drawable.ic_vector2)
                }

                val split = name.split(" ")
                if (split.size > 2) {
                    var text = ""
                    split.forEachIndexed { index, s ->
                        if (index == 0) {
                            text += split[0].substring(0, 1) + ". "
                        } else if (index == 1) {
                            text += split[1].substring(0, 1) + ". "
                        } else {
                            text += split[index] + " "
                        }
                    }
                    root.toolbar_layout.title = text
                } else {
                    root.toolbar_layout.title = name
                }
                isShow = true
            }

        })

        root.et_search.addTextChangedListener { item ->
            if (item.toString().length > 1) {
                setHighLightedText(root.tv_info, item.toString())
            } else {
                root.tv_info.text = desc
            }

        }

        return root
    }

    private fun setHighLightedText(tv: TextView, textToHighlight: String) {
        val tvt = tv.text.toString()
        var ofe = tvt.indexOf(textToHighlight, 0)
        val wordToSpan: Spannable = SpannableString(tv.text)
        var ofs = 0
        while (ofs < tvt.length && ofe != -1) {
            ofe = tvt.indexOf(textToHighlight, ofs)
            if (ofe == -1) break else {
                // set color here
                wordToSpan.setSpan(
                    BackgroundColorSpan(root.context.resources.getColor(R.color.mark_color)),
                    ofe,
                    ofe + textToHighlight.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE)
            }
            ofs = ofe + 1
        }
    }

    private fun setLocale() {
        val locale = Locale(MySharedPreference.language!!)
        Locale.setDefault(locale)
        val config: Configuration = resources.configuration
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }

}