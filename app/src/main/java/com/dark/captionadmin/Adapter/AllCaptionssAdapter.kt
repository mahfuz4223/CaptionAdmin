package com.dark.captionadmin.Adapter

import android.app.PendingIntent.getActivity
import android.content.*
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dark.captionadmin.AllCaption
import com.dark.captionadmin.Model.CaptionModel
import com.dark.captionadmin.databinding.ItemCaptionBinding
import com.google.firebase.firestore.FirebaseFirestore

import java.util.*

class AllCaptionssAdapter(
    val allCaption: AllCaption,
    val captionList: ArrayList<CaptionModel>,
    val catid: String
) : RecyclerView.Adapter<AllCaptionssAdapter.CaptionViewHolder>() {
    class CaptionViewHolder(val binding: ItemCaptionBinding) : RecyclerView.ViewHolder(binding.root)

    val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaptionViewHolder {
       return CaptionViewHolder(ItemCaptionBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CaptionViewHolder, position: Int) {
        holder.binding.quotesTEXT.text= captionList[position].data.toString()

        holder.binding.btnDelete.setOnClickListener{

            db.collection("Captions").document(catid).collection("All").document(captionList[position].id!!).delete().addOnSuccessListener {
                Toast.makeText(allCaption,"Delete Successful",Toast.LENGTH_SHORT).show()
            }
        }



    }

    override fun getItemCount()= captionList.size
}