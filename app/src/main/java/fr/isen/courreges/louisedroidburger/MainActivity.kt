package fr.isen.courreges.louisedroidburger

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import fr.isen.courreges.louisedroidburger.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val burgerSpinner = binding.burgerChoiceSpinner
        var time = ""
        sharedPreferences = getSharedPreferences("UserOrderDetails", Context.MODE_PRIVATE)

        binding.pickTimeButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                binding.timeChoice.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            time = binding.timeChoice.text.toString()

            if(time.isNotEmpty()){
                binding.pickTimeButton.text = "Modifier l'heure"
                binding.timeChoice.text = time
                binding.timeChoice.visibility = View.VISIBLE
            }
        }


        binding.validateButton.setOnClickListener {
            val name = binding.nameTextBox.text.toString()
            val firstName = binding.firstNameTextBox.text.toString()
            val address = binding.addressTextBox.text.toString()
            val phoneNumber = binding.phoneEditText.text.toString()
            val burgerChoice = binding.burgerChoiceSpinner.selectedItem.toString()
            val timeChoice = binding.timeChoice.text.toString()

            if (checkIfBoxIsEmpty(binding.nameTextBox) && checkIfBoxIsEmpty(binding.firstNameTextBox) && checkIfBoxIsEmpty(binding.addressTextBox) && checkIfBoxIsEmpty(binding.phoneEditText)
            ) {
                if(phoneNumber.length == 10) {
                    val sharedPreferencesEditor = sharedPreferences.edit()
                    sharedPreferencesEditor.putString("Name", name)
                    sharedPreferencesEditor.putString("FirstName", firstName)
                    sharedPreferencesEditor.apply()

                    val intent = Intent(this, OrderInfosActivity::class.java)
                    intent.putExtra("Address", address)
                    intent.putExtra("BurgerChoice", burgerChoice)
                    intent.putExtra("Time", timeChoice)
                    intent.putExtra("PhoneNumber", phoneNumber)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Le numéro de téléphone doit être composé de 10 chiffres", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_SHORT).show()
            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.burger_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            burgerSpinner.adapter = adapter
        }

    }

    private fun checkIfBoxIsEmpty(textView: TextView) : Boolean{
        return !textView.text.isEmpty()
    }
}