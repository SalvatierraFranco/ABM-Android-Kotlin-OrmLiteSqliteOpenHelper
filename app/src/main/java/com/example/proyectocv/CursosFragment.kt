package com.example.proyectocv

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.proyectocv.Adapters.CursosAdapter
import com.example.proyectocv.Entities.Cursos
import com.example.proyectocv.EntitiesManagers.CursosManager
import java.sql.SQLException

class CursosFragment : Fragment() {

    lateinit var et_nombreCurso: EditText
    lateinit var et_establecimiento: EditText
    lateinit var btn_guardar: Button
    lateinit var lv_cursos: ListView
    //lateinit var auxCursos: List<Cursos>
    lateinit var auxCursos: MutableList<Cursos>
    lateinit var adapter: CursosAdapter
    //lateinit var sw_cursos: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v: View = inflater.inflate(R.layout.fragment_cursos, container, false)

        val bundle: Bundle? = activity!!.intent.extras
        val idUsuario = bundle!!.getInt("idUsuario")

        //sw_cursos = v.findViewById(R.id.swiper_cursos)

        /*sw_cursos.setOnRefreshListener{
            setAdapter(v, idUsuario)

            sw_cursos.isRefreshing = false
        }*/

        setAdapter(v, idUsuario)
        setViews(v)
        setListView(v, idUsuario)
        guardarCurso(v, idUsuario)

        return v
    }

    private fun setListView(v: View, idUsuario: Int) {
        lv_cursos.setOnItemClickListener { parent, view, position, id ->
            try {
                val auxCurso = auxCursos.get(position)
                CursosManager.getInstance(activity!!.applicationContext)!!.eliminarCurso(auxCurso.id!!)
                refreshListView(auxCurso, "Eliminar")
                Toast.makeText(activity!!.applicationContext, "Eliminado!", Toast.LENGTH_LONG).show()
            }catch (e: SQLException){
                e.printStackTrace()
            }
        }
    }

    private fun setAdapter(v: View, idUsuario: Int) {
        lv_cursos = v.findViewById(R.id.lv_cursos)
        adapter = CursosAdapter(obtenerCursosPorId(idUsuario))
        lv_cursos.adapter = adapter
    }

    private fun obtenerCursosPorId(idUsuario: Int): List<Cursos> {
        try {
            auxCursos = CursosManager.getInstance(activity!!.applicationContext)!!.traerCursosPorId(idUsuario)
        }catch (e: SQLException){
            e.printStackTrace()
            auxCursos = ArrayList()
        }

        return auxCursos
    }

    private fun guardarCurso(v: View, idUsuario: Int) {
        btn_guardar.setOnClickListener{
            if(comprobarCampos()){
                Toast.makeText(activity!!.applicationContext, "Todos los campos deben estar completos",
                    Toast.LENGTH_LONG).show()
            }else{
                var auxCurso = Cursos(getDato(et_nombreCurso), getDato(et_establecimiento), idUsuario)

                AgregarCursoAsyncTask(this).execute(auxCurso)
                refreshListView(auxCurso, "Agregar")
                limpiarCampos()
                Toast.makeText(activity!!.applicationContext, "Guardado!", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun limpiarCampos() {
        et_nombreCurso.setText("")
        et_establecimiento.setText("")
    }

    private fun refreshListView(auxCurso: Cursos, queHacer: String) {
        if(queHacer.equals("Agregar")){
            auxCursos.add(auxCurso)
            adapter = CursosAdapter(auxCursos)
            lv_cursos.adapter = adapter
        }else if(queHacer.equals("Eliminar")){
            auxCursos.remove(auxCurso)
            adapter = CursosAdapter(auxCursos)
            lv_cursos.adapter = adapter
        }

    }

    private fun comprobarCampos(): Boolean {
        return getDato(et_nombreCurso).isEmpty() || getDato(et_establecimiento).isEmpty()
    }

    private fun setViews(v: View) {
        et_nombreCurso = v.findViewById(R.id.et_nombreCurso)
        et_establecimiento = v.findViewById(R.id.et_nombreInstitucion)
        btn_guardar = v.findViewById(R.id.btn_guardarCurso)
    }

    private fun getDato(et: EditText): String{
        return et.text.toString()
    }

    class AgregarCursoAsyncTask(var agregarCursoFrag: CursosFragment):
        AsyncTask<Cursos, Unit, Boolean>(){

        var agregarCurso = false

        override fun doInBackground(vararg params: Cursos?): Boolean {
            try {
                CursosManager.getInstance(agregarCursoFrag.activity!!.applicationContext)!!
                    .agregarCurso(params[0]!!)
                agregarCurso = true
            }catch (e: SQLException){
                e.printStackTrace()
            }

            return agregarCurso
        }
    }

}
