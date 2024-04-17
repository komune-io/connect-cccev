package cccev.projection.api.converter

import cccev.commons.utils.parseJsonTo
import cccev.commons.utils.toJson
import io.komune.fs.s2.file.domain.model.FilePath
import org.neo4j.driver.Value
import org.neo4j.driver.internal.value.StringValue
import org.springframework.core.convert.TypeDescriptor
import org.springframework.core.convert.converter.GenericConverter

class FilePathConverter: GenericConverter {
    override fun getConvertibleTypes(): MutableSet<GenericConverter.ConvertiblePair> {
        return mutableSetOf(
            GenericConverter.ConvertiblePair(FilePath::class.java, Value::class.java),
            GenericConverter.ConvertiblePair(Value::class.java, FilePath::class.java)
        )
    }

    override fun convert(source: Any?, sourceType: TypeDescriptor, targetType: TypeDescriptor): Any? {
        return when (source) {
            is FilePath -> StringValue(source.toJson())
            is Value -> source.asString().parseJsonTo(FilePath::class.java)
            else -> null
        }
    }
}
