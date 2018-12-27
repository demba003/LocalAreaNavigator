package com.demba.localareanavigator.screen.browseplaces

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demba.localareanavigator.R
import kotlinx.android.synthetic.main.fragment_browse_places.*
import com.demba.localareanavigator.network.models.Place

class BrowsePlacesFragment : Fragment() {
    private lateinit var presenter: BrowsePlacesPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_browse_places, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = BrowsePlacesPresenter(this)
        presenter.downloadPlaces()
    }

    fun displayDownloadedPlaces(places: List<Place>) {
        val adapter = PlaceAdapter(context!!, places, presenter::onCardClick)
        placesRecycler.layoutManager = LinearLayoutManager(activity)
        placesRecycler.adapter = adapter
        adapter.notifyDataSetChanged()
        hideProgressBar()
    }

    fun showLoadingError() {
        loadingErrorText.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}
