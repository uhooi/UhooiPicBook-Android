package com.theuhooi.uhooipicbook.ui.monsterdetail

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.databinding.MonsterDetailFragmentBinding
import com.theuhooi.uhooipicbook.domain.models.MonsterItem
import com.theuhooi.uhooipicbook.extensions.IntColorInterface
import com.theuhooi.uhooipicbook.ui.monsterlist.MonsterViewModel
import java.io.File
import java.io.FileOutputStream

class MonsterDetailFragment : Fragment(R.layout.monster_detail_fragment), IntColorInterface {

    // region Stored Instance Properties

    private val args: MonsterDetailFragmentArgs by navArgs()

    private val viewModel: MonsterViewModel by hiltNavGraphViewModels(R.id.monster_nav_graph)

    private var disposable: Disposable? = null

    // endregion

    // region Computed Instance Properties

    private val monster: MonsterItem by lazy { viewModel.findMonster(args.monsterOrder) }

    // endregion

    // region View Life-Cycle Methods

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.monster_detail_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.share_menu_item -> {
                        shareMonster()
                        true
                    }
                    else -> false
                }
            }

            override fun onMenuClosed(menu: Menu) {
                super.onMenuClosed(menu)
                disposable?.dispose()
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val binding = MonsterDetailFragmentBinding.bind(view)
        binding.monster = monster

        binding.dancingImageview.setOnClickListener {
            val action =
                MonsterDetailFragmentDirections.actionDetailToDancing(monster.dancingUrlString)
            findNavController().navigate(action)
        }

        val baseColorCode = monster.baseColorCode
        if (baseColorCode.isNotEmpty()) {
            val activity = requireActivity()
            val actionBarColor = Color.parseColor(baseColorCode)
            (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
                ColorDrawable(actionBarColor)
            )
            activity.window.statusBarColor = actionBarColor.actionBarColorToStatusBarColor
        }
    }

    // endregion

    // region Other Private Methods

    private fun shareMonster() {
        val context = requireContext()
        val request = ImageRequest.Builder(context)
            .data(monster.iconUrlString)
            .target { drawable ->
                ShareCompat.IntentBuilder(context)
                    .setText(monster.name + "\n" + unescapeNewline(monster.description) + "\n#UhooiPicBook")
                    .setStream(createTempPngFileUri(context, drawable))
                    .setType("image/png")
                    .setChooserTitle(R.string.share_menu_item_title)
                    .startChooser()
            }
            .build()
        disposable = ImageLoader(context).enqueue(request)
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
