package com.example.composition.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.composition.R
import com.example.composition.presentation.GameFragment.OnButtonOptionClickListener

class MainActivity : AppCompatActivity(),
    WelcomeFragment.OnButtonUnderstandClickListener,
ChooseLevelFragment.OnButtonLevelClickListener,
    OnButtonOptionClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, WelcomeFragment())
            .commit()
    }

    override fun onButtonUnderstandClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, ChooseLevelFragment())
            .commit()
    }

    override fun OnButtonLevelClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, GameFragment())
            .commit()
    }

    override fun onButtonOptionClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment())
            .commit()
    }
}