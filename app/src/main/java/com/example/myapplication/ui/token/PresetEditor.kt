package com.example.myapplication.ui.token

import android.content.Context
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

class PresetEditor: Fragment() {
    var adapter:CursorAdapter?=null
    var PreID:Int?=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val v= inflater.inflate(R.layout.activity_preset_editor,container,false)
        var bt=v.findViewById<Button>(R.id.button3)
        bt.setOnClickListener { AddNewToken(bt) }
        bt=v.findViewById<Button>(R.id.button4)
        bt.setOnClickListener { Save(bt) }
        bt=v.findViewById<Button>(R.id.button6)
        bt.setOnClickListener { setPreset(bt) }
        bt=v.findViewById<Button>(R.id.button7)
        bt.setOnClickListener { Delete(bt) }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter=fetchContent()
    }
    fun fetchContent():CursorAdapter?
    {
        val dbm = DBM(this.requireContext())
        val rt=this.requireView()
        PreID=DataContainer.PreID
        val list: ListView =rt.findViewById(R.id.TeCList)

        val crs: Cursor? = dbm.query1(PreID!!)

            Log.d("asd", crs?.count.toString())
            val adapter = object : CursorAdapter(this.requireContext(), crs, 0) {
                override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
                    val v = layoutInflater.inflate(R.layout.list2_row, parent, false)
                    val bt = v.findViewById<Button>(R.id.button)
                    bt.setOnClickListener { editToken(bt) }
                    if(crs==null||cursor?.getString(crs.getColumnIndex("NAME"))==null)
                        bt.isClickable=false
                    return v
                }

                override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
                    if (cursor != null) {

                        var txt: TextView? = rt.findViewById(R.id.Title)
                        txt?.text = cursor.getString(crs?.getColumnIndex("TITLE")!!)

                        txt = view?.findViewById(R.id.NoT)
                        txt?.text = cursor.getString(crs.getColumnIndex("NAME"))
                        /*txt=findViewById(R.id.countnum)
                    txt.text=cursor.getString(crs.getColumnIndex("CCOUNT"))+"counters"*/
                    }

                }

            }
            list.adapter = adapter

            return adapter


    }
    fun editToken(v: View)
    {
        val PreID=DataContainer.PreID
        DataContainer.TokenIsNew=false
        val position = this.view?.findViewById<ListView>(R.id.TeCList)?.getPositionForView(v)
        if(adapter==null) println("no adpater")
        val TokeID = adapter?.getItemId(position!!)?.toInt()
        DataContainer.TokeID=TokeID
        val controller=findNavController()
        controller.navigate(R.id.tokenEditor)

    }
    fun AddNewToken(v:View)
    {
        DataContainer.TokenIsNew=true
        findNavController().navigate(R.id.tokenEditor)
    }
    fun Save(v:View)
    {
        val dbm = DBM(this.requireContext())
        dbm.savePre(this.requireView().findViewById<EditText>(R.id.Title).text.toString(), PreID!!)
        this.parentFragmentManager.popBackStack()
    }
    fun Delete(v:View)
    {
        val dbm = DBM(this.requireContext())
        dbm.deletepre(PreID!!)
        this.parentFragmentManager.popBackStack()
    }
    override fun onResume() {
        super.onResume()
        val dbm = DBM(this.requireContext())
        adapter?.changeCursor(dbm.query1(PreID!!))
    }

    fun setPreset(v:View)
    {
        val pref = requireContext().getSharedPreferences("Preset",0).edit()
        pref.putInt("PreID",PreID!!)
        pref.commit()
        val pref1 =requireContext().getSharedPreferences("Preset",0)
        println(pref1.getInt("PreID",-1))
        DataContainer.tokens= ArrayList()
        this.parentFragmentManager.popBackStack()
    }
}