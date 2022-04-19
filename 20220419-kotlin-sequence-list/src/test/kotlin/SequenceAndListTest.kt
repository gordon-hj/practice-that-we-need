import org.junit.Test
import kotlin.system.measureTimeMillis


class SequenceAndListTest {
    @Test
    fun streamAndCollection() {
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
    fun streamAndList() {
        val list = (1..50).toList()
        val stream = list.asSequence()

        var countList = 0
        val listResult = list
            .map {
                countList++;
                println("1. map : $it")
                it
            }
            .filter {
                countList++;
                println("2. filter : $it")
                (it % 2) == 0
            }
            .map {
                countList++;
                println("3. map : $it")
                it
            }
            .filter {
                countList++;
                println("4. filter : $it")
                (it % 5) == 0
            }
            .map {
                countList++;
                println("5. map : $it")
                it
            }
            .take(2)
        println(listResult)
        println(countList)
        println("------------------------------------------------------------------------------------------------------------------------")
        var countStream = 0
        val streamResult = stream
            .map {
                countStream++;
                println("1. map : $it")
                it
            }
            .filter {
                countStream++;
                println("2. filter : $it")
                (it % 5) == 0
            }
            .map {
                countStream++;
                println("3. map : $it")
                it
            }
            .filter {
                countStream++;
                println("4. filter : $it")
                (it % 2) == 0
            }
            .map {
                countStream++;
                println("5. map : $it")
                it
            }
            .take(2)
            .toList()
        println(streamResult)
        println(countStream)
        println("------------------------------------------------------------------------------------------------------------------------")
        var resultList = mutableListOf<Int>()
        for (n in list) {
            val t = pseudoStream(n)
            if (t != null) {
                resultList.add(t)
            }
            if (resultList.size == 2) break
        }
        println(resultList)
        println(count)
    }

    var count: Int = 0
    fun pseudoStream(n: Int): Int? {
        println("1. map $n").also { count++ }
        println("2. filter $n").also { count++ }
        if (n % 5 != 0) return null
        println("3. map $n").also { count++ }
        println("4. filter $n").also { count++ }
        if (n % 2 != 0) return null
        println("5. map $n").also { count++ }
        return n
    }


    @Test
    fun streamAndList2() {
        // 숫자 적을 땐 list가 더 나음
        val list = (1..10000L).toList()
        val stream = list.asSequence()
        val size = 100000

        var listResult: List<Long>
        val listTime = measureTimeMillis {
            listResult = list
                .filter { (it % 2) == 0L }
                .filter { (it % 5) == 0L }
                .take(size)
        }

        var stream1Result: List<Long>
        val stream1Time = measureTimeMillis {
            stream1Result = stream
                .filter { (it % 2) == 0L }
                .filter { (it % 5) == 0L }
                .take(size)
                .toList()
        }

        var stream2Result: List<Long>
        val stream2Time = measureTimeMillis {
            stream2Result = stream
                .filter { (it % 2) == 0L }
                .filter { (it % 5) == 0L }
                .take(size)
                .toList()
        }

        var directList: MutableList<Long> = mutableListOf()
        val directListTime = measureTimeMillis {
            for (n in list) {
                if (n % 2 != 0L) continue
                if (n % 5 != 0L) continue
                directList.add(n)
                if (directList.size == size) break
            }
        }

        var directList2: MutableList<Long> = mutableListOf()
        val directListTime2 = measureTimeMillis {
            for (n in list) {
                if (n % 5 != 0L && n % 2 != 0L) continue
                directList2.add(n)
                if (directList2.size == size) break
            }
        }

        var directList3: MutableList<Long> = mutableListOf()
        val directListTime3 = measureTimeMillis {
            for (n in list) {
                if (n % 2 != 0L && n % 5 != 0L) continue
                directList3.add(n)
                if (directList3.size == size) break
            }
        }

        println(listResult == stream1Result)
        println(stream1Result == stream2Result)
        println(stream2Result == directList)
        println(directList == directList2)
        println("list $listTime\nstream1 $stream1Time\nstream2 $stream2Time\ndirectTime $directListTime\ndirectTime2 $directListTime2\ndirectTime3 $directListTime3")
    }
}