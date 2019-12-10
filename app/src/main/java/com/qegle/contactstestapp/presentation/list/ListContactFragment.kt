package com.qegle.contactstestapp.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.qegle.contactstestapp.R
import com.qegle.contactstestapp.databinding.FContactsBinding
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.f_contacts.*
import me.tatarka.bindingcollectionadapter2.BR
import me.tatarka.bindingcollectionadapter2.ItemBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListContactFragment : Fragment() {
    private val vm by viewModel<ListContactViewModel>()
    private var mayRefresh = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FContactsBinding>(inflater, R.layout.f_contacts, container, false)

        binding.vm = vm
        binding.contactBinding = ItemBinding.of<ContactItemViewModel> { itemBinding, _, _ ->
            itemBinding.set(BR.item, R.layout.v_contact_item)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true

        tbContacts.inflateMenu(R.menu.search_menu)

        val searchItem = tbContacts.menu.findItem(R.id.action_search)
        searchItem.isVisible = false

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                val searchView = item?.actionView as? SearchView ?: return false
                searchView.maxWidth = Integer.MAX_VALUE
                searchView.queryHint = resources.getString(R.string.search)



                vm.setSearchObservable(searchView.toFlowable())

                mayRefresh = false
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                mayRefresh = true
                return true
            }
        })
    }

    fun SearchView.toFlowable(): Flowable<String> {
        return Flowable.create({ emitter ->
            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(text: String): Boolean {
                    if (this@toFlowable.isIconified.not())
                        emitter.onNext(text)
                    return true
                }

                override fun onQueryTextChange(text: String): Boolean {
                    if (this@toFlowable.isIconified.not())
                        emitter.onNext(text)
                    return true
                }
            })
        }, BackpressureStrategy.LATEST)
    }
}