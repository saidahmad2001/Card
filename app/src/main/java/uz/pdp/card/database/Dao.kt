package uz.pdp.card.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.pdp.card.model.Card

@Dao
interface Dao {
    @Insert()
    fun addCard(card: Card)

    @Query("SELECT * FROM cards")
    fun getCards(): List<Card>
}