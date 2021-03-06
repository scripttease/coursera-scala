package recfun
import scala.annotation.tailrec

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
        println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0)
      1
    else if (c == r)
      1
    else
      pascal(c, r-1) + pascal(c-1, r-1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    @tailrec
    def inner(chars: List[Char], openParens: Int): Boolean = {
      if (openParens < 0)
        false
      else if (chars.isEmpty)
        openParens == 0
      else 
        chars.head match {
          case '(' => inner(chars.tail, openParens + 1)
          case ')' => inner(chars.tail, openParens -1)
          case _ => inner(chars.tail, openParens)
        }
    }

    inner(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0)
      1
    else if (coins.isEmpty)
      0
    else if (money < 0)
      0
    else
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }
}
