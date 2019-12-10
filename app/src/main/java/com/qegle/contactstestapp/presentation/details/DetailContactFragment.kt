package com.qegle.contactstestapp.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.qegle.contactstestapp.R
import com.qegle.contactstestapp.databinding.FContactCardBinding
import kotlinx.android.synthetic.main.f_contact_card.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailContactFragment : Fragment() {
    private val args: DetailContactFragmentArgs by navArgs()
    private val vm: DetailViewModel by viewModel { parametersOf(args.id) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FContactCardBinding>(inflater, R.layout.f_contact_card, container, false)
        binding.vm = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tbContactCard.setNavigationOnClickListener { findNavController().popBackStack() }
    }
}