package com.example.attendancekt.ui.member

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.attendancekt.R
import com.example.attendancekt.model.entity.Member
import kotlinx.android.synthetic.main.fragment_list_member.*
import kotlinx.android.synthetic.main.fragment_member_edit.*

class MemberListFragment : Fragment() {

    private lateinit var viewModel: MemberViewModel
    private lateinit var memberAdapter: MemberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        memberAdapter = MemberAdapter()
        viewModel = ViewModelProviders.of(this).get(MemberViewModel::class.java)
        val memberObserver = Observer<List<Member>> {
            memberAdapter.submitList(it)
        }
        viewModel.members.observe(this, memberObserver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_member, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        memberListRecycle.setHasFixedSize(false)
        memberListRecycle.addItemDecoration(
            DividerItemDecoration(
                view.context,
                DividerItemDecoration.VERTICAL
            )
        )

        //memberAdapter.setAdapterItemClickListener()

        memberListRecycle.adapter = memberAdapter

       /* memberAdapter.setAdapterItemClickListener(

        )*/

        //findNavController().navigate(R.id.action_memberListFragment_to_memberEditFragment)

        //bundle.putInt()

        //memberListRecycle.adapter(memberAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_member, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.memberEditFragment) {
            findNavController().navigate(R.id.action_memberListFragment_to_memberEditFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}