package yestoya.c14220331.codelab_recyclerview

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {
    private lateinit var _nama : Array<String>
    private lateinit var _deskripsi : Array<String>
    private lateinit var _karakter : Array<String>
    private lateinit var _foto : Array<String>

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

        _rvWayang = findViewById(R.id.rvWayang)
        siapkanData()
        tambahData()
        tampilkanData()
    }

    fun siapkanData(){
        _nama = resources.getStringArray(R.array.namaWayang)
        _deskripsi = resources.getStringArray(R.array.deskripsiWayang)
        _karakter = resources.getStringArray(R.array.karakterUtamaWayang)
        _foto = resources.getStringArray(R.array.gambarWayang)
    }

    fun tambahData(){
        for (i in _nama.indices){
            val data = wayang(
                _foto[i],
                _nama[i],
                _karakter[i],
                _deskripsi[i]
            )
            listWayang.add(data)
        }
    }

    fun tampilkanData(){
//        _rvWayang.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        _rvWayang.layoutManager = GridLayoutManager(this, 2)

        val adapterWayang = adapterRecView(listWayang)
        _rvWayang.adapter = adapterWayang

        adapterWayang.setOnItemClickCallback(object : adapterRecView.OnItemClickCallback{
            override fun onItemClicked(data: wayang) {
//                Toast.makeText(this@MainActivity, data.nama, Toast.LENGTH_LONG).show()

                val intent = Intent(this@MainActivity, detWayang::class.java)
                intent.putExtra("Kirim Data", data)
                startActivity(intent)
            }

        })
    }
}