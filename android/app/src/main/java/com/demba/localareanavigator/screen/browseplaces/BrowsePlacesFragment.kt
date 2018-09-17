package com.demba.localareanavigator.screen.browseplaces

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demba.localareanavigator.R
import com.demba.localareanavigator.network.models.Place
import kotlinx.android.synthetic.main.fragment_browse_places.*
import android.support.v7.widget.DefaultItemAnimator
import com.demba.localareanavigator.network.BackendService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BrowsePlacesFragment : Fragment() {

    private lateinit var fragmentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        fragmentView = inflater.inflate(R.layout.fragment_browse_places, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
                .baseUrl("http://172.19.75.229:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val service = retrofit.create(BackendService::class.java)

        service
                .getPlaces()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            val adapter = PlaceAdapter(context!!, it)
                            placesRecycler.layoutManager = LinearLayoutManager(activity)
                            placesRecycler.adapter = adapter
                            placesRecycler.itemAnimator = DefaultItemAnimator()
                            adapter.notifyDataSetChanged()
                        },
                        onError = {
                            it.printStackTrace()
                        }
                )
    }
}
