package work.daniel.sample

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class Generator : AbstractProcessor() {
    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        println("supportedAnnotationTypes")
        return mutableSetOf(GenName::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        println("process")
        roundEnv.getElementsAnnotatedWith(GenName::class.java).forEach {
            val className = it.simpleName.toString()
            println("Processing: $className")
            val pack = processingEnv.elementUtils.getPackageOf(it).toString()
            generateClass(className, pack)
        }
        return true
    }

    private fun generateClass(className: String, pack: String) {
        val fileName = "Generated_$className"
        val file = FileSpec.builder(pack, fileName)
            .addType(
                TypeSpec.classBuilder(fileName)
                    .addFunction(
                        FunSpec.builder("getName")
                            .addStatement("return \"World\"")
                            .build()
                    )
                    .build()
            )
            .build()
        val kaptKotlinGenerateDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlinGenerateDir, "$fileName.kt"))
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}
