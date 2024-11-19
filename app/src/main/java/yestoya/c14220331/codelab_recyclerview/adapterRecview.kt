package yestoya.c14220331.codelab_recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapterRecView (private val listWayang: ArrayList<wayang>) : RecyclerView.Adapter<adapterRecView.listViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {

        fun onItemClicked(data: wayang)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class listViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _namaWayang = itemView.findViewById<TextView>(R.id.namaWayang)
        var _karakterWayang = itemView.findViewById<TextView>(R.id.karakterWayang)
        var _deskripsiWayang = itemView.findViewById<TextView>(R.id.deskripsiWayang)
        var _gambarWayang = itemView.findViewById<ImageView>(R.id.gambarWayang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)

        return listViewHolder(view)
    }

    override fun getItemCount(): Int {

        return listWayang.size
    }

    override fun onBindViewHolder(holder: listViewHolder, position: Int) {
        var wayang = listWayang[position]
        holder._namaWayang.setText(wayang.nama)
        holder._karakterWayang.setText(wayang.karakter)
        holder._deskripsiWayang.setText(wayang.deskripsi)
        Log.d("TEST", wayang.foto)
        Picasso.get().load(wayang.foto).into(holder._gambarWayang)

        holder._gambarWayang.setOnClickListener {
//            Toast.makeText(holder.itemView.context, "Kamu memilih " + wayang.nama, Toast.LENGTH_LONG).show()
            onItemClickCallback.onItemClicked(listWayang[position])
        }


    }
}