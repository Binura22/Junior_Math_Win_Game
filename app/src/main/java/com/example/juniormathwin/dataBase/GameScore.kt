package com.example.juniormathwin.dataBase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GameScore(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY, $COL_SCORE INTEGER, $COL_LEVEL TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addScore(score: Int, level: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_SCORE, score)
            put(COL_LEVEL, level)
        }
        db.insert(TABLE_NAME, null, values)
    }

    fun getScores(level: String): List<Int> {
        val scores = mutableListOf<Int>()
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery(
            "SELECT $COL_SCORE FROM $TABLE_NAME WHERE $COL_LEVEL = ? ORDER BY $COL_SCORE DESC LIMIT 5",
            arrayOf(level)
        )
        cursor?.use {
            while (it.moveToNext()) {
                val score = it.getInt(it.getColumnIndex(COL_SCORE))
                scores.add(score)
            }
        }
        cursor?.close()
        return scores
    }


    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "JuniorMathWinData"
        private const val TABLE_NAME = "scores"
        private const val COL_ID = "id"
        private const val COL_SCORE = "score"
        private const val COL_LEVEL = "level"
    }
}





