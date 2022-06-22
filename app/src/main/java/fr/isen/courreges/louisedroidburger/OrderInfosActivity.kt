package fr.isen.courreges.louisedroidburger

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import fr.isen.courreges.louisedroidburger.databinding.ActivityOrderInfosBinding
import java.util.*
import kotlin.time.Duration.Companion.hours

class OrderInfosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderInfosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        binding = ActivityOrderInfosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = applicationContext.getSharedPreferences("UserOrderDetails", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("Name", "")
        val firstName = sharedPreferences.getString("FirstName", "")
        val address = sharedPreferences.getString("Address", "")
        val burgerChoice = intent.getStringExtra("BurgerChoice").toString()
        val time = intent.getStringExtra("Time").toString()
        val phone = intent.getStringExtra("PhoneNumber").toString()


        if(address != null){
            binding.firstNameRetrievedTextView.text = firstName
            binding.customerName.text = name
            binding.customerFirstName.text = firstName
            binding.customerAddress.text = address
            binding.customerBurgerChoice.text = burgerChoice
            binding.customerTimeChoice.text = time
            binding.customerPhone.text = phone
        }


        if(burgerChoice == "Burger du chef"){
            binding.burgerImage.setImageResource(R.drawable.burger_du_chef)
        } else if(burgerChoice == "Burger Italien"){
            binding.burgerImage.setImageResource(R.drawable.burger_italien)
        } else if(burgerChoice == "Burger Vegétarien"){
            binding.burgerImage.setImageResource(R.drawable.burger_vegetarien)
        } else if(burgerChoice == "Cheeseburger"){
            binding.burgerImage.setImageResource(R.drawable.cheeseburger)
        } else if(burgerChoice == "Burger Montagnard"){
            binding.burgerImage.setImageResource(R.drawable.burger_montagnard)
        }

        binding.confirmButton.setOnClickListener {
            openGmail("marc.mollinari@gmail.com")
        }
    }



    private fun openGmail(email: String){
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${email}"))
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Confirmation commande")
        intent.putExtra(Intent.EXTRA_TEXT, "Votre commande a bien été enregistrée")
        startActivity(intent)

    }
}