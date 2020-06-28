package com.example.proyectocv.EntitiesManagers

import android.content.Context
import com.example.proyectocv.Database.DbHelper
import com.example.proyectocv.Entities.Datos
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.stmt.UpdateBuilder
import java.sql.SQLException

class DatosManager private constructor(context: Context) {
    var dao: Dao<Datos, Int>? = null

    companion object{
        private var instance: DatosManager? = null
        @JvmStatic
        fun getInstance(context: Context): DatosManager?{
            if(instance == null){
                instance = DatosManager(context)
            }
            return instance
        }
    }

    init {
        val helper: OrmLiteSqliteOpenHelper = OpenHelperManager.getHelper(context, DbHelper::class.java)
        try {
            dao = helper.getDao(Datos::class.java)
        }catch (e: SQLException){
            e.printStackTrace()
        }
    }

    @Throws(SQLException::class)
    fun agregarDatos(Datos: Datos){
        dao!!.create(Datos)
    }

    @Throws(SQLException::class)
    fun getDatosById(idUsuario: Int): List<Datos>{
        return dao!!.queryForEq("idUsuario", idUsuario)
    }

    @Throws(SQLException::class)
    fun modificarDatos(auxDatos: Datos){
        var ub: UpdateBuilder<Datos, Int> = dao!!.updateBuilder()
        ub.where().eq("id", auxDatos.id)
        ub.updateColumnValue("nombre", auxDatos.nombre)
        ub.updateColumnValue("apellido", auxDatos.apellido)
        ub.updateColumnValue("edad", auxDatos.edad)
        ub.updateColumnValue("localidad", auxDatos.localidad)
        ub.updateColumnValue("nacionalidad", auxDatos.nacionalidad)
        ub.update()
    }
}