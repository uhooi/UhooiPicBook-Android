package com.theuhooi.uhooipicbook.modules.monsterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.theuhooi.uhooipicbook.R
import kotlinx.android.synthetic.main.fragment_monster_detail.view.*

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
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setText("aiueo") // FIXME: 仮の文言
                .setType("text/plain")
                .setChooserTitle(R.string.share_menu_item)
                .startChooser()
            true
        }
    }

    // endregion

}
