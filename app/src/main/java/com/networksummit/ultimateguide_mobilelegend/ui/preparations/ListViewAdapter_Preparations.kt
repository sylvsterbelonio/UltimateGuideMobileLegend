package com.networksummit.ultimateguide_mobilelegend.ui.preparations

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.networksummit.ultimateguide_mobilelegend.R

class ListViewAdapter_Preparations(private val context: Activity,private val imgid: Array<Int>, private val title: Array<String>)
    : ArrayAdapter<String>(context, R.layout.fragment_preparations_listview, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.fragment_preparations_listview, null, true)

        val titleText = rowView.findViewById(R.id.textViewPreparationName) as TextView
        val imageView = rowView.findViewById(R.id.imageViewPreparationIcon) as ImageView

        titleText.text = title[position]
        imageView.setImageResource(imgid[position])

        return rowView
    }
}