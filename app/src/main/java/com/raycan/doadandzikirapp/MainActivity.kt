package com.raycan.doadandzikirapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var llSunnahQauliyah: LinearLayout
    private lateinit var llDzikirSetiapSaat: LinearLayout
    private lateinit var llDoaHarian: LinearLayout
    private lateinit var llDzikirPagiPetang: LinearLayout
    private lateinit var vpArtikel: ViewPager2
    private lateinit var sliderDots: LinearLayout

    private lateinit var dotsIndicator: Array<ImageView?>
    private var listArtikel: ArrayList<Artikel> = arrayListOf()

    private val slidingCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
           for (i in 0 until listArtikel.size) {
               dotsIndicator[i]?.setImageDrawable(
                   ContextCompat.getDrawable(applicationContext, R.drawable.dot_inactive)
               )
           }
            dotsIndicator[position]?.setImageDrawable(
                ContextCompat.getDrawable(applicationContext, R.drawable.dot_active)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initData()
        initView()

        setupViewPager()
    }

    private fun setupViewPager() {
        dotsIndicator = arrayOfNulls(listArtikel.size)
        for (i in 0 until listArtikel.size) {
            dotsIndicator[i] = ImageView(this)
            dotsIndicator[i]?.setImageDrawable(
                ContextCompat.getDrawable(applicationContext, R.drawable.dot_inactive)
            )

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8,0,8,0)
            params.gravity = Gravity.CENTER_VERTICAL
            sliderDots.addView(dotsIndicator[i], params)
        }
    }

    private fun initView() {
        llSunnahQauliyah = findViewById(R.id.ll_sunnah_qauliyah)
        llDzikirSetiapSaat = findViewById(R.id.ll_dzikir_setiap_saat)
        llDoaHarian = findViewById(R.id.ll_doa_harian)
        llDzikirPagiPetang = findViewById(R.id.ll_dzikir_pagi_petang)

        llSunnahQauliyah.setOnClickListener(this)
        llDzikirSetiapSaat.setOnClickListener(this)
        llDoaHarian.setOnClickListener(this)
        llDzikirPagiPetang.setOnClickListener(this)

        vpArtikel = findViewById(R.id.vp_article)
        vpArtikel.adapter = ArtikelAdapter(listArtikel)
        vpArtikel.registerOnPageChangeCallback(slidingCallback)

        sliderDots = findViewById(R.id.ll_slider_dots)
    }

    private fun initData() {
        val titleArtikel = resources.getStringArray(R.array.title_artikel)
        val descArtikel = resources.getStringArray(R.array.desc_artikel)
        val imgArtikel = resources.obtainTypedArray(R.array.img_artikel)

        listArtikel.clear()

        for (data in titleArtikel.indices){
            val artikel = Artikel(
                imgArtikel.getResourceId(data, 0),
                titleArtikel[data],
                descArtikel[data]
            )
            listArtikel.add(artikel)
        }
        imgArtikel.recycle()
    }

    override fun onClick(view: View?) {
//

        when (view?.id) {
            R.id.ll_sunnah_qauliyah -> startActivity(Intent(this, QauliyahShalatActivity::class.java))
            R.id.ll_doa_harian -> startActivity(Intent(this, HarianDzikirDoaActivity::class.java))
            R.id.ll_dzikir_pagi_petang -> startActivity(Intent(this, PagiPetangDzikirActivity::class.java))
            R.id.ll_dzikir_setiap_saat -> startActivity(Intent(this, SetiapSaatDzikirActivity::class.java))


        }
    }

}