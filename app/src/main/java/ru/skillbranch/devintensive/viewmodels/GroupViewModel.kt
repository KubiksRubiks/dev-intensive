package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.repositories.GroupRepository

class GroupViewModel : ViewModel() {

    private val groupRepository = GroupRepository

    private val query = mutableLiveData("")
    private val userItems = mutableLiveData(loadUsers())
    private val selectedItems = Transformations.map(userItems) { users ->
        users.filter { it.isSelected }
    }

    fun getUserData(): LiveData<List<UserItem>> {
        val result = MediatorLiveData<List<UserItem>>()

        val filter = {
            val queryStr = query.value!!
            val users = userItems.value!!

            result.value =
                if (!queryStr.isEmpty())
                    users.filter { it.fullName.contains(queryStr, true) }
                else users
        }

        result.addSource(userItems) { filter.invoke() }
        result.addSource(query) { filter.invoke() }

        return result
    }

    fun getSelectedData(): LiveData<List<UserItem>> = selectedItems

    fun handleSelectedItem(userId: String) {
        userItems.value = userItems.value!!.map {
            if (it.id == userId)
                it.copy(isSelected = !it.isSelected)
            else it
        }
    }

    fun handleRemoveChip(userId: String) {
        userItems.value = userItems.value!!.map {
            if (it.id == userId)
                it.copy(isSelected = false)
            else it
        }
    }

    fun handleSearchQuery(text: String?) {
        query.value = text
    }

    fun handleCreateGroup() {
        groupRepository.createChat(selectedItems.value!!)
    }

    private fun loadUsers(): List<UserItem> = groupRepository.loadUsers().map { it.toUserItem() }
}