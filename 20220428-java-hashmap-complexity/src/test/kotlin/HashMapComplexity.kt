import org.junit.Test
import kotlin.collections.HashMap
import kotlin.system.measureNanoTime


class HashMapComplexity {
    @Test
    fun hashMap_get_heavy_chaining() {
        val map: HashMap<DatumInWonderLand, Int> = HashMap(1, 1000000f)
        for(i in 1..10000) {
            val key = DatumInWonderLand(i)
            map.put(key, i)
            val t = measureNanoTime {
                map.get(key)
            }
            println("$i : $t")
        }
    }

    data class DatumInWonderLand(
        val t: Int
    ) {
        override fun hashCode(): Int = 1
    }

    @Test
    fun hashMap_get_low_chaining() {
        val map: HashMap<DatumInReal, Int> = HashMap(10000, 1000000f)
        for(i in 1..10000) {
            val key = DatumInReal(i)
            map.put(key, i)
            val t = measureNanoTime {
                map.get(key)
            }
            println("$i : $t")
        }
    }

    data class DatumInReal(
        val t: Int
    ) {
        override fun hashCode(): Int = t
    }
}