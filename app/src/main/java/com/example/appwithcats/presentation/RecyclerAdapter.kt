package com.example.appwithcats.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appwithcats.domain.CatModel
import com.example.appwithcats.R


class RecyclerAdapter (var context: Context, var catList: MutableList<CatModel>):
    RecyclerView.Adapter<RecyclerAdapter.CatHolder>() {

    inner class CatHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private lateinit var cat: CatModel
        var imageCat: ImageView = itemView.findViewById(R.id.image)
        private var callbacks: Callbacks? = null

        init {
            imageCat.apply {
                setOnClickListener {
                    callbacks = context as Callbacks?
                    callbacks?.onCatSelected(cat.url)
                }
            }
        }

        fun bind(cat: CatModel) {
            this.cat = cat
            val url = cat.url
            Glide.with(context).load(url).into(imageCat)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatHolder {
        return CatHolder(
            LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CatHolder, position: Int) {
        val cat = catList[position]
        holder.bind(cat)
    }

    override fun getItemCount(): Int {
        return catList.size
    }
}
