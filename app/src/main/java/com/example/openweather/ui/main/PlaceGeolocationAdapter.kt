package com.example.openweather.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.openweather.databinding.ItemGeolocationBinding
import com.example.openweather.domain.PlaceGeolocation

class PlaceGeolocationAdapter(
    private var onPlaceGeolocationClickListener: OnPlaceGeolocationClickListener
): ListAdapter<PlaceGeolocation, RecyclerView.ViewHolder>(PlaceGeoLocationDiffCallback()) {

    class PlaceGeoLocationDiffCallback : DiffUtil.ItemCallback<PlaceGeolocation>() {
        override fun areItemsTheSame(
            oldItem: PlaceGeolocation,
            newItem: PlaceGeolocation
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: PlaceGeolocation,
            newItem: PlaceGeolocation
        ): Boolean {
            return oldItem == newItem
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGeolocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceGeolocationViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item = currentList[position]
        (holder as PlaceGeolocationViewHolder).bind(item, onPlaceGeolocationClickListener)
    }

    class PlaceGeolocationViewHolder(private val binding: ItemGeolocationBinding): ViewHolder(binding.root){
        fun bind(
            item: PlaceGeolocation,
            onPlaceGeolocationClickListener: OnPlaceGeolocationClickListener
        ){
            binding.placeGeolocation = item
            binding.placeGeoLocationItem.setOnClickListener {
               onPlaceGeolocationClickListener.placeClicked(item)
            }
            binding.executePendingBindings()

        }

    }

}


interface OnPlaceGeolocationClickListener {
    fun placeClicked(placeGeolocation: PlaceGeolocation)
}