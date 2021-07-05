package com.example.Data

object DataContainer
{

   var TokeID: Int? = 0
       get(){return field}
        set(newID:Int?){ field=newID}
    var PreID : Int? = 0
        get() {
            return field
        }
        set(newID:Int?) {field=newID}
    var TokenIsNew : Boolean = true
        get(){return field}
        set(b : Boolean){field=b}

    var tokens=ArrayList<Token>()
}
class Token(val name:String, var Ucount:Int,var Tcount: Int)
{

}