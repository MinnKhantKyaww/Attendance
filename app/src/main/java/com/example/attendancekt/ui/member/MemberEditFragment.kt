package com.example.attendancekt.ui.member

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.attendancekt.MainActivity
import com.example.attendancekt.R
import com.example.attendancekt.databinding.MemberEditBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_member_edit.*


class MemberEditFragment : Fragment(), AdapterView.OnItemSelectedListener {

    val KEY_PRODUCT_ID = "product_id"

    private lateinit var viewModel: MemberEditViewModel

    var background: View? = null

    private var memberEditBinding: MemberEditBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val activity = requireActivity() as MainActivity
        activity.actionBar?.setDisplayHomeAsUpEnabled(true)

        //memberEditBinding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_member_edit)

        if (savedInstanceState == null) {
            viewModel = ViewModelProviders.of(this).get(MemberEditViewModel::class.java)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentManager: FragmentManager? = getFragmentManager()
        val fragmentTransaction = fragmentManager?.beginTransaction()

        memberEditBinding = MemberEditBinding.inflate(inflater, container, false)

        memberEditBinding?.lifecycleOwner = this
        memberEditBinding?.viewModel = viewModel

        fragmentTransaction?.addToBackStack(null)

        return memberEditBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val age = ArrayList<Int>()
        for (x in 1 until 100) {
            age.add(x)
        }

        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, age)

        //  arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        ageSpinner?.adapter = arrayAdapter
        ageSpinner?.onItemSelectedListener = this

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val memberObserver = Observer<Boolean> { op -> if (op) requireActivity().onBackPressed() }
        viewModel.operation.observe(this, memberObserver)

        var id: Int
        if (arguments != null) {
            id = arguments!!.getInt(KEY_PRODUCT_ID)
        } else {
            id = 0
        }
        viewModel.init(id)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val activity = requireActivity() as MainActivity
        activity.actionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            viewModel.save()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}