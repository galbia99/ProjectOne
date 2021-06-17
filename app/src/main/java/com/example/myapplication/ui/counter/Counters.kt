package com.example.myapplication.ui.counter


import android.app.AlertDialog
import android.content.DialogInterface
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.ui.db.DBM
import com.google.android.material.snackbar.Snackbar

class Counters : AppCompatActivity() {
    var counters:ArrayList<Counter> = Holder.counterList
    private var adapter:ArrayAdapter<Counter>?=null
    var presetID=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.counterslayout)
        if(counters.count()==0) {
            Holder.counterList = populateArray()
            counters = Holder.counterList
        }
        Update()
    }

    fun populateArray():ArrayList<Counter>
    {
        val dbm= DBM(this)
        var crs: Cursor? = null
        try {
            val pref = getSharedPreferences("Preset", 0)
            presetID = pref.getInt("PreID", 1)
            crs=dbm.query3(presetID)
        }
        catch(e: Exception)
        {
            presetID=1
            crs = dbm.query3(1)
        }
        val list = ArrayList<Counter>()
        try{
            do{
                val s =crs?.getString(crs.getColumnIndex("DESCRIPTION"))!!
                val e = Counter(s,0)
                list.add(e)
            } while(crs?.moveToNext() == true)
        }
        catch(e: Exception)
        {

        }
        return list
    }
    fun Update() {
        val dbm = DBM(this)
        val list: ListView = findViewById(R.id.CList)
        adapter = object : ArrayAdapter<Counter>(this,0,counters) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val ctr= counters[position]
                var v: View? = convertView
                if(v==null) {
                    v = layoutInflater.inflate(R.layout.couterslistlayout, parent, false)
                }
                var txt=v?.findViewById<TextView>(R.id.textView)
                txt?.text= ctr.name.toString()
                txt=v?.findViewById(R.id.valueView2)
                txt?.text= ctr.value.toString()
                return v!!
            }
        }
        list.adapter=adapter
    }
    fun newCtr(view: View)
    {
        val v : View= layoutInflater.inflate(R.layout.dialoguetextfrag,null)
        val dg = AlertDialog.Builder(this)
                .setTitle("Pick a Name")
                .setView(v)
                .setPositiveButton("OK", DialogInterface.OnClickListener{ dialog, id ->
                    val s = v.findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
                    val dbm=DBM(this)
                    dbm.saveCnt(s,presetID)
                    Holder.counterList=populateArray()
                    counters=Holder.counterList
                    adapter?.clear()
                    adapter?.addAll(counters)
                    adapter?.notifyDataSetChanged()
                })
        dg.show()
    }
    fun onClickinc(v:View)
    {
        val position = findViewById<ListView>(R.id.CList).getPositionForView(v)
        if(adapter==null) println("no adpater")
        println(position)
        counters[position].value+=1
        adapter?.notifyDataSetChanged()
    }
    fun onClickdec(v:View)
    {
        val position = findViewById<ListView>(R.id.CList).getPositionForView(v)

        val PreID = adapter?.getItemId(position)
        if(counters[position].value>0)
        {
            counters[position].value-=1
            adapter?.notifyDataSetChanged()
        }
        else
        {
            val notokenerror = Snackbar.make(findViewById(R.id.mla),"value can't become negative", Snackbar.LENGTH_SHORT)
            notokenerror.show()
        }

    }
    fun onClickdelete(v:View)
    {
        val position = findViewById<ListView>(R.id.CList).getPositionForView(v)
        if(adapter==null) println("no adpater")
        val db = DBM(this)
        db.deleteCnt(counters[position].name)
        Holder.counterList=populateArray()
        counters=Holder.counterList
        adapter?.clear()
        adapter?.addAll(counters)
        adapter?.notifyDataSetChanged()

    }
}
class Counter(val name:String, var value:Int)
