package com.example.exam4.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.exam4.FLOATING_ACTION_BUTTON_DIALOG
import com.example.exam4.R
import com.example.exam4.databinding.FragmentMainBinding
import com.example.exam4.ui.SharedViewModel
import com.example.exam4.ui.dialog.AppDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainFragmentViewModel by activityViewModels<SharedViewModel>()

    private lateinit var binding: FragmentMainBinding
    private lateinit var appListAdapter: MainFragmentAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        recyclerViewInit()

        mainFragmentViewModel.getUserListFromServer()
        lifecycleScope.launch {
            mainFragmentViewModel.userListFromServer.collect {
                appListAdapter.submitList(it)
            }
        }

        binding.floatingActionButtonFragmentMain.setOnClickListener {
            createUserDialog()
        }

        val itemTouchHelper = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (direction == ItemTouchHelper.LEFT){
                    showDetail(position)
                }
                else {
                    recyclerView.adapter?.notifyItemChanged(position)
                    addUserOnDataBase(position)
                }
            }
        }

        val itemTouch = ItemTouchHelper(itemTouchHelper)
        itemTouch.attachToRecyclerView(recyclerView)

    }

    private fun recyclerViewInit() {
        appListAdapter = MainFragmentAdapter { UserListModelItem ->
            showDetail(UserListModelItem)
        }
        recyclerView = binding.recyclerViewFragmentFirst
        recyclerView.adapter = appListAdapter
    }

    private fun createUserDialog() {
        val dialog = AppDialog { firstName, lastName, nationalCode ->
            createUser(firstName, lastName, nationalCode)
        }
        dialog.show(parentFragmentManager, FLOATING_ACTION_BUTTON_DIALOG)
    }

    private fun createUser(firstName: String, lastName: String, nationalCode: String) {
            mainFragmentViewModel.createUserOnServer(firstName, lastName, nationalCode)
    }

    private fun showDetail(position: Int) {
        val action = MainFragmentDirections.actionGlobalUserDetailFragment(position)
        findNavController().navigate(action)
    }

    private fun addUserOnDataBase(position: Int){
        mainFragmentViewModel.createUserOnDataBase(position)
    }
}