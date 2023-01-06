package com.example.memory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min
import android.util.Log
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import models.BoardSize
import models.MemoryCard

class MemoryBoardAdapter(
    private val context: Context,
    private val boardSize: BoardSize,
    private val cards: List<MemoryCard>,
    private val cardClickListener: CardClickListener
) :
    RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>() {
    companion object{
        private const val MARGIN_SIZE=10
        private const val TAG= "MemoryBoardAdapter"
    }

    interface CardClickListener {
        fun onCardClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardWidth = parent.width/boardSize.getWidth()- (2* MARGIN_SIZE)
        val cardHeigth = parent.height/boardSize.getHeight() - (2* MARGIN_SIZE)
        val cardSideLength = min(cardWidth, cardHeigth)
        val view: View = LayoutInflater.from(context).inflate(R.layout.memory_card,parent,false)
        val layoutParams= view.findViewById<CardView>(R.id.cardView).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width=cardSideLength
        layoutParams.height=cardSideLength
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount()=boardSize.numCards

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageBotton= itemView.findViewById<ImageButton>(R.id.imageButton)
        fun bind(position: Int) {
            val memoryCard=cards[position]
            imageBotton.setImageResource(if (cards[position].isFaceUp) cards[position].identifier else R.drawable.ic_launcher_background)
            imageBotton.alpha=if (memoryCard.isMatched) .4f else 1.0f
            val colorStateList=if (memoryCard.isMatched) ContextCompat.getColorStateList(context,R.color.color_gray) else  null
            ViewCompat.setBackgroundTintList(imageBotton,colorStateList)
            imageBotton.setOnClickListener{
                Log.i(TAG,"Clicked on position $position")
                cardClickListener.onCardClicked(position)
            }

        }
    }
}
