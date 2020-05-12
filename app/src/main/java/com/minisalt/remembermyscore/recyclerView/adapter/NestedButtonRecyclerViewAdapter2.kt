package com.minisalt.remembermyscore.recyclerView.adapter

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.minisalt.remembermyscore.R
import com.minisalt.remembermyscore.data.GameRules
import com.minisalt.remembermyscore.data.PlayerData

class NestedButtonRecyclerViewAdapter2(
    val context: Context, val gameRule: GameRules, val
    mScore: EditText, val mScore2: EditText, val mRadio1: RadioButton,
    val playerData: PlayerData
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mButton: Button = itemView.findViewById(R.id.nestedButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ButtonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.button_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return gameRule.buttons.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ButtonViewHolder -> {
                if (gameRule.buttons[position] > 0)
                    holder.mButton.text = "+${gameRule.buttons[position]}"
                else
                    holder.mButton.text = gameRule.buttons[position].toString()


                holder.mButton.setOnClickListener {
                    if (mRadio1.isChecked) {
                        var score: Int = Integer.parseInt(mScore.text.toString())
                        score += Integer.parseInt(holder.mButton.text.toString())
                        playerData.playerPoints = score
                        mScore.text = Editable.Factory.getInstance().newEditable(score.toString())
                        checkWinner(score, gameRule.pointsToWin)
                    } else {
                        var score: Int = Integer.parseInt(mScore2.text.toString())
                        score += Integer.parseInt(holder.mButton.text.toString())
                        playerData.playerPoints2 = score
                        mScore2.text = Editable.Factory.getInstance().newEditable(score.toString())
                        if (gameRule.extraField_1condition)
                            checkWinner(score, gameRule.extraField_1_pointsToWin)
                    }


                }
            }
        }
    }

    private fun checkWinner(playerPoints: Int, pointsToWin: Int) {
        if (playerPoints >= pointsToWin) {
            MaterialAlertDialogBuilder(context)
                .setTitle("${playerData.playerName} has won the game!")
                .setMessage("On dialog close, you can resume the game, or start another")
                .setPositiveButton("Awesome!") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}