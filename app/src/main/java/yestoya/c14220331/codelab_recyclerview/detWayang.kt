package yestoya.c14220331.codelab_recyclerview

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class detWayang : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_det_wayang)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var _ivFoto = findViewById<ImageView>(R.id.ivFoto)
        var _tvNama = findViewById<TextView>(R.id.tvNama)
        var _tvDetail = findViewById<TextView>(R.id.tvDetail)

        val dataIntent = intent.getParcelableExtra<wayang>("Kirim Data", wayang::class.java)
        if (dataIntent != null) {
            Picasso.get().load(dataIntent.foto).into(_ivFoto)
            _tvNama.setText(dataIntent.nama)
            _tvDetail.setText(dataIntent.deskripsi)
        }

    }
}