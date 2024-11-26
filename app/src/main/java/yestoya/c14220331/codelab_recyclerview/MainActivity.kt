package yestoya.c14220331.codelab_recyclerview

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private var _nama : MutableList<String> = emptyList<String>().toMutableList()
    private var _deskripsi : MutableList<String> = emptyList<String>().toMutableList()
    private var _karakter : MutableList<String> = emptyList<String>().toMutableList()
    private var _foto : MutableList<String> = emptyList<String>().toMutableList()

    lateinit var sp : SharedPreferences


    private var listWayang  = arrayListOf<wayang>()

    private lateinit var _rvWayang : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sp = getSharedPreferences("dataSP", MODE_PRIVATE)

        val gson = Gson()
        val isiSp = sp.getString("spWayang", null)
        val type = object : TypeToken<ArrayList<wayang>> () {}.type
        if (isiSp != null){
            listWayang = gson.fromJson(isiSp, type)
        }

        _rvWayang = findViewById(R.id.rvWayang)
        if (listWayang.size == 0){
            siapkanData()
        }else{
            listWayang.forEach {
                _nama.add(it.nama)
                _deskripsi.add(it.deskripsi)
                _karakter.add(it.karakter)
                _foto.add(it.foto)
            }
            listWayang.clear()
        }
        tambahData()
        tampilkanData()
    }

    fun siapkanData(){
        _nama = resources.getStringArray(R.array.namaWayang).toMutableList()
        _deskripsi = resources.getStringArray(R.array.deskripsiWayang).toMutableList()
        _karakter = resources.getStringArray(R.array.karakterUtamaWayang).toMutableList()
        _foto = resources.getStringArray(R.array.gambarWayang).toMutableList()
    }

    fun tambahData(){
        val gson = Gson()
        val editor = sp.edit()

        listWayang.clear()
        for (i in _nama.indices){
            val data = wayang(
                _foto[i],
                _nama[i],
                _karakter[i],
                _deskripsi[i]
            )
            listWayang.add(data)
        }
        val json = gson.toJson(listWayang)
        editor.putString("spWayang", json)
        editor.apply()
    }

    fun tampilkanData(){
//        _rvWayang.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//        _rvWayang.layoutManager = GridLayoutManager(this, 2)
        _rvWayang.layoutManager = LinearLayoutManager(this)

        val adapterWayang = adapterRecView(listWayang)
        _rvWayang.adapter = adapterWayang

        adapterWayang.setOnItemClickCallback(object : adapterRecView.OnItemClickCallback{
            override fun onItemClicked(data: wayang) {
//                Toast.makeText(this@MainActivity, data.nama, Toast.LENGTH_LONG).show()

                val intent = Intent(this@MainActivity, detWayang::class.java)
                intent.putExtra("Kirim Data", data)
                startActivity(intent)
            }

            override fun delData(pos: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Hapus Data")
                    .setMessage("Apakah anda yakin menghapus data ini?")
                    .setPositiveButton(
                        "HAPUS",
                        DialogInterface.OnClickListener { dialog, which ->
                            _foto.removeAt(pos)
                            _nama.removeAt(pos)
                            _karakter.removeAt(pos)
                            _deskripsi.removeAt(pos)
                            tambahData()
                            tampilkanData()

                        }
                    )
                    .setNegativeButton(
                        "BATAL",
                        DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(
                                this@MainActivity,
                                "Batal menghapus data",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    ).show()
            }

        })
    }
}