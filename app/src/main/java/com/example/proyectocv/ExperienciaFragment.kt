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
import com.example.proyectocv.Adapters.ExperienciaAdapter
import com.example.proyectocv.Entities.Experiencia
import com.example.proyectocv.EntitiesManagers.ExperienciaManager
import java.sql.SQLException

class ExperienciaFragment : Fragment() {

    lateinit var et_empresa: EditText
    lateinit var et_puesto: EditText
    lateinit var et_anios: EditText
    lateinit var btn_guardar: Button
    lateinit var lv_Experiencias: ListView
    lateinit var auxExperiencias: MutableList<Experiencia>
    lateinit var adapter: ExperienciaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v: View = inflater.inflate(R.layout.fragment_experiencia, container, false)

        // Creamos bundle y traemos el idUsuario
        var bundle: Bundle? = activity!!.intent.extras
        var idUsuario: Int = bundle!!.getInt("idUsuario")

        // Creamos funcionalidades para el fragment
        setAdapter(v, idUsuario)
        setListView(v)
        setViews(v)
        guardar(idUsuario)

        return v
    }

    private fun setListView(v: View) {
        lv_Experiencias.setOnItemClickListener { parent, view, position, id ->
            try {
                var auxExp = auxExperiencias.get(position)
                ExperienciaManager.getInstance(activity!!.applicationContext)!!.eliminarExp(auxExp.id)
                refreshListView(auxExp, "Eliminar")
                Toast.makeText(activity!!.applicationContext, "Eliminado!", Toast.LENGTH_LONG).show()
            }catch (e: SQLException){
                e.printStackTrace()
            }
        }
    }

    private fun setAdapter(v: View, idUsuario: Int) {
        lv_Experiencias = v.findViewById(R.id.lv_experiencia)
        adapter = ExperienciaAdapter(traerExperienciasPorID(idUsuario))
        lv_Experiencias.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun traerExperienciasPorID(idUsuario: Int): List<Experiencia> {
        try {
            auxExperiencias = ExperienciaManager.getInstance(activity!!.applicationContext)!!.traerExperienciasPorId(idUsuario)
        }catch (e:SQLException){
            e.printStackTrace()
            auxExperiencias = ArrayList()
        }

        return auxExperiencias
    }

    private fun guardar(idUsuario: Int) {
        btn_guardar.setOnClickListener{
            if(comprobarCampos()){
                Toast.makeText(activity!!.applicationContext, "Todos los campos deben estar completos.",
                    Toast.LENGTH_LONG).show()
            }else{
                var unaExp = Experiencia(getDato(et_empresa), getDato(et_puesto), getDato(et_anios), idUsuario)

                AgregarExpAsyncTask(this).execute(unaExp)
                refreshListView(unaExp, "Agregar")
                limpiarCampos()
                Toast.makeText(activity!!.applicationContext, "Guardado!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun refreshListView(unaExp: Experiencia, queHacer: String) {

        if(queHacer.equals("Agregar")){
            auxExperiencias.add(unaExp)
            adapter = ExperienciaAdapter(auxExperiencias)
            lv_Experiencias.adapter = adapter
        }else if (queHacer.equals("Eliminar")){
            auxExperiencias.remove(unaExp)
            adapter = ExperienciaAdapter(auxExperiencias)
            lv_Experiencias.adapter = adapter
        }
    }

    private fun limpiarCampos() {
        et_empresa.setText("")
        et_puesto.setText("")
        et_anios.setText("")
    }

    private fun comprobarCampos(): Boolean {
        return getDato(et_empresa).isEmpty() ||
                getDato(et_puesto).isEmpty() ||
                getDato(et_anios).isEmpty()
    }

    private fun setViews(v: View) {
        et_empresa = v.findViewById(R.id.et_nombreEmpresa)
        et_puesto = v.findViewById(R.id.et_rol)
        et_anios = v.findViewById(R.id.et_anios)
        btn_guardar = v.findViewById(R.id.btn_guardarExp)
    }

    private fun getDato(et: EditText): String{
        return et.text.toString()
    }

    class AgregarExpAsyncTask(private var agregarExpFragment: ExperienciaFragment):
        AsyncTask<Experiencia, Unit, Boolean>(){

        override fun doInBackground(vararg params: Experiencia?): Boolean {
            var expAgregada = false

            try {
                ExperienciaManager.getInstance(agregarExpFragment.activity!!.applicationContext)!!.
                agregarExperiencia(params[0]!!)
                expAgregada = true
            }catch (e: SQLException){
                e.printStackTrace()
            }

            return expAgregada
        }
    }
}
