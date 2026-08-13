package com.example.composition.presentation
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.composition.R
import androidx.fragment.app.Fragment

class WelcomeFragment : Fragment() {
    private lateinit var onButtonUnderstandClickListener: OnButtonUnderstandClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnButtonUnderstandClickListener){
            onButtonUnderstandClickListener = context
        }
        else {
            throw RuntimeException("Activity must implement OnButtonUnderstandClickListener")
        }
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): android.view.View? {
        return inflater.inflate(com.example.composition.R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnUnderstand = view.findViewById<Button>(R.id.btn_understand)
        btnUnderstand.setOnClickListener {
            onButtonUnderstandClickListener.onButtonUnderstandClick()
        }
    }

    interface OnButtonUnderstandClickListener{
        fun onButtonUnderstandClick()
    }
}