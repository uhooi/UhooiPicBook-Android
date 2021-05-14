package com.theuhooi.uhooipicbook.modules.monsterdetail

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.databinding.FragmentMonsterDetailBinding
import com.theuhooi.uhooipicbook.extensions.IntColorInterface
import com.theuhooi.uhooipicbook.modules.monsterlist.viewmodels.MonsterViewModel
import java.io.File
import java.io.FileOutputStream

class MonsterDetailFragment : Fragment(), IntColorInterface {

    // region Stored Instance Properties

    private val args: MonsterDetailFragmentArgs by navArgs()

    private val viewModel: MonsterViewModel by hiltNavGraphViewModels(R.id.monster_nav_graph)

    private var _binding: FragmentMonsterDetailBinding? = null
    private val binding get() = _binding!!

    private var disposable: Disposable? = null

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        _binding = FragmentMonsterDetailBinding.inflate(inflater, container, false)
        this.binding.args = this.args
        this.binding.viewModel = this.viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.dancingImageview.setOnClickListener {
            val action = MonsterDetailFragmentDirections.actionDetailToDancing(
                this.viewModel.findMonster(this.args.monsterOrder)?.dancingUrlString!!
            )
            findNavController().navigate(action)
        }

        val baseColorCode = this.viewModel.findMonster(this.args.monsterOrder)?.baseColorCode
        if (baseColorCode?.isNotEmpty() == true) {
            val activity = requireActivity()
            val actionBarColor = Color.parseColor(baseColorCode)
            (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
                ColorDrawable(actionBarColor)
            )
            activity.window.statusBarColor = actionBarColor.actionBarColorToStatusBarColor
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        val monster = this.viewModel.findMonster(this.args.monsterOrder)!!
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
