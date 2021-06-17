package com.example.myapplication.ui.token

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.ui.counter.Counters
import com.example.myapplication.ui.db.DBM
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class TokenManager : AppCompatActivity() {
    var TokenIndex = 0
    var tokens:ArrayList<Token> =  ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tokenwindow)
        tokens=LoadTokenArray()
        Update(TokenIndex,true)
    }


    fun AddNewToken (view: View)
    {
        val UCount: TextView = findViewById(R.id.UCount1)
        val count = UCount.text.toString().toInt()+1
        UCount.text=count.toString()
        println(count)
        //UCount.text=count.toString()
    }
    fun DeleteToken (view: View)
    {
        val notokenerror = Snackbar.make(findViewById(R.id.mlo), R.string.notokenerror, Snackbar.LENGTH_SHORT)
        val UCount: TextView = findViewById(R.id.UCount1)
        val TCount: TextView = findViewById(R.id.TCount)
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
    //TEMPORARY
    fun callTestActivity(v :View)
    {
        val intent = Intent(this, Counters::class.java)
        startActivity(intent)
    }

    fun testQuery(v: View)
    {
        val db = DBM(this)
        val crs=db.query1(0)
        println(crs?.getString(crs.getColumnIndex("TITLE")))
    }
    fun LoadTokenArray():ArrayList<Token>
    {
        val dbm= DBM(this)
        var crs: Cursor? = null
        try {
            val pref = getSharedPreferences("Preset", 0)
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
        val namev:TextView=findViewById(R.id.Name1)
        val tcntv:TextView=findViewById(R.id.TCount)
        val ucntv:TextView=findViewById(R.id.UCount1)
        namev.text= tokens.get(tind).name!!
        if(load)
        {
            tcntv.text= tokens[tind].Tcount.toString()
            ucntv.text=tokens[tind].Ucount.toString()
        }
        else
        {
            tokens[tind].Ucount=ucntv.text.toString().toInt()
            tokens[tind].Tcount=tcntv.text.toString().toInt()
        }

    }

    fun tap(v:View)
    {
        val Ubox=findViewById<TextView>(R.id.UCount1)
        val Tbox=findViewById<TextView>(R.id.TCount)

        if(Ubox.text.toString().toInt()>0)
        {
            Ubox.text = (Ubox.text.toString().toInt() - 1).toString()
            Tbox.text = (Tbox.text.toString().toInt() + 1).toString()
        }
        else
        {
            val error=Snackbar.make(findViewById(R.id.mlo), "There are no untapped tokens to tap", Snackbar.LENGTH_SHORT)
            error.show()
        }
    }
    fun untap(v:View)
    {
        val Ubox=findViewById<TextView>(R.id.UCount1)
        val Tbox=findViewById<TextView>(R.id.TCount)
        if(Tbox.text.toString().toInt()>0)
        {
            Ubox.text = (Ubox.text.toString().toInt() + 1).toString()
            Tbox.text = (Tbox.text.toString().toInt() - 1).toString()
        }
        else
        {
            val error=Snackbar.make(findViewById(R.id.mlo), "There are no tapped tokens to untap", Snackbar.LENGTH_SHORT)
            error.show()
        }
    }
    fun left(v:View)
    {
        val max = tokens.size-1
        Update(TokenIndex,false)
        if(TokenIndex>0)
            TokenIndex-=1
        else
            TokenIndex=max
        Update(TokenIndex,true)
    }
override fun onResume()
{
    super.onResume()
    tokens= LoadTokenArray()
    if(TokenIndex >= tokens.size)
    {
        TokenIndex=tokens.size-1
    }
}
}
class Token(val name:String, var Ucount:Int,var Tcount: Int)
{

}