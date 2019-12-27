package com.example.attendancekt.ui.member

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.view.*
import android.webkit.PermissionRequest
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.room.util.FileUtil
import com.example.attendancekt.MainActivity
import com.example.attendancekt.R
import com.example.attendancekt.databinding.MemberEditBinding
import com.example.attendancekt.util.PermissionUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_member_edit.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MemberEditFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        var KEY_PRODUCT_ID = "product_id"
        var REQUEST_IMAGE_CAPTURE = 1
        var REQUEST_PICK_IMAGE = 3
    }

    private lateinit var viewModel: MemberEditViewModel

    var background: View? = null

    private var memberEditBinding: MemberEditBinding? = null

    var currentPhotoFilePath: String? = null

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

        addMemberPhoto.setOnClickListener(View.OnClickListener {
            var bottomSheetDialog = BottomSheetDialog(requireContext())

            var bottomSheet = layoutInflater.inflate(R.layout.bottom_sheet, null)

            tvTakePhoto.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            tvChooseGallery.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setContentView(bottomSheet)
            bottomSheetDialog.show()
        })

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var restul = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && requestCode == Activity.RESULT_OK && currentPhotoFilePath != null) {
            memberEditBinding?.addMemberPhoto?.setImageURI(Uri.parse(currentPhotoFilePath))
            viewModel.members.value?.photo.(currentPhotoFilePath)
        } else if(requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            var photoURI = data
            try {

            }
        }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionUtil.PERMISSION_CAMERA) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) dispatchPictureIntent()
        }
    }

    fun dispatchPictureIntent() {
        if (!PermissionUtil.hasCameraPermission(this)) {
            return
        }

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
            var photoPath: File? = null
            try {
                photoPath = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            if (photoPath != null) {
                var photoURI = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.attendancekt.fileprovider", photoPath
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        var time = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        var imageFileName = "JPEG_ $time _"
        var image = File.createTempFile(
            imageFileName,
            ".jpg",
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        currentPhotoFilePath = image.absolutePath
        return image
    }

    fun dispatchChoosePictureIntent() {
        var contentIntent = Intent(Intent.ACTION_GET_CONTENT)
        contentIntent.setType("image/*")

        var imagePickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        var chooserIntent = Intent.createChooser(contentIntent, "Select Image Apk")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf<Intent>(imagePickIntent))

        startActivityForResult(chooserIntent, REQUEST_PICK_IMAGE)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}