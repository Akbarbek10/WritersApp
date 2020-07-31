package uz.mobiler.adiblar.fragments.data

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.content_scrolling.view.*
import kotlinx.android.synthetic.main.fragment_writer_info.view.*
import kotlinx.android.synthetic.main.fragment_writer_info.view.like_back
import kotlinx.android.synthetic.main.item_writer.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.database.MyDBHelper
import uz.mobiler.adiblar.models.Writer


class WriterInfoFragment : Fragment() {

    private lateinit var root: View
    lateinit var writer: Writer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_writer_info, container, false)

        val myDBHelper = MyDBHelper(root.context)
        if (arguments != null) {
            writer = arguments?.getSerializable("writer") as Writer
        }

        val toolbar: androidx.appcompat.widget.Toolbar = root.findViewById(R.id.toolbar)
        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(toolbar)

        root.toolbar_layout.title = writer.writer
        root.toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)
        root.toolbar_layout.setExpandedTitleColor(Color.BLACK)

        root.like_btn1.isLiked = myDBHelper.getWriterById(writer)

        if (myDBHelper.getWriterById(writer)) {
            root.like_back.setBackgroundResource(R.drawable.unlike_background)
        } else {
            root.like_back.setBackgroundResource(R.drawable.like_background_white)
        }

        root.like_btn1.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                myDBHelper.addWriter(writer)
                root.like_back.setBackgroundResource(R.drawable.unlike_background)
            }

            override fun unLiked(likeButton: LikeButton?) {
                myDBHelper.deleteWriter(writer)
                root.like_back.setBackgroundResource(R.drawable.like_background_white)
            }

        })

        Glide.with(root).load(writer.imgUrl).placeholder(R.drawable.place_holder)
            .centerCrop().error(R.drawable.error_image).into(root.iv_image)

        root.tv_info.text = writer.description

        root.tv_birthDeath.text = ("(${writer.birthDead})")

        root.btn_back.setOnClickListener {
            findNavController().popBackStack()
        }

        "Ogahiy Muhammad Rizo — o‘zbek yozuvchisi, tarixshunos va tarjimon. Shoir va tarixshunos Munis Xorazmiyning jiyani. U o‘zbeklarning yuz sulolasiga tegishli bo‘lgan. Ogahiy xivalik xonlar saroyida yilnomachi va mirobi (suv taqsimlovchi) bo‘lib xizmat qilgan. O‘tmishdoshining mehnatini davom ettirib, Xorazmiyning solnomasini 1872 yilga qadar davom ettirgan.\n\nMuhammad Rizo mirob ibn Ir-Niyozbek, adabiy tahallusi Ogahiy 1809 yilda Xiva shahridan 8 metr uzoqlikdagi Qiyot qishlog‘ida tavallud topgan.\n\nOtasidan erta ayrilgan Muhammad Rizo, tog‘asi - taniqli tarixshunos va shoir Shir Muhammad ibn Amir Ivaz biy mirob, adabiy tahallusi Munisning qo‘lida tarbiyalanib, undan savodni o‘rganadi. Keyinchalik Xiva xonligining poytaxtlaridan biridagi madrasada ta’limni davom ettirib, barcha kuchini arab va fors tillarini o‘rganishga qaratadi.\n\nU bor ishtiyoqi bilan kitob mutoalasi va bilim olishga kirishadi. Katta muhabbat ila sharq xalqlarining mumtoz namoyondalari Navoiy, Fuzuliy, Bedil va boshqalarning asarlarini o‘rganadi.\n\nXonning qo‘lida mirob bo‘lib xizmat qiluvchi tog‘asining vafotidan so‘ng, bu lavozimni u egallaydi. Yer sun’iy sug‘orishga asoslangan diyorda miroblik lavozimi og‘ir va mas’uliyatli kasb hisoblangan. Biroq Ogahiy ilmiy va adabiy bilimlar uchun vaqt topgan.\n\nBir kuni miroblik ishlari bo‘yicha yurganida Ogahiy otdan yiqilib, oyog‘ini sindirib qo‘yadi. Shundan so‘ng shoir yurishlardan bosh tortishga majbur bo‘ladi va ona qishlog‘i Qiyotda qolib, adabiy ishlar bilan shug‘ullanadi.\n\n52-55 yoshlarida Ogahiy o‘zi ichiga she’rlarni olgan, bayaz to‘plami “Sevishganlar tumori” nomli devon tuzib, uni sinchkovlik bilan tahrirlaydi. Shoir tomonidan yozilgan g‘azallar turlidir. Birida toza sevgi tarannum etilsa, boshqalarida so‘filik g‘oyalari, uchinchisida shaxsiy kechinmalari bayon etilgan.\n\nOgahiy nozik lirik, Navoiy maktabining iqtidorli izdoshi kabi taniqli.\n\n“Riyoz ud davla”, “Zubdat ut tavorih”, “Jomiy ul vakiati sultoniy”, “Gulshan davlat” va “Shohid ul iqbol” kabi asarlar muallifi.\n\n“Riyoz ud davla” asarida 1825 yildan 1842 yilgacha bo‘lgan Xorazm tarixi bayon etilgan.\n\n“Zubdat ut tavorih” da 1843 yildan 1846 yilgacha bo‘lgan Xorazm tarixi ifodalangan.\n\n“Gulshan davlat” asari o‘z ichiga 1856 dan 1865 yilgacha bo‘lgan tarixni o’z ichiga olgan.\n\nOgahiyning so‘nggi asari “Shohid ul iqbol” 1865 yildan 1872 yilgacha bo‘lgan davrga bag‘ishlangan.\n\nUning o‘zbek va tojik tilidagi she’rlarida fuqarolik mavzulari - nohaqlikka norozilik, ikkiyuzlamachilikni qoralash jaranglaydi. “Sevishganlar tumori” lirik devonida Ogahiy insoniyatning yuqori tuyg‘ularini tarannum etgan.\n\nOgahiy o‘zbek tiliga Nizomiy, Amir Husraf Dahlaviy, Saadi, Jomiy, Hiloliy va boshqalarning asarlarini she’riy tarjimasi bilan tanilgan. Tarjima sohasida Ogahiy chinakam yangilik yaratuvchi bo‘lgan. Uning tarjimonlik qarashlari bizning davrimizning badiiy qarashlariga juda yaqin.\n\nO‘zbek adabiyoti bu vaqt mobaynida tojik, ozarbayjon, turkman va boshqa adabiyotlar bilan uzviy rivojlandi. Oddiy xalq orasida Firdavsiyning “Shohnoma”, Bedil she’riyati, Fuzuliyning g‘azallariga ko‘plab taxmislar va’zxonligini eshitish mumkin bo‘lgan - bularning bari o‘sha davr o‘zbek adabiyotining o‘ziga xos jihatlarini tashkil etgan.\n\nOgahiy Sharq mumtoz ijodkorlarining 20 dan ziyod tarixiy va badiiy asarlarini (Saadiyning “Guliston” asari shular jumlasidan) o‘zbek tiliga tarjima qilgan. Uning tarixiy asari Bayoniy tomonidan davom ettirilgan.\n\nOgahiy 1874 yil Xivada vafot etgan."
        return root
    }

}