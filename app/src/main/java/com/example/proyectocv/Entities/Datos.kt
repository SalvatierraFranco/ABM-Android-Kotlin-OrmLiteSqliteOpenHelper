package com.example.proyectocv.Entities

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "Datos")
class Datos {
    @DatabaseField(generatedId = true)
    var id: Int = 0
    @DatabaseField
    var nombre: String? = null
    @DatabaseField
    var apellido: String? = null
    @DatabaseField
    var edad: String? = null
    @DatabaseField
    var localidad: String? = null
    @DatabaseField
    var nacionalidad: String? = null
    @DatabaseField
    var idUsuario: Int = 0

    constructor(){}

    constructor(Nombre: String, Apellido: String, Edad: String, Localidad: String,
                Nacionalidad: String, IdUsuario: Int){
        this.nombre = Nombre
        this.apellido = Apellido
        this.edad = Edad
        this.localidad = Localidad
        this.nacionalidad = Nacionalidad
        this.idUsuario = IdUsuario
    }

    constructor(Id: Int, Nombre: String, Apellido: String, Edad: String, Localidad: String,
                Nacionalidad: String, IdUsuario: Int){
        this.id = Id
        this.nombre = Nombre
        this.apellido = Apellido
        this.edad = Edad
        this.localidad = Localidad
        this.nacionalidad = Nacionalidad
        this.idUsuario = IdUsuario
    }
}