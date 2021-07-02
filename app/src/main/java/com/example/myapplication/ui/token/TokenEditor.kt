package com.example.myapplication.ui.token

import android.database.Cursor
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.ui.db.DBM
import com.example.Data.DataContainer

class TokenEditor : Fragment() {
    var ID:Int? = -1
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedinstanceState: Bundle?):View? {
        super.onCreate(savedinstanceState)
        val v=inflater.inflate(R.layout.activity_token_editor,container,false)
        var bt=v.findViewById<Button>(R.id.button2)
        bt.setOnClickListener { SaveToken(bt) }
        bt=v.findViewById(R.id.button8)
        bt.setOnClickListener { delete(bt) }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setlayout()
    }
    fun setlayout()
    {
        ID=DataContainer.TokeID
        if(!DataContainer.TokenIsNew)
        {
            val db= DBM(requireContext())
            val crs: Cursor? =db.query2(ID!!)
            requireView().findViewById<EditText>(R.id.editTextTextPersonName2).text= Editable.Factory.getInstance().newEditable(crs?.getString(crs.getColumnIndex("NAME")))
            requireView().findViewById<EditText>(R.id.editTextNumber2).text=Editable.Factory.getInstance().newEditable(crs?.getInt(crs.getColumnIndex("STR")).toString())
            requireView().findViewById<EditText>(R.id.editTextNumber3).text=Editable.Factory.getInstance().newEditable(crs?.getInt(crs.getColumnIndex("CONST")).toString())
            requireView().findViewById<EditText>(R.id.editTextTextPersonName3).text=Editable.Factory.getInstance().newEditable(crs?.getString(crs.getColumnIndex("IMAGE")))
        }
    }

    fun SaveToken(v: View)
    {
        val db = DBM(requireContext())
        val name:String? = requireView().findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()

        val str:Int?=requireView().findViewById<EditText>(R.id.editTextNumber2).text.toString().toInt()
        val cons:Int?=requireView().findViewById<EditText>(R.id.editTextNumber3).text.toString().toInt()
        val Img:String?=requireView().findViewById<EditText>(R.id.editTextTextPersonName3).text.toString()
        val PresID = DataContainer.PreID!!
        if(!DataContainer.TokenIsNew)
        {

            db.saveTkn(ID!!,name,str,cons,Img,PresID)
        }
        else {
            db.saveTkn(-1,name, str, cons, Img,PresID)
        }
        parentFragmentManager.popBackStack()
    }
    fun delete(v: View)
    {
        val db = DBM(requireContext())
        db.deleteToken(ID!!)
        parentFragmentManager.popBackStack()
    }


}