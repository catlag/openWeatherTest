package com.example.openweather.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.openweather.core.Status
import com.example.openweather.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {
    private val mainViewModel by sharedViewModel<MainViewModel>()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservables()
        setClickListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setClickListener(){
//        add checks for empty field
        binding.button.setOnClickListener {
            getCoordinates(binding.userInput.text.toString())
        }
    }

    private fun setObservables(){
        mainViewModel.coordinates.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data
                    //show list in recycler for user to click
                }
                Status.LOADING -> {
//                    add loading icon
                }
                Status.ERROR -> TODO()
            }
        }
    }

    private fun getCoordinates(userInput: String){
        mainViewModel.getLocationCoordinates(userInput)
    }

}