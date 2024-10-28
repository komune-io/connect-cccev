package cccev.client

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.request.forms.FormPart
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders


//fun CertificationClient.certificationAddEvidence(): CertificationAddEvidenceFunction = F2Function { msgs ->
//    msgs.map { (cmd, file) ->
//        val httpF2Client = (client as HttpF2Client)
//        httpF2Client.httpClient.submitFormWithBinaryData(
//            url = "${httpF2Client.urlBase}/certificationAddEvidence",
//            formData = FormDataBodyBuilder().apply {
//                param("command", cmd)
//                file("file", file, cmd.name)
//            }.toFormData()
//        ).body()
//    }
//}

class FormDataBodyBuilder {
    private val formParts = mutableListOf<FormPart<*>>()

    fun toFormData() = formData { formParts.forEach { append(it) } }

    fun param(key: String, value: String, contentType: String? = null) {
        val headers = contentType
            ?.let { Headers.build { append(HttpHeaders.ContentType, contentType) } }
            ?: Headers.Empty

        FormPart(
            key = key,
            value = value,
            headers = headers
        ).let(formParts::add)
    }

    fun <T> param(key: String, value: T) {
        param(key, value.toJson(), "application/json")
    }

    fun file(key: String, file: ByteArray, filename: String) {
        FormPart(
            key = key,
            value = file,
            headers = Headers.build { append(HttpHeaders.ContentDisposition, "filename=$filename") }
        ).let(formParts::add)
    }

    private fun <T> T.toJson(): String = jacksonObjectMapper()
        .writeValueAsString(this)
}
