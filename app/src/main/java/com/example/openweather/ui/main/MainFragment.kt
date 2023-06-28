package com.example.openweather.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openweather.LAST_LAT_LON
import com.example.openweather.R
import com.example.openweather.core.Status
import com.example.openweather.databinding.FragmentMainBinding
import com.example.openweather.domain.PlaceGeolocation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MainFragment : Fragment(), OnPlaceGeolocationClickListener {
    private val mainViewModel by sharedViewModel<MainViewModel>()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val placeGeolocationAdapter = PlaceGeolocationAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservables()
        setListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setAdapter(){
        val linearLayoutManager = LinearLayoutManager(context)
        binding.placesRecycler.apply {
            layoutManager = linearLayoutManager
            adapter = placeGeolocationAdapter
        }
    }

    private fun setListeners(){
        binding.userInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                search for geo coordinates for places marching user input
                fetchInputCoordinates(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.userInput.setOnEditorActionListener { input, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                search when user click search on keyboard in case place is not on list
               getWeatherByPlace(input.text.toString())
            }
            true
        }
    }

    private fun setObservables(){
        mainViewModel.placeGeoLocations.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.placesRecycler.visibility = View.VISIBLE
                    placeGeolocationAdapter.submitList(it.data)
                }
                Status.LOADING -> {
                    binding.placesRecycler.visibility = View.GONE
//                    hide results on loading
                }
                Status.ERROR ->{
                    binding.placesRecycler.visibility = View.GONE
//                    show error
                }
            }
        }

    }

    private fun getWeatherByPlace(userInput: String){
        binding.placesRecycler.visibility = View.GONE
        mainViewModel.getWeatherByPlace(userInput)
        binding.userInput.setText("")
        findNavController().navigate(R.id.action_mainFragment_to_ResultsFragmet)
    }

    private fun fetchInputCoordinates(userInput: String){
        mainViewModel.getLocationCoordinates(userInput)
    }

    override fun placeClicked(placeGeolocation: PlaceGeolocation) {
        binding.userInput.setText(placeGeolocation.fullLocation)
        binding.placesRecycler.visibility = View.GONE
        mainViewModel.getWeatherByCoordinates(placeGeolocation.lat.toString(), placeGeolocation.lon.toString())
        binding.userInput.setText("")
        findNavController().navigate(R.id.action_mainFragment_to_ResultsFragmet)
    }

}