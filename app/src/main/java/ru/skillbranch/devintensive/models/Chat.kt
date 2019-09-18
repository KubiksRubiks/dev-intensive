package ru.skillbranch.devintensive.models

class Chat(
    val id: String,
    var members: MutableList<User> = mutableListOf(),
    var messegs: MutableList<BaseMessage> = mutableListOf()
) {

}