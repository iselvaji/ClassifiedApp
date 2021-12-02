package com.dubizzle.classifiedapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dubizzle.classifiedapp.R
import com.dubizzle.classifiedapp.databinding.FragmentListBinding
import com.dubizzle.classifiedapp.utils.ConnectionUtils
import com.dubizzle.classifiedapp.utils.Constants.Companion.KEY_GLASSIFIED
import com.dubizzle.classifiedapp.utils.NetworkApiResult
import com.dubizzle.classifiedapp.utils.UiUtils
import com.dubizzle.classifiedapp.view.adapter.ClassifiedAdapter
import com.dubizzle.classifiedapp.viewmodel.ClassifiedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ClassifiedListFragment : Fragment() {

    @Inject
    lateinit var classifiedAdapter: ClassifiedAdapter
    private var binding: FragmentListBinding? = null
    private val viewModel by viewModels<ClassifiedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setUpObservers()
        fetchData()
    }

    private fun setupUI() {
        binding?.recyclerviewClassified?.adapter = classifiedAdapter

        classifiedAdapter.onItemClick = { selectedItem, _ ->
            val bundle = Bundle()
            bundle.putParcelable(KEY_GLASSIFIED, selectedItem)
            findNavController().navigate(R.id.list_to_details_view, bundle)
        }
    }

    private fun setUpObservers() {
        viewModel.response.observe(viewLifecycleOwner, {

            when (it) {
                is NetworkApiResult.Success -> {
                    it.data?.results?.let { it1 ->
                        classifiedAdapter.updateAdapter(it1)
                    }
                    binding?.progressDialog?.visibility = View.GONE
                }

                is NetworkApiResult.Error -> {
                    binding?.progressDialog?.visibility = View.GONE
                    UiUtils.showErrorDialog(requireContext(), getString(R.string.api_error_message))
                }

                is NetworkApiResult.Loading -> {
                    binding?.progressDialog?.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun fetchData() {
        if(ConnectionUtils.hasInternetConnection(context)) {
            viewModel.fetchClassifiedResponse()
        } else {
            UiUtils.showErrorDialog(requireContext(), getString(R.string.network_error_message))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}