package com.example.rpgdice

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class ShowDiceRollResultDialog(private val rollResults: RollResult) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        val view: View = inflater.inflate(R.layout.dice_roll_result_dialog, null)
        builder.setView(view)

        view.findViewById<TextView>(R.id.whichDiceWasRolled).text = rollResults.whatDiceWasRolled
        view.findViewById<TextView>(R.id.mainRollResult).text = rollResults.mainResult.toString()
        view.findViewById<TextView>(R.id.rollResults).text = rollResults.rolls.toString()

        // Остальной код
        return builder.create()
    }
}