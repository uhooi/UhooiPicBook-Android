package com.theuhooi.uhooipicbook.modules.monsterdetail

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.api.load
import coil.request.ImageRequest
import com.theuhooi.uhooipicbook.R
import kotlinx.android.synthetic.main.fragment_monster_detail.view.*
import java.io.File
import java.io.FileOutputStream

class MonsterDetailFragment : Fragment() {

    // region Stored Instance Properties

    private val args: MonsterDetailFragmentArgs by navArgs()

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_monster_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.icon_imageview.load(this.args.monster.iconUrlString)
        view.name_textview.text = this.args.monster.name
        // TODO: 文字列の加工を終わらせた状態で渡す
        view.description_textview.text = this.args.monster.description.replace("\\n", "\n")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_share, menu)
        val shareMenuItem = menu.findItem(R.id.share_menu_item)
        shareMenuItem.setOnMenuItemClickListener {
            shareMonster()
            true
        }
    }

    // endregion

    // region Other Private Methods

    private fun shareMonster() {
        val monster = this.args.monster
        val context = requireContext()
        val request = ImageRequest.Builder(context)
            .data(monster.iconUrlString)
            .target { drawable ->
                val file = File(context.externalCacheDir, "share_temp.jpeg")
                FileOutputStream(file).use { outputStream ->
                    val bitmap = (drawable as BitmapDrawable).bitmap
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.flush()
                }
                val fileUri: Uri? = try {
                    FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
                } catch (e: IllegalArgumentException) {
                    null
                }
                fileUri ?: run {
                    // TODO: エラーハンドリング
                }
                val text =
                    monster.name + "\n" + monster.description.replace(
                        "\\n",
                        "\n"
                    ) + "\n#UhooiPicBook"
                ShareCompat.IntentBuilder
                    .from(requireActivity())
                    .setText(text)
                    .setStream(fileUri)
                    .setType("image/png")
                    .setChooserTitle(R.string.share_menu_item_title)
                    .startChooser()
            }
            .build()
        val disposable = ImageLoader(requireContext()).enqueue(request)
    }

    // endregion

}
