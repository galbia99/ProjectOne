package com.example.myapplication.ui.token

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.example.myapplication.ui.db.DBM
import java.util.zip.Inflater


class Presets : AppCompatActivity() {
   var adapter:CursorAdapter?=null
     val dbm: DBM =DBM(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presets)
         adapter = fetchPresets()
    }

    fun fetchPresets():CursorAdapter?
    {

        val list:ListView=findViewById(R.id.LstView)
        val crs: Cursor? = dbm.query()
        val adapter:CursorAdapter = object: CursorAdapter(this, crs,0)
        {
            override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
                val v: View = layoutInflater.inflate(R.layout.list_row, parent,false)
                //val hld : ViewHolder = v.findViewById(R.id.set_Name)
                //v.tag = hld
                    return v
            }

            override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
                if (cursor != null) {

                    //val holder : ViewHolder = view?.getTag() as ViewHolder
                    val txt : TextView? = view?.findViewById(R.id.set_Name)
                    if(txt!=null) {
                        txt.text = cursor.getString(crs?.getColumnIndex("TITLE")!!)
                        /*   txt=findViewById(R.id.tokenum)
                    txt.text=cursor.getString(crs.getColumnIndex("TCOUNT"))+"tokens"
                    txt=findViewById(R.id.countnum)
                    txt.text=cursor.getString(crs.getColumnIndex("CCOUNT"))+"counters"*/
                    }}

            }

        }
        list.adapter=adapter
        return adapter
    }
    fun onClick(v:View)
    {
        val intent= Intent(this,PresetEditor::class.java)
        val position = findViewById<ListView>(R.id.LstView).getPositionForView(v)
        if(adapter==null) println("no adpater")
        val PreID = adapter?.getItemId(position)
        println(PreID)
        intent.putExtra("PresetName",PreID)
        println("preclick",)
        startActivity(intent)
    }
    fun newPre(view: View)
    {
        val v : View= layoutInflater.inflate(R.layout.dialoguetextfrag,null)
        val dg =AlertDialog.Builder(this)
                .setTitle("Pick a Name")
                .setView(v)
                .setPositiveButton("OK",DialogInterface.OnClickListener{dialog, id ->
                    val s = v.findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
                    dbm.savePre(s,-1)
                    adapter?.changeCursor(dbm.query())
                })
        dg.show()
    }
     override fun onResume() {
         super.onResume()
         adapter?.changeCursor(dbm.query())
     }

}
/*class PickName : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val inflater = layoutInflater
            builder.setMessage("Pick a name")
                    .setView(inflater.)
                    .setPositiveButton("ok",
                            DialogInterface.OnClickListener { dialog, id ->
                                // FIRE ZE MISSILES!
                            })
                    .setNegativeButton("cancel",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User cancelled the dialog
                            })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}*/
inline class ViewHolder(val name:TextView)