package com.example.sticazzi

import com.example.myapplication.ui.token.Token

object DataContainer
{
   var PreID : Int? = 1
       get() {
           return field
       }
       set(newID:Int?) {field=newID}
   var TokeID: Int? = 0
       get(){return field}
        set(newID:Int?){ field=newID}
    var PreIDtmp : Int? = 0
        get() {
            return field
        }
        set(newID:Int?) {field=newID}
    var TokenIsNew : Boolean = true
        get(){return field}
        set(b : Boolean){field=b}

    var tokens=ArrayList<Token>()
}
