package com.qegle.konturtestapp.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.qegle.konturtestapp.R
import com.qegle.konturtestapp.presentation.common.ContactsAdapter
import com.qegle.konturtestapp.vm.model.DoneState
import com.qegle.konturtestapp.vm.model.ErrorState
import com.qegle.konturtestapp.vm.model.LoadingState
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.f_contacts.*
import org.koin.android.ext.android.inject


class ListContactF : Fragment() {
    private val viewModel: ListContactVM by inject()
    private var mayRefresh = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (this.view != null) this.view!! else inflater.inflate(
            R.layout.f_contacts,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true

        lifecycle.addObserver(viewModel)

        tbContacts.inflateMenu(R.menu.search_menu)

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                LoadingState -> showLoading()
                DoneState -> showData()
                is ErrorState -> if (state.isShown.not()) {
                    state.isShown = true
                    showError()
                }
            }
        })

        viewModel.contacts.observe(this, Observer { list ->
            (rvContacts.adapter as? ContactsAdapter)?.submitList(list)
            rvContacts.scrollToPosition(0)
        })

        rvContacts.setHasFixedSize(true)
        rvContacts.adapter =
            ContactsAdapter { contact ->
                findNavController().navigate(
                    ListContactFDirections.toDetailContactF(
                        contact
                    )
                )
            }

        val searchItem = tbContacts.menu.findItem(R.id.action_search)
        searchItem.isVisible = false

        srlContainer.setOnRefreshListener {
            searchItem.isVisible = false
            viewModel.update()
        }
        srlContainer.setOnChildScrollUpCallback { _, _ -> mayRefresh.not() }


        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                val searchView = item?.actionView as? SearchView ?: return false
                searchView.maxWidth = Integer.MAX_VALUE
                searchView.queryHint = resources.getString(R.string.search)



                viewModel.setSearchObservable(searchView.toFlowable())

                mayRefresh = false
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                mayRefresh = true
                return true
            }
        })
    }

    private fun showLoading() {
        rvContacts.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        srlContainer.isRefreshing = false
    }

    private fun showData() {
        tbContacts.menu.findItem(R.id.action_search).isVisible = true
        rvContacts.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        srlContainer.isRefreshing = false
    }

    private fun showError() {
        progressBar.visibility = View.GONE
        srlContainer.isRefreshing = false
        rvContacts.visibility = View.VISIBLE
        Snackbar.make(srlContainer, R.string.data_loading_error, Snackbar.LENGTH_LONG)
            .setAction(android.R.string.ok) { }
            .setActionTextColor(resources.getColor(R.color.colorRed))
            .show()
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