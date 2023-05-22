package io.yeahx4.cpu.logic

/// Byte Operation
class Op {
    companion object {
        fun plus(b1: VByte, b2: VByte): VByte {
            var up = 0
            val sb = StringBuilder()

            b1
                .toList()
                .reversed()
                .zip(b2.toList().reversed())
                .forEach {
                    val f = it.first
                    val s = it.second

                    var result = f.value + s.value + up

                    if (result > 1) {
                        if (result % 2 == 0) {
                            up = result / 2
                            result = 0
                        } else {
                            up = (result - 1) / 2
                            result = 1
                        }
                    } else {
                        up = 0
                    }

                    sb.append(result.toString())
                }

            return VByte(sb.reversed().toString())
        }

        fun minus(b1: VByte, b2: VByte): VByte {
            return plus(b1, b2.toNegative())
        }
    }
}
