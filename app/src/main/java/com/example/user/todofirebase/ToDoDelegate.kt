package com.example.user.todofirebase

import com.example.user.todofirebase.models.ToDoItem

interface ToDoDelegate {
    fun onDone(objectId:String,isDone:Boolean)
    fun onDelete(item:ToDoItem)
}