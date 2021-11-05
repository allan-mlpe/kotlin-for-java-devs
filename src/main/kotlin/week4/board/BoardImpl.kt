package week4.board

import week4.board.Direction.*
import java.lang.IllegalArgumentException

open class SquareBoardImpl(final override val width: Int) : SquareBoard {

    protected var cells: List<Cell>

    init {
        cells = (1..width)
                .flatMap { row -> (1..width).map { col -> Cell(row, col) }}
    }

    private fun isOutsideRange(i: Int, j: Int): Boolean {
        return (i > width || j > width)
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return when {
            isOutsideRange(i, j) -> null
            else -> cells.firstOrNull() { it.i == i && it.j == j }
        }
    }

    override fun getCell(i: Int, j: Int): Cell {
        return when {
            isOutsideRange(i, j) -> throw  IllegalArgumentException()
            else -> cells.first { it.i == i && it.j == j }
        }
    }

    override fun getAllCells(): Collection<Cell> {
        return cells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return jRange.mapNotNull { getCellOrNull(i, it) }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return iRange.mapNotNull { getCellOrNull(it, j) }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            UP -> getCellOrNull(i - 1, j)
            DOWN -> getCellOrNull(i + 1, j)
            LEFT -> getCellOrNull(i, j - 1)
            RIGHT -> getCellOrNull(i, j + 1)
        }
    }
}

class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width), GameBoard<T> {

    private val storageMap: MutableMap<Cell, T?> = mutableMapOf()

    init {
        cells.forEach { cell -> storageMap[cell] = null }
    }

    override fun get(cell: Cell): T? {
        return storageMap[cell]
    }

    override fun set(cell: Cell, value: T?) {
        storageMap[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return storageMap.filterValues(predicate).keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return storageMap.filterValues(predicate).keys.first()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return storageMap.values.any(predicate)
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return storageMap.values.all(predicate)
    }

}

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)

fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl<T>(width)
