package week3.taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.filterNot { driver ->
            trips.groupBy { it.driver }.keys.contains(driver)
        }.toSet()


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        if (minTrips == 0) allPassengers
        else trips.flatMap { it.passengers }
                .groupingBy { it }
                .eachCount()
                .filterValues { it >= minTrips }
                .keys

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        trips.filter { it.driver == driver }
                .flatMap { it.passengers }
                .groupingBy { it }
                .eachCount()
                .filterValues { it > 1 }
                .keys

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        allPassengers.map { p ->
            p to trips.filter { it.passengers.contains(p) }.partition { (it.discount ?: 0.0) > 0.0 }
        }.filter { (_, trips) ->
            trips.first.size > trips.second.size
        }.map { (psg, _) -> psg }
                .toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    val x = trips.map { it.duration }
            .sorted()

    val maxTime = x.lastOrNull()

    val tranches = (maxTime ?: 0 / 10) + 1
    val ranges = HashSet<IntRange>()

    for (i in 0..tranches) {
        ranges.add(IntRange(start = i * 10, endInclusive = i * 10 + 9))
    }

    return if (trips.isEmpty()) null
    else ranges.map { range ->
        range to x.count { range.contains(it) }
    }.sortedByDescending { it.second }[0].first
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val driversCount = allDrivers.size
    val driversPercentage20 = (0.2 * driversCount).toInt()
    val totalIncome = trips.sumOf { it.cost }
    val incomePercentage80 = 0.8 * totalIncome

    val driversRanking = allDrivers.map { d ->
        d to trips.filter { d == it.driver }.sumOf { it.cost }
    }.sortedByDescending { it.second }

    return if (trips.isEmpty()) false
    else driversRanking
            .take(driversPercentage20)
            .sumOf { it.second } >= incomePercentage80
}