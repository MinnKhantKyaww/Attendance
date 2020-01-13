package com.example.attendancekt.ui.member.attendance

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancekt.BR
import com.example.attendancekt.R
import com.example.attendancekt.model.entity.tuple.MemberAttendance
import com.example.attendancekt.ui.member.AdpaterItemClickListener
import java.lang.reflect.Member

class MemberAttendanceAdapter :
    PagedListAdapter<MemberAttendance, MemberAttendanceAdapter.MemberAttendanceViewHolder>(
        DIFF_CALLBACK
    ) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MemberAttendance>() {
            override fun areItemsTheSame(
                oldItem: MemberAttendance,
                newItem: MemberAttendance
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MemberAttendance,
                newItem: MemberAttendance
            ): Boolean {
                return oldItem == newItem
            }

        }


    }

    private var adapterItemClickListener: AdpaterItemClickListener<MemberAttendance>? = null

    fun setAdapterItemClickListener(adapterItemClickListener: AdpaterItemClickListener<MemberAttendance>) {
        this.adapterItemClickListener = adapterItemClickListener
    }

    inner class MemberAttendanceViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        init {
            viewDataBinding.root.setOnClickListener {
                getItem(adapterPosition)?.let { it1 -> adapterItemClickListener?.onClick(it1) }
            }
        }

        fun bind(obj: MemberAttendance) {
            viewDataBinding.setVariable(BR.obj, obj)
            viewDataBinding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAttendanceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.layout_member_attendace,
            parent,
            false
        )

        return MemberAttendanceViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MemberAttendanceViewHolder, position: Int) {
        getItem(position)?.also { holder.bind(it) }
    }
}