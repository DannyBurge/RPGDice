package com.example.rpgdice

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.rpgdice.databinding.DicesFragmentBinding

class DiceFragment : Fragment() {
    private lateinit var binding: DicesFragmentBinding
    lateinit var dialog: AlertDialog.Builder
    var howMuch: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog = AlertDialog.Builder(this.context)

        binding = DataBindingUtil.inflate(inflater, R.layout.dices_fragment, container, false)

        // Вяжем кнопки кубов с функцией показа результата бросков
        binding.dice4.setOnClickListener { showDialog(Dice(4)) }
        binding.dice6.setOnClickListener { showDialog(Dice(6)) }
        binding.dice8.setOnClickListener { showDialog(Dice(8)) }
        binding.dice10.setOnClickListener { showDialog(Dice(10)) }
        binding.dice20.setOnClickListener { showDialog(Dice(20)) }
        binding.dice100.setOnClickListener { showDialog(Dice(100)) }

        // Инкремент и декремент количества бросков с ограничениями от 1 до 100
        binding.increment.setOnClickListener {
            howMuch = if (howMuch < 100) howMuch + 1 else 100
            binding.howMuchRollTheDiceText.text = howMuch.toString()
        }
        binding.decrement.setOnClickListener {
            howMuch = if (howMuch > 1) howMuch - 1 else 1
            binding.howMuchRollTheDiceText.text = howMuch.toString()
        }

        return binding.root
    }

    // Показываем диалог с результатом бросков
    private fun showDialog(dice: Dice) {
        dialog.setMessage(getResultOfDiceRoll(dice))
        dialog.setView(
            requireActivity().layoutInflater.inflate(
                R.layout.dise_roll_result_dialog,
                null
            )
        )
        dialog.show()
    }


    // Бросаем экземпляр куба и выводим результат в строку для диалога
    private fun getResultOfDiceRoll(dice: Dice): String {
        val rollResults = ArrayList<Int>()
        for (count in 0 until howMuch) {
            rollResults.add(dice.roll())
        }
        return "$rollResults, ${rollResults.sum()}".replace("[", "").replace("]", "")
    }
}