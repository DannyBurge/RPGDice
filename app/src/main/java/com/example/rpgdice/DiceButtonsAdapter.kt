package com.example.rpgdice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiceButtonsAdapter(
    context: Context,
    private val dices: MutableList<Dice>?,
    private val diceCellClickListener: DiceCellClickListener
) :
    RecyclerView.Adapter<DiceButtonsAdapter.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    // Создаем элемент списка который отображается на экране
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.dice_list_item, parent, false)
        return ViewHolder(view)
    }

    // Задаем значения для элемента списка
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dices?.get(position)?.let { holder.bind(it) }

        holder.itemView.setOnClickListener {
            dices?.get(position)?.let { it1 -> diceCellClickListener.onCellClickListener(it1) }
        }
    }

    // Получаем количество элементов в списке
    override fun getItemCount(): Int {
        return dices?.size ?: 0
    }

    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        private val diceType: TextView = view.findViewById(R.id.diceTitleInListItem)
        private val diceButton: TextView = view.findViewById(R.id.buttonInListItem)

        fun bind(dice: Dice) {
            diceType.text = dice.diceType
            diceButton.text = dice.diceType
        }
    }
}