package com.dark.captionadmin.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dark.captionadmin.AllCaption
import com.dark.captionadmin.MainActivity
import com.dark.captionadmin.Model.CatagoryModel
import com.dark.captionadmin.databinding.ItemCatagoryBinding
import com.google.firebase.firestore.FirebaseFirestore


class CategoryAdapter(val mainActivity: MainActivity, val list: ArrayList<CatagoryModel>) : RecyclerView.Adapter<CategoryAdapter.CatViewHolder>() {

    class CatViewHolder(val binding: ItemCatagoryBinding) : RecyclerView.ViewHolder(binding.root)


    //connect firebase
    val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(ItemCatagoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.binding.itemText.text = list[position].name.toString()
        holder.binding.root.setOnClickListener {
//            val intent=Intent(mainActivity, AllCaption::class.java)
//            intent.putExtra("id",list[position].id)
//            intent.putExtra("name",list[position].name)
//
//            mainActivity.startActivity(intent)
        }


        holder.binding.btnDelete.setOnClickListener {

            db.collection("Captions").document(list[position].id!!).delete().addOnSuccessListener {
                Toast.makeText(mainActivity,"Delete Successful",Toast.LENGTH_SHORT).show()
            }


        }

        holder.binding.itemText.text = list[position].name.toString()
        holder.binding.root.setOnClickListener {
            val intent=Intent(mainActivity, AllCaption::class.java)
            intent.putExtra("id",list[position].id)
            intent.putExtra("name",list[position].name)

            mainActivity.startActivity(intent)
        }


    }

    override fun getItemCount() = list.size
}