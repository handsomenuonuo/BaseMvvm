package org.lib.base.extensions

import org.lib.base.utils.DigitUtils
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * @author: HuangFeng
 * @time: 2020/10/27 3:26 PM
 * @description: -
 * @since: 1.0.0
 */

fun BigDecimal.plus(
    v: String?,
    precision: Int? = null,
    mode: RoundingMode = RoundingMode.FLOOR
): BigDecimal {
    return DigitUtils.plus(this.toString(), v, precision, mode)
}

fun BigDecimal.minus(
    v: String?,
    precision: Int? = null,
    mode: RoundingMode = RoundingMode.FLOOR
): BigDecimal {
    return DigitUtils.minus(this.toString(), v, precision, mode)
}

fun BigDecimal.multiply(
    v: String?,
    precision: Int? = null,
    mode: RoundingMode = RoundingMode.FLOOR
): BigDecimal {
    return DigitUtils.multiply(this.toString(), v, precision, mode)
}

fun BigDecimal.divide(
    v: String?,
    precision: Int? = null,
    mode: RoundingMode = RoundingMode.HALF_EVEN
): BigDecimal {
    return DigitUtils.divide(this.toString(), v, precision, mode)
}