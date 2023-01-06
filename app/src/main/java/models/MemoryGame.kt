package models

import com.example.memory.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize: BoardSize){


    val cards: List<MemoryCard>
    var numPairsFound = 0
    private var numCardFllip = 0

    private var indexOfSingleSelectedCard: Int? = null
    init {
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages+chosenImages).shuffled()
        cards = randomizedImages.map{MemoryCard(it)}
    }

    fun flipCard(position: Int):Boolean {
        numCardFllip++
        val card= cards[position]
        //case1: 0 cards flipped => flip over the card
        //case1: 1 card flipped => flip the card + check if cards match
        //case2: 2 cards flipped => restore cards+flip over the card
        var foundMatch=false
        if (indexOfSingleSelectedCard == null){
            // 0 or 2 cards previouslyy flipped
            restoreCards()
            indexOfSingleSelectedCard=position
        } else{
            //1 card flipped
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!,position)
            indexOfSingleSelectedCard=null
        }
        card.isFaceUp = !card.isFaceUp
        return foundMatch
    }

    private fun checkForMatch(position1: Int, position2: Int): Boolean {
        if (cards[position1].identifier != cards[position2].identifier){
            return false
        }
        cards[position1].isMatched=true
        cards[position2].isMatched=true
        numPairsFound++
        return true
    }

    private fun restoreCards() {
        for (card in cards){
            if(!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    fun haveWonGame(): Boolean {
        return numPairsFound ==boardSize.getNumPairs()
    }

    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp

    }

    fun getNumMoves(): Int {
        return numCardFllip / 2
    }
}
