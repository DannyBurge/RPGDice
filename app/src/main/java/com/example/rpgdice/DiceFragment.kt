package com.example.rpgdice

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rpgdice.databinding.DicesFragmentBinding

class DiceFragment : Fragment(), DiceCellClickListener {
    private var diceList: MutableList<Dice> = ArrayList()
    private lateinit var binding: DicesFragmentBinding
    private lateinit var rollResultHistory: LiveData<MutableList<RollResult>?>
    private lateinit var historyWasChanged: LiveData<Int>
    private lateinit var dialog: ShowDiceRollResultDialog
    private var howMuch: Int = 0
    private var modifier: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        historyWasChanged = (activity as MainActivity).getHistorySize()
        rollResultHistory = (activity as MainActivity).getRollResultHistory()
        binding = DataBindingUtil.inflate(inflater, R.layout.dices_fragment, container, false)

        // Подгружаем из сейва значения, если есть, если нет, то ставим по умолчанию
        howMuch = savedInstanceState?.getInt("howMuch") ?: 1
        modifier = savedInstanceState?.getInt("modifier") ?: 0

        // Вяжем кубы со списком и, соответсвенно, вяжем каллбаки через интерфейс
        initDiceList()
        // Инициализация полей с количеством бросков
        initHowMuchButtons()
        // Инициализация полей с модификатором бросков
        initModifierButtons()

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("howMuch", howMuch)
        outState.putInt("modifier", modifier)
        super.onSaveInstanceState(outState)
    }

    private fun initDiceList() {
        // Это стандартные кубы
        diceList.add(Dice(2))
        diceList.add(Dice(4))
        diceList.add(Dice(6))
        diceList.add(Dice(8))
        diceList.add(Dice(10))
        diceList.add(Dice(12))
        diceList.add(Dice(20))
        diceList.add(Dice(100))
        // Здесь нужно будет проверять пользовательские кубы из SharedPreferences
        //TODO(ввести возможность добавлять пользовательские кубы)

        // Засовываем список кубов в RecyclerView с адаптером, разметку устанавливаем сеточную,
        // в зависимостри от ориентации экрана изменяется число столбцов
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.diceList.layoutManager = GridLayoutManager(this.context!!, 4)
        } else binding.diceList.layoutManager = GridLayoutManager(this.context!!, 8)
        binding.diceList.adapter = DiceButtonsAdapter(this.context!!, diceList, this)
    }

    private fun initHowMuchButtons() {
        binding.howMuchRollTheDiceText.text = getString(R.string.howMuch, howMuch)
        // Инкремент и декремент количества бросков с ограничениями от 1 до 100
        binding.decrement.setOnClickListener {
            howMuch = if (howMuch > 1) howMuch - 1 else 1
            binding.howMuchRollTheDiceText.text = getString(R.string.howMuch, howMuch)
        }
        binding.increment.setOnClickListener {
            howMuch = if (howMuch < 100) howMuch + 1 else 100
            binding.howMuchRollTheDiceText.text = getString(R.string.howMuch, howMuch)
        }
        // Длительное нажатие устанавливает граничные значения
        binding.decrement.setOnLongClickListener {
            howMuch = 1
            binding.howMuchRollTheDiceText.text = getString(R.string.howMuch, howMuch)
            true
        }
        binding.increment.setOnLongClickListener {
            howMuch = 100
            binding.howMuchRollTheDiceText.text = getString(R.string.howMuch, howMuch)
            true
        }
    }

    private fun initModifierButtons() {
        binding.ModifierText.text = getString(R.string.modifier, modifier)
        // Инкремент и декремент модификатора бросков с ограничениями от +0 до +100
        binding.decrementModifier.setOnClickListener {
            modifier = if (modifier > 0) modifier - 1 else 0
            binding.ModifierText.text = getString(R.string.modifier, modifier)
        }
        binding.incrementModifier.setOnClickListener {
            modifier = if (modifier < 100) modifier + 1 else 100
            binding.ModifierText.text = getString(R.string.modifier, modifier)
        }
        // Длительное нажатие устанавливает граничные значения
        binding.decrementModifier.setOnLongClickListener {
            modifier = 0
            binding.ModifierText.text = getString(R.string.modifier, modifier)
            true
        }
        binding.incrementModifier.setOnLongClickListener {
            modifier = 100
            binding.ModifierText.text = getString(R.string.modifier, modifier)
            true
        }
    }

    // Бросаем экземпляр куба заданное количество раз и с заданным модификатором
    // и выводим результат в строку для диалога
    private fun getResultOfDiceRoll(dice: Dice, howMuch: Int, modifier: Int): RollResult {
        val rollResults = RollResult(
            if (modifier == 0) {
                "$howMuch${dice.diceType}"
            } else {
                "$howMuch${dice.diceType}+$modifier"
            }
        )
        repeat(howMuch)
        {
            rollResults.rolls.add(dice.roll())
        }
        rollResults.mainResult = rollResults.rolls.sum() + modifier
        return rollResults
    }

    // Реагируем на нажатие на каждый кубик
    override fun onCellClickListener(dice: Dice) {
        // Результат броска
        val result = getResultOfDiceRoll(dice, howMuch, modifier)
        // Показываем диалог с результатом бросков
        dialog = ShowDiceRollResultDialog(result)
        fragmentManager?.let { dialog.show(it, "") }
        // Передаем в историю бросков
        if (rollResultHistory.value == null) {
            (activity as MainActivity).setRollResultHistory(arrayListOf(result))
            (activity as MainActivity).setHistorySize(0)
        } else {
            rollResultHistory.value?.add(result)
            (activity as MainActivity).setHistorySize(rollResultHistory.value!!.size)
        }
    }
}