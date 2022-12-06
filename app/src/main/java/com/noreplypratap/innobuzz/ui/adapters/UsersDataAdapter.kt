package com.noreplypratap.innobuzz.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noreplypratap.innobuzz.R
import com.noreplypratap.innobuzz.model.UsersPostsModel

class UsersDataAdapter( var usersPostsData : MutableList<UsersPostsModel>) : RecyclerView.Adapter<UsersDataAdapter.ViewHolder>()  {


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var textView: TextView
        init {
            textView = itemView.findViewById(R.id.tvPostTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_data,parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val postData  = usersPostsData[position]

        holder.itemView.apply {

            holder.textView.text = postData.title

            setOnClickListener {
                onItemClicked?.let {
                    it(postData)
                }
            }
        }
    }

    override fun getItemCount(): Int {
       return usersPostsData.size
    }

    private var onItemClicked : ((UsersPostsModel) -> Unit)? = null

    fun setOnClickListener(listener : (UsersPostsModel) -> Unit){
        onItemClicked = listener
    }

}