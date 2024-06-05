package com.example.instagram_clone.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.instagram_clone.fragments.MyReelFragment

class ViewPagerAdapter (fm:FragmentManager):FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    val fragmentList = mutableListOf<Fragment>()
    val titleList = mutableListOf<String>()
    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList.get(position)
    }
    fun addList(f: Fragment, s:String){
        fragmentList.add(f)
        titleList.add(s)
    }

}