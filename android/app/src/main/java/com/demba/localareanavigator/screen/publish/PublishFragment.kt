package com.demba.localareanavigator.screen.publish

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.demba.localareanavigator.R
import com.demba.localareanavigator.network.models.Place
import com.demba.localareanavigator.utils.NetworkUtils
import com.demba.navigator.Navigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_publish.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PublishFragment : Fragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    companion object {
        private const val CHOOSE_FILE_CODE = 123
    }

    private lateinit var gpxData: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_publish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fileChoose.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/xml"
            }

            startActivityForResult(intent, CHOOSE_FILE_CODE)
        }

        publish.setOnClickListener {
            publish.isEnabled = false
            NetworkUtils.getBackendService()
                    .putPlace(Place(
                            placeName.text.toString(),
                            Navigator.getGeoJsonFromGpx(gpxData),
                            "http://www.mapa-pro.pl/fileadmin/_processed_/csm_carte_138e5fc9bc.jpg"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onComplete = {
                                publish.isEnabled = true
                                Toast.makeText(context, getString(R.string.place_sent), Toast.LENGTH_LONG).show()
                            },
                            onError = { exception ->
                                exception.printStackTrace()
                                publish.isEnabled = true
                                Toast.makeText(context, getString(R.string.sending_error), Toast.LENGTH_LONG).show()
                            }
                    )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PublishFragment.CHOOSE_FILE_CODE && resultCode == Activity.RESULT_OK) {
            data?.let { intent ->
                launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        activity?.let { activity ->
                            gpxData = activity.contentResolver.openInputStream(intent.data).reader().readText()
                        }
                    }
                    publish.isEnabled = true
                }

                activity?.contentResolver
                        ?.query(intent.data, null, null, null, null)
            }?.use { cursor ->
                cursor.moveToFirst()
                fileName.setText(cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)))
            }
        }
    }
}
