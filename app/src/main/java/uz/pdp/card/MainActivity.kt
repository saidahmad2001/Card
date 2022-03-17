package uz.pdp.card

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.pdp.card.adapter.CardAdapter
import uz.pdp.card.database.AppDatabase
import uz.pdp.card.model.Card
import uz.pdp.card.networking.ApiClient
import uz.pdp.card.networking.Service

class MainActivity : AppCompatActivity() {

    private lateinit var service: Service
    private lateinit var ivAddCard: ImageView
    private lateinit var rvCards: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {

        window.statusBarColor = Color.parseColor("#393939")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service = ApiClient.createService(Service::class.java)
        appDatabase = AppDatabase.getInstance(this)

        initViews()
    }

    private fun initViews() {
        rvCards = findViewById(R.id.rvCards)
        ivAddCard = findViewById(R.id.ivAddCard)
        cardAdapter = CardAdapter()
        getCards()
        refreshAdapter()

        ivAddCard.setOnClickListener {
            addCard()
        }
    }

    val detailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data: Intent? = it.data
            val cardToAdd = data?.getParcelableExtra<Card>("card")

            saveCard(cardToAdd!!)
        }
    }

    private fun saveCard(card: Card) {
        if (isInternetAvailable()){
            service.addCard(card).enqueue(object :Callback<Card>{
                override fun onResponse(call: Call<Card>, response: Response<Card>) {

                }

                override fun onFailure(call: Call<Card>, t: Throwable) {

                }

            })
        }
    }

    private fun addCard() {
        val intent = Intent(this, AddCardActivity::class.java)
        detailLauncher.launch(intent)
    }

    private fun refreshAdapter() {
        rvCards.adapter = cardAdapter
    }

    private fun getCards() {
        service.getCards().enqueue(object : Callback<List<Card>> {
            override fun onResponse(call: Call<List<Card>>, response: Response<List<Card>>) {
                cardAdapter.submitData(response.body()!!)
            }

            override fun onFailure(call: Call<List<Card>>, t: Throwable) {

            }
        })
    }

    private fun isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }
}