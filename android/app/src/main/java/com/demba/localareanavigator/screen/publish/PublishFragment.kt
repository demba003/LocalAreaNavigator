package com.demba.localareanavigator.screen.publish

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demba.localareanavigator.R
import kotlinx.android.synthetic.main.fragment_publish.*

class PublishFragment : Fragment() {
    companion object {
        private const val CHOOSE_FILE_CODE = 123
    }

    private var gpxData: String? = null

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
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PublishFragment.CHOOSE_FILE_CODE && resultCode == Activity.RESULT_OK) {
            data?.let { intent ->
                gpxData = activity?.contentResolver?.openInputStream(intent.data)?.reader()?.readText()
                activity?.contentResolver?.query(intent.data, null, null, null, null)
            } ?.use { cursor ->
                cursor.moveToFirst()
                fileName.setText(cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)))
            }
        }
    }
}
