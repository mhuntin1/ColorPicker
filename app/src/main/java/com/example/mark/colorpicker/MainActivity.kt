package com.example.mark.colorpicker

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.SurfaceView
import android.widget.*
import java.io.FileWriter

import kotlinx.android.synthetic.main.activity_main.*
import menu.MenuFragment


class MainActivity : AppCompatActivity()  {

    var redValue: Int = 0
    var greenValue: Int = 0
    var blueValue: Int = 0

    val manager = supportFragmentManager



    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        title = ""
        supportActionBar!!.setIcon(R.drawable.lion)



        val colorField = findViewById<SurfaceView>(R.id.colorView)

        val redSlider = findViewById<SeekBar>(R.id.redBar)
        val redAmount = findViewById<TextView>(R.id.red)

        val greenSlider = findViewById<SeekBar>(R.id.greenBar)
        val greenAmount = findViewById<TextView>(R.id.green)

        val blueSlider = findViewById<SeekBar>(R.id.blueBar)
        val blueAmount = findViewById<TextView>(R.id.blue)

        val clear = findViewById<Button>(R.id.clearButton)
        var db = ColorDatabase(context)

        //var redValue: Int
        //var greenValue: Int
        //var blueValue: Int


        redSlider.max = 255
        blueSlider.max = 255
        greenSlider.max = 255

        redSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                redAmount.text = progress.toString()
                redValue = progress
                colorField.setBackgroundColor(Color.rgb(redValue, greenValue,blueValue))

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        greenSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, gProgress: Int, p2: Boolean) {
                greenAmount.text = gProgress.toString()
                greenValue = gProgress
                colorField.setBackgroundColor(Color.rgb(redValue,greenValue,blueValue))


            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        blueSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                blueAmount.text = p1.toString()
                blueValue = p1
                colorField.setBackgroundColor(Color.rgb(redValue,greenValue,blueValue))

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        clear.setOnClickListener({
            db.clearTable()
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        var db = ColorDatabase(context)
         when (item.itemId) {
            R.id.action_save -> {
                //var db = ColorDatabase(context)
                db.insertData(redValue,greenValue,blueValue)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

        when (item.itemId){
            R.id.action_recall -> {
                showMenu()
               /*var data = db.readData()
                dataResults.text = " "
                for (i in 0..data.size-1){
                  dataResults.append("Red Value: " + data.get(i).Red.toString() + " " +
                          "Green Value: " + data.get(i).Green.toString() + " " +
                          "Blue Value: " + data.get(i).Blue.toString() + " ")
                }*/
            }
        }

        return when (item.itemId) {
            R.id.action_clear -> true
            //db.clearTable()
            else -> super.onOptionsItemSelected(item)
        }

    }

    fun saveColor(redValue: Int, greenValue: Int, blueValue: Int){
        try{
            var file = FileWriter("color_menu.txt")
            file.write(redValue)
            file.write(greenValue)
            file.write(blueValue)
            file.close()
        }catch (ex:Exception){
            print(ex.message)
        }
        Toast.makeText(this, "Color Saved", Toast.LENGTH_LONG).show()

    }

    fun showMenu(){
        val transaction = manager.beginTransaction()
        val fragment = MenuFragment()
        //transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }



    }

