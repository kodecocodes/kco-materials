package com.raywenderlich.android.starsync.ui.mainscreen

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.starsync.repository.model.People
import kotlinx.android.synthetic.main.people_list_item.view.*

class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bindFields(item: People) {
    itemView.people_name.text = "Name: ${item.name}"
    itemView.people_gender.text = "Gender: ${item.gender}"
    itemView.people_mass.text = "Mass: ${item.mass}"
    itemView.people_height.text = "Height: ${item.height}"
    itemView.people_eye_color.text = "Eye Color: ${item.eye_color}"
    itemView.people_hair_color.text = "Hair Color: ${item.hair_color}"
    itemView.people_skin_color.text = "Skin Color: ${item.skin_color}"
  }
}