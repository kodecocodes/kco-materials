package com.raywenderlich.android.starsync.ui.mainscreen

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.starsync.R
import com.raywenderlich.android.starsync.repository.model.People
import com.raywenderlich.android.starsync.utils.inflate

class PeopleListAdapter : RecyclerView.Adapter<PeopleViewHolder>() {

  private var listOfItem = ArrayList<People>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
    val inflatedView = parent.inflate(R.layout.people_list_item, false)
    return PeopleViewHolder(inflatedView)
  }

  override fun getItemCount(): Int = listOfItem.size

  override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
    val item = listOfItem[position]
    holder.bindFields(item)
  }

  fun loadItemList(listOfItem: List<People>) {
    this.listOfItem = ArrayList(listOfItem)
    notifyDataSetChanged()
  }
}