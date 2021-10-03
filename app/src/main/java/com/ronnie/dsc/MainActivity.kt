package com.ronnie.dsc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private val fireStoreDatabase = FirebaseFirestore.getInstance()
    private val userList = ArrayList<User>()
    private lateinit var textViewShow: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSave = findViewById<Button>(R.id.save)
        val btnretrive = findViewById<Button>(R.id.retrieve)
        textViewShow = findViewById<TextView>(R.id.showText)

        btnSave.setOnClickListener {
            save()
        }
        btnretrive.setOnClickListener {
            retrieve()
        }

    }

    private fun retrieve() {
        fireStoreDatabase.collection("Users")
            .get()
            .addOnSuccessListener { snapshots ->
                snapshots.forEach { snapshot ->
                    val user = snapshot.toObject(User::class.java)
                    userList.add(user)
                }

                var textToShow: String = ""

                userList.forEach { user ->
                    textToShow += user.toString()
                }

                textViewShow.text = textToShow


            }
            .addOnFailureListener {

            }
    }

    private fun save() {

        //To use hashmap
        //to use data classes

        val userRonnie = hashMapOf(
            "name" to "Ronnie",
            "company" to "Amarula",
            "age" to 26,
            "school" to School("TUK")
        )

        //or

        val user1 = User("Juliet", 19, "Nyeri", School("DKUT"))

        fireStoreDatabase.collection("Users").add(userRonnie)
            .addOnSuccessListener {
                Toast.makeText(this, "User with id ${it.id} has been saved", Toast.LENGTH_SHORT)
                    .show()

            }.addOnFailureListener {
                Toast.makeText(this, "There was failure $it", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}