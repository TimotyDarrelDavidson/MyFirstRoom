package paba.c14230235.myfirstroom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import paba.c14230235.myfirstroom.database.Note
import paba.c14230235.myfirstroom.database.NoteRoomDatabase
import paba.c14230235.myfirstroom.helper.DateHelper.getCurrentDate

class TambahData : AppCompatActivity() {

    val DB = NoteRoomDatabase.getDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_data)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var tanggal : String = getCurrentDate()

        val _etJudul = findViewById<EditText>(R.id.etJudul)
        val _etDeskripsi = findViewById<EditText>(R.id.etDeskripsi)
        val _btnTambah = findViewById<Button>(R.id.btnTambah)
        val _btnUpdate = findViewById<Button>(R.id.btnUpdate)

        _btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.funNoteDao().insert(
                    Note(
                        0,
                        _etJudul.text.toString(),
                        _etDeskripsi.text.toString(),
                        tanggal
                    )
                )

            }
        }


    }
}