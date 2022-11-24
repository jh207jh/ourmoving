
package com.example.edusungshinourmoving;

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.edusungshinourmoving.DBKey.Companion.DB_ARTICLES
import com.example.edusungshinourmoving.home.ArticleModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class MainActivity5: AppCompatActivity() {


    private var selectedUri: Uri? = null
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val storage: FirebaseStorage by lazy {
        Firebase.storage
    }

    private val articleDB: DatabaseReference by lazy {
        Firebase.database.reference.child(DB_ARTICLES)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val colors = arrayOf("카테고리 선택", "가구", "침구류", "욕실용품", "주방용품", "생활가전", "디지털 기기", "생활용품", "보안장치")
        val colorSpinner : Spinner = findViewById(R.id.colorSpinner)
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, colors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        colorSpinner.adapter = adapter

        findViewById<Button>(R.id.btn_pic).setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startContentProvider()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showPermissionContextPopup()
                }

                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1010)
                }
            }
        }
        var button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {

            var intent = Intent(applicationContext,MainActivity7::class.java)
            // 인텐트 보이기
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_register).setOnClickListener {
            val title = findViewById<EditText>(R.id.edit_Title).text.toString()
            val price = findViewById<EditText>(R.id.edit_price).text.toString()
            val sellerId = auth.currentUser?.uid.orEmpty()


            if (title.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "제목 및 가격 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showProgress()

            if (selectedUri != null) {
                val photoUri = selectedUri ?: return@setOnClickListener
                uploadPhoto(photoUri,
                    successHandler = { uri ->
                        uploadArticle(sellerId, title, price, uri)
                    },
                    errorHandler = {
                        Toast.makeText(this, "사진 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        hideProgress()
                    }
                )
            } else {

                uploadArticle(sellerId, title, price, "")
                val intent = Intent(applicationContext, MainActivity7::class.java)
                startActivity(intent)

            }

        }
    }

    private fun uploadPhoto(uri: Uri, successHandler: (String) -> Unit, errorHandler: () -> Unit) {
        val fileName = "${System.currentTimeMillis()}.png"
        storage.reference.child("article/photo").child(fileName)
            .putFile(uri)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    storage.reference.child("article/photo").child(fileName).downloadUrl
                        .addOnSuccessListener { uri ->
                            successHandler(uri.toString())
                        }.addOnFailureListener {
                            errorHandler()
                        }
                } else {
                    errorHandler()
                }
            }

    }


    private fun uploadArticle(sellerId: String, title: String, price: String, imageUrl: String) {
        val model = ArticleModel(sellerId, title, System.currentTimeMillis(), price, imageUrl)
        articleDB.push().setValue(model)
        hideProgress()
        Toast.makeText(this, "아이템이 등록되었습니다.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@MainActivity5, MainActivity7::class.java)
        startActivity(intent)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1010 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startContentProvider()
                } else {
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startContentProvider() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 2020)
    }

    private fun showProgress() {
        findViewById<ProgressBar>(R.id.progressBar).isVisible = true
    }

    private fun hideProgress() {
        findViewById<ProgressBar>(R.id.progressBar).isVisible = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            2020 -> {
                val uri = data?.data
                if(uri != null) {
                    findViewById<ImageView>(R.id.image_view).setImageURI(uri)
                    selectedUri = uri
                } else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("사진을 가져오기 위해 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1010)
            }
            .create()
            .show()
    }

}

