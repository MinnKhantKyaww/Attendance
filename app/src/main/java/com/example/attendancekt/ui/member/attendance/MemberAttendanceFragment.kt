package com.example.attendancekt.ui.member.attendance

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.attendancekt.R
import com.example.attendancekt.model.entity.tuple.MemberAttendance
import com.example.attendancekt.ui.member.AdpaterItemClickListener
import com.example.attendancekt.ui.member.attendance.edit.MemberAttendanceEditFragment
import kotlinx.android.synthetic.main.fragment_member_attendance.*

class MemberAttendanceFragment: Fragment() {

    private lateinit var viewModel: MemberAttendanceViewModel
    private lateinit var adapter: MemberAttendanceAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        adapter = MemberAttendanceAdapter()

        viewModel = ViewModelProviders.of(this)[MemberAttendanceViewModel::class.java]
        viewModel.attendance.observe(this, Observer {
            adapter.submitList(it)
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_member_attendance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        adapter.setAdapterItemClickListener(object : AdpaterItemClickListener<MemberAttendance> {

            override fun onClick(t: MemberAttendance) {
                if(getView() == null) return

                var args = Bundle()
                args.putLong(MemberAttendanceEditFragment.KEY_ATTENDANCE_ID, t.id)
                findNavController().navigate(R.id.action_memberAttendanceFragment_to_memberAttendanceEditFragment, args)
            }

        })
        
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = this@MemberAttendanceFragment.adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_attendace, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.memberAttendanceEditFragment) {
            findNavController().navigate(R.id.action_memberAttendanceFragment_to_memberAttendanceEditFragment)
        }

        return super.onOptionsItemSelected(item)
    }
}