package com.example.proyectocv.Entities

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "Experiencias")
class Experiencia {
    @DatabaseField(generatedId = true)
    var id: Int = 0
    @DatabaseField
    var empresa: String? = null
    @DatabaseField
    var puesto: String? = null
    @DatabaseField
    var anios: String? = null
    @DatabaseField
    var idUsuario: Int? = null

    constructor(){}

    constructor(Empresa: String, Puesto: String, Anios: String, IdUsuario: Int){
        this.empresa = Empresa
        this.puesto = Puesto
        this.anios = Anios
        this.idUsuario = IdUsuario
    }

    constructor(Id: Int, Empresa: String, Puesto: String, Anios: String, IdUsuario: Int){
        this.id = Id
        this.empresa = Empresa
        this.puesto = Puesto
        this.anios = Anios
        this.idUsuario = IdUsuario
    }
}