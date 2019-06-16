package com.example.nearbyshop

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_items_frag.*
import java.io.ByteArrayOutputStream


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [items_frag.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [items_frag.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class items_frag : Fragment() {
    // TODO: Rename and change types of parameters
    var x: ImageView? = null
    var uri = ArrayList<Uri>()
    var l: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_items_frag, container, false)
        var z = 100
        var ed = ArrayList<EditText>()
        var ed1 = ArrayList<EditText>()
        var ed2 = ArrayList<EditText>()
        var spin1 = ArrayList<Spinner>()
        var img2 = ArrayList<ImageView>()
        val upload=view.findViewById<Button>(R.id.upload)


        upload.setOnClickListener {
            for (j in uri.size downTo 0) {
                if (j == 0) {
                    startActivity(Intent(this.activity,Order::class.java))
                } else {
                    var sRef = FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().uid.toString())
                    var child_ref = sRef.child(ed.get(j - 1).text.toString())
                    var file_ref = child_ref.child(ed.get(j - 1).toString() + ".png")
                    file_ref.putFile(uri.get(j - 1)!!).addOnSuccessListener {
                        var m: Uri? = null

                        file_ref.downloadUrl.addOnCompleteListener() {
                            m = it.result
                            var dBase = FirebaseDatabase.getInstance()
                            var dRef = dBase.getReference("items")
                            var uid = FirebaseAuth.getInstance().uid
                            var child_db_dRef = dRef.child(
                                uid.toString() + "/" + spin1.get(j - 1).selectedItem.toString() + "/" + ed.get(j - 1).text.toString()
                            )
                            child_db_dRef.child("name").setValue(ed.get(j - 1).text.toString())
                            child_db_dRef.child("url").setValue(m.toString())
                            child_db_dRef.child("price").setValue(ed1.get(j - 1).text.toString())
                            child_db_dRef.child("quantity").setValue(ed2.get(j - 1).text.toString())
                            child_db_dRef.child("list").setValue(spin1.get(j - 1).selectedItem.toString())
                        }

                    }
                }
            }

        }//upload



        val add=view.findViewById<Button>(R.id.add)


        add.setOnClickListener {
            val p = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val p1 = LinearLayout.LayoutParams(250, 300)
            val g = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val s = LinearLayout.LayoutParams(250, ViewGroup.LayoutParams.WRAP_CONTENT)
            s.gravity = Gravity.CENTER
            val v = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2)
            var ll1 = LinearLayout(this.context)
            ll1.layoutParams = g
            ll1.orientation = LinearLayout.HORIZONTAL

            val et = EditText(this.context)
            et.layoutParams = p
            et.setHint("Name of product")
            ed.add(et)

            val et1 = EditText(this.context)
            et1.layoutParams = p
            et1.id = z + 1
            et1.setHint("Quantity")
            ed1.add(et1)

            val et2 = EditText(this.context)
            et2.layoutParams = p
            et2.id = z + 3
            et2.setHint("Price")
            ed2.add(et2)


            val img1 = ImageView(this.context)
            img1.layoutParams = p1
            img1.id = z + 4
            img1.setImageURI(l)
            img1.setOnClickListener {
                x = img1
                imageEnroll(img1)
            }
            img2.add(img1)


            var spin = Spinner(this.context)
            spin.id = z + 5
            spin.layoutParams = s
            arrayAdapter(spin)
            spin1.add(spin)


            var view = View(this.context)
            view.layoutParams = v
            view.setBackgroundColor(Color.BLACK)

            ll.addView(ll1)
            ll1.addView(img1)
            ll1.addView(et)
            ll1.addView(et1)
            ll1.addView(et2)
            ll.addView(spin)
            ll.addView(view)
            z + 5
        }
        return view
    }

    fun arrayAdapter(spin: Spinner) {
        val list = arrayOf("list", "Grocery", "Beverages", "Choclates")
        val dataAdapter = ArrayAdapter<String>(
            this.context,
            android.R.layout.simple_spinner_item, list
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.setAdapter(dataAdapter)

    }//arrayAdapter


    fun imageEnroll(x: ImageView) {
        var aDialog = AlertDialog.Builder(this.context!!)
        aDialog.setTitle("Message")
        aDialog.setMessage("Take a photo/select a file for adding an attachment")
        aDialog.setPositiveButton("Camera",
            DialogInterface.OnClickListener { dialogInterface, i ->
                if (ContextCompat.checkSelfPermission(this.context!!, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        this.context!!,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is not granted
                    var i = Intent()
                    i.setAction("android.media.action.IMAGE_CAPTURE")
                    startActivityForResult(i, 123)
                } else {
                    ActivityCompat.requestPermissions(
                        this.activity!!,
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        123
                    )
                }

            })
        aDialog.setNegativeButton("File Explorer",
            DialogInterface.OnClickListener { dialogInterface, i ->
                if (ContextCompat.checkSelfPermission(this.context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    var i = Intent()
                    i.setAction(Intent.ACTION_GET_CONTENT)
                    i.setType("*/*")
                    startActivityForResult(i, 124)
                } else {
                    ActivityCompat.requestPermissions(
                        this.activity!!,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        123
                    )
                }

            })
        aDialog.setNeutralButton("Cancel",
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
        aDialog.show()
    } //imageEnroll


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            var bmp = data?.extras?.get("data") as Bitmap
            var z: Uri = getImageUri(this.context!!, bmp)
            uri.add(z)
            x!!.setImageURI(z)
            l = z
        } else if (requestCode == 124 && resultCode == Activity.RESULT_OK) {
            var z: Uri? = data?.data
            uri.add(z!!)
            x!!.setImageURI(z)
            l = z
        }


    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)

    }//getImageUri

}
