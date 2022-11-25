package com.example.consomationapirec

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class MainActivity : AppCompatActivity() {
    //declaration
    lateinit var idUser: TextView
    lateinit var idname: TextView
    lateinit var idusername: TextView
    lateinit var idbtn: Button
    lateinit var loadingPB: ProgressBar
    lateinit var loadingPBSECOND: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        idUser = findViewById(R.id.idUser)
        idname = findViewById(R.id.idname)
        idusername = findViewById(R.id.idusername)

        idbtn = findViewById(R.id.idBtn)
        loadingPB = findViewById(R.id.idLoadingPB)
        loadingPBSECOND = findViewById(R.id.loadingPBSECOND)


        // on below line we are creating a method
        // to get data from api using retrofit.
        getData()
    }

    private fun getData() {

        // building and passing our base url
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/users/")


            //  we are building our retrofit builder.
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        //  create an instance for our retrofit api class.
        val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

        val call: Call<UsersDataModal?>? = retrofitAPI.getCourse()

        // on below line we are making a call.
        call!!.enqueue(object : Callback<UsersDataModal?> {
            override fun onResponse(
                call: Call<UsersDataModal?>?,
                response: Response<UsersDataModal?>
            ) {
                if (response.isSuccessful()) {
                    loadingPB.visibility = View.GONE
                    loadingPBSECOND.visibility = View.GONE
                    Toast.makeText(this@MainActivity, " Loading data..", Toast.LENGTH_SHORT)
                        .show()
                    // on below line we are getting data from our response
                    // and setting it in variables.
                    val User: Int = response.body()!!.id
                    val name: String = response.body()!!.name
                    val username: String = response.body()!!.username
                    val email: String = response.body()!!.email

                    // on below line we are setting our data
                    idUser.text = User.toString() // cuz use is an int sp we added tostring
                    idname.text = name
                    idusername.text = username

                    Toast.makeText(this@MainActivity, name, Toast.LENGTH_SHORT)
                        .show()
                    print("hellllllllllllllllllllllllllllllllllllllllllll")
                    // on below line we are changing visibility for our button.
                    idbtn.visibility = View.VISIBLE

                    // on below line we are adding click listener for our button.
                    idbtn.setOnClickListener {
                        // on below line we are opening a intent to view the url.
                        val i = Intent(Intent.ACTION_VIEW)
                        i.setData(Uri.parse(email))
                        startActivity(i)
                    }
                }
            }

            override fun onFailure(call: Call<UsersDataModal?>?, t: Throwable?) {
                // displaying an error message in toast
               // loadingPB.visibility = View.GONE

                Toast.makeText(this@MainActivity, "Fail to get the data..", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}