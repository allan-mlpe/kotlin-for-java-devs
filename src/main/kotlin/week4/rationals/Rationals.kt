package week4.rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger

class Rational(private val numerator: BigInteger, private val denominator: BigInteger): Comparable<Rational> {
    init {
        if (0.toBigInteger() == denominator) {
            throw IllegalArgumentException()
        }
    }

    private fun simplify(): Rational {
        val gcd = numerator.gcd(denominator)

        return Rational(numerator.div(gcd), denominator.div(gcd))
    }

    operator fun plus(other: Rational): Rational {
        val numerator = this.numerator.multiply(other.denominator)
        val numerator2 = other.numerator.multiply(this.denominator)

        return Rational(numerator.plus(numerator2), this.denominator.times(other.denominator)).simplify()
    }

    operator fun minus(other: Rational): Rational {
        val numerator1 = this.numerator.multiply(other.denominator)
        val numerator2 = other.numerator.multiply(this.denominator)

        return Rational(numerator1.minus(numerator2), this.denominator.times(other.denominator)).simplify()
    }

    operator fun times(other: Rational): Rational {
        return Rational(this.numerator.times(other.numerator), this.denominator.times(other.denominator))
    }

    operator fun div(other: Rational): Rational {
        return Rational(this.numerator.times(other.denominator), this.denominator.times(other.numerator))
    }

    operator fun unaryMinus(): Rational {
        return Rational(this.numerator.negate(), this.denominator)
    }

    override operator fun compareTo(other: Rational): Int {
        val numerator1 = this.numerator.multiply(other.denominator)
        val numerator2 = other.numerator.multiply(this.denominator)

        return when {
            numerator1 > numerator2 -> 1
            numerator1 == numerator2 -> 0
            else -> -1
        }
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Rational -> {
                this.simplify().toString() == other.simplify().toString()
            }
            else -> false
        }
    }

    override fun toString(): String {
        val simplifiedRational = this.simplify()
        val valuesSet = setOf(simplifiedRational.denominator, simplifiedRational.denominator)

        return when {
            1.toBigInteger() == simplifiedRational.denominator ->
                "${simplifiedRational.numerator}"
            valuesSet.all { it < 0.toBigInteger()} ->
                "${simplifiedRational.numerator.negate()}/${simplifiedRational.denominator.negate()}"
            valuesSet.any { it < 0.toBigInteger()} ->
                "-${simplifiedRational.numerator.abs()}/${simplifiedRational.denominator.abs()}"
            else -> "${simplifiedRational.numerator}/${simplifiedRational.denominator}"
        }
    }
}

infix fun Int.divBy(x: Int): Rational {
    return Rational(this.toBigInteger(), x.toBigInteger())
}

infix fun BigInteger.divBy(x: BigInteger): Rational {
    return Rational(this, x)
}

infix fun Long.divBy(x: Long): Rational {
    return Rational(this.toBigInteger(), x.toBigInteger())
}

fun String.toRational(): Rational {
    val rationalString = this.split("/")

    return if (rationalString.size > 1) {
        val (num, den) = rationalString
        Rational(num.toBigInteger(), den.toBigInteger())
    } else {
        val (num) = rationalString
        Rational(num.toBigInteger(), 1.toBigInteger())
    }
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)

}