package com.example.myapplication.ui.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper


class DBhelper(context: Context?) :
    SQLiteOpenHelper(context, DBNAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val q = "CREATE TABLE TOKENS ( _id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,STR INTEGER,CONST INTEGER,IMAGE TEXT,preID INTEGER)"
        db.execSQL(q)
        val r = "CREATE TABLE COUNTERS (_id INTEGER PRIMARY KEY AUTOINCREMENT, DESCRIPTION TEXT, STARTING_VALUE INTEGER,preID INTEGER)"
        db.execSQL(r)
        val s = "CREATE TABLE PRESETS (_id INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT)"
        db.execSQL(s)
        val cv: ContentValues = ContentValues()
        cv.put("NAME","Angel")
        cv.put("IMAGE", "nothing")
        cv.put("preID", 1)
        db.insert("TOKENS",null,cv)
        val cs: ContentValues = ContentValues()
        cs.put("DESCRIPTION","cnt")
        cs.put("STARTING_VALUE", 1)
        cs.put("preID", 1)
        db.insert("COUNTERS",null,cs)
        val cr: ContentValues = ContentValues()
        cr.put("TITLE","Default")
        db.insert("PRESETS",null,cr)
        val ch: ContentValues = ContentValues()
        ch.put("TITLE","preset2")
        db.insert("PRESETS",null,ch)


    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        const val DBNAME = "DATA"
    }
}