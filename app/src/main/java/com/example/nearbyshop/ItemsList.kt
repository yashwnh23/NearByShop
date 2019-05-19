package com.example.nearbyshop

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_items_list.*
import java.io.ByteArrayOutputStream
import kotlin.math.log
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_register.*


class ItemsList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_list)

      upload.setOnClickListener {

            var sRef = FirebaseStorage.getInstance().
                getReference(FirebaseAuth.getInstance().uid.toString())
            var child_ref = sRef.child(name1.text.toString())
            var file_ref =    child_ref.child(name1.text.toString()+".png")
         file_ref.putFile(uri!!).addOnSuccessListener {
             var m: Uri? = null
             file_ref.downloadUrl.addOnCompleteListener(){
                 m = it.result
                 var dBase = FirebaseDatabase.getInstance()
                 var dRef = dBase.getReference("items")
                 var uid = FirebaseAuth.getInstance().uid
                 var child_db_dRef = dRef.child(uid.toString()+name1.text.toString())
                 child_db_dRef.child("name").setValue(name1.text.toString())
                 child_db_dRef.child("profile_pic_url").
                     setValue(m.toString())
                 child_db_dRef.child("price").
                     setValue(price.text.toString())
                 child_db_dRef.child("quantity").
                     setValue(quantity.text.toString())
                 child_db_dRef.child("list").
                     setValue(spin.selectedItem.toString())

                 startActivity(Intent(this@ItemsList,
                     Items::class.java))
             }
         }
      }//upload

      img.setOnClickListener {
            var aDialog = AlertDialog.Builder(this@ItemsList)
            aDialog.setTitle("Message")
            aDialog.setMessage("Take a photo/select a file for adding an attachment")
            aDialog.setPositiveButton("Camera",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    var i = Intent( )
                    i.setAction("android.media.action.IMAGE_CAPTURE")
                    startActivityForResult(i,123)
                })
            aDialog.setNegativeButton("File Explorer",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    var i = Intent( )
                    i.setAction(Intent.ACTION_GET_CONTENT)
                    i.setType("*/*")
                    startActivityForResult(i,124)

                })
            aDialog.setNeutralButton("Cancel",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
            aDialog.show()

      }//img


    } // onCreate( )

    var uri:Uri? = null

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 123 && resultCode == Activity.RESULT_OK)
        {
            var bmp = data?.extras?.get("data") as Bitmap
            uri = getImageUri(this@ItemsList, bmp)
            img.setImageURI(uri)
        }else  if(requestCode == 124 && resultCode == Activity.RESULT_OK)
        {
            uri = data?.data
            img.setImageURI(uri)
        }

    }  // onActivityResult( )

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)

    }




}//Activity

