package com.example.learning_english_kotlin.lesson

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.learning_english_kotlin.R
import com.example.learning_english_kotlin.model.ResourcesOfCategory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [ResourceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResourceFragment : Fragment() {

    private var wordRu: String? = null
    private var wordEn: String? = null
    private var resourceImageId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            wordRu = arguments?.getString(WORD_RU)
            wordEn = arguments?.getString(WORD_EN)
            resourceImageId = arguments!!.getInt(IMAGE_RESOURCE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_resource, null)
        val tvWordRu = view.findViewById<View>(R.id.tvWordRu) as TextView
        tvWordRu.text = wordRu
        val tvWordEn = view.findViewById<View>(R.id.tvWordEn) as TextView
        tvWordEn.text = wordEn
        val ivPicture = view.findViewById<View>(R.id.ivPicture) as ImageView
        ivPicture.setImageResource(resourceImageId)
        return view
    }

    companion object {
        private const val WORD_RU = "wordRu"
        private const val WORD_EN = "wordEn"
        private const val IMAGE_RESOURCE_ID = "imageResourceId"

        fun newInstance(resources: ResourcesOfCategory): ResourceFragment {
            val fragment = ResourceFragment()
            val args = Bundle()
            args.putString(WORD_RU, resources.wordRu)
            args.putString(WORD_EN, resources.wordEn)
            args.putInt(IMAGE_RESOURCE_ID, resources.imageResourceId)
            fragment.arguments = args
            return fragment
        }
    }
}
