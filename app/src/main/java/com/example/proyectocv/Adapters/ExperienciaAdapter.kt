package com.example.proyectocv.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.proyectocv.Entities.Experiencia
import com.example.proyectocv.R

class ExperienciaAdapter : BaseAdapter {
    var auxExperiencias: List<Experiencia>? = null

    constructor(){}

    constructor(AuxExpes: List<Experiencia>){
        this.auxExperiencias = AuxExpes
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v = LayoutInflater.from(parent!!.context)
            .inflate(R.layout.item_experiencia, parent, false)
        val auxExpe = auxExperiencias!![position]

        val tv_nombreEmpresa = v.findViewById<TextView>(R.id.tv_nombreEmpresa)
        val tv_posicion = v.findViewById<TextView>(R.id.tv_posicion)
        val tv_anios = v.findViewById<TextView>(R.id.tv_aniosTrabajados)

        tv_nombreEmpresa.setText(auxExpe.empresa)
        tv_posicion.setText(auxExpe.puesto)
        tv_anios.setText(auxExpe.anios)

        return v
    }

    override fun getItem(position: Int): Any {
        return this.auxExperiencias!![position]
    }

    override fun getItemId(position: Int): Long {
        return this.auxExperiencias!![position].id.toLong()
    }

    override fun getCount(): Int {
        return this.auxExperiencias!!.size
    }
}