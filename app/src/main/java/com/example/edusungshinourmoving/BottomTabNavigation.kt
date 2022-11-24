package com.example.edusungshinourmoving

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.edusungshinourmoving.chatlist.ChatListFragment
import com.example.edusungshinourmoving.home.HomeFragment
import com.example.edusungshinourmoving.mypage.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomTabNavigation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottomtab)

        // 아래 3번에서 구현한 Fragment들을 가져옴
        val homeFragment = HomeFragment()
        val chatListFragment = ChatListFragment()
        val myPageFragment = MyPageFragment()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // fragment 초기값
        replaceFragment(homeFragment)

        // setOnNavigationItemSelectedListener는 네비게이션바의 탭들이 선택되었을 때 호출되어 선택된 탭의 id가 내려온다.
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.chatting -> replaceFragment(chatListFragment)
                R.id.mypage -> replaceFragment(myPageFragment)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    // FrameLayout에 선택된 Fragment를 attach하는 메소드
    private fun replaceFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment)
                commit()
            }
    }
}