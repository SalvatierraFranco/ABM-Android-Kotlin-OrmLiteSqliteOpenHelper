package com.example.proyectocv.EntitiesManagers

import android.content.Context
import com.example.proyectocv.Database.DbHelper
import com.example.proyectocv.Entities.Credencial
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

class CredencialManager private constructor(context: Context) {
    var dao: Dao<Credencial, Int>? = null

    companion object{
        private var instance: CredencialManager? = null
        @JvmStatic
        fun getInstance(context: Context): CredencialManager?{
            if(instance == null){
                instance = CredencialManager(context)
            }
            return instance
        }
    }

    init {
        val helper: OrmLiteSqliteOpenHelper = OpenHelperManager.getHelper(context, DbHelper::class.java)
        try {
            dao = helper.getDao(Credencial::class.java)
        }catch (e: SQLException){
            e.printStackTrace()
        }
    }

    @Throws(SQLException::class)
    fun agregarCredencial(unaCred: Credencial){
        dao!!.create(unaCred)
    }

    @Throws(SQLException::class)
    fun obtenerCredenciales(): List<Credencial>{
        return dao!!.queryForAll()
    }
}