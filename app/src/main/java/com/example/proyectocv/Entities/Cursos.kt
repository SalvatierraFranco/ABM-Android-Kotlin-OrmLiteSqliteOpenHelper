package com.example.proyectocv.Entities

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "Cursos")
class Cursos {
    @DatabaseField(generatedId = true)
    var id: Int = 0
    @DatabaseField
    var nombre: String? = null
    @DatabaseField
    var establecimiento: String? = null
    @DatabaseField
    var idUsuario: Int? = null

    constructor(){
    }

    constructor(Nombre: String, Establecimiento: String, IdUsuario: Int){
        this.nombre = Nombre
        this.establecimiento = Establecimiento
        this.idUsuario = IdUsuario
    }

    constructor(Id: Int, Nombre: String, Establecimiento: String, IdUsuario: Int){
        this.id = Id
        this.nombre = Nombre
        this.establecimiento = Establecimiento
        this.idUsuario = IdUsuario
    }
}