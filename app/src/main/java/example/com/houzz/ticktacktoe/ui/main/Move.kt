package example.com.houzz.ticktacktoe.ui.main

class Move(val turn:Int, val i: Int, val j: Int) {

    override fun equals(other: Any?): Boolean = (i - (other as Move).i == 0) and (j - other.j == 0)

}