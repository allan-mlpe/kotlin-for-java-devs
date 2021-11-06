package week4.rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger

data class Rational(private val numerator: BigInteger, private val denominator: BigInteger) : Comparable<Rational> {
    init {
        if (0.toBigInteger() == denominator) {
            throw IllegalArgumentException()
        }
    }

    private fun normalize(): Rational {
        val gcd = numerator.gcd(denominator)
        val signal = denominator.signum().toBigInteger()

        // we multiply numerator for `signal` (1 or -1) to avoid negative denominators
        val normalizedNumerator = (numerator * signal) / gcd
        val normalizedDenominator = denominator.abs() / gcd

        return Rational(normalizedNumerator, normalizedDenominator)
    }

    operator fun plus(other: Rational): Rational {
        val numerator = this.numerator * other.denominator
        val numerator2 = other.numerator * this.denominator

        return Rational(
                numerator + numerator2,
                this.denominator * other.denominator
        ).normalize()
    }

    operator fun minus(other: Rational): Rational {
        val numerator1 = this.numerator * other.denominator
        val numerator2 = other.numerator * this.denominator

        return Rational(
                numerator1 - numerator2,
                this.denominator * other.denominator
        ).normalize()
    }

    operator fun times(other: Rational): Rational {
        return Rational(
                this.numerator * other.numerator,
                this.denominator * other.denominator
        )
    }

    operator fun div(other: Rational): Rational {
        return Rational(
                this.numerator * other.denominator,
                this.denominator * other.numerator
        )
    }

    operator fun unaryMinus(): Rational {
        return Rational(this.numerator.negate(), this.denominator)
    }

    override operator fun compareTo(other: Rational): Int {
        val numerator1 = this.numerator * other.denominator
        val numerator2 = other.numerator * this.denominator

        return numerator1.compareTo(numerator2)
    }

    override fun toString(): String {
        val simplifiedRational = this.normalize()

        return when {
            1.toBigInteger() == simplifiedRational.denominator ->
                "${simplifiedRational.numerator}"
            else -> "${simplifiedRational.numerator}/${simplifiedRational.denominator}"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational
        val r1 = this.normalize()
        val r2 = other.normalize()

        if (r1.numerator != r2.numerator) return false
        if (r1.denominator != r2.denominator) return false

        return true
    }

    override fun hashCode(): Int {
        val normalized = this.normalize()
        var result = normalized.numerator.hashCode()
        result = 31 * result + normalized.denominator.hashCode()
        return result
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