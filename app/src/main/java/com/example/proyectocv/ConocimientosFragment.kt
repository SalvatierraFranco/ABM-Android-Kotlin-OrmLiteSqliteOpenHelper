package com.example.proyectocv

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.proyectocv.Adapters.ConocimientosAdapter
import com.example.proyectocv.Adapters.ExperienciaAdapter
import com.example.proyectocv.Entities.Conocimiento
import com.example.proyectocv.EntitiesManagers.ConocimientoManager
import kotlinx.android.synthetic.*
import java.sql.SQLException
import java.util.*
import kotlin.collections.ArrayList

class ConocimientosFragment : Fragment() {

    lateinit var et_nombre: EditText
    lateinit var sp_nivel: Spinner
    lateinit var btn_guardar: Button
    lateinit var lv_conoc: ListView
    lateinit var auxConocimientos: MutableList<Conocimiento>
    lateinit var adapter: ConocimientosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v: View = inflater.inflate(R.layout.fragment_conocimientos, container, false)

        val bundle: Bundle? = activity!!.intent.extras
        val idUsuario = bundle!!.getInt("idUsuario")

        setAdapter(v, idUsuario)
        setListView(v)
        setViews(v)
        guardar(idUsuario)

        return v
    }

    private fun setListView(v: View) {
        lv_conoc.setOnItemClickListener { parent, view, position, id ->
            try {
                var auxConoc = auxConocimientos.get(position)
                ConocimientoManager.getInstance(activity!!.applicationContext)!!.eliminarConoc(auxConoc.id)
                refreshListView(auxConoc, "Eliminar")
            }catch (e: SQLException){
                e.printStackTrace()
            }
        }
    }

    private fun setAdapter(v: View, idUsuario: Int) {
        lv_conoc = v.findViewById(R.id.lv_conocimientos)
        adapter = ConocimientosAdapter(obtenerConocPorId(idUsuario))
        lv_conoc.adapter = adapter
    }

    private fun obtenerConocPorId(idUsuario: Int): List<Conocimiento> {
        try {
            auxConocimientos = ConocimientoManager.getInstance(activity!!.applicationContext)!!
                .traerConocimientosPorId(idUsuario)
        }catch (e: SQLException){
            e.printStackTrace()
            auxConocimientos = ArrayList()
        }

        return auxConocimientos
    }

    private fun guardar(idUsuario: Int) {
        btn_guardar.setOnClickListener{
            if(comprobarCampos()){
                Toast.makeText(activity!!.applicationContext, "Todos los campos deben estar completos.",
                    Toast.LENGTH_LONG).show()
            }else{
                var auxConoc = Conocimiento(getDato(et_nombre), sp_nivel.selectedItem.toString(), idUsuario)

                AgregarConocAsyncTask(this).execute(auxConoc)
                refreshListView(auxConoc, "Agregar")
                limpiarCampos()
                Toast.makeText(activity!!.applicationContext, "Guardado!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun limpiarCampos() {
        et_nombre.setText("")
        sp_nivel.setSelection(0)
    }

    private fun refreshListView(auxConoc: Conocimiento, queHacer: String) {

        if(queHacer.equals("Agregar")){
            auxConocimientos.add(auxConoc)
            adapter = ConocimientosAdapter(auxConocimientos)
            lv_conoc.adapter = adapter
        }else if (queHacer.equals("Eliminar")){
            auxConocimientos.remove(auxConoc)
            adapter = ConocimientosAdapter(auxConocimientos)
            lv_conoc.adapter = adapter
        }
    }

    private fun comprobarCampos(): Boolean {
        return getDato(et_nombre).isEmpty() || sp_nivel.selectedItem.toString().isEmpty()
    }

    private fun setViews(v: View) {
        et_nombre = v.findViewById(R.id.et_nombreConoc)
        btn_guardar = v.findViewById(R.id.btn_guardarConoc)
        sp_nivel = v.findViewById(R.id.spinner_nivel)

        val niveles = resources.getStringArray(R.array.Niveles)
        val adapter = ArrayAdapter(activity!!.applicationContext, android.R.layout.simple_spinner_item, niveles)
        sp_nivel.adapter = adapter
    }

    private fun getDato(et: EditText): String{
        return et.text.toString()
    }

    class AgregarConocAsyncTask(var agregarDatoFrag: ConocimientosFragment):
        AsyncTask<Conocimiento, Unit, Boolean>(){
        override fun doInBackground(vararg params: Conocimiento?): Boolean {
            var agregarDato = false

            try {
                ConocimientoManager.getInstance(agregarDatoFrag.activity!!.applicationContext)!!
                    .agregarConocimiento(params[0]!!)
                agregarDato = true
            }catch (e: SQLException){
                e.printStackTrace()
            }

            return agregarDato
        }
    }

}
