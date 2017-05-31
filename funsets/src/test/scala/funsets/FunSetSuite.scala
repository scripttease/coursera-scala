package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains only elements in both sets") {
    new TestSets {
      val s4 = union(s1, s2) // s4 sontains 1 and 2
      val s5 = intersect(s1, s4) // s1 contains 1 so s5 should only contain 1
      assert(contains(s5, 1), "Intersection1 contains elem from first set that is in second set")
      // should really test that it doesn't contain repetitions
      assert(!contains(s5, 2), "Intersection1 does not contain elem from second set that is not in first set")
      assert(!contains(s5, 7), "Intersection1 does not contain elem that is in neither set")

      val s6 = union(s2, s3) // s6 contains 2 and 3
      val s7 = intersect(s4, s6) // s7 should only contain 3
      assert(contains(s7, 2), "Intersection2 contains elem from first set that is in second set")
      assert(!contains(s7, 3), "Intersection2 does not contain elem from second set that is not in first set")
      assert(!contains(s7, 7), "Intersection2 does not contain elem that is in neither set")
    }
  }

  test("diff contains all elems of first set that are NOT in second set") {
    new TestSets {
      val s4 = union(s1, s2) // s4 sontains 1 and 2
      val s6 = union(s2, s3) // s6 contains 2 and 3

      val s8 = diff(s4, s6) // should contain 1
      assert(contains(s8, 1), "diff1 contains elems of first set that arent in second set")
      assert(!contains(s8, 2), "diff1 does not contain elems of first set that are in second set")
      assert(!contains(s8, 9), "diff1 does not contain elems that are in neither set")
      assert(!contains(s8, 3), "diff1 does not contain elems of second set that are not in first set")
    }
  }

  test("filter returns the subset of set for which fn p is true") {
    new TestSets {
      val s4 = union(s1, s2) // s4 sontains 1 and 2
      val s6 = union(s2, s3) // s6 contains 2 and 3
      val s9 = union(s4, s6) // s9 contains 1,2 and 3

      val s10 = filter(s9, (x: Int) => x % 2 == 0) // s10 should contain only 2
      assert(contains(s10, 2), "filter1 contains value that satisfies fn")
      assert(!contains(s10, 3), "filter1 doesnt contain value in set that fails to satisfy fn")
      assert(!contains(s10, 4), "filter1 doesnt contain value that satistfies fn but is not in set")
      assert(!contains(s10, 7), "filter1 doesnt contain value that  fails to satistfy fn and is not in set")
      
    }
  }

  // Part 2.2

  test("forall returns true if all bounded ints in set satisfy pred") {
    new TestSets {
      val s = union(union(s1, s2), s3) // contains 1,2,3
      val p = (x: Int) => x % 2 == 0 // predicate states must be even
      
      assert(!forall(s, p), "set contains some ints that fail pred")
      assert(forall(s2, p), "set only contains ints that satisfy pred")
    }
  }

  test("exists returns true if any in bounded ints satisfy pred") {
    new TestSets {
      val s = union(union(s1, s2), s3) // contains 1,2,3
      val p = (x: Int) => x % 2 == 0 // predicate states must be even

      assert(exists(s, p), "set has an int that satisfies pred")
      assert(!exists(s1, p), "set has no ints that satisfy pred")
    }
  }
}
