package xyz.mufanc.aproc.ksp

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import xyz.mufanc.aproc.annotation.AProcEntry
import java.util.Properties

class AProcProcessor(
    private val env: SymbolProcessorEnvironment
) : SymbolProcessor {

    private val mEntries = mutableSetOf<String>()
    private var isGenerated = false

    private fun makeProperties(block: Properties.() -> Unit): Properties {
        return Properties().apply(block)
    }

    override fun process(resolver: Resolver): List<KSAnnotated> = env.run {
        val entries = resolver.getSymbolsWithAnnotation(AProcEntry::class.qualifiedName!!)
            .filterIsInstance<KSClassDeclaration>().toList()
        mEntries.addAll(entries.map { it.qualifiedName!!.asString() })

        if (mEntries.size != 1) {
            logger.error("AProc must be annotated on a single class; found ${mEntries.size}: $mEntries")
            return@run emptyList()
        }

        if (!isGenerated) {
            isGenerated = true

            val entry = mEntries.first()
            val dependencies = Dependencies(true, *entries.mapNotNull { it.containingFile }.toTypedArray())
            val property = codeGenerator.createNewFileByPath(dependencies, "META-INF/aproc", "properties")

            val props = makeProperties {
                setProperty("entry", entry)
            }

            property.bufferedWriter().use { props.store(it, null) }
        }

        return emptyList()
    }
}
