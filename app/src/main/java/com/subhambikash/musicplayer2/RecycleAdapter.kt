package com.subhambikash.musicplayer2

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapter: RecyclerView.Adapter<RecycleAdapter.viewholder> {

    val mp: MediaPlayer = MediaPlayer()
    val context:Context
    var songitem=arrayListOf<soninfo>()

    constructor(context: Context, songitem: ArrayList<soninfo>) : super() {
        this.context = context
        this.songitem = songitem
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {

        val view=LayoutInflater.from(context).inflate(R.layout.musicitem,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {

        val song=songitem[position]
        holder.tittle.text=song.tittle
        holder.author.text=song.author



        holder.play.setOnClickListener {

            if (mp.isPlaying)
            {
                mp.stop()
                mp.reset()
                holder.play.visibility=View.VISIBLE
                holder.stop.visibility=View.GONE
                notifyDataSetChanged()
            }
            if (!mp.isPlaying){
                try {

                    Toast.makeText(context,holder.play.text, Toast.LENGTH_SHORT).show()
                    mp.setDataSource(song.songurl)
                    mp.prepare()
                    mp.start()
                    holder.play.visibility=View.GONE
                    holder.stop.visibility=View.VISIBLE
                }catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }

        }


        holder.stop.setOnClickListener {
            Toast.makeText(context,holder.stop.text, Toast.LENGTH_SHORT).show()
            mp.stop()
            mp.reset()
            holder.play.visibility=View.VISIBLE
            holder.stop.visibility=View.GONE
        }


    }
    override fun getItemCount(): Int {
       return songitem.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      var tittle=itemView.findViewById<TextView>(R.id.tittle)
        var author=itemView.findViewById<TextView>(R.id.author)
        var play=itemView.findViewById<Button>(R.id.play)
        var stop=itemView.findViewById<Button>(R.id.stop)
    }

}