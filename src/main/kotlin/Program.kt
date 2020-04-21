import javax.naming.Context
import kotlin.random.Random


fun main() {
    var newGame:GameOfYamb=GameOfYamb();
    do{
        print("Start game ? (y/n) ");
        val input= readLine()
    }while(input!="y")
    newGame.rollDices();
    newGame.showGameState();
    var forLocking:MutableList<Int> = mutableListOf();
    println("You have:"+newGame.checkResult());
    do{
        print("If you want to lock dice enter its position(1,2,3...), otherwise enter x: ");
        var input= readLine()
        if(input!="x") forLocking.add(input!!.toInt())  // !! operator sluzi za hendlanje null vrijednosti inputa
    }while(input!="x");
    newGame.lockFollowing(*forLocking.toIntArray());
    forLocking.clear();
    println("Roll unlocked dices ? (y/n)")
    when(readLine()){
        "y"-> newGame.rollDices();
        else ->

    }
    do{
        print("If you want to lock dice enter its position(1,2,3...), otherwise enter x: ");
        var input= readLine()
        if(input!="x") forLocking.add(input!!.toInt())  // !! operator sluzi za hendlanje null vrijednosti inputa
    }while(input!="x");
    newGame.lockFollowing(*forLocking.toIntArray());
    do{
        newGame.showGameState();
        print("Roll again? otherwise x. ");
        var input= readLine()
        if(input=="yes"){newGame.rollDices(); println(newGame.checkResult())}
    }while(input!="x")
    newGame.rollDices();
    println()



}

fun lockingUI(ctx: Context){


}


class GameOfYamb {
    var dices: MutableList<Dice> = mutableListOf<Dice>();

    init {
        for (n in 1..6) {
            dices.add(Dice());
        }
    }

    fun showGameState() {
        for (dice in dices) {
            print("${dice.rolled} ")
        }
        println("");
    }

    fun lockFollowing(vararg indexes: Int) {
        for (num in indexes) {
            dices[num - 1].lock()
        }
    }

    fun rollDices() {
        for (dice in dices) {
            dice.roll();
        }
    }

    fun checkResult(): String {
        var array: MutableList<Int> = mutableListOf<Int>()
        for (dice in dices) {
            array.add(dice.rolled!!)
        }
        when (array.distinct().size) {

            1 -> return "YAMB"
            2 -> if (
                (array.filter { it -> it == array.distinct()[0] }.size == 4)
                ||
                (array.filter { it -> it == array.distinct()[1] }.size == 4)) {
                    return "POKER"
            }
            6 -> return "SKALA"
            else -> return "nista"
        }
        return "nista"
    }

}


class Dice{
    var locked:Boolean=false
        private set;
    var rolled : Int? = null
        private set;

    fun roll():Unit{
        if(!locked) this.rolled= Random.nextInt(from=1,until=7);
    }
    fun lock(){
        this.locked=true;
    }

}

