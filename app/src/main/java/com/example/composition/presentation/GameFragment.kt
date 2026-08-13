package com.example.composition.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var onButtonOptionClickListener: OnButtonOptionClickListener
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding?: throw RuntimeException("FragmentGameBinding==null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnButtonOptionClickListener){
            onButtonOptionClickListener = context
        }
        else {
            throw RuntimeException("Activity must implement OnButtonOptionClickListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnOption1 = view.findViewById<TextView>(R.id.tv_option_1)
        btnOption1.setOnClickListener {
            onButtonOptionClickListener.onButtonOptionClick()
        }
    }

    override fun onDestroyView() {
        super.onDestroy()
        _binding=null
    }

    interface OnButtonOptionClickListener{
        fun onButtonOptionClick()
    }
}