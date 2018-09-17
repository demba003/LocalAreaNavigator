package com.demba.localareanavigator.screen.browseplaces

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import kotlinx.android.synthetic.main.element_card_place.view.*

class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.name
    val image: ImageView = itemView.image
}