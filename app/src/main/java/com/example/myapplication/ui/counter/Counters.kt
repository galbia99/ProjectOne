package com.example.myapplication.ui.counter


import android.app.AlertDialog
import android.content.DialogInterface
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.ui.db.DBM
import com.google.android.material.snackbar.Snackbar

class Counters : Fragment() {
    var counters:ArrayList<Counter> = Holder.counterList
    private var adapter:ArrayAdapter<Counter>?=null
    var presetID=-1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        val v = inflater.inflate(R.layout.counterslayout,container,false)
        val bt=v.findViewById<Button>(R.id.button13)
        bt.setOnClickListener { newCtr(bt) }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(counters.count()==0) {
            Holder.counterList = populateArray()
            counters = Holder.counterList
        }
        Update()
    }

    fun populateArray():ArrayList<Counter>
    {
        val dbm= DBM(requireContext())
        var crs: Cursor? = null
        try {
            val pref = requireActivity().getSharedPreferences("Preset", 0)
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
        val dbm = DBM(requireContext())
        val list: ListView = requireView().findViewById(R.id.CList)
        adapter = object : ArrayAdapter<Counter>(requireContext(),0,counters) {
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
                var bt = v?.findViewById<Button>(R.id.button12)
                bt?.setOnClickListener { onClickdec(bt!!) }
                bt = v?.findViewById<Button>(R.id.button11)
                bt?.setOnClickListener { onClickinc(bt) }
                val ibt = v?.findViewById<ImageView>(R.id.imageView)
                ibt?.setOnClickListener { onClickdelete(ibt)}
                return v!!
            }
        }
        list.adapter=adapter
    }
    fun newCtr(view: View)
    {
        val v : View= layoutInflater.inflate(R.layout.dialoguetextfrag,null)
        val dg = AlertDialog.Builder(requireContext())
                .setTitle("Pick a Name")
                .setView(v)
                .setPositiveButton("OK", DialogInterface.OnClickListener{ dialog, id ->
                    val s = v.findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
                    val dbm=DBM(requireContext())
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
        val position = requireView().findViewById<ListView>(R.id.CList).getPositionForView(v)
        if(adapter==null) println("no adpater")
        println(position)
        counters[position].value+=1
        adapter?.notifyDataSetChanged()
    }
    fun onClickdec(v:View)
    {
        val position = requireView().findViewById<ListView>(R.id.CList).getPositionForView(v)

        val PreID = adapter?.getItemId(position)
        if(counters[position].value>0)
        {
            counters[position].value-=1
            adapter?.notifyDataSetChanged()
        }
        else
        {
            val notokenerror = Snackbar.make(requireView().findViewById(R.id.mla),"value can't become negative", Snackbar.LENGTH_SHORT)
            notokenerror.show()
        }

    }
    fun onClickdelete(v:View)
    {
        val position = requireView().findViewById<ListView>(R.id.CList).getPositionForView(v)
        if(adapter==null) println("no adpater")
        val db = DBM(requireContext())
        db.deleteCnt(counters[position].name)
        Holder.counterList=populateArray()
        counters=Holder.counterList
        adapter?.clear()
        adapter?.addAll(counters)
        adapter?.notifyDataSetChanged()

    }
}
class Counter(val name:String, var value:Int)
