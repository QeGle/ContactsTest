package com.qegle.konturtestapp.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qegle.konturtestapp.R
import com.qegle.konturtestapp.vm.model.ContactUI
import kotlinx.android.synthetic.main.v_contact_item.view.*

class ContactsAdapter(val onItemClickListener: (contact: ContactUI) -> Unit) :
	RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
	
	private var items: List<ContactUI> = emptyList()
	
	override fun getItemCount() = items.size
	
	fun submitList(items: List<ContactUI>) {
		this.items = items
		notifyDataSetChanged()
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.v_contact_item, parent, false)
        )
	}
	
	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = items[position]
		holder.bind(item)
		holder.itemView.setOnClickListener { onItemClickListener(item) }
	}
	
	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		private val tvName = view.tvName
		private val tvNumber = view.tvNumber
		private val tvHeight = view.tvHeight
		
		fun bind(contact: ContactUI) {
			tvName?.text = contact.name
			tvNumber?.text = contact.phone
			tvHeight?.text = contact.height.toString()
		}
	}
}