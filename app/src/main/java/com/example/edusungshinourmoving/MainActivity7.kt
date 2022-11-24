package com.example.edusungshinourmoving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edusungshinourmoving.DBKey.Companion.CHILD_CHAT
import com.example.edusungshinourmoving.DBKey.Companion.DB_ARTICLES
import com.example.edusungshinourmoving.DBKey.Companion.DB_USERS
import com.example.edusungshinourmoving.chatlist.ChatListItem
import com.example.edusungshinourmoving.databinding.ActivityMain7Binding
import com.example.edusungshinourmoving.home.ArticleAdapter
import com.example.edusungshinourmoving.home.ArticleModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity7: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)


        lateinit var articleDB: DatabaseReference
        lateinit var userDB: DatabaseReference
        lateinit var articleAdapter: ArticleAdapter

        val articleList = mutableListOf<ArticleModel>()
        val listener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val articleModel = snapshot.getValue(ArticleModel::class.java)
                articleModel ?: return

                articleList.add(articleModel)
                articleAdapter.submitList(articleList)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}

        }

        var binding: ActivityMain7Binding? = null
        val auth: FirebaseAuth by lazy {
            Firebase.auth
        }

        fun onViewCreated(view: View, savedInstanceState: Bundle?) {


            val fragmentHomeBinding = ActivityMain7Binding.bind(view)
            binding = fragmentHomeBinding

            articleDB = Firebase.database.reference.child(DB_ARTICLES)
            userDB = Firebase.database.reference.child(DB_USERS)
            articleAdapter = ArticleAdapter(onItemClicked = { articleModel ->
                if (auth.currentUser != null) {
                    if (auth.currentUser?.uid != articleModel.sellerId) {

                        val chatRoom = ChatListItem(
                            buyerId = auth.currentUser!!.uid,
                            sellerId = articleModel.sellerId,
                            itemTitle = articleModel.title,
                            key = System.currentTimeMillis()
                        )

                        userDB.child(auth.currentUser!!.uid)
                            .child(CHILD_CHAT)
                            .push()
                            .setValue(chatRoom)

                        userDB.child(articleModel.sellerId)
                            .child(CHILD_CHAT)
                            .push()
                            .setValue(chatRoom)

                        Snackbar.make(view, "채팅방이 생성되었습니다. 채팅탭에서 확인해주세요.", Snackbar.LENGTH_LONG)
                            .show()

                    } else {
                        Snackbar.make(view, "내가 올린 아이템 입니다.", Snackbar.LENGTH_LONG).show()
                    }
                } else {
                    Snackbar.make(view, "로그인 후 사용해주세요.", Snackbar.LENGTH_LONG).show()
                }

            })
            articleList.clear()

            fragmentHomeBinding.articleRecyclerView.layoutManager = LinearLayoutManager(this)
            fragmentHomeBinding.articleRecyclerView.adapter = articleAdapter

            fragmentHomeBinding.addFloatingButton.setOnClickListener {
                this?.let {
                    if (auth.currentUser != null) {
                        val intent = Intent(it, MainActivity5::class.java)
                        startActivity(intent)
                    } else {
                        Snackbar.make(view, "로그인 후 사용해주세요.", Snackbar.LENGTH_LONG).show()
                    }
                }
            }

            articleDB.addChildEventListener(listener)

        }

        fun onResume() {
            super.onResume()

            articleAdapter.notifyDataSetChanged()
        }

        fun onDestroyView() {
            super.onDestroy()

            articleDB.removeEventListener(listener)
        }
    }
}
