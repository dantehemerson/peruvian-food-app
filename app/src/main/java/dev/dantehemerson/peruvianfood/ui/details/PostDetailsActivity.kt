package dev.dantehemerson.peruvianfood.ui.details

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.app.ShareCompat
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import dev.dantehemerson.peruvianfood.R
import dev.dantehemerson.peruvianfood.databinding.ActivityPostDetailsBinding
import dev.dantehemerson.peruvianfood.model.Post
import dev.dantehemerson.peruvianfood.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_post_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PostDetailsActivity : BaseActivity<PostDetailsViewModel, ActivityPostDetailsBinding>() {

    override val mViewModel: PostDetailsViewModel by viewModels()

    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val postId = intent.extras?.getInt(POST_ID)
            ?: throw IllegalArgumentException("`postId` must be non-null")

        initPost(postId)
    }

    private fun initPost(postId: Int) {
        mViewModel.getPost(postId).observe(this) { post ->
            mViewBinding.postContent.apply {
                this@PostDetailsActivity.post = post

                postTitle.text = post.title
                postAuthor.text = post.author

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    postBody.text = Html.fromHtml(post.bodyHTML, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    postBody.text = Html.fromHtml(post.bodyHTML)
                }
            }

            mViewBinding.imageView.load(post.imageUrl)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun getViewBinding(): ActivityPostDetailsBinding =
        ActivityPostDetailsBinding.inflate(layoutInflater)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }

            R.id.action_share -> {
                val shareMsg = getString(
                    R.string.share_message,
                    post.title,
                    post.author
                )

                val intent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(shareMsg)
                    .intent

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val POST_ID = "postId"
    }
}
