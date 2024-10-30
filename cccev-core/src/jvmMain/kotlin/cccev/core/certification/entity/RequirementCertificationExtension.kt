package cccev.core.certification.entity

fun RequirementCertification.isFulfilled() = isEnabled
        && isValidated
        && hasAllValues
        && areEvidencesProvided
        && subCertifications.all { !it.isEnabled || !it.requirement.required || it.isFulfilled }
