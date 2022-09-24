package com.dark.captionadmin

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dark.captionadmin.Adapter.CategoryAdapter
import com.dark.captionadmin.Model.CatagoryModel
import com.dark.captionadmin.databinding.ActivityMainBinding
import com.dark.captionadmin.databinding.CatDialogBinding
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        db.collection("Captions").addSnapshotListener { value, error ->

            val list = arrayListOf<CatagoryModel>()
            val data = value?.toObjects(CatagoryModel::class.java)
            list.addAll(data!!)

            binding.idCatagory.layoutManager= LinearLayoutManager(this)
            binding.idCatagory.adapter= CategoryAdapter(this,list)
        }

        binding.addCat.setOnClickListener {
            val addCatDialog = Dialog(this@MainActivity)
            val binding = CatDialogBinding.inflate(layoutInflater)
            addCatDialog.setContentView(binding.root)
            addCatDialog.show()

             if(addCatDialog.window != null){
                 addCatDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
             }

            binding.addCatBtn.setOnClickListener {
                val name = binding.editCategoryName.text.toString()
                val id= db.collection("Captions").document().id
                val data =  CatagoryModel(id,name)
                db.collection("Captions").document(id).set(data).addOnSuccessListener {
                    Toast.makeText(this@MainActivity,"Category Added Successfully", Toast.LENGTH_SHORT).show()
                    addCatDialog.dismiss()
                }.addOnCanceledListener {
                    Toast.makeText(this@MainActivity,"Error"+it.toString(),Toast.LENGTH_SHORT).show()
                }
            }

        }


    }
}