package com.qegle.contactstestapp.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.qegle.contactstestapp.R
import com.qegle.contactstestapp.databinding.FContactDetailsBinding
import com.qegle.contactstestapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.f_contact_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailContactFragment : BaseFragment() {
    private val args: DetailContactFragmentArgs by navArgs()
    private val vm: DetailViewModel by viewModel { parametersOf(args.id) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FContactDetailsBinding>(inflater, R.layout.f_contact_details, container, false)
        binding.vm = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.errorLiveData.observe(viewLifecycleOwner, Observer { handleError(it) })

        tbContactCard.setNavigationOnClickListener { findNavController().popBackStack() }
    }
}