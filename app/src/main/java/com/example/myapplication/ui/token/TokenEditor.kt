package com.example.myapplication.ui.token

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.CursorAdapter
import android.widget.EditText
import com.example.myapplication.R
import com.example.myapplication.ui.db.DBM

class TokenEditor : AppCompatActivity() {
    var ID:Int? = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_token_editor)
        setlayout()
    }
    fun setlayout()
    {
        ID=intent.getIntExtra("TokenID", -1)
        if(ID!=-1)
        {
            val db= DBM(this)
            val crs: Cursor? =db.query2(ID!!)
            findViewById<EditText>(R.id.editTextTextPersonName2).text= Editable.Factory.getInstance().newEditable(crs?.getString(crs.getColumnIndex("NAME")))
            findViewById<EditText>(R.id.editTextNumber2).text=Editable.Factory.getInstance().newEditable(crs?.getInt(crs.getColumnIndex("STR")).toString())
            findViewById<EditText>(R.id.editTextNumber3).text=Editable.Factory.getInstance().newEditable(crs?.getInt(crs.getColumnIndex("CONST")).toString())
            findViewById<EditText>(R.id.editTextTextPersonName3).text=Editable.Factory.getInstance().newEditable(crs?.getString(crs.getColumnIndex("IMAGE")))
        }
    }

    fun SaveToken(v: View)
    {
        val db = DBM(this)
        val name:String? = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()

        val str:Int?=findViewById<EditText>(R.id.editTextNumber2).text.toString().toInt()
        val cons:Int?=findViewById<EditText>(R.id.editTextNumber3).text.toString().toInt()
        val Img:String?=findViewById<EditText>(R.id.editTextTextPersonName3).text.toString()
        val PresID = intent.getIntExtra("PresetID",0)
        if(intent.getIntExtra("Caller",0)!=0)
        {

            db.saveTkn(ID!!,name,str,cons,Img,PresID)
        }
        else {
            db.saveTkn(-1,name, str, cons, Img,PresID)
        }
        finish()
    }
    fun delete(v: View)
    {
        val db = DBM(this)
        db.deleteToken(ID!!)
        finish()
    }


}