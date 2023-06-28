package com.example.openweather.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.openweather.databinding.ItemCompleteWeatherBinding
import com.example.openweather.domain.Weather

class WeatherAdapter(): ListAdapter<Weather, ViewHolder>(WeatherDiffCallback()) {

    class WeatherDiffCallback : DiffUtil.ItemCallback<Weather>() {
        override fun areItemsTheSame(
            oldItem: Weather,
            newItem: Weather
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Weather,
            newItem: Weather
        ): Boolean {
            return oldItem == newItem
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCompleteWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        (holder as WeatherViewHolder).bind(item)
    }

    class WeatherViewHolder(private val binding: ItemCompleteWeatherBinding): ViewHolder(binding.root){
        fun bind(
            item: Weather,
        ){
            binding.weather = item
            binding.executePendingBindings()
            Glide.with(itemView.context)
                .load(item.iconUrl)
                .into(binding.imageViewIcon)
        }

    }

}