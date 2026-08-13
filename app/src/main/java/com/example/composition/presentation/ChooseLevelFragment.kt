package com.example.composition.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.composition.R


class ChooseLevelFragment : Fragment() {
    private lateinit var onButtonLevelClickListener: OnButtonLevelClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnButtonLevelClickListener){
            onButtonLevelClickListener = context
        }
        else {
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
        return inflater.inflate(R.layout.fragment_choose_level, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnTestLevel = view.findViewById<Button>(R.id.btn_choose_test_level)
        btnTestLevel.setOnClickListener {
            onButtonLevelClickListener.OnButtonLevelClick()
        }
    }

    interface OnButtonLevelClickListener{
        fun OnButtonLevelClick()
    }
}