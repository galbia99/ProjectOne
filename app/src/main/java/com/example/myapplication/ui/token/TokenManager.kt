package com.example.myapplication.ui.token

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.ui.counter.Counters
import com.example.myapplication.ui.db.DBM
import com.example.sticazzi.DataContainer
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import javax.xml.datatype.DatatypeConfigurationException

class TokenManager : Fragment() {
    var TokenIndex = 0
    lateinit var tokenlist:ArrayList<Token>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val v = inflater.inflate(R.layout.tokenwindow,container,false)
        var ibt=v.findViewById<ImageButton>(R.id.imageButton3)
        ibt.setOnClickListener { AddNewToken(ibt) }
        ibt=v.findViewById(R.id.imageButton)
        ibt.setOnClickListener { DeleteToken(ibt) }

        var bt=v.findViewById<Button>(R.id.Next)
        bt.setOnClickListener { right(bt) }
        bt=v.findViewById(R.id.Prev)
        bt.setOnClickListener { left(bt) }
        bt=v.findViewById(R.id.button9)
        bt.setOnClickListener { tap(bt) }
        bt=v.findViewById(R.id.button10)
        bt.setOnClickListener { untap(bt) }


        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tokenlist = DataContainer.tokens
        if(tokenlist.isEmpty()){tokenlist=LoadTokenArray()}
        Update(TokenIndex,true)

    }


    fun AddNewToken (v: View)
    {
        val UCount: TextView = requireView().findViewById(R.id.UCount1)
        val count = UCount.text.toString().toInt()+1
        UCount.text=count.toString()

        //UCount.text=count.toString()
    }
    fun DeleteToken (v: View)
    {
        val notokenerror = Snackbar.make(requireView().findViewById(R.id.mlo), R.string.notokenerror, Snackbar.LENGTH_SHORT)
        val UCount: TextView = requireView().findViewById(R.id.UCount1)
        val TCount: TextView = requireView().findViewById(R.id.TCount)
        if(UCount.text.toString().toInt()>0) {
            val count = UCount.text.toString().toInt() - 1
            UCount.text=count.toString()
        }
        else  if(TCount.text.toString().toInt()>0) {
            val count = TCount.text.toString().toInt() - 1
            TCount.text=count.toString()
        }
        else
        {
            notokenerror.show()
        }
    }
    fun LoadTokenArray():ArrayList<Token>
    {
        val dbm= DBM(requireContext())
        var crs: Cursor? = null
        try {
            val pref = requireActivity().getSharedPreferences("Preset", 0)
            val preset = pref.getInt("PreID", 1)
            crs=dbm.query1(preset)
        }
        catch(e:Exception)
        {
         crs = dbm.query1(1)
        }
        val list = ArrayList<Token>()
        try{
            do{
                val s =crs?.getString(crs?.getColumnIndex("NAME"))!!
                val e = Token(s,0,0)
                list.add(e)
              } while(crs?.moveToNext() == true)
            }
        catch(e:Exception)
        {

        }
        return list
    }
    fun Update(tind:Int,load:Boolean)
    {
        val namev:TextView=requireView().findViewById(R.id.Name1)
        val tcntv:TextView=requireView().findViewById(R.id.TCount)
        val ucntv:TextView= requireView().findViewById(R.id.UCount1)
        namev.text= tokenlist.get(tind).name!!
        if(load)
        {
            tcntv.text= tokenlist[tind].Tcount.toString()
            ucntv.text=tokenlist[tind].Ucount.toString()
        }
        else
        {
            tokenlist[tind].Ucount=ucntv.text.toString().toInt()
            tokenlist[tind].Tcount=tcntv.text.toString().toInt()
        }

    }

    fun tap(v:View)
    {
        println(tokenlist[TokenIndex+1].Ucount)
        val Ubox=requireView().findViewById<TextView>(R.id.UCount1)
        val Tbox=requireView().findViewById<TextView>(R.id.TCount)

        if(Ubox.text.toString().toInt()>0)
        {
            Ubox.text = (Ubox.text.toString().toInt() - 1).toString()
            Tbox.text = (Tbox.text.toString().toInt() + 1).toString()
        }
        else
        {
            val error=Snackbar.make(requireView().findViewById(R.id.mlo), "There are no untapped tokenlist to tap", Snackbar.LENGTH_SHORT)
            error.show()
        }
    }
    fun untap(v:View)
    {
        val Ubox=requireView().findViewById<TextView>(R.id.UCount1)
        val Tbox=requireView().findViewById<TextView>(R.id.TCount)
        if(Tbox.text.toString().toInt()>0)
        {
            Ubox.text = (Ubox.text.toString().toInt() + 1).toString()
            Tbox.text = (Tbox.text.toString().toInt() - 1).toString()
        }
        else
        {
            val error=Snackbar.make(requireView().findViewById(R.id.mlo), "There are no tapped tokenlist to untap", Snackbar.LENGTH_SHORT)
            error.show()
        }
    }
    fun left(v:View)
    {
        val size=tokenlist.size
        val max = size-1

        Update(TokenIndex,false)
        if(TokenIndex>0)
            TokenIndex-=1
        else
            TokenIndex=max
        Update(TokenIndex,true)
    }
    fun right(v:View)
    {
        val max = tokenlist.size-1
        Update(TokenIndex,false)
        if(TokenIndex<max)
            TokenIndex+=1
        else
            TokenIndex=0
        Update(TokenIndex,true)
    }

    override fun onStop() {
        super.onStop()
        DataContainer.tokens=tokenlist
    }

override fun onResume()
{
    super.onResume()
    tokenlist= LoadTokenArray()
    if(TokenIndex >= tokenlist.size)
    {
        TokenIndex=tokenlist.size-1
    }
}
}
class Token(val name:String, var Ucount:Int,var Tcount: Int)
{

}