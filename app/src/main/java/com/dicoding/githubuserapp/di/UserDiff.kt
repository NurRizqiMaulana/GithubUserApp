package com.dicoding.githubuserapp.di

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.githubuserapp.data.response.ItemsItem

class UserDiff(
    private val mOldUserList: List<ItemsItem>,
    private val mNewUserList: List<ItemsItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldUserList.size

    override fun getNewListSize(): Int = mNewUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].login == mNewUserList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldUserList[oldItemPosition]
        val newEmployee = mOldUserList[newItemPosition]
        return oldEmployee.avatarUrl == newEmployee.avatarUrl
    }
}