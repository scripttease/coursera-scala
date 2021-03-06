package funsets


/**
 * 2. Purely Functional Sets.
 */
object FunSets {
  /**
   * We represent a set by its characteristic function, i.e.
   * its `contains` predicate.
   */
  type Set = Int => Boolean

  /**
   * Indicates whether a set contains a given element.
   */
  def contains(s: Set, elem: Int): Boolean = s(elem)

  /**
   * Returns the set of the one given element.
   */
    def singletonSet(elem: Int): Set = {
      // elem => true
      (x: Int) => x == elem
      // this is returning an anonymous function
    }
  

  /**
   * Returns the union of the two given sets,
   * the sets of all elements that are in either `s` or `t`.
   */
    def union(s: Set, t: Set): Set = {
      (x: Int) => s(x) || t(x)
    }
  

  /**
   * Returns the intersection of the two given sets,
   * the set of all elements that are both in `s` and `t`.
   */
    def intersect(s: Set, t: Set): Set = {
      (x: Int) => s(x) && t(x)
      // not entirely sure why && here works and == doesn't but != works below
      // but == doesn't work here because if x is in neither set, 
      // s(x) = false and t(x) = false and false && false = true
    }
  

  /**
   * Returns the difference of the two given sets,
   * the set of all elements of `s` that are not in `t`.
   */
    def diff(s: Set, t: Set): Set = {
      (x: Int) => s(x) && !t(x)
    }
    // if s(x) is false BUT t(x) is true (so its not in one but is in 2)
    // s(x) != t(x) so that would actually return true... which we dont want.

  
  /**
   * Returns the subset of `s` for which `p` holds.
   */
    def filter(s: Set, p: Int => Boolean): Set = {
      (x: Int) => s(x) && p(x)
    }

  /**
   * The bounds for `forall` and `exists` are +/- 1000.
   */
  val bound = 1000

  /**
   * Returns whether all bounded integers within `s` satisfy `p`.
   */
    def forall(s: Set, p: Int => Boolean): Boolean = {
      def iter(a: Int): Boolean = {
        if (a == bound + 1) // gets to 1001 and nothing in set fails pred.
          true
        // test if in set but fails predicate
        // don't need to test if in set only.
        else if (contains(s, a) && !contains(p, a))
          false
        else iter(a + 1)
      }
      // don't know what is in set (other than it's an Int)
      // so must test each int between the bounds fulfils predicate
      iter(-bound) // start from lower bound of -1000
    }
  
  /**
   * Returns whether there exists a bounded integer within `s`
   * that satisfies `p`.
   */
    def exists(s: Set, p: Int => Boolean): Boolean = {
      def iter(a: Int): Boolean = {
        if (a == bound + 1) // gets to 1001 and nothing in set satisfied pred.
          false
        // test if in set and satisfies predicate
        else if (contains(s, a) && contains(p, a))
          true
        else iter(a + 1)
      }
      // don't know what is in set (other than it's an Int)
      // so must test each int between the bounds fulfils predicate
      iter(-bound) // start from lower bound of -1000
    }
  
  /**
   * Returns a set transformed by applying `f` to each element of `s`.
   */
    def map(s: Set, f: Int => Int): Set = ???
      // if bound int a in set, return new set containing f(a)
      // if s(a) 
      //    singletonSet(f(a))
      // but on next iter do union (so add the next singletonSet(f(a)) to prev.
      //
  
  /**
   * Displays the contents of a set
   */
  def toString(s: Set): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }

  /**
   * Prints the contents of a set on the console.
   */
  def printSet(s: Set) {
    println(toString(s))
  }
}
