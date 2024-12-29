package com.example.testassessment.ui.home

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testassessment.common.BaseListItemCallback
import com.example.testassessment.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), BaseListItemCallback<CombinedPhotoData> {
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
            viewModel.combinedPhotoData.collect { combinedData ->
                photoAdapter.addAll(combinedData)
                photoAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onItemClicked(item: CombinedPhotoData) {
        super.onItemClicked(item)

    }
}