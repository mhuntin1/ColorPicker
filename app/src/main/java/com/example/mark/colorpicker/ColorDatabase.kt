package com.example.mark.colorpicker

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import java.security.AccessControlContext

/**
 * Created by Mark on 2/27/2018.
 */


val Database_Name = "ColorDB"
val Table_Name = "Colors"
val Col_ID = "id"
val Col_Red = "Red"
val Col_Green = "Green"
val Col_Blue = "Blue"

class ColorDatabase (var context: Context) : SQLiteOpenHelper(context, Database_Name, null, 1){
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + Table_Name + "( " +
              Col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Col_Red + " INTEGER," +
                Col_Green + " INTEGER, " +
                Col_Blue + " INTEGER)";

        db?.execSQL(createTable)
    }

    fun insertData(redValue: Int, greenValue: Int, blueValue: Int){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(Col_Red, redValue)
        cv.put(Col_Green, greenValue)
        cv.put(Col_Blue, blueValue)
        var result = db.insert(Table_Name, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "Did not save", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Did save", Toast.LENGTH_SHORT).show()
    }

    fun readData() : MutableList<Color>{
        var list : MutableList<Color> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from "+ Table_Name
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var color = Color()
                color.Red = result.getString(result.getColumnIndex(Col_Red)).toInt()
                color.Green = result.getString(result.getColumnIndex(Col_Green)).toInt()
                color.Blue = result.getString(result.getColumnIndex(Col_Blue)).toInt()
                list.add(color)
            }
                while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun clearTable() {
        val db = this.writableDatabase
        db.delete(Table_Name, null, null)
        //db.execSQL("delete from " + Table_Name)
        db.close()
    }

}