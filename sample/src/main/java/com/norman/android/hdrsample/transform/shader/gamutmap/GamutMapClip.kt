package com.norman.android.hdrsample.transform.shader.gamutmap

import com.norman.android.hdrsample.transform.shader.ColorConversion.methodBt2020ToBt709

/**
 * 直接裁剪BT2020转BT709色域映射后超出范围的值
 */
class GamutMapClip: GamutMap() {

    override val code: String
        get() = """
        vec3 $methodGamutMap(vec3 color) {
            color = $methodBt2020ToBt709(color);
            color = clamp(color, 0.0, 1.0);
            return color;
        }
        """.trimIndent()
}