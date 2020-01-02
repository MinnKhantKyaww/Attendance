package com.example.attendancekt.ui.member.attendance.edit

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.attendancekt.R
import com.example.attendancekt.databinding.EditMemAttedanceBinding
import com.example.attendancekt.model.entity.Member
import kotlinx.android.synthetic.main.fragment_attendance_edit.*

class MemberAttendanceEditFragment : Fragment() {

    private var binding: EditMemAttedanceBinding? = null

    private lateinit var viewModel: MemberAttendanceEditViewModel
    private lateinit var memberAdapter: ArrayAdapter<Member>

    companion object {
        const val KEY_ATTENDANCE_ID = "attendance_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        memberAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1)

        if(savedInstanceState == null) {
            viewModel = ViewModelProviders.of(this).get(MemberAttendanceEditViewModel::class.java)
        }

        viewModel.members.observe(this, Observer { memberAdapter.addAll(it) })

        viewModel.attendance.observe(this, Observer {
            viewModel.memberId.value = it.memberId
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditMemAttedanceBinding.inflate(inflater, container, false)

        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectMemberEditText.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Choose Member")
                .setAdapter(memberAdapter) { di, i ->
                    memberAdapter.getItem(i)?.also {
                        viewModel.memberId.value = it.id
                        viewModel.attendance.value?.memberId = it.id
                    }
                    di.dismiss()
                }
                .show()
        }

        /*selectMemberEditText.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                *//*viewModel.members.observe(this@MemberAttendanceEditFragment, Observer {
                }*//*

                with(alertDialogBuilder) {

                    setTitle("Members")
                    setIcon(R.drawable.ic_people_black_24dp)
                        setItems(listItems) {dialog, which ->
                            selectMemberEditText.setText(listItems[which])
                    }

                    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
                        Toast.makeText(context, "Not Choose Item!", Toast.LENGTH_SHORT).show()

                    }

                    setNegativeButton("Cancel", positiveButtonClick)
                    show()
                }

*//*
                alertDialogBuilder.setPositiveButton("Yes"){dialog, which ->
                    Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()
                }

                alertDialogBuilder.setNegativeButton("No"){dialog, which ->
                    Toast.makeText(context, "No", Toast.LENGTH_SHORT).show()
                }

                alertDialogBuilder.setNeutralButton("Maybe"){dialog, which ->
                    Toast.makeText(context, "Maybe", Toast.LENGTH_SHORT).show()
                }*//*

                //val alertDialog = alertDialogBuilder.create()
                //alertDialog.show()
            }

        })*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id = arguments?.getLong(KEY_ATTENDANCE_ID) ?: 0

        viewModel.attendanceId.value = id
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_save) {
            viewModel.save()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(false)
    }

}