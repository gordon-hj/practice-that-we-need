import org.junit.Test
import kotlin.system.measureTimeMillis


class SequenceAndListTest {
    @Test
    fun collectionAndSequence_showStep() {
        val arr = (1..5)

        val collection = arr.toList()
        val sequence = arr.asSequence()

        println("Collection Execution")
        collection
            .map { it.also { println("step 1 : $it") } }
            .let { it.also { println("intermediate collection 1 : $it")}}
            .filter { v -> (v % 2 == 0).also { println("step 2 : $v") } }
            .let { it.also { println("intermediate collection 2 : $it")}}
            .filter { v -> (v % 5 == 0).also { println("step 3 : $v") } }
            .let { it.also { println("intermediate collection 3(also result collection) : $it")}}

        println("Sequence Execution")
        sequence
            .map { it.also { println("step 1 : $it") } }
            .filter { v -> (v % 2 == 0).also { println("step 2 : $v") } }
            .filter { v -> (v % 5 == 0).also { println("step 3 : $v") } }
            .first()
    }

    @Test
    fun collectionAndSequence_countOperation() {
        val arr = (1..50)
        val resultSize = 2

        val collection = arr.toList()
        val sequence = arr.asSequence()

        var collectionCount = 0
        val collectionResult = collection
            .map { it.also { collectionCount++ } }
            .filter { v -> (v % 2 == 0).also { collectionCount++ } }
            .map { it.also { collectionCount++ } }
            .filter { v -> (v % 5 == 0).also { collectionCount++ } }
            .map { it.also { collectionCount++ } }
            .take(resultSize)
        println("Collection result : $collectionResult, operation count: $collectionCount")
        // ====================================================================================
        var sequenceCount = 0
        val sequenceResult = sequence
            .map { it.also { sequenceCount++ } }
            .filter { v -> (v % 2 == 0).also { sequenceCount++ } }
            .map { it.also { sequenceCount++ } }
            .filter { v -> (v % 5 == 0).also { sequenceCount++ } }
            .map { it.also { sequenceCount++ } }
            .take(resultSize)
            .toList()
        println("Sequence result : $sequenceResult, operation count: $sequenceCount")
    }

    @Test
    fun collectionAndSequence_measureTime_smallCase() {
        val arr = (1..5000)
        val resultSize = 2

        val collection = arr.toList()
        val sequence = arr.asSequence()

        var collectionCount = 0
        val collectionTime = measureTimeMillis {
            collection
                .map { it.also { collectionCount++ } }
                .filter { v -> (v % 2 == 0).also { collectionCount++ } }
                .map { it.also { collectionCount++ } }
                .filter { v -> (v % 5 == 0).also { collectionCount++ } }
                .map { it.also { collectionCount++ } }
                .take(resultSize)
        }
        println("Collection spent time: $collectionTime, operation count: $collectionCount")
        // ====================================================================================
        var sequenceCount = 0
        val sequenceTime = measureTimeMillis {
            sequence
                .map { it.also { sequenceCount++ } }
                .filter { v -> (v % 2 == 0).also { sequenceCount++ } }
                .map { it.also { sequenceCount++ } }
                .filter { v -> (v % 5 == 0).also { sequenceCount++ } }
                .map { it.also { sequenceCount++ } }
                .take(resultSize)
                .toList()

        }
        println("Sequence spent time: $sequenceTime, operation count: $sequenceCount")
    }

    @Test
    fun collectionAndSequence_measureTime_largeCase() {
        val arr = (1..25000)
        val resultSize = 150

        val collection = arr.toList()
        val sequence = arr.asSequence()

        var collectionCount = 0
        val collectionTime = measureTimeMillis {
            collection
                .map { it.also { collectionCount++ } }
                .filter { v -> (v % 2 == 0).also { collectionCount++ } }
                .map { it.also { collectionCount++ } }
                .filter { v -> (v % 5 == 0).also { collectionCount++ } }
                .map { it.also { collectionCount++ } }
                .take(resultSize)
        }
        println("Collection spent time: $collectionTime, operation count: $collectionCount")
        // ====================================================================================
        var sequenceCount = 0
        val sequenceTime = measureTimeMillis {
            sequence
                .map { it.also { sequenceCount++ } }
                .filter { v -> (v % 2 == 0).also { sequenceCount++ } }
                .map { it.also { sequenceCount++ } }
                .filter { v -> (v % 5 == 0).also { sequenceCount++ } }
                .map { it.also { sequenceCount++ } }
                .take(resultSize)
                .toList()

        }
        println("Sequence spent time: $sequenceTime, operation count: $sequenceCount")
    }


    @Test
    fun collectionAndSequence_latteIsBest() {
        val arr = (1..10000000)
        val resultSize = 1500

        val collection = arr.toList()
        val sequence = arr.asSequence()

        val collectionTime = measureTimeMillis {
            collection
                .map { it }
                .filter { v -> (v % 2 == 0) }
                .map { it }
                .filter { v -> (v % 5 == 0) }
                .map { it }
                .take(resultSize)
        }
        println("Collection spent time: $collectionTime")
        // ====================================================================================
        val sequenceTime = measureTimeMillis {
            sequence
                .map { it }
                .filter { v -> (v % 2 == 0) }
                .map { it }
                .filter { v -> (v % 5 == 0) }
                .map { it }
                .take(resultSize)
                .toList()

        }
        println("Sequence spent time: $sequenceTime")
        // ====================================================================================
        val latteTime = measureTimeMillis {
            val resultList: MutableList<Int> = mutableListOf()
            for (n in arr) {
                if (n % 2 != 0) continue
                if (n % 5 != 0) continue
                resultList.add(n)
                if (resultList.size == resultSize) break
            }
        }
        println("Old-way spent time: $latteTime")
    }
}