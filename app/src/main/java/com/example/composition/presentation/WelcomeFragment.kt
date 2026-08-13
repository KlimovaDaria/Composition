package com.example.composition.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.composition.R
import com.example.composition.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var onButtonUnderstandClickListener: OnButtonUnderstandClickListener
    private var _binding: FragmentWelcomeBinding? = null

    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding==null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnButtonUnderstandClickListener) {
            onButtonUnderstandClickListener = context
        } else {
            throw RuntimeException("Activity must implement OnButtonUnderstandClickListener")
        }
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnUnderstand = view.findViewById<Button>(R.id.btn_understand)
        btnUnderstand.setOnClickListener {
            onButtonUnderstandClickListener.onButtonUnderstandClick()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnButtonUnderstandClickListener {
        fun onButtonUnderstandClick()
    }
}