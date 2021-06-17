package com.example.myapplication.ui.token

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.ui.db.DBM

class PresetEditor : AppCompatActivity() {
    var adapter:CursorAdapter?=null
    var PreID:Int=0
    val dbm: DBM =DBM(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preset_editor)
        adapter=fetchContent()
    }

    fun fetchContent():CursorAdapter?
    {

        PreID=intent.getLongExtra("PresetName",-1).toInt()
        val list: ListView =findViewById(R.id.TeCList)
        val crs: Cursor? = dbm.query1(PreID)
        adapter= object: CursorAdapter(this, crs,0)
        {
            override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
                val v: View = layoutInflater.inflate(R.layout.list2_row, parent,false)
                return v
            }

            override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
                if (cursor != null) {

                    var txt : TextView? = findViewById(R.id.Title)
                        txt?.text = cursor.getString(crs?.getColumnIndex("TITLE")!!)
                    txt=view?.findViewById(R.id.NoT)
                    txt?.text = cursor.getString(crs.getColumnIndex("NAME"))
                    /*txt=findViewById(R.id.countnum)
                    txt.text=cursor.getString(crs.getColumnIndex("CCOUNT"))+"counters"*/
                    }

            }

        }
        list.adapter=adapter
        return adapter
    }
    fun editToken(v: View)
    {
        val PreID=getIntent().getLongExtra("PresetName",-1).toInt()
        val intent= Intent(this,TokenEditor::class.java)
        intent.putExtra("PresetID", PreID)
        intent.putExtra("Caller",1)
        val position = findViewById<ListView>(R.id.TeCList).getPositionForView(v)
        if(adapter==null) println("no adpater")
        val TokeID = adapter?.getItemId(position)
        intent.putExtra("TokenID", TokeID?.toInt())
        startActivity(intent)
    }
    fun AddNewToken(v:View)
    {
        val PreID=getIntent().getLongExtra("PresetName",-1).toInt()
        val intent = Intent(this,TokenEditor::class.java)
        intent.putExtra("PresetID",PreID)
        intent.putExtra("Caller",0)
        startActivity(intent)
    }
    fun Save(v:View)
    {
        dbm.savePre(findViewById<EditText>(R.id.Title).text.toString(),PreID)
        finish()
    }
    fun Delete(v:View)
    {
        dbm.deletepre(PreID)
        finish()
    }
    override fun onResume() {
        super.onResume()

        adapter?.changeCursor(dbm.query1(PreID))
    }

    fun setPreset(v:View)
    {
        val pref = getSharedPreferences("Preset",0).edit()
        pref.putInt("PreID",PreID)
    }
}