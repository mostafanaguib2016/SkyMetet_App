package com.skymeter.skymeterapp.ui.shots_explorer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.skymeter.skymeterapp.R
import com.skymeter.skymeterapp.data.pojo.model.PicturesTable
import com.skymeter.skymeterapp.databinding.ItemPictureBinding
import com.skymeter.skymeterapp.ui.home.HomeFragment
import com.skymeter.skymeterapp.utils.RecyclerViewOnItemClickListener
import com.skymeter.skymeterapp.utils.getBitMap
import java.lang.Exception

class PicturesAdapter(val context: Context,
                      val navController: NavController,
                      val listener: RecyclerViewOnItemClickListener):
    RecyclerView.Adapter<PicturesAdapter.ViewHolder>()
{

    private val TAG = "PicturesAdapter"
    var list = ArrayList<PicturesTable>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_picture,parent,false
            )
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position],position)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val binding: ItemPictureBinding):
        RecyclerView.ViewHolder(binding.root){

            fun bind(item: PicturesTable,position: Int){


                val decodedByte =
                    getBitMap(item.picturePath)
                binding.image.setImageBitmap(decodedByte)



                binding.root.setOnClickListener {

                    Log.e(TAG, "bind:${item.id} " )
                    val bundle = Bundle()
                    bundle.putString("image",item.picturePath)
                    navController.navigate(R.id.viewShotFragment,bundle)

                }

                binding.ivDelete.setOnClickListener {
                    listener.onImageDelete(item.picturePath,position,item.id)
                }


            }

    }

    fun deleteImage(position: Int) {
        try {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
        catch (e: Exception){
            Log.e(TAG, "deleteImage: ${e.localizedMessage}" )
        }
    }

    fun submitData(list: ArrayList<PicturesTable>){
        this.list = list
        notifyDataSetChanged()

    }

}