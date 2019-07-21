package com.kirkbushman.sampleapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.fragment_bottom_reply.*
import kotlinx.android.synthetic.main.fragment_bottom_reply.view.*

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

    private val client by lazy { TestApplication.instance.getClient() }
    private val comment by lazy { arguments?.getParcelable(PASSED_COMMENT) as Comment? }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_reply, container, false)

        val titleStr = "Reply for comment ${comment?.id}"
        view.title.text = titleStr

        view.bttn_reply.setOnClickListener {

            var responseComment: Comment? = null

            doAsync(doWork = {

                val replyText = edit_reply.text.toString().trim()
                responseComment = client?.reply(comment?.fullname ?: "", replyText)
            }, onPost = {

                val toastStr = "Response is: ${responseComment?.body}"
                Toast.makeText(activity, toastStr, Toast.LENGTH_SHORT).show()
            })
        }

        return view
    }
}
