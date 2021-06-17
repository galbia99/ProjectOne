package com.example.myapplication.ui.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.text.Editable

class DBM(ctx: Context) {
    private val dbh : DBhelper = DBhelper(ctx)

    fun saveTkn(ID:Int,name:String?, str:Int?, const: Int?, img:String?,PreID:Int)
    {
        val db:SQLiteDatabase = dbh.writableDatabase
        val cv:ContentValues = ContentValues()
        cv.put("NAME", name)
        cv.put("STR",str)
        cv.put("CONST",const)
        cv.put("IMAGE", img)
        cv.put("PreID",PreID)
        try
        {
            if(ID==-1)
            db.insert("TOKENS",null,cv)
            else
            {
                db.update("TOKENS",cv,"_id=$ID",null)
            }
        }
        catch (sqle:SQLiteException)
        {

        }
    }


    fun saveCnt(name:String,PreID: Int)
    {
        val db:SQLiteDatabase = dbh.writableDatabase
        val cv:ContentValues = ContentValues()
        cv.put("DESCRIPTION", name)
        cv.put("preID",PreID)
        try
        {
            db.insert("COUNTERS",null,cv)
        }
        catch (sqle:SQLiteException)
        {

        }
    }
    fun savePre(name:String?,ID: Int)
    {
        val db:SQLiteDatabase = dbh.writableDatabase
        val cv:ContentValues = ContentValues()
        cv.put("TITLE", name)
        try
        {
            if(ID!=-1)
            db.update("PRESETS",cv,"_id=$ID",null)
            else
                db.insert("PRESETS",null,cv)
        }
        catch (sqle:SQLiteException)
        {

        }
    }


    fun deleteCnt(desc:String)
    {
        val db=dbh.writableDatabase
        db.delete("COUNTERS","DESCRIPTION = '$desc'",null)
    }
    fun deletepre(id:Int){
        val db=dbh.writableDatabase
        db.delete("TOKENS","preID=$id",null)
        db.delete("PRESETS","_id=$id", null)
    }

    fun deleteToken(id: Int)
    {
        val db=dbh.writableDatabase
        db.delete("TOKENS","_id=$id", null)
    }
    fun query():Cursor?
    {
        var crs:Cursor?
        val select = arrayOf("Pid","p.TITLE","COUNT(TITLE) AS TOKENS")
        val tableName = "TOKENS t LEFT JOIN PRESETS p ON p.Pid = t.preID"
        try {
            val db: SQLiteDatabase = dbh.readableDatabase
            crs = db.query("PRESETS",null, null, null, "TITLE", null, null, null)
        }
        catch(sqle:SQLiteException)
        {
            return null
        }
        crs.moveToFirst()
        /*val clnames =crs.columnNames
        //for (s in clnames){
            //println(s)
            //val x =crs.getColumnIndex(s)
            //println(x)
        println(crs.getString(crs.getColumnIndex(s)))
    }*/

        return crs
    }
    fun query1(PreID: Int):Cursor?
    {
        return try
        {
            val db:SQLiteDatabase=dbh.readableDatabase
            println("id_=$PreID")
            val crs:Cursor=db.query("PRESETS p LEFT JOIN TOKENS t ON p._id=PreID", arrayOf("t._id","NAME","TITLE"),"P._id=$PreID",null,null,null,null)
            crs.moveToFirst()
           // println(crs.getString(crs.getColumnIndex("TITLE")))
            crs
        }
        catch (sqle:SQLiteException)
        {
            val crs:Cursor?=null
            crs
        }
    }

    fun query2(id: Int): Cursor? {
        return try
        {
            val db:SQLiteDatabase=dbh.readableDatabase
            val crs:Cursor=db.query("TOKENS", null,"_id=$id",null,null,null,null)
            crs.moveToFirst()
            // println(crs.getString(crs.getColumnIndex("TITLE")))
            crs
        }
        catch (sqle:SQLiteException)
        {
            val crs:Cursor?=null
            crs
        }
    }
    fun query3(id:Int): Cursor?{
    return try
    {
        val db:SQLiteDatabase=dbh.readableDatabase
        val crs:Cursor=db.query("COUNTERS", null,"preID=$id",null,null,null,null)
        crs.moveToFirst()
        // println(crs.getString(crs.getColumnIndex("TITLE")))
        crs
    }
    catch (sqle:SQLiteException)
    {
        val crs:Cursor?=null
        crs
    }
}

}