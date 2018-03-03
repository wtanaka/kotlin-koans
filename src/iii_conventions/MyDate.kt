package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        this.year != other.year -> this.year - other.year
        this.month != other.month -> this.month - other.month
        else -> this.dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.compareTo(other: MyDate): Int = 0

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(interval: TimeInterval) = this.addTimeIntervals(interval, 1)

operator fun MyDate.plus(interval: MultipleTimeIntervals) = this.addTimeIntervals(interval.interval, interval.count)

operator fun TimeInterval.times(count: Int): MultipleTimeIntervals = MultipleTimeIntervals(this, count)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

data class MultipleTimeIntervals(val interval: TimeInterval, val count: Int)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current: MyDate = start
            override fun hasNext(): Boolean = current <= endInclusive

            override fun next(): MyDate {
                val next = current
                current = current.nextDay()
                return next
            }
        }
    }

}
