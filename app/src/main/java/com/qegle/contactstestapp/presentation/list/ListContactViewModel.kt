package com.qegle.contactstestapp.presentation.list

import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.DiffUtil
import com.qegle.contactstestapp.entity.UserContact
import com.qegle.contactstestapp.extensions.observeOnUi
import com.qegle.contactstestapp.interactor.ContactInteractor
import com.qegle.contactstestapp.presentation.base.BaseViewModel
import com.qegle.contactstestapp.presentation.common.DiffCollectionResult
import com.qegle.contactstestapp.repository.DataFetchStrategy
import io.reactivex.Flowable
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import java.util.concurrent.TimeUnit

class ListContactViewModel(private val contactInteractor: ContactInteractor) : BaseViewModel() {

    val contactItems = DiffObservableList<ContactItemViewModel>(callback)
    val isRefreshing = ObservableBoolean()
    val isLoading = ObservableBoolean()

    init {
        loadInfo(DataFetchStrategy.PreferLocal, isLoading)
    }

    fun refreshData() {
        loadInfo(DataFetchStrategy.Remote, isRefreshing)
    }

    fun setSearchObservable(observable: Flowable<String>) {
        observable
            .observeOnUi()
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { contactInteractor.find(it).toFlowable() }
            .map { it.toCollectionDiffResult() }
            .subscribe({ showContacts(it) }, {
                it.printStackTrace()
            })
            .disposeLater()
    }

    private fun loadInfo(strategy: DataFetchStrategy, loadingObservableField: ObservableBoolean) {
        contactInteractor.getContacts(strategy)
            .observeOnUi()
            .doOnSubscribe { loadingObservableField.set(true) }
            .doAfterTerminate { loadingObservableField.set(false) }
            .map { it.toCollectionDiffResult() }
            .subscribe({ showContacts(it) }, {
                it.printStackTrace()
            })
            .disposeLater()
    }

    private fun showContacts(diffResult: DiffCollectionResult<ContactItemViewModel>) {
        this.contactItems.update(diffResult.collection, diffResult.diff)
    }

    private fun List<UserContact>.toCollectionDiffResult(): DiffCollectionResult<ContactItemViewModel> {
        val scheduledItems = this.map { it.toListContactViewModel() }
        return DiffCollectionResult(contactItems.calculateDiff(scheduledItems), scheduledItems)
    }

    private fun UserContact.toListContactViewModel(): ContactItemViewModel {
        return ContactItemViewModel(
            id = this.id,
            name = this.name,
            height = this.height.toString(),
            phone = this.phone
        )
    }

    companion object {
        private val callback = object : DiffUtil.ItemCallback<ContactItemViewModel>() {
            override fun areItemsTheSame(
                oldItem: ContactItemViewModel,
                newItem: ContactItemViewModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: ContactItemViewModel,
                newItem: ContactItemViewModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}