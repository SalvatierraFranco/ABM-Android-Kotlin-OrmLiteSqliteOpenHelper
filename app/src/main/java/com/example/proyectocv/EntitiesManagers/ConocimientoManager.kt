package com.example.proyectocv.EntitiesManagers

import android.content.Context
import com.example.proyectocv.Database.DbHelper
import com.example.proyectocv.Entities.Conocimiento
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

class ConocimientoManager private constructor(context: Context) {
    var dao: Dao<Conocimiento, Int>? = null

    companion object{
        private var instance: ConocimientoManager? = null
        @JvmStatic
        fun getInstance(context: Context): ConocimientoManager?{
            if(instance == null){
                instance = ConocimientoManager(context)
            }
            return instance
        }
    }

    init {
        var helper: OrmLiteSqliteOpenHelper = OpenHelperManager.getHelper(context, DbHelper::class.java)
        try {
            dao = helper.getDao(Conocimiento::class.java)
        }catch (e: SQLException){
            e.printStackTrace()
        }
    }

    @Throws(SQLException::class)
    fun agregarConocimiento(unConoc: Conocimiento){
        dao!!.create(unConoc)
    }

    @Throws(SQLException::class)
    fun traerConocimientosPorId(idUsuario: Int): MutableList<Conocimiento>{
        return dao!!.queryForEq("idUsuario", idUsuario)
    }

    @Throws(SQLException::class)
    fun eliminarConoc(id: Int){
        dao!!.deleteById(id)
    }
}