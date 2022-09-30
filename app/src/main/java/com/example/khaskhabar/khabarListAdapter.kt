package com.example.khaskhabar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


@Suppress("DEPRECATION")
class KhabarListAdapter( private val  listener : KhabarKholo) : RecyclerView.Adapter<KhabarViewHolder>() {

    private val items : ArrayList<Khabar> =  ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KhabarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_khabar,parent,false)
        val viewHolder = KhabarViewHolder(view)
            view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: KhabarViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imgUrl).into(holder.image)
    }
    fun updateKhabar(updatedKhabar : ArrayList<Khabar>){
        items.clear()
        items.addAll(updatedKhabar)
        notifyDataSetChanged()
    }

}
class KhabarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val titleView : TextView = itemView.findViewById(R.id.upadhi)
    val image : ImageView = itemView.findViewById(R.id.image)
    val author : TextView = itemView.findViewById(R.id.author)
}

interface KhabarKholo{
    fun onItemClicked(item : Khabar)
}