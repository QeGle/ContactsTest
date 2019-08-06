package com.qegle.konturtestapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.qegle.konturtestapp.R
import com.qegle.konturtestapp.vm.DetailVM
import kotlinx.android.synthetic.main.f_contact_card.*
import org.koin.android.ext.android.inject

class DetailContactF : Fragment() {
	private val viewModel: DetailVM by inject()
	private val args: DetailContactFArgs by navArgs()
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.f_contact_card, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		lifecycle.addObserver(viewModel)
		
		tbContactCard.setNavigationOnClickListener { findNavController().popBackStack() }
		
		viewModel.contact.observe(this, Observer {
			tvBiography.text = it.biography
			tvEducationPeriod.text = it.educationPeriod
			tvName.text = it.name
			tvNumber.text = it.phone
			tvTemperament.text = it.temperament
		})
		
		viewModel.setData(args.contact)
	}
}