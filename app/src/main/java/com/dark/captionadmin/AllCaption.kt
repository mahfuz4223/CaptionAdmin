package com.dark.captionadmin

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dark.captionadmin.Adapter.AllCaptionssAdapter
import com.dark.captionadmin.Model.CaptionModel
import com.dark.captionadmin.Model.CatagoryModel
import com.dark.captionadmin.databinding.ActivityAllCaptionBinding
import com.dark.captionadmin.databinding.CaptionDialogBinding
import com.dark.captionadmin.databinding.CatDialogBinding
import com.google.firebase.firestore.FirebaseFirestore

class AllCaption : AppCompatActivity() {
    lateinit var binding: ActivityAllCaptionBinding
    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityAllCaptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name=intent.getStringExtra("name")
        val id=intent.getStringExtra("id")

        db = FirebaseFirestore.getInstance()

        binding.back.setOnClickListener {
            onBackPressed()
        }


        binding.catagoryheaad.text= name.toString()

        db.collection("Captions").document(id!!).collection("All").addSnapshotListener{ value, error ->

            val captionList = arrayListOf<CaptionModel>()
            val data = value?.toObjects(CaptionModel::class.java)
            captionList.addAll(data!!)


            binding.allcaptionss.layoutManager= LinearLayoutManager(this)
            binding.allcaptionss.adapter = AllCaptionssAdapter(this,captionList,id)



            binding.addCaption.setOnClickListener{

                val addCatDialog = Dialog(this@AllCaption)
                val binding = CaptionDialogBinding.inflate(layoutInflater)
                addCatDialog.setContentView(binding.root)
                addCatDialog.show()

                if(addCatDialog.window != null){
                    addCatDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
                }

                binding.addCapBtn.setOnClickListener {
                    val uid =  db.collection("Captions").document().id

                    val editcaption = binding.editCaptionName.text.toString()

                    val finalValue = CaptionModel(uid,editcaption)

                    db.collection("Captions").document(id).collection("All").document(uid).set(finalValue).addOnSuccessListener {
                        Toast.makeText(this@AllCaption,"Caption Added Successfully", Toast.LENGTH_SHORT).show()
                        addCatDialog.dismiss()
                    }.addOnCanceledListener {
                        Toast.makeText(this@AllCaption,"Error"+it.toString(),Toast.LENGTH_SHORT).show()
                    }
                }





            }

        }




    }
}