package com.example.proyectocv.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.proyectocv.Entities.Conocimiento
import com.example.proyectocv.R
import org.w3c.dom.Text

class ConocimientosAdapter: BaseAdapter {
    var auxConocimientos: List<Conocimiento>? = null

    constructor(){}

    constructor(misConoc: List<Conocimiento>){
        this.auxConocimientos = misConoc
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val v: View = LayoutInflater.from(parent!!.context)
            .inflate(R.layout.item_conocimiento, parent, false)

        val unConoc = auxConocimientos!![position]

        var tv_nombreConoc = v.findViewById<TextView>(R.id.tv_nombreConoc)
        var tv_nivel = v.findViewById<TextView>(R.id.tv_nivel)

        tv_nombreConoc.setText(unConoc.nombre)
        tv_nivel.setText(unConoc.nivel)

        return v
    }

    override fun getItem(position: Int): Any {
        return this.auxConocimientos!!.get(position)
    }

    override fun getItemId(position: Int): Long {
        return this.auxConocimientos!![position].id.toLong()
    }

    override fun getCount(): Int {
        return this.auxConocimientos!!.size
    }
}