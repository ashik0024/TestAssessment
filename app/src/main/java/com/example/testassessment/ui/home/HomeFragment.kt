package com.example.testassessment.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testassessment.R
import com.example.testassessment.common.BaseListItemCallback
import com.example.testassessment.databinding.FragmentHomeBinding
import com.example.testassessment.roomdb.PhotosEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() , BaseListItemCallback<PhotosEntity> {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var photoAdapter: PhotoAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoAdapter = PhotoAdapter(this)
        binding?.photoRec?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = photoAdapter
        }

        observeData()

    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.photos.collect { photosList ->
                photoAdapter.removeAll()
                photoAdapter.addAll(photosList)
                photoAdapter.notifyDataSetChanged()
                Log.d("PhotoFetchService", "Photos list updated in UI: ${photosList.size}")
            }
        }
    }
    override fun onItemClicked(item: PhotosEntity) {
        super.onItemClicked(item)

    }
}