package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        this.allDrivers.filter {
                x -> this.trips.none { it.driver == x }
        }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        this.allPassengers.filter {
                passenger -> this.trips.count { passenger in it.passengers  } >= minTrips
        }.toSet();

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        this.allPassengers.filter {
            passenger: Passenger -> this.trips.count {
                it.driver == driver && passenger in it.passengers
            } > 1
        }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        this.allPassengers.filter {
            passenger ->
            this.trips.count {
                passenger in it.passengers && it.discount != null
            } > this.trips.count {
                passenger in it.passengers && it.discount == null
            }
        }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if (this.trips.isEmpty()) {
        return null;
    } else {
        return this.trips.groupBy {
            val start = it.duration / 10 * 10
            val end = start + 9
            start..end
        }.maxBy { (_, group) -> group.size }?.key
    }
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (this.trips.isEmpty()) {
        return false
    } else {
        val totalIncome80 = this.trips.sumByDouble { it.cost } * 0.8
        val costDriverSorted = this.trips.groupBy { it.driver }.mapValues {
            (_, trips) -> trips.sumByDouble { it.cost }
        }.entries.sortedByDescending { it.value }

        return costDriverSorted.take(this.allDrivers.size /5)
            .sumByDouble { it.value } >= totalIncome80;
    }
}