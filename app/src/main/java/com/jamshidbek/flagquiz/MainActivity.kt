package com.jamshidbek.flagquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import com.jamshidbek.flagquiz.models.Flag
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var flagArrayList: ArrayList<Flag>
    var count = 0
    var countryName = ""
    lateinit var buttonArrayList: ArrayList<Button>

    lateinit var linerMatn: LinearLayout
    lateinit var linerBnt1: LinearLayout
    lateinit var linerBtn2: LinearLayout
    lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonArrayList = ArrayList()

        linerMatn = findViewById(R.id.lin_1_matn)
        linerBnt1 = findViewById(R.id.lin_2_btn_1)
        linerBtn2 = findViewById(R.id.lin_3_btn_2)
        image = findViewById(R.id.image_1)

        obyektYaratish()
        btnJoylaCount()
    }

    private fun obyektYaratish() {
        flagArrayList = ArrayList()
        flagArrayList.add(Flag("uzbekistan", R.drawable.uzb))
        flagArrayList.add(Flag("usa", R.drawable.usa))
        flagArrayList.add(Flag("germaniya", R.drawable.ecuador))
        flagArrayList.add(Flag("russia", R.drawable.russia))
    }

    fun btnJoylaCount() {
        image.setImageResource(flagArrayList[count].image!!)
        linerMatn.removeAllViews()
        linerBnt1.removeAllViews()
        linerBtn2.removeAllViews()
        countryName = ""
        btnJoyla(flagArrayList[count].name)
    }

    private fun btnJoyla(countryName: String?) {
        val btnArray: ArrayList<Button> = randomBtn(countryName)
        for (i in 0..5) {
            linerBnt1.addView(btnArray[i])
        }
        for (i in 6..11) {
            linerBtn2.addView(btnArray[i])
        }
    }

    private fun randomBtn(countryName: String?): ArrayList<Button> {
        val array = ArrayList<Button>()
        var arrayText = ArrayList<String>()

        for (c in countryName!!) {
            arrayText.add(c.toString())
        }
        if (arrayText.size != 12) {
            val str = "ABCDEFGHIJKLMNOPQRSTUVXYZ"
            for (i in arrayText.size until 12) {
                val random = Random().nextInt(str.length)
                arrayText.add(str[random].toString())
            }
        }

        arrayText.shuffle()

        for (i in 0 until arrayText.size) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            button.text = arrayText[i]
            button.setOnClickListener(this)
            array.add(button)
        }
        return array
    }

    override fun onClick(v: View?) {
        val button1 = v as Button
        if (buttonArrayList.contains(button1)) {
            linerMatn.removeView(button1)
            var hasC = false
            linerBnt1.children.forEach { button ->
                if ((button as Button).text.toString() == button1.text.toString()) {
                    button.visibility = View.VISIBLE
                    countryName = countryName.substring(0, countryName.length - 1)
                    hasC = true
                }
            }
            linerBtn2.children.forEach { button ->
                if ((button as Button).text.toString() == button1.text.toString()) {
                    button.visibility = View.VISIBLE
                    if (!hasC) {
                        countryName = countryName.substring(0, countryName.length - 1)
                    }
                }
            }
        } else {
            button1.visibility = View.INVISIBLE
            countryName += button1.text.toString().toUpperCase()
            val button2 = Button(this)
            button2.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            button2.text = button1.text
            button2.setOnClickListener(this)
            buttonArrayList.add(button2)
            linerMatn.addView(button2)
            matnTogri()
            if(button1.text == "A" || button2.text == "a"){
                Toast.makeText(this, "'A' pressed", Toast.LENGTH_SHORT).show()
            }
            if (button1.text == "B" || button2.text == "b"){
                Toast.makeText(this, "'B' pressed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun matnTogri() {
        if (countryName == flagArrayList[count].name?.toUpperCase()) {
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
            if (count == flagArrayList.size - 1) {
                count = 0
            } else {
                count++
            }
            btnJoylaCount()
        } else {
            if (countryName.length == flagArrayList[count].name?.length) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                linerMatn.removeAllViews()
                linerBnt1.removeAllViews()
                linerBtn2.removeAllViews()
                btnJoyla(flagArrayList[count].name)
                countryName = ""
            }
        }
    }
}