package com.example.proyectocv.EntitiesManagers

import android.content.Context
import com.example.proyectocv.Database.DbHelper
import com.example.proyectocv.Entities.Experiencia
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

class ExperienciaManager private constructor(context: Context) {
    var dao: Dao<Experiencia, Int>? = null
    companion object{
        private var instance: ExperienciaManager? = null
        @JvmStatic
        fun getInstance(context: Context): ExperienciaManager?{
            if(instance == null){
                instance = ExperienciaManager(context)
            }
            return instance
        }
    }

    init {
        var helper: OrmLiteSqliteOpenHelper = OpenHelperManager.getHelper(context, DbHelper::class.java)
        try {
            dao = helper.getDao(Experiencia::class.java)
        }catch (e: SQLException){
            e.printStackTrace()
        }
    }

    @Throws(SQLException::class)
    fun agregarExperiencia(Exp: Experiencia){
        dao!!.create(Exp)
    }

    @Throws(SQLException::class)
    fun traerExperiencias(): List<Experiencia>{
        return dao!!.queryForAll()
    }

    @Throws(SQLException::class)
    fun traerExperienciasPorId(idUsuario: Int): MutableList<Experiencia>{
        return dao!!.queryForEq("idUsuario", idUsuario)
    }

    @Throws(SQLException::class)
    fun eliminarExp(id: Int){
        dao!!.deleteById(id)
    }
}