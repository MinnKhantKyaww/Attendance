package com.example.attendancekt.ui.member

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancekt.BR
import com.example.attendancekt.R
import com.example.attendancekt.model.entity.Member
import com.example.attendancekt.model.vo.MemberVO

class MemberAdapter(): ListAdapter<Member, MemberAdapter.MemberViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Member>() {
            override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
               return  oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }


    private var adapterItemClickListener: AdpaterItemClickListener<Member>? = null

    fun setAdapterItemClickListener(adapterItemClickListener : AdpaterItemClickListener<Member>): Unit {
        this.adapterItemClickListener = adapterItemClickListener
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding: ViewDataBinding = DataBindingUtil.inflate(inflater, R.layout.layout_member, parent, false)

        return MemberViewHolder(binding)
    }

    inner class  MemberViewHolder constructor(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                adapterItemClickListener?.onClick(getItem(adapterPosition))
            }
        }

        fun bind(obj: Member) {
            binding.setVariable(BR.obj, obj)
            binding.executePendingBindings()
        }

    }

}