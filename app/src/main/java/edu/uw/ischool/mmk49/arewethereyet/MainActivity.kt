package edu.uw.ischool.mmk49.arewethereyet

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var validated = false
    lateinit var messageText: TextView
    lateinit var phoneText: TextView
    lateinit var intervalText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        messageText = findViewById(R.id.editTextText)
        phoneText = findViewById(R.id.editTextPhone2)
        intervalText = findViewById(R.id.editTextTime)

        val sendBtn = findViewById<Button>(R.id.sendBtn)
        phoneText.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        sendBtn.setOnClickListener {
            validated = validate()
            if(validated) {
                sendBtn.text = "Stop"
                Toast.makeText(this, getString(R.string.send_toast, phoneText.text, messageText.text), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun validate () : Boolean {
        var valid = true
        if(messageText.text.isEmpty()) {
            Toast.makeText(this, "Please Enter A Message", Toast.LENGTH_SHORT).show()
            valid = false
        }
        if(phoneText.text.length != 14) {
            Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show()
            valid = false
        }
        if(intervalText.text.isEmpty()) {
            Toast.makeText(this, "Please Enter Time", Toast.LENGTH_SHORT).show()
            valid = false
        }
        return valid
    }
}