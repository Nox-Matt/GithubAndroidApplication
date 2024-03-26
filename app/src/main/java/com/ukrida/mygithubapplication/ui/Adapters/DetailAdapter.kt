package com.ukrida.mygithubapplication.ui.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ukrida.mygithubapplication.R
import com.ukrida.mygithubapplication.data.respond.DetailResponse

class DetailAdapter(private var detailItems: List<DetailResponse>) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_github_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detailItem = detailItems[position]
        holder.bind(detailItem)
    }

    override fun getItemCount(): Int {
        return detailItems.size
    }

    fun updateData(newDetailItems: List<DetailResponse>) {
        detailItems = newDetailItems
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgProfile: ImageView = view.findViewById(R.id.profile_picture)
        private val tvUsername: TextView = view.findViewById(R.id.github_name)

        fun bind(detailItem: DetailResponse) {
            tvUsername.text = detailItem.login
            Glide.with(itemView.context)
                .load(detailItem.avatarUrl)
                .into(imgProfile)
        }
    }
}
