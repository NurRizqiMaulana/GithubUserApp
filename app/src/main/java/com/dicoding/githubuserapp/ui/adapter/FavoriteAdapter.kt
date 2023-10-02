package com.dicoding.githubuserapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.data.response.DetailResponseUsers
import com.dicoding.githubuserapp.data.response.ItemsItem
import com.dicoding.githubuserapp.data.retrofit.ApiConfig
import com.dicoding.githubuserapp.databinding.ItemRowUsersBinding
import com.dicoding.githubuserapp.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteAdapter(private val listusers: ArrayList<ItemsItem>)
    : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgUser = binding.imageView
        val tvName = binding.tvName
        val tvUsername = binding.tvUsername
        val tvBio= binding.tvBio
        val tvLocation = binding.tvLocation

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = listusers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listusers[position]
        Glide.with(holder.itemView.context).load(item.avatarUrl).into(holder.imgUser)
        holder.tvUsername.text=item.login
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER,listusers[holder.adapterPosition])
            holder.itemView.context.startActivity(intent) }

        val client = ApiConfig.getApiService().getDetailUser(item.login)

        client.enqueue(object : Callback<DetailResponseUsers> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<DetailResponseUsers>, response: Response<DetailResponseUsers>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        holder.tvName.text = responseBody.name
                        holder.tvBio.text = "Bio: ${responseBody.bio}"
                        holder.tvLocation.text = "Loc: ${responseBody.location}"

                    }
                }
            }
            override fun onFailure(call: Call<DetailResponseUsers>, t: Throwable) {}
        })
    }
}