package com.raywenderlich.android.disneyexplorer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.disneyexplorer.databinding.ListItemNumberBinding

class NumbersAdapter : RecyclerView.Adapter<NumberViewHolder>() {
  private val numbers = mutableListOf<Int>()

  fun addNumber(number: Int) {
    numbers.add(number)
    notifyItemInserted(itemCount - 1)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    NumberViewHolder.create(parent)

  override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
    holder.bind(numbers[position])
  }

  override fun getItemCount() = numbers.size

  override fun getItemId(position: Int) = numbers[position].toLong()
}

class NumberViewHolder(private val binding: ListItemNumberBinding) :
  RecyclerView.ViewHolder(binding.root) {

  fun bind(number: Int) {
    binding.number.text = number.toString()
  }

  companion object {
    fun create(parent: ViewGroup): NumberViewHolder {
      val binding = ListItemNumberBinding.inflate(
        LayoutInflater.from(parent.context), parent,
        false
      )
      return NumberViewHolder(binding)
    }
  }
}
