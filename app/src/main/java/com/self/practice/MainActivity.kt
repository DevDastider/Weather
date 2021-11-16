package com.self.practice

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getForecastButton= findViewById<Button>(R.id.getForecastButton)
        getForecastButton.setOnClickListener {
            val moveIntent= Intent(this, ForecastActivity::class.java)
            val editText= findViewById<EditText>(R.id.editText)
            moveIntent.putExtra("searchTerm", editText.text.toString())
            startActivity(moveIntent)
        }

        val callButton= findViewById<Button>(R.id.custCare)

        callButton.setOnClickListener {
            val phoneUri= Uri.parse("tel:1800211391")
            val callIntent= Intent(Intent.ACTION_DIAL, phoneUri)
            startActivity(callIntent)
        }
    }
}   