package com.example.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.Question

class GameFragment : Fragment() {
    private val args by navArgs<GameFragmentArgs>()

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding==null")

    private val viewModelFactory by lazy {
        GameFragmentViewModelFactory(requireActivity().application, args.level)
    }

    private val gameFragmentViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameFragmentViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        setClickListenersForOptions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
        )
    }

    private fun setClickListenersForOptions() {
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                gameFragmentViewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        gameFragmentViewModel.questionLD.observe(viewLifecycleOwner) {
            setQuestion(it)
        }
        gameFragmentViewModel.timerStrLD.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        gameFragmentViewModel.gameResultLD.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
        gameFragmentViewModel.progressAnswersLD.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
        gameFragmentViewModel.percentOfRightAnswersLD.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        gameFragmentViewModel.enoughCountOfRightAnswersLD.observe(viewLifecycleOwner) {
            setColorForTvAnswersProgress(it)
        }
        gameFragmentViewModel.enoughPercentsOfRightAnswersLD.observe(viewLifecycleOwner) {
            setColorForProgressBar(it)
        }
        gameFragmentViewModel.minPercentLD.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
    }

    private fun setColorForTvAnswersProgress(isEnough: Boolean) {
        val color = getColorByState(isEnough)
        binding.tvAnswersProgress.setTextColor(color)
    }

    private fun setColorForProgressBar(isEnough: Boolean) {
        val color = getColorByState(isEnough)
        binding.progressBar.progressTintList = ColorStateList.valueOf(color)
    }

    private fun getColorByState(isEnough: Boolean): Int {
        val colorResId = if (isEnough) {
            android.R.color.holo_green_dark
        } else {
            android.R.color.holo_red_dark
        }
        val color = ContextCompat.getColor(requireContext(), colorResId)
        return color
    }

    private fun setQuestion(question: Question) {
        val sum = question.sum
        val options = question.options
        val visibleNumber = question.visibleNumber
        binding.tvSum.apply {
            text = sum.toString()
        }
        binding.tvLeftNumber.apply {
            text = visibleNumber.toString()
        }
        for ((i, element) in tvOptions.withIndex()) {
            element.text = options[i].toString()
        }

    }
}