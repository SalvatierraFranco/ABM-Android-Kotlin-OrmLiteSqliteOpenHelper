package com.example.proyectocv.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.proyectocv.Entities.*
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import java.sql.SQLException

class DbHelper(context: Context): OrmLiteSqliteOpenHelper(context, NOMBRE_DB, null, VERSION_DB) {
    companion object{
        private const val NOMBRE_DB = "CV_DB"
        private const val VERSION_DB = 1
    }

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        try {
            TableUtils.createTable(connectionSource, Credencial::class.java)
            TableUtils.createTable(connectionSource, Datos::class.java)
            TableUtils.createTable(connectionSource, Experiencia::class.java)
            TableUtils.createTable(connectionSource, Cursos::class.java)
            TableUtils.createTable(connectionSource, Conocimiento::class.java)
        }catch (e: SQLException){
            e.printStackTrace()
        }
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        TODO("Not yet implemented")
    }
}