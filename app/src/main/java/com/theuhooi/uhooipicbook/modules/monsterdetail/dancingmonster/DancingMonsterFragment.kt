package com.theuhooi.uhooipicbook.modules.monsterdetail.dancingmonster

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.databinding.FragmentDancingMonsterBinding
import com.theuhooi.uhooipicbook.modules.monsterlist.viewmodels.MonsterViewModel

class DancingMonsterFragment : AppCompatDialogFragment() {

    // region Stored Instance Properties

    private val viewModel: MonsterViewModel by hiltNavGraphViewModels(R.id.monster_nav_graph)

    private var _binding: FragmentDancingMonsterBinding? = null
    private val binding get() = _binding!!

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDancingMonsterBinding.inflate(inflater, container, false)
        this.binding.viewModel = this.viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.closeButton.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

        this.dialog?.window?.apply {
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
