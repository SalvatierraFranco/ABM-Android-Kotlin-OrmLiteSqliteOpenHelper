package com.example.proyectocv.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.proyectocv.Entities.Cursos
import com.example.proyectocv.R

class CursosAdapter: BaseAdapter {
    var auxCursos: List<Cursos>? = null

    constructor(){}

    constructor(misCursos: List<Cursos>){
        this.auxCursos = misCursos
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val v = LayoutInflater.from(parent!!.context)
            .inflate(R.layout.item_curso, parent , false)

        var unCurso = this.auxCursos!![position]

        var tv_nombreCurso = v.findViewById<TextView>(R.id.tv_nombreCurso)
        var tv_nombreInstitucion = v.findViewById<TextView>(R.id.tv_nombreInstitucion)

        tv_nombreCurso.setText(unCurso.nombre)
        tv_nombreInstitucion.setText(unCurso.establecimiento)

        return v
    }

    override fun getItem(position: Int): Any {
        return this.auxCursos!!.get(position)
    }

    override fun getItemId(position: Int): Long {
        return this.auxCursos!![position].id.toLong()
    }

    override fun getCount(): Int {
        return this.auxCursos!!.size
    }
}