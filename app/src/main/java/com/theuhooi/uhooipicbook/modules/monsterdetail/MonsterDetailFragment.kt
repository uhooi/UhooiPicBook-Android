package com.theuhooi.uhooipicbook.modules.monsterdetail

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import coil.load
import coil.request.Disposable
import coil.request.ImageRequest
import com.theuhooi.uhooipicbook.R
import kotlinx.android.synthetic.main.fragment_monster_detail.view.*
import java.io.File
import java.io.FileOutputStream

class MonsterDetailFragment : Fragment() {

    // region Stored Instance Properties

    private val args: MonsterDetailFragmentArgs by navArgs()

    private var disposable: Disposable? = null

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_monster_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.icon_imageview.load(this.args.monster.iconUrlString)
        view.name_textview.text = this.args.monster.name
        view.description_textview.text = unescapeNewline(this.args.monster.description)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_monster_detail, menu)
        val shareMenuItem = menu.findItem(R.id.share_menu_item)
        shareMenuItem.setOnMenuItemClickListener {
            shareMonster()
            true
        }
    }

    override fun onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu()

        this.disposable?.dispose()
    }

    // endregion

    // region Other Private Methods

    private fun shareMonster() {
        val monster = this.args.monster
        val context = requireContext()
        val request = ImageRequest.Builder(context)
            .data(monster.iconUrlString)
            .target { drawable ->
                ShareCompat.IntentBuilder
                    .from(requireActivity())
                    .setText(monster.name + "\n" + unescapeNewline(monster.description) + "\n#UhooiPicBook")
                    .setStream(createTempPngFileUri(context, drawable))
                    .setType("image/png")
                    .setChooserTitle(R.string.share_menu_item_title)
                    .startChooser()
            }
            .build()
        this.disposable = ImageLoader(context).enqueue(request)
    }

    private fun createTempPngFileUri(context: Context, drawable: Drawable): Uri? {
        val cacheFile = File(context.externalCacheDir, "share_temp.png")
        createPngFile(drawable, cacheFile)
        return FileProvider.getUriForFile(context, context.packageName + ".fileprovider", cacheFile)
    }

    private fun createPngFile(drawable: Drawable, file: File) {
        FileOutputStream(file).use { outputStream ->
            val bitmap = (drawable as BitmapDrawable).bitmap
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
        }
    }

    private fun unescapeNewline(text: String): String {
        return text.replace("\\n", "\n")
    }

    // endregion

}
