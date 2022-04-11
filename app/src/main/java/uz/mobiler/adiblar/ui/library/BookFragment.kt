package uz.mobiler.adiblar.ui.library

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import com.like.LikeButton
import com.like.OnLikeListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_writer.view.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.database.MyDBHelper
import uz.mobiler.adiblar.database.MyDBHelperBook
import uz.mobiler.adiblar.databinding.DialogDownloadBinding
import uz.mobiler.adiblar.databinding.FragmentBookBinding
import uz.mobiler.adiblar.models.Book
import uz.mobiler.adiblar.utils.LotinKrilService
import uz.mobiler.adiblar.utils.MySharedPreference
import java.io.File
import java.util.*


class BookFragment : Fragment() {
    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!
    private lateinit var myDBHelperBook: MyDBHelperBook

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        myDBHelperBook = MyDBHelperBook(binding.root.context)
        val book = arguments?.getSerializable("book") as Book
        if (myDBHelperBook.getBookById(book)) {
            binding.likeBack.setBackgroundResource(R.drawable.unlike_background)
        } else {
            binding.likeBack.setBackgroundResource(R.drawable.like_background)
        }
        binding.likeBtn.isLiked = myDBHelperBook.getBookById(book)

        OverScrollDecoratorHelper.setUpOverScroll(binding.scrollView)

        val downloadFolder =
            binding.root.context.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

        val path = downloadFolder?.path

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()


        val config = PRDownloaderConfig.newBuilder()
            .setDatabaseEnabled(true)
            .build()
        PRDownloader.initialize(binding.root.context.applicationContext, config)



        book.apply {
            when(MySharedPreference.language) {
                "kr" -> {
                    binding.tvBookName.text = LotinKrilService.convert(name?.uz!!)
                    binding.tvAuthorName.text = LotinKrilService.convert(author?.uz!!)
                    binding.tvDesc.text = LotinKrilService.convert(desc?.uz!!)
                    binding.tvGenre.text = LotinKrilService.convert(genre?.uz!!)
                }
                "ru"  -> {
                    binding.tvBookName.text = name?.ru
                    binding.tvAuthorName.text = author?.ru
                    binding.tvDesc.text = desc?.ru
                    binding.tvGenre.text = genre?.ru
                }
                "en"  -> {
                    binding.tvBookName.text = name?.eng
                    binding.tvAuthorName.text = author?.eng
                    binding.tvDesc.text = desc?.eng
                    binding.tvGenre.text = genre?.eng
                }
                else -> {
                    binding.tvBookName.text = name?.uz
                    binding.tvAuthorName.text = author?.uz
                    binding.tvDesc.text = desc?.uz
                    binding.tvGenre.text = genre?.uz
                }
            }
            binding.tvBookLang.text = language

            Picasso.get().load(image)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.error_image)
                .into(binding.ivBook)
        }

        val listFiles: Array<File>? = downloadFolder?.listFiles()

        listFiles?.forEach { file ->
            if (file.name.contains(book.name?.eng!!, true)) {
                binding.cardDownload.visibility = View.GONE
                binding.cardRead.visibility = View.VISIBLE
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.likeBtn.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                myDBHelperBook.addBook(book)
                binding.likeBack.setBackgroundResource(R.drawable.unlike_background)
            }

            override fun unLiked(likeButton: LikeButton?) {
                myDBHelperBook.deleteBook(book)
                binding.likeBack.setBackgroundResource(R.drawable.like_background)
            }
        })

        binding.cardRead.setOnClickListener {
            val listFiles1 = downloadFolder?.listFiles()
            listFiles1?.forEach { file ->
                if (file.name.contains(book.name?.eng!!, true)) {
                    openPDF(path!!, book.name?.eng.toString())
                }
            }
        }

        binding.cardDownload.setOnClickListener {
            val alertDialog = AlertDialog.Builder(binding.root.context).create()

            val view = DialogDownloadBinding.inflate(layoutInflater, null, false)
            alertDialog.setView(view.root)

            alertDialog.setCancelable(false)

            PRDownloader.download(book.url, path, "${book.name}.pdf").build()
                .setOnProgressListener {
                    view.progressCircular.progressMax = it.totalBytes.toFloat()
                    view.progressCircular.progress = it.currentBytes.toFloat()
                }
                .start(object : OnDownloadListener {
                    override fun onDownloadComplete() {
                        alertDialog.dismiss()
                        Toast.makeText(view.root.context, getString(R.string.downloaded_msg), Toast.LENGTH_SHORT)
                            .show()
                        binding.cardDownload.visibility = View.GONE
                        binding.cardRead.visibility = View.VISIBLE
                    }

                    override fun onError(error: Error?) {
                        alertDialog.dismiss()
                        Toast.makeText(
                            view.root.context,
                            "${error?.serverErrorMessage}",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                })

            alertDialog.show()
        }

        return binding.root
    }

    private fun openPDF(path: String, name: String) {
        val file =
            File("$path/$name.pdf")
        val uri: Uri = Uri.fromFile(file)
        val target = Intent(Intent.ACTION_VIEW)
        target.setDataAndType(uri, "application/pdf")
        target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        val intent = Intent.createChooser(target, "Open File with PDF VIEWER")
        try {
            activity?.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                binding.root.context,
                getString(R.string.downloaded_msg_error),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}