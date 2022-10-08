package com.yusufcancakmak.chattapp

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.UUID

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth=FirebaseAuth.getInstance()

            button_register.setOnClickListener {
            val emailregister = email_edittext_register.text.toString()
            val passwordregister = password_edittext_register.text.toString()
                if (emailregister.isEmpty() || passwordregister.isEmpty()){
                    Toast.makeText(this,"Plase enter text in email/pw",Toast.LENGTH_SHORT).show()
                }
            //Firebase Authentication to create a user with login and register
            auth.createUserWithEmailAndPassword(emailregister,passwordregister).addOnCompleteListener {task ->
                if (task.isSuccessful){
                   Log.d("Main","createuserwithemail:succes")
                uploadImageToFirebaseStrorage()

                }
            }.addOnFailureListener {
                Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        }
        already_have_accont_text.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            Log.d("Mainactivty", "Try show to login activty")
        }
        imageview_register.setOnClickListener {
            Log.d("MainActivity", "Trt to show photo selector")
            val intent= Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,0)
        }
    }
        var selectPhotoUri : Uri? =null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==0&&resultCode==Activity.RESULT_OK && data!=null){
            Log.d("RegisterActivity","photo was selected")
            selectPhotoUri =data.data

            val bitmap =MediaStore.Images.Media.getBitmap(contentResolver,selectPhotoUri)
            selectphot_image_register.setImageBitmap(bitmap)

         //   val bitmapDrawable=BitmapDrawable(bitmap)
           // imageview_register.setBackgroundDrawable(bitmapDrawable)
        }
    }
    private fun uploadImageToFirebaseStrorage(){
        if (selectPhotoUri ==null)return
        val filename=UUID.randomUUID().toString()
        val ref=FirebaseStorage.getInstance().getReference("/images/"+filename)

        ref.putFile(selectPhotoUri!!).addOnSuccessListener{
            Log.d("Register","Successfully uploaded image :")

            ref.downloadUrl.addOnSuccessListener {
                Log.d("Register","File Location: "+it)

                saveUserTodDatabase(it.toString())
            }
        }.addOnFailureListener{
            // do some logging here
        }
    }
    private fun saveUserTodDatabase(profileImageUrl: String){
       val uid= FirebaseAuth.getInstance().uid?:""
       val ref= FirebaseDatabase.getInstance().getReference("/users$uid")
       val user= User(uid, username_edittext_register.text.toString(),profileImageUrl)
        ref.setValue(user).addOnSuccessListener {
            Log.d("RegisterActivity","Finally we saved the user to Firebase Database")
            val intent=Intent(this,MessagesActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}
    class User(val Uid: String,val username: String, val profileImageUrl: String)