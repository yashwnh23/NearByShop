package com.example.nearbyshop

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.View
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
import android.view.ViewGroup
import android.widget.*
import android.widget.ArrayAdapter
import kotlin.math.sign


class ItemsList : AppCompatActivity() {
    var x:ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_list)
        var z = 100
        var ed = arrayOf<EditText>()
        var ed1 = arrayOf<EditText>()
        var ed2 = arrayOf<EditText>()
        var img2 = arrayOf<ImageView>()
        var sp1 = arrayOf<Spinner>()
        var j = 0

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
             }
         }


      }//upload




     listItems.setOnClickListener {

         startActivity(Intent(this@ItemsList,
             Items::class.java))

     }   //listItems



      add.setOnClickListener {
          val p = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
          val p1 = LinearLayout.LayoutParams(250,300)
          val g = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
          val s = LinearLayout.LayoutParams(250,ViewGroup.LayoutParams.WRAP_CONTENT)
          s.gravity = Gravity.CENTER
          val v = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2)
          var ll1 = LinearLayout(this)
          ll1.layoutParams = g
          ll1.orientation = LinearLayout.HORIZONTAL

          val et = EditText(this)
          et.layoutParams = p
          et.id = z + 2
          et.setHint("Name of product")
          ed.set(j,et)

          val et1 = EditText(this)
          et1.layoutParams = p
          et1.id = z + 1
          et1.setHint("Quantity")
          ed1.set(j,et1)

          val et2 = EditText(this)
          et2.layoutParams = p
          et2.id = z + 3
          et2.setHint("Price")
          ed2.set(j,et2)


          val img1 = ImageView (this)
          img1.layoutParams = p1
          img1.id = z + 4
          img1.setImageURI(uri)
          img1.setOnClickListener{
              x=img1
              imageEnroll(img1)
          }
          img2.set(j,img1)



          var spin = Spinner(this)
          spin.id = z + 5
          spin.layoutParams = s
          arrayAdapter(spin)
          sp1.set(j,spin)



          var view = View(this)
          view.layoutParams = v
          view.setBackgroundColor(Color.BLACK)

          ll.addView(ll1)
          ll1.addView(img1)
          ll1.addView(et)
          ll1.addView(et1)
          ll1.addView(et2)
          ll.addView(spin)
          ll.addView(view)
          z+5
          j++
      }

      img.setOnClickListener {
        x=img
        imageEnroll(img)
      }//img


    } // onCreate( )

    var uri:Uri? = null

    fun arrayAdapter(spin:Spinner){
        val list = arrayOf("list", "Grocery", "Beverages" , "Choclates")
        val dataAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, list
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.setAdapter(dataAdapter)

    }//arrayAdapter





    fun imageEnroll(x:ImageView) {
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
    } //imageEnroll







    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 123 && resultCode == Activity.RESULT_OK)
        {
            var bmp = data?.extras?.get("data") as Bitmap
            uri = getImageUri(this@ItemsList, bmp)
            x!!.setImageURI(uri)
        }else  if(requestCode == 124 && resultCode == Activity.RESULT_OK)
        {
            uri = data?.data
            x!!.setImageURI(uri)
        }

    }  // onActivityResult( )






    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)

    }//getImageUri




}//Activity

