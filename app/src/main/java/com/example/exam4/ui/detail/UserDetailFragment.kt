package com.example.exam4.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.exam4.R
import com.example.exam4.databinding.FragmentUserDateilBinding
import com.example.exam4.ui.SharedViewModel
import com.example.exam4.util.toByteArrayConverter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_dateil) {

    private val userDataFragmentViewModel by activityViewModels<SharedViewModel>()

    private val argument: UserDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentUserDateilBinding

    private lateinit var byteArray: ByteArray
    private lateinit var imageFromCamera: ActivityResultLauncher<Void?>
    private lateinit var imageFromGallery: ActivityResultLauncher<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUserDateilBinding.bind(view)

        showDetail()

        imageFromCamera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                if (it != null) {
                    byteArray = it.toByteArrayConverter()
                    binding.imageViewFragmentUserDetail.setImageBitmap(it)
                }
            }

        imageFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                byteArray = requireActivity().contentResolver?.openInputStream(it)?.readBytes() as ByteArray
                binding.imageViewFragmentUserDetail.setImageURI(it)
            }
        }

        binding.takeImageFragmentUserDetail.setOnClickListener {
            takeImageDialog()
        }

        binding.uploadImageFragmentUserDetail.setOnClickListener {
            uploadImage()
        }

    }

    private fun showDetail() {
        val currentUser = userDataFragmentViewModel.userListFromServer.value[argument.position]
        binding.apply {
            firstNameFragmentUserDetail.text = currentUser.firstName
            lastNameFragmentUserDetail.text = currentUser.lastName
            nationCodeFragmentUserDetail.text = currentUser.nationalCode
            idFragmentUserDetail.text = currentUser._id
        }
    }

    private fun takeImageDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Take Image")
            .setMessage("Select Image Source")
            .setNegativeButton("CAMERA") { dialog, which ->
                takeImageFromCamera()
            }
            .setPositiveButton("GALLERY") { dialog, which ->
                takeImageFromGallery()
            }
            .show()
    }

    private fun takeImageFromCamera() {
        imageFromCamera.launch(null)
    }

    private fun takeImageFromGallery() {
        imageFromGallery.launch("image/*")
    }

    private fun uploadImage() {
        userDataFragmentViewModel.uploadImage(userDataFragmentViewModel.userListFromServer.value[argument.position]._id, byteArray)
    }


}

