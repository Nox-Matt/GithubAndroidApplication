package com.ukrida.mygithubapplication.ui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ukrida.mygithubapplication.R
import com.ukrida.mygithubapplication.data.respond.GithubItem

class GithubAdapter(var githubItems: List<GithubItem>) : RecyclerView.Adapter<GithubAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_github_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val githubItem = githubItems[position]
        holder.bind(githubItem)
    }

    override fun getItemCount(): Int {
        return githubItems.size
    }

    fun updateData(newGithubItems: List<GithubItem>) {
        githubItems = newGithubItems
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgProfile: ImageView = view.findViewById(R.id.profile_picture)
        private val tvUsername: TextView = view.findViewById(R.id.github_name)
        private var onItemClickCallback: OnItemClickCallback? = null
        fun bind(githubItem: GithubItem) {
            tvUsername.text = githubItem.login
            Glide.with(itemView.context)
                .load(githubItem.avatarUrl)
                .into(imgProfile)
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(githubItem)
            }
        }

    }
    interface OnItemClickCallback{
        fun onItemClicked(data: GithubItem)
    }
}