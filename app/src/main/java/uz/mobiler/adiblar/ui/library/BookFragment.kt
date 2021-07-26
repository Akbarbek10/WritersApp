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
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.databinding.DialogDownloadBinding
import uz.mobiler.adiblar.databinding.FragmentBookBinding
import uz.mobiler.adiblar.models.Book
import java.io.File
import java.util.*


class BookFragment : Fragment() {

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
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

        val book = arguments?.getSerializable("book") as Book

        book.apply {
            binding.tvBookName.text = name
            binding.tvAuthorName.text = author
            binding.tvDesc.text = desc
            binding.tvGenre.text = genre

            Picasso.get().load(image)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.error_image)
                .into(binding.ivBook)
        }

        val listFiles: Array<File>? = downloadFolder?.listFiles()

        listFiles?.forEach { file ->
            if (file.name.contains(book.name!!, true)) {
                binding.cardDownload.visibility = View.GONE
                binding.cardRead.visibility = View.VISIBLE
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.likeBtn.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                binding.likeBack.setBackgroundResource(R.drawable.unlike_background)
            }

            override fun unLiked(likeButton: LikeButton?) {
                binding.likeBack.setBackgroundResource(R.drawable.like_background)
            }
        })

        binding.cardRead.setOnClickListener {
            val listFiles1 = downloadFolder?.listFiles()
            listFiles1?.forEach { file ->
                if (file.name.contains(book.name!!, true)) {
                    openPDF(path!!, book.name)
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
                        Toast.makeText(view.root.context, "Yuklab olindi", Toast.LENGTH_SHORT)
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
                "Kitobni ochishda xatolik !",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}