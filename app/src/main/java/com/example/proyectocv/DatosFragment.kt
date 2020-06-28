package com.example.proyectocv

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isInvisible
import com.example.proyectocv.Entities.Datos
import com.example.proyectocv.EntitiesManagers.DatosManager
import java.sql.SQLException

class DatosFragment : Fragment() {

    lateinit var et_nombre: EditText
    lateinit var et_apellido: EditText
    lateinit var et_edad: EditText
    lateinit var et_localidad: EditText
    lateinit var et_nacionalidad: EditText
    lateinit var btn_guardar: Button
    lateinit var btn_modificar: Button
    lateinit var btn_cerrarsesion: Button
    lateinit var auxDatos: List<Datos>
    var paraModificar: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v: View = inflater.inflate(R.layout.fragment_datos, container, false)

        var bundle: Bundle? = activity!!.intent.extras
        val idUsuario = bundle!!.getInt("idUsuario")

        setViews(v)
        verificarDatos(idUsuario)
        guardarDatos(idUsuario)
        modificarDatos()
        cerrarsesion()

        return v
    }

    private fun cerrarsesion() {
        btn_cerrarsesion.setOnClickListener{
            var logOffIntent = Intent(activity!!.applicationContext, LoginActivity::class.java)
            startActivity(logOffIntent)
            activity!!.finish()
        }
    }

    private fun modificarDatos() {
        btn_modificar.setOnClickListener{
            unblockViews()
            paraModificar = true
        }
    }

    private fun unblockViews() {
        et_nombre.isEnabled = true
        et_apellido.isEnabled = true
        et_edad.isEnabled = true
        et_localidad.isEnabled = true
        et_nacionalidad.isEnabled = true
        btn_guardar.isEnabled = true
        btn_modificar.isEnabled = false
    }

    private fun verificarDatos(idUsuario: Int) {
        auxDatos = DatosManager.getInstance(activity!!.applicationContext)!!.getDatosById(idUsuario)

        if(auxDatos.isNotEmpty()){
            et_nombre.setText(auxDatos[0].nombre)
            et_apellido.setText(auxDatos[0].apellido)
            et_edad.setText(auxDatos[0].edad)
            et_localidad.setText(auxDatos[0].localidad)
            et_nacionalidad.setText(auxDatos[0].nacionalidad)
            blockViews()
            btn_modificar.isEnabled = true
            btn_guardar.isEnabled = false
        }else{
            btn_modificar.isEnabled = false
        }
    }

    private fun guardarDatos(idUsuario: Int) {
        btn_guardar.setOnClickListener{
            if(paraModificar){
                if(comprobarCampos()){
                    Toast.makeText(activity!!.applicationContext, "Todos los campos deben ser completados.",
                        Toast.LENGTH_LONG).show()
                }else{
                    try {
                        val modDato = Datos(auxDatos[0].id, getDatos(et_nombre), getDatos(et_apellido),
                                            getDatos(et_edad), getDatos(et_localidad), getDatos(et_nacionalidad),
                                            idUsuario)
                        DatosManager.getInstance(activity!!.applicationContext)!!.modificarDatos(modDato)
                        paraModificar = false

                        blockViews()
                        btn_guardar.isEnabled = false
                        btn_modificar.isEnabled = true
                        Toast.makeText(activity!!.applicationContext, "Modificado!", Toast.LENGTH_LONG).show()
                    }catch (e: SQLException){
                        e.printStackTrace()
                    }
                }
            }else{
                if(comprobarCampos()){
                    Toast.makeText(activity!!.applicationContext, "Todos los campos deben ser completados.",
                        Toast.LENGTH_LONG).show()
                }else{
                    var auxdatos = Datos(getDatos(et_nombre), getDatos(et_apellido), getDatos(et_edad),
                        getDatos(et_localidad), getDatos(et_nacionalidad), idUsuario)

                    try {
                        DatosManager.getInstance(activity!!.applicationContext)!!.agregarDatos(auxdatos)
                        Toast.makeText(activity!!.applicationContext, "Datos guardados.",
                            Toast.LENGTH_LONG).show()
                        blockViews()
                        btn_guardar.isEnabled = false
                        btn_modificar.isEnabled = true
                    }catch (e: SQLException){
                        e.printStackTrace()
                    }
                }
            }


        }

    }

    private fun comprobarCampos(): Boolean {
        return et_nombre.text.isEmpty() ||
                et_apellido.text.isEmpty() ||
                et_edad.text.isEmpty() ||
                et_localidad.text.isEmpty() ||
                et_nacionalidad.text.isEmpty()
    }

    private fun setViews(v: View) {
        et_nombre = v.findViewById(R.id.et_nombre)
        et_apellido = v.findViewById(R.id.et_apellido)
        et_edad = v.findViewById(R.id.et_edad)
        et_localidad = v.findViewById(R.id.et_localidad)
        et_nacionalidad = v.findViewById(R.id.et_nacionalidad)
        btn_guardar = v.findViewById(R.id.btn_guardar)
        btn_modificar = v.findViewById(R.id.btn_modificar)
        btn_cerrarsesion = v.findViewById(R.id.btn_logoff)
    }

    private fun getDatos(et: EditText): String{
        return et.text.toString()
    }

    private fun blockViews(){
        et_nombre.isEnabled = false
        et_apellido.isEnabled = false
        et_edad.isEnabled = false
        et_localidad.isEnabled = false
        et_nacionalidad.isEnabled = false
    }

    class AgregarDatosAsyncTask(private var agregarDatosFrag: DatosFragment):
        AsyncTask<Datos, Unit, Boolean>(){
        override fun doInBackground(vararg params: Datos?): Boolean {
            var datoAgregado = false

            try {
                DatosManager.getInstance(agregarDatosFrag.activity!!.applicationContext)!!
                    .agregarDatos(params[0]!!)
                datoAgregado = true
            }catch (e: SQLException){
                e.printStackTrace()
            }

            return datoAgregado
        }
    }
}
