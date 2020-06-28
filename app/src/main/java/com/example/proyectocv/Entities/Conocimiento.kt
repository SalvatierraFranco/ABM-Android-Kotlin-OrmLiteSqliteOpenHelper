package com.example.proyectocv.Entities

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "Conocimientos")
class Conocimiento {
    @DatabaseField(generatedId = true)
    var id: Int = 0
    @DatabaseField
    var nombre: String? = null
    @DatabaseField
    var nivel: String? = null
    @DatabaseField
    var idUsuario: Int? = null

    constructor(){}

    constructor(Nombre: String, Nivel: String, IdUsuario:Int){
        this.nombre = Nombre
        this.nivel = Nivel
        this.idUsuario = IdUsuario
    }

    constructor(Id: Int, Nombre: String, Nivel: String, IdUsuario:Int){
        this.id = Id
        this.nombre = Nombre
        this.nivel = Nivel
        this.idUsuario = IdUsuario
    }

}