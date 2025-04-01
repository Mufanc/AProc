package xyz.mufanc.aproc.runtime

import pxb.android.axml.AxmlParser


internal class ManifestHelper {

    fun parseTargetSdk(): Int? {
        javaClass.getResourceAsStream("/AndroidManifest.xml").use { stream ->
            val parser = AxmlParser(stream!!.readBytes())

            while (true) {
                val tokenType = parser.next()

                if (tokenType == AxmlParser.END_FILE) {
                    break
                }

                if (tokenType == AxmlParser.START_TAG) {
                    for (i in 0 ..< parser.attributeCount) {
                        val attr = parser.getAttrName(i)
                        if (parser.name == "uses-sdk") {
                            if (attr == "targetSdkVersion") {
                                return parser.getAttrValue(i).toString().toInt()
                            }
                        }
                    }
                }
            }
        }

        return null
    }
}
