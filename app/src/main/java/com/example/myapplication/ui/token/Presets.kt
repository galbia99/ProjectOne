package com.example.myapplication.ui.token

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.ui.db.DBM
import com.example.Data.DataContainer


class Presets : Fragment() {
   var adapter:CursorAdapter?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val v=inflater.inflate(R.layout.activity_presets,container,false)
        val bt=v.findViewById<Button>(R.id.button5)
        bt.setOnClickListener { newPre(bt) }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = fetchPresets()
    }

    fun fetchPresets():CursorAdapter?
    {
        val dbm: DBM =DBM(this.requireContext())
        val list:ListView= this.requireView().findViewById(R.id.LstView)
        val crs: Cursor? = dbm.query()
        val adapter:CursorAdapter = object: CursorAdapter(this.requireContext(), crs,0)
        {
            override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
                val v= layoutInflater.inflate(R.layout.list_row, parent,false)
                val bt=v.findViewById<Button>(R.id.EditPreset)
                bt.setOnClickListener { EditPre(bt) }
                return v
            }

            override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
                if (cursor != null) {
                    val txt : TextView? = view?.findViewById(R.id.set_Name)
                    if(txt!=null) {
                        txt.text = cursor.getString(crs?.getColumnIndex("TITLE")!!)
                    }}

            }

        }

        list.adapter=adapter
        return adapter
    }
    public fun EditPre(v:View)
    {
        val position = this.requireView().findViewById<ListView>(R.id.LstView).getPositionForView(v)
        if(adapter==null) println("no adpater")
        val PreID = adapter?.getItemId(position)
        DataContainer.PreID=PreID?.toInt()
        Log.d("preID=",PreID.toString())
        findNavController().navigate(R.id.presetEditor)
    }
    fun newPre(view: View)
    {
        val dbm: DBM =DBM(this.requireContext())
        val v : View= layoutInflater.inflate(R.layout.dialoguetextfrag,null)
        val dg =AlertDialog.Builder(this.requireContext())
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
         val dbm: DBM =DBM(this.requireContext())
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