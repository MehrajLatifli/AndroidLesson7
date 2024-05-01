package com.example.androidlesson7.Fragments


import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidlesson7.R
import com.example.androidlesson7.databinding.FragmentReadBinding
import com.example.androidlesson7.Fragments.ReadFragment
import androidx.navigation.fragment.navArgs




class ReadFragment : Fragment() {

    private var _binding: FragmentReadBinding? = null
    private val binding get() = _binding!!

    private val args:ReadFragmentArgs by navArgs()





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReadBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewName.text="Username:  ${args.userData.username}"
        binding.textViewGender.text="Gender:  ${args.userData.gender}"
        binding.textViewRaiting.text="Rating:  ${args.userData.rating.toString()}"
        binding.textViewTime.text="RegistrationDate:  ${args.userData.registrationDate}"
        binding.imageView.setImageURI(Uri.parse(args.userData.imagePath))


        Log.e("arg",args.userData.gender)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}