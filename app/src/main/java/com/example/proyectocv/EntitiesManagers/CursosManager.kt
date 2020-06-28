package com.example.proyectocv.EntitiesManagers

import android.content.Context
import com.example.proyectocv.Database.DbHelper
import com.example.proyectocv.Entities.Cursos
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

class CursosManager private constructor(context: Context) {
    var dao: Dao<Cursos, Int>? = null

    companion object{
        private var instance: CursosManager? = null
        @JvmStatic
        fun getInstance(context: Context): CursosManager?{
            if(instance == null){
                instance = CursosManager(context)
            }
            return instance
        }
    }

    init {
        var helper: OrmLiteSqliteOpenHelper = OpenHelperManager.getHelper(context, DbHelper::class.java)
        try {
            dao = helper.getDao(Cursos::class.java)
        }catch (e: SQLException){
            e.printStackTrace()
        }
    }

    @Throws(SQLException::class)
    fun agregarCurso(unCurso: Cursos){
        dao!!.create(unCurso)
    }

    @Throws(SQLException::class)
    fun traerCursosPorId(idUsuario: Int): MutableList<Cursos>{
        return dao!!.queryForEq("idUsuario", idUsuario)
    }

    @Throws(SQLException::class)
    fun eliminarCurso(idUsuario: Int){
        dao!!.deleteById(idUsuario)
    }
}