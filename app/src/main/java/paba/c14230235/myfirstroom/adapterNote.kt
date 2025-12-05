package paba.c14230235.myfirstroom

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import paba.c14230235.myfirstroom.database.Note

class adapterNote (private val listNote: MutableList<Note>) :
    RecyclerView.Adapter<adapterNote.ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycle, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int
    ) {
        var note = listNote[position]

        holder._tvJudul.setText(note.judul)
        holder._tvDeskripsi.setText(note.deskripsi)
        holder._tvTanggal.setText(note.tanggal)

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahData::class.java)
            intent.putExtra("noteId", note.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(note)
        }
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    // ListViewHolder
    class ListViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        var _tvJudul = view.findViewById<TextView>(R.id.tvJudul)
        var _tvDeskripsi = view.findViewById<TextView>(R.id.tvDeskripsi)
        var _tvTanggal = view.findViewById<TextView>(R.id.tvTanggal)

        var _btnEdit = view.findViewById<ImageButton>(R.id.btnEdit)
        var _btnDelete = view.findViewById<ImageButton>(R.id.btnDelete)
    }

    // -- Fungsi Tambahan -- //
    // Isi Data
    fun isiData(list: List<Note>) {
        listNote.clear()
        listNote.addAll(list)
        notifyDataSetChanged()
    }

    // Set onItemClickCallback
    private lateinit var onItemClickCallback : OnItemClickCallback
    interface OnItemClickCallback {
        fun delData(dtNote: Note)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}