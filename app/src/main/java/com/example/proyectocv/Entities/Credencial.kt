package com.example.proyectocv.Entities

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "Credenciales")
class Credencial {
    @DatabaseField(generatedId = true)
    var id: Int = 0
    @DatabaseField
    var usuario: String? = null
    @DatabaseField
    var contrasena: String? = null

    constructor(){
    }

    constructor(Usuario: String, Contrasena: String){
        this.usuario = Usuario
        this.contrasena = Contrasena
    }

    constructor(Id: Int,Usuario: String, Contrasena: String){
        this.id = Id
        this.usuario = Usuario
        this.contrasena = Contrasena
    }
}