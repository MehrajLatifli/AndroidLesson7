package com.example.androidlesson7.Fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.service.autofill.UserData
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.androidlesson7.Models.User
import com.example.androidlesson7.databinding.FragmentWriteBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class WriteFragment : Fragment() {

    private val PICK_IMAGE_REQUEST = 1


    private var _binding: FragmentWriteBinding? = null
    private val binding get() = _binding!!

    private var progress = 0
    private val handler = Handler()
    private val calendar = Calendar.getInstance()

    var raiting=0.0
    var gender=""
    var formattedDateTime=""
    var imagePath=""

    private val progressRunnable = object : Runnable {
        override fun run() {
            if (progress <= 101) {
                binding.progressBar.progress = progress
                progress++
                handler.postDelayed(this, 50)
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                binding.editTextText.visibility = View.VISIBLE
                binding.ratingBar.visibility = View.VISIBLE
                binding.checkBox.visibility = View.VISIBLE
                binding.button.visibility = View.VISIBLE
                binding.button2.visibility = View.VISIBLE
                binding.button3.visibility = View.VISIBLE
                binding.imageView.visibility = View.VISIBLE
                binding.textView1.visibility = View.VISIBLE

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler.post(progressRunnable)

        binding.button.setOnClickListener {
            showDatePickerDialog()
        }

        binding.button2.setOnClickListener {
            showTimePickerDialog()
        }

        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->

            raiting = rating.toDouble()
        }

        binding.checkBox.setOnClickListener {

            if (binding.checkBox.isChecked) {

                gender="Male"
                binding.checkBox.text = gender
            } else {

                gender="Female"
                binding.checkBox.text = gender
            }
        }


        binding.button3.setOnClickListener {
            openFileChooser()



        }



        binding.textView1.setOnClickListener {

            val userData  = User(
                binding.editTextText.text.toString(),
                raiting,
                gender,
                formattedDateTime,
                imagePath
            )
            val action = WriteFragmentDirections.actionWriteFragmentToReadFragment(userData)
            findNavController().navigate(action)
        }




    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacks(progressRunnable)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            binding.imageView.setImageURI(imageUri)
             imagePath = imageUri.let { getImagePath(it!!) }




            Log.e("image",imagePath!!)
        }
    }



    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }


    private fun getImagePath(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }
        return ""
    }


    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateSelectedDateTimeTextView()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                updateSelectedDateTimeTextView()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    private fun updateSelectedDateTimeTextView() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        formattedDateTime = dateFormat.format(calendar.time)

    }
}
