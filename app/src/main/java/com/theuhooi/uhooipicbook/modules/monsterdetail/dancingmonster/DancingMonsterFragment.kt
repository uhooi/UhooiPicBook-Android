package com.theuhooi.uhooipicbook.modules.monsterdetail.dancingmonster

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.theuhooi.uhooipicbook.R
import kotlinx.android.synthetic.main.fragment_dancing_monster.view.*

class DancingMonsterFragment : AppCompatDialogFragment() {

    // region Stored Instance Properties

    private val args: DancingMonsterFragmentArgs by navArgs()

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dancing_monster, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.icon_imageview.load(args.dancingUrlString)
        view.close_button.setOnClickListener { dismiss() }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes.apply {
                width = WindowManager.LayoutParams.MATCH_PARENT
                height = WindowManager.LayoutParams.MATCH_PARENT
            }
            attributes = layoutParams
        }
    }

    // endregion
}
