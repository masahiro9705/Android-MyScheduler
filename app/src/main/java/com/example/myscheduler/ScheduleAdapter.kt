package com.example.myscheduler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import java.text.DateFormat
import java.text.SimpleDateFormat

class ScheduleAdapter(data: OrderedRealmCollection<Schedule>) :
        RealmRecyclerViewAdapter<Schedule, ScheduleAdapter.ViewHolder>(data, true){

        init {
            setHasStableIds(true)
        }
        class  ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
                val date: TextView = cell.findViewById(android.R.id.text1)
                val title: TextView = cell.findViewById(android.R.id.text2)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(android.R.layout.simple_list_item_2,parent,false)

                return  ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder, position: Int) {
                val schedule: Schedule? = getItem(position)
                val df = SimpleDateFormat("yyyy/MM/dd")
                holder.date.text = df.format(schedule?.date)
                holder.title.text =schedule?.title
        }


        override fun getItemId(position: Int): Long {
                return getItem(position)?.id ?: 0
        }
}