package com.demba.localareanavigator.screen.browseplaces

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.demba.localareanavigator.R
import com.demba.localareanavigator.network.models.Place

class PlaceAdapter(private val context: Context, private val places: List<Place>) : RecyclerView.Adapter<PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.element_card_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]
        holder.name.text = place.name
        Glide.with(context).load(place.imageURL).into(holder.image)
        holder.image
    }

    override fun getItemCount(): Int {
        return places.size
    }
}