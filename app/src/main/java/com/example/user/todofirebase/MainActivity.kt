package com.example.user.todofirebase

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log.e
import android.widget.EditText
import android.widget.LinearLayout
import com.example.user.todofirebase.models.ToDoItem
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var fbdb:FirebaseDatabase?=null
    var dbref:DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_todo.layoutManager=LinearLayoutManager(this)

        fbdb= FirebaseDatabase.getInstance()
        dbref=fbdb!!.getReference("todo")

        fab_todo.setOnClickListener {
            var ad=AlertDialog.Builder(this)
            ad.setTitle("To Add New Task")
            ad.setMessage("Enter a new task to save in list")

            var et=EditText(this)
            et.hint="Enter Task"
            var et1=EditText(this)
            et.hint="Enter age"
            var ll= LinearLayout(this)
            ll.orientation=LinearLayout.VERTICAL

            ll.addView(et)
            ll.addView(et1)

            ad.setView(et)
            ad.setPositiveButton("Add",object :DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    var task=et.text.toString()

                    var item=ToDoItem()
                    item.itemText=tasask
                    item.done=false

                    var dbItem=dbref!!.push()
                    var key=dbItem.key

                    item.objectId=key

                    dbItem.setValue(item)
                }

            })
            ad.show()
        }

        var adapter=ToDoAdapter(this)
        rv_todo.adapter=adapter

        adapter.setUpdate(object :ToDoDelegate{
            override fun onDone(objectId: String, isDone: Boolean) {
                var item=dbref!!.child(objectId)
                item.child("done").setValue(isDone)
            }

            override fun onDelete(item: ToDoItem) {
                var item=dbref!!.child(item.objectId!!)
                item.removeValue()
            }

        })

        dbref!!.orderByKey().addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(data: DataSnapshot) {
                var item=ArrayList<ToDoItem>()

                data.children.forEach {
                    e("abc",it.value.toString())

                    val map=it.value as HashMap<String,Any>

                    var todoItem=ToDoItem()
                    todoItem.done=map.get("done") as Boolean?
                    todoItem.itemText=map.get("itemText") as String?
                    todoItem.objectId=map.get("objectId") as String?

                    item.add(todoItem)
                }

                adapter.setNewList(item)
            }

        })
    }
}
