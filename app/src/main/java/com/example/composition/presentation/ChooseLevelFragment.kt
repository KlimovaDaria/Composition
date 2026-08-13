package com.example.composition.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.composition.R
import com.example.composition.databinding.FragmentChooseLevelBinding


class ChooseLevelFragment : Fragment() {
    private lateinit var onButtonLevelClickListener: OnButtonLevelClickListener
    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnButtonLevelClickListener) {
            onButtonLevelClickListener = context
        } else {
            throw RuntimeException("Activity must implement OnButtonLevelClickListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnTestLevel = view.findViewById<Button>(R.id.btn_choose_test_level)
        btnTestLevel.setOnClickListener {
            onButtonLevelClickListener.OnButtonLevelClick()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnButtonLevelClickListener {
        fun OnButtonLevelClick()
    }
}