package com.example.attendancekt.ui.member.attendance.edit

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.attendancekt.R
import com.example.attendancekt.databinding.EditMemAttedanceBinding
import com.example.attendancekt.databinding.EditMemAttedanceBindingImpl
import com.example.attendancekt.model.entity.Member
import kotlinx.android.synthetic.main.fragment_attendance_edit.*

class MemberAttendanceEditFragment : Fragment() {

    private var binding: EditMemAttedanceBinding? = null

    private lateinit var viewModel: MemberAttendanceEditViewModel

    companion object {
        const val KEY_ATTENDANCE_ID = "attendance_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)

        if(savedInstanceState == null) {
            viewModel = ViewModelProviders.of(this).get(MemberAttendanceEditViewModel::class.java)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentTransaction = fragmentManager?.beginTransaction()

        binding = EditMemAttedanceBinding.inflate(inflater, container, false)

        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        fragmentTransaction?.addToBackStack(null)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val listItems = arrayOf("Item 1", "Item 2", "Item 3", "Item 4")


        val items = viewModel.members.value

        selectMemberEditText.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {
                var alertDialogBuilder = AlertDialog.Builder(activity)
                //viewModel.members.observe(this@MemberAttendanceEditFragment, Observer {

                with(alertDialogBuilder) {

                    setTitle("Members")
                    setIcon(R.drawable.ic_people_black_24dp)
                        setItems(listItems) { dialog, which ->
                            selectMemberEditText.setText(listItems[which])
                    }

                    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
                        Toast.makeText(context, "Not Choose Item!", Toast.LENGTH_SHORT).show()

                    }

                    setNegativeButton("Cancel", positiveButtonClick)
                    show()
                }

/*
                alertDialogBuilder.setPositiveButton("Yes"){dialog, which ->
                    Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()
                }

                alertDialogBuilder.setNegativeButton("No"){dialog, which ->
                    Toast.makeText(context, "No", Toast.LENGTH_SHORT).show()
                }

                alertDialogBuilder.setNeutralButton("Maybe"){dialog, which ->
                    Toast.makeText(context, "Maybe", Toast.LENGTH_SHORT).show()
                }*/

                //val alertDialog = alertDialogBuilder.create()
                //alertDialog.show()
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var id: Long
        if (arguments != null) {
            id = arguments!!.getLong(KEY_ATTENDANCE_ID)
        } else {
            id = 0
        }
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