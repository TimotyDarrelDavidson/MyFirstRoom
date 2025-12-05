package paba.c14230235.myfirstroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import paba.c14230235.myfirstroom.database.Note
import paba.c14230235.myfirstroom.database.NoteRoomDatabase

class MainActivity : ComponentActivity() {

    private lateinit var DB : NoteRoomDatabase
    private lateinit var adapter : adapterNote

    private var arNote : MutableList<Note> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        DB = NoteRoomDatabase.getDatabase(this)
        adapter = adapterNote(arNote)

        var _rvNote = findViewById<RecyclerView>(R.id.rvNote)
        _rvNote.layoutManager = LinearLayoutManager(this)
        _rvNote.adapter = adapter

        adapter.isiData(arNote)
        adapter.setOnItemClickCallback(

            object : adapterNote.OnItemClickCallback {

                override fun delData(dtNote: Note) {
                    CoroutineScope(Dispatchers.IO).async {
                        DB.funNoteDao().delete(dtNote)
                        val note = DB.funNoteDao().selectAll()
                        Log.d("data ROOM2", note.toString())

                        withContext(Dispatchers.Main) {
                            adapter.isiData(note)
                        }
                    }
                }
            }
        )

        val _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahData::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val note = DB.funNoteDao().selectAll()
            Log.d("data ROOM", note.toString())

            withContext(Dispatchers.Main) {
                adapter.isiData(note)
            }
        }
    }
}
