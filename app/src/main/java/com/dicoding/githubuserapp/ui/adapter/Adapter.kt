package com.dicoding.githubuserapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.response.DetailResponseUsers
import com.dicoding.githubuserapp.data.response.ItemsItem
import com.dicoding.githubuserapp.data.retrofit.ApiConfig
import com.dicoding.githubuserapp.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Adapter(private val listUsers: ArrayList<ItemsItem>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    fun setData(usersData: List<ItemsItem>) {
        listUsers.clear()
        listUsers.addAll(usersData)
        notifyDataSetChanged()
    }
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val imgUser: ImageView = view.findViewById(R.id.imageView)
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvUsername: TextView = view.findViewById(R.id.tv_username)
        val tvBio: TextView = view.findViewById(R.id.tv_bio)
        val tvLocation: TextView = view.findViewById(R.id.tv_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view :View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_users,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listUsers[position]
        Glide.with(holder.itemView.context).load(item.avatarUrl).into(holder.imgUser)
        holder.tvUsername.text=item.login
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER,listUsers[holder.adapterPosition])
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