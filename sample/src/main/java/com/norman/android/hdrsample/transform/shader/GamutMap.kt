package com.norman.android.hdrsample.transform.shader

import com.norman.android.hdrsample.opengl.GLShaderCode
import com.norman.android.hdrsample.transform.shader.ColorSpaceConversion.methodBt2020ToBt709

/**
 * 色域映射抽象类
 */
abstract class GamutMap: GLShaderCode() {

    val methodGamutMap = "GAMUT_MAP"
}