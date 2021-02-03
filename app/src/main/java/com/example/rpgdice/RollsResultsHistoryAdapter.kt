package com.example.rpgdice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RollsResultsHistoryAdapter(
    context: Context,
    private val rollResults: List<RollResult>?,
) :
    RecyclerView.Adapter<RollsResultsHistoryAdapter.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    // Создаем элемент списка который отображается на экране
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.roll_result_list_item, parent, false)
        return ViewHolder(view)
    }

    // Задаем значения для элемента списка
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        rollResults?.get(position)?.let { holder.bind(it) }
    }

    // Получаем количество элементов в списке
    override fun getItemCount(): Int {
        return rollResults?.size ?: 0
    }

    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        private val rollConfiguration: TextView = view.findViewById(R.id.rollConfigurationListItem)
        private val mainRollResult: TextView = view.findViewById(R.id.rollMainResultListItem)
        private val rollsResult: TextView = view.findViewById(R.id.rollResultsListItem)

        fun bind(rollResult: RollResult) {
            rollConfiguration.text = rollResult.whatDiceWasRolled
            mainRollResult.text = rollResult.mainResult.toString()
            rollsResult.text = rollResult.rolls.toString()
        }
    }
}