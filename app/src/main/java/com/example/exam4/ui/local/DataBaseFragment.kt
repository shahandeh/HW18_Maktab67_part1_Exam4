package com.example.exam4.ui.local

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.exam4.R
import com.example.exam4.databinding.FragmentDataBaseBinding
import com.example.exam4.ui.SharedViewModel
import com.example.exam4.ui.dialog.AppDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DataBaseFragment: Fragment(R.layout.fragment_data_base) {

    private val dataBaseFragment by activityViewModels<SharedViewModel>()
    private lateinit var binding: FragmentDataBaseBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDataBaseBinding.bind(view)

        val recyclerView = DataBaseFragmentAdapter()

        binding.recyclerViewFragmentDatabase.adapter = recyclerView

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                dataBaseFragment.userListFromDataBase.collect{
                    recyclerView.submitList(it)
                }
            }
        }

        val itemTouch = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (direction == ItemTouchHelper.RIGHT){
                    dataBaseFragment.deleteUserFromDataBase(position)
                } else {
                    val showDetailDialog = AppDialog{ firstName, lastName, nationalCode ->
                        editUser(position, firstName, lastName, nationalCode)
                    }
                    showDetailDialog.show(parentFragmentManager, "edit user dialog")
                    binding.recyclerViewFragmentDatabase.adapter?.notifyItemChanged(position)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouch)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewFragmentDatabase)

    }

    private fun editUser(position: Int, firstName: String, lastName: String, nationalCode: String){
        if (firstName.isNotBlank() && lastName.isNotBlank() && nationalCode.isNotBlank())
            dataBaseFragment.editUserInDataBase(position, firstName, lastName, nationalCode)
        else
            Snackbar.make(requireView(), "Please fill all field!", Snackbar.LENGTH_LONG).show()
    }
}