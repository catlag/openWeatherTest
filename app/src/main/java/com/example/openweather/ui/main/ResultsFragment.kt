package com.example.openweather.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openweather.LAST_LAT_LON
import com.example.openweather.LAT
import com.example.openweather.LON
import com.example.openweather.core.Status
import com.example.openweather.databinding.FragmentResultsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ResultsFragment : Fragment() {
    private val mainViewModel by sharedViewModel<MainViewModel>()
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!
    private val weatherAdapter = WeatherAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservables()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setAdapter(){
        val linearLayoutManager = LinearLayoutManager(context)
        binding.weatherRecycler.apply {
            layoutManager = linearLayoutManager
            adapter = weatherAdapter
        }
    }

    private fun setObservables(){

        mainViewModel.completeWeather.observe(this){
            when(it.status){
                Status.SUCCESS -> {
//                    save successful search lat lon for use upon launch
                    val editor: SharedPreferences.Editor = requireContext().getSharedPreferences(
                        LAST_LAT_LON, Context.MODE_PRIVATE).edit()
                    editor.putString(LAT, it.data?.coordinates?.lat.toString())
                    editor.putString(LON, it.data?.coordinates?.lon.toString())
                    editor.apply()

                    binding.completeWeather = it.data
//                  send Weather class to recycler in case there is more than one in array
                    weatherAdapter.submitList(it.data?.weather)
                }
                Status.LOADING -> {
//                    set loading state
                }
                Status.ERROR -> {
//                    set error state and possiblu navigate back
                }
            }
        }
    }
}