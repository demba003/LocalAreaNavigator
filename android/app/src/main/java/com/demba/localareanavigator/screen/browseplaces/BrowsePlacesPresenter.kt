package com.demba.localareanavigator.screen.browseplaces

import com.demba.localareanavigator.R
import com.demba.localareanavigator.network.models.Place
import com.demba.localareanavigator.screen.map.NavigatorFragment
import com.demba.localareanavigator.utils.NetworkUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class BrowsePlacesPresenter(val view: BrowsePlacesFragment) {
    fun downloadPlaces() {
        NetworkUtils
                .getBackendService()
                .getPlaces()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            view.displayDownloadedPlaces(it)
                            view.hideProgressBar()
                        },
                        onError = {
                            it.printStackTrace()
                            view.showLoadingError()
                            view.hideProgressBar()
                        }
                )
    }

    fun onCardClick(place: Place) {
        view
                .fragmentManager!!
                .beginTransaction()
                .replace(R.id.content_frame, NavigatorFragment.getFragment(place))
                .commit()
    }
}