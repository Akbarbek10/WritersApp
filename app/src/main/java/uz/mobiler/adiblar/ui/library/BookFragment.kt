package uz.mobiler.adiblar.ui.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.databinding.FragmentBookBinding
import uz.mobiler.adiblar.models.Book


class BookFragment : Fragment() {

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)

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

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}