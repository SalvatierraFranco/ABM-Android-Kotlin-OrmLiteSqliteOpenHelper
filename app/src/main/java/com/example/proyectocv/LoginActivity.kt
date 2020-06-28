package com.example.proyectocv

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.proyectocv.Entities.Credencial
import com.example.proyectocv.EntitiesManagers.CredencialManager
import java.sql.SQLException

class LoginActivity : AppCompatActivity() {

    lateinit var et_usuario: EditText
    lateinit var et_contrasenia: EditText
    lateinit var btn_login: Button
    lateinit var btn_register: Button
    lateinit var auxCredenciales: List<Credencial>
    //lateinit var cbo_recordar: CheckBox

    companion object{
        private const val KEY_CORREO = "KEY_CORREO"
        private const val KEY_PASS = "KEY_PASS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        setViews()
        //loginAutomatico()
        registro()
        login()
    }

    /*private fun loginAutomatico() {
        var prefs = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var email = prefs.getString(KEY_CORREO, "")
        var pass = prefs.getString(KEY_PASS, "")

        if(!email!!.isEmpty() || !pass!!.isEmpty()){
            et_usuario.setText(email)
            et_contrasenia.setText(pass)
            cbo_recordar.isChecked = true
        }
    }*/

    private fun login() {
        btn_login.setOnClickListener(){
            if(comprobarCampos()){
                Toast.makeText(applicationContext, "Todos los campos deben ser completados.", Toast.LENGTH_LONG).show()
            }else{
                var auxId: Int = 0
                var checkUser = false
                var checkPass = false
                auxCredenciales = getCredencialesDB()
                var unaCred = Credencial(getTexto(et_usuario), getTexto(et_contrasenia))
                for(i in auxCredenciales.indices){
                    if(unaCred.usuario!!.equals(auxCredenciales[i].usuario)){
                        checkUser = true
                        if(unaCred.contrasena!!.equals(auxCredenciales[i].contrasena)){
                            checkPass = true
                            auxId = i
                            break
                        }
                    }
                }

                if(checkUser && checkPass) {
                    goToHome(unaCred.usuario.toString(), unaCred.contrasena.toString(), auxCredenciales[auxId].id)
                    finish()
                }
                else if(checkUser && !checkPass)
                    Toast.makeText(applicationContext, "Contraseña inexistente", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(applicationContext, "Usuario inexistente", Toast.LENGTH_LONG).show()


            }
        }
    }

    private fun goToHome(usuario: String, contrasenia: String, auxId: Int) {

        /*val recordar = cbo_recordar.isChecked

        if(recordar){
            var prefs = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(KEY_CORREO, usuario)
            editor.putString(KEY_PASS, contrasenia)
            editor.apply()
        }*/

        var HomeIntent = Intent(applicationContext, MainActivity::class.java).apply {
            putExtra("usuario", usuario)
            putExtra("idUsuario", auxId)
        }
        startActivity(HomeIntent)
    }

    private fun registro() {

        btn_register.setOnClickListener(){
            if(comprobarCampos()){
                Toast.makeText(applicationContext, "Todos los campos deben ser completados.", Toast.LENGTH_LONG).show()
            }else{
                var unaCred = Credencial(getTexto(et_usuario), getTexto(et_contrasenia))
                var check: Boolean = false
                auxCredenciales = getCredencialesDB()

                for(i in auxCredenciales.indices){
                    if(unaCred.usuario!!.equals(auxCredenciales[i].usuario)){
                        check = true
                        break
                    }
                }

                if(check){
                    Toast.makeText(applicationContext, "Ya existe usuario.", Toast.LENGTH_LONG).show()
                }else{
                    try {
                        CredencialManager.getInstance(applicationContext)!!.agregarCredencial(unaCred)
                        Toast.makeText(applicationContext, "Registrado!!", Toast.LENGTH_LONG).show()
                    }catch (e: SQLException){
                        e.printStackTrace()
                    }
                }
            }
        }


    }

    private fun getCredencialesDB(): List<Credencial> {
        try {
            return CredencialManager.getInstance(applicationContext)!!.obtenerCredenciales()
        }catch (e: SQLException){
            e.printStackTrace()
            return ArrayList<Credencial>()
        }
    }

    private fun comprobarCampos(): Boolean {
        return getTexto(et_usuario).isEmpty() || getTexto(et_contrasenia).isEmpty()
    }

    private fun setViews() {
        et_usuario = findViewById(R.id.et_usuario)
        et_contrasenia = findViewById(R.id.et_contrasenia)
        btn_login = findViewById(R.id.btn_login)
        btn_register = findViewById(R.id.btn_register)
        //cbo_recordar = findViewById(R.id.cb_recordar)
    }

    private fun getTexto(et: EditText): String{
        return et.text.toString()
    }
}
