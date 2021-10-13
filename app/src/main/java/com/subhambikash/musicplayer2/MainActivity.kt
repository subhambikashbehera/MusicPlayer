package com.subhambikash.musicplayer2

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.musicitem.*
import kotlinx.android.synthetic.main.musicitem.view.*

class MainActivity : AppCompatActivity() {

    var listsongs= arrayListOf<soninfo>()
    lateinit var songadapter: RecycleAdapter
    lateinit var recyclerview:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
    {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1023)
    }else
    {
        loadsongs()
    }

    recyclerview=findViewById(R.id.listview)
        songadapter= RecycleAdapter(this,listsongs)
        val linearLayout=LinearLayoutManager(this)
        recyclerview.adapter=songadapter
        recyclerview.layoutManager=linearLayout
        songadapter.notifyDataSetChanged()
    }

    @SuppressLint("Recycle")
    private fun loadsongs() {

        val uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection=MediaStore.Audio.Media.IS_MUSIC+"!=0"
         val resultSet=contentResolver.query(uri,null,selection,null,null)

        if (resultSet != null)
        {
            while (resultSet.moveToNext())
            {
                val url=resultSet.getString(resultSet.getColumnIndex(MediaStore.Audio.Media.DATA))
                val tittle=resultSet.getString(resultSet.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                 val author=resultSet.getString(resultSet.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                listsongs.add(soninfo(tittle,author,url))
            }
        }
//        songadapter=adapter(listsongs)
//        listview.adapter=songadapter

    }






//    inner class adapter(var songlist: ArrayList<soninfo>) : BaseAdapter() {
//
//        override fun getCount(): Int {
//            return songlist.size
//        }
//
//        override fun getItem(position: Int): Any {
//            return songlist[position]
//        }
//
//        override fun getItemId(position: Int): Long {
//           return position.toLong()
//        }
//
//        @SuppressLint("ViewHolder")
//        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//           val view:View=layoutInflater.inflate(R.layout.musicitem,parent,false)
//           val song=songlist[position]
//            view.tittle.text=song.tittle
//            view.author.text=song.author
//
//            val mp:MediaPlayer=MediaPlayer()
//
//            view.play.setOnClickListener {
//                Toast.makeText(this@MainActivity,view.play.text,Toast.LENGTH_SHORT).show()
//                if (mp.isPlaying)
//                {
//                    mp.stop()
//                    mp.reset()
//                    view.play.visibility=View.VISIBLE
//                    view.stop.visibility=View.GONE
//                }else
//                {
//                    try {
//                        mp.setDataSource(song.songurl)
//                        mp.prepare()
//                        mp.start()
//                    }catch (e:Exception)
//                    {
//                        e.printStackTrace()
//                    }
//                    view.play.visibility=View.GONE
//                    view.stop.visibility=View.VISIBLE
//                }
//            }
//            view.stop.setOnClickListener {
//                Toast.makeText(this@MainActivity,view.stop.text,Toast.LENGTH_SHORT).show()
//                mp.stop()
//                mp.reset()
//                view.play.visibility=View.VISIBLE
//                view.stop.visibility=View.GONE
//            }
//
//
//
//            return view
//        }
//    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode==1023 && grantResults[0] != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this,"permission is needed for fetching the songs",Toast.LENGTH_SHORT).show()
        }
    }
}