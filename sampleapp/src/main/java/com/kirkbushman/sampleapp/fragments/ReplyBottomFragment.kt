package com.kirkbushman.sampleapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.sampleapp.databinding.FragmentBottomReplyBinding
import com.kirkbushman.sampleapp.utils.DoAsync
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReplyBottomFragment : BottomSheetDialogFragment() {

    companion object {

        private const val PASSED_COMMENT = "comment_parcelable passed"

        fun instance(comment: Comment): ReplyBottomFragment {
            val fr = ReplyBottomFragment()
            val args = Bundle()

            args.putParcelable(PASSED_COMMENT, comment)
            fr.arguments = args

            return fr
        }
    }

    @Inject
    lateinit var client: RedditClient

    private val comment by lazy { arguments?.getParcelable(PASSED_COMMENT) as Comment? }

    private var binding: FragmentBottomReplyBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBottomReplyBinding.inflate(layoutInflater, container, false)

        binding?.let { b ->

            val titleStr = "Reply for comment ${comment?.id}"
            b.title.text = titleStr

            b.bttnReply.setOnClickListener {

                var responseComment: Comment? = null

                DoAsync(
                    doWork = {

                        val replyText = b.editReply.text.toString().trim()
                        responseComment =
                            client.contributionsClient.reply(comment?.fullname ?: "", replyText)
                    },
                    onPost = {

                        val toastStr = "Response is: ${responseComment?.body}"
                        Toast.makeText(activity, toastStr, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
