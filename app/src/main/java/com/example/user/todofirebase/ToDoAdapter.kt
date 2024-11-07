package com.example.user.todofirebase

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.user.todofirebase.models.ToDoItem
import kotlinx.android.synthetic.main.sample_view.view.*

class ToDoAdapter(var ctxt:Context): RecyclerView.Adapter<ToDoAdapter.ToDoHolder>() {
    var itemList=ArrayList<ToDoItem>()

    lateinit var todoDelegete:ToDoDelegate
    fun setUpdate(toDoDelegate: ToDoDelegate){
        this.todoDelegete=toDoDelegate
    }
    fun setNewList(itemList: ArrayList<ToDoItem>)
    {
        this.itemList=itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        var view=LayoutInflater.from(ctxt).inflate(R.layout.sample_view,parent,false);
        return ToDoHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size;
    }

    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        var item= itemList[position]
        holder.itemView.tv_sv.text=item.itemText;
        holder.itemView.cb_sv.isChecked=item.done!!
        holder.itemView.cb_sv.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                todoDelegete.onDone(item.objectId!!,p1)
            }
        })
        holder.itemView.ib_sv.setOnClickListener {
            todoDelegete.onDelete(item)
        }
    }

    class ToDoHolder(itemView:View): RecyclerView.ViewHolder(itemView)
}