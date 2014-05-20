package TutorialExamples

import Chisel._

class Combinational extends Module {
  val io = new Bundle {
    val x   = UInt(INPUT,  16)
    val y   = UInt(INPUT,  16)
    val z   = UInt(OUTPUT, 16)
  }
  val sum = io.x + io.y
  io.z := sum
  counter(sum)
}

class CombinationalTests(c: Combinational) extends Tester(c, isLoggingPokes = true) {
  val maxInt = 1 << 16
  for (i <- 0 until 10) {
    val x = rnd.nextInt(maxInt)
    val y = rnd.nextInt(maxInt)
    poke(c.io.x, x)
    poke(c.io.y, y)
    step(1)
    expect(c.io.z, (x + y)&(maxInt-1))
  }
}

// same as CombinationalTests but extends DaisyTester
class CombinationalDaisyTests(c: Combinational) extends DaisyTester(c) {
  val maxInt = 1 << 16
  for (i <- 0 until 10) {
    val x = rnd.nextInt(maxInt)
    val y = rnd.nextInt(maxInt)
    poke(c.io.x, x)
    poke(c.io.y, y)
    step(1)
    expect(c.io.z, (x + y)&(maxInt-1))
  }
}

class CombinationalWrapper extends DaisyWrapper(new Combinational)
 
class CombinationalWrapperTests(c: CombinationalWrapper) extends DaisyWrapperTester(c) {
  val maxInt = 1 << 16
  for (i <- 0 until 10) {
    val x = rnd.nextInt(maxInt)
    val y = rnd.nextInt(maxInt)
    poke(c.top.io.x, x)
    poke(c.top.io.y, y)
    step(1)
    expect(c.top.io.z, (x + y)&(maxInt-1))
  }
}
