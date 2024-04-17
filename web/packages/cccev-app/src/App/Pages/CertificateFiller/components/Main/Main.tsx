import { Box } from "@mui/material"
import { PageFilters } from "./PageFilters"
import { CertificatFillerAccrodion, Category, CcevFormField } from "components"
import { useCallback, useMemo } from "react"
import { LanguageSelector } from "./LanguageSelector"
import { FiltersState } from "store/filters/filters.reducer"
import { objToArray } from "utils"
import { FormPartialField, useFormWithPartialFields } from "@komune-io/g2-forms"
import { Button } from "@komune-io/g2-components"
import { useTranslation } from "react-i18next"
import { useAsyncResponse } from "utils"
import { EvidenceTypeDTO, getInformationConcepts, InformationConceptDTO, requestSupportedValueAddCommand, SupportedValueDTO, requestSendToBubbleCommand } from "datahub"
import { MainLoading } from "./MainLoading"
import leven from 'leven'

interface MainProps {
    filters: FiltersState
    changeFilters: (filters: FiltersState) => void
    evidenceTypeMapped?: Map<string, EvidenceTypeDTO>
}

export const Main = (props: MainProps) => {
    const { filters, changeFilters, evidenceTypeMapped } = props

    const { t } = useTranslation()

    const informationConcepts = useAsyncResponse(getInformationConcepts)

    const partialFields = useMemo(() => informationConcepts.result ?
        informationConcepts.result.informationConcepts.map((info): FormPartialField => {
            return {
                name: info.identifier,
                defaultValue: getDefaultValue(info.unit.type, info.supportedValue.value) ?? (info.unit.type === "boolean" ? false : undefined)
            }
        })
        :
        [], [informationConcepts.result])

    const onSubmit = useCallback(
        async (values: any) => {
            if (informationConcepts.result) {
                const supportedValues = informationConcepts.result.informationConcepts.map((info): SupportedValueDTO => ({
                    identifier: info.supportedValue.identifier,
                    providesValueFor: info.identifier,
                    value: info.unit.type === "date" ? values[info.identifier].getTime() : info.unit.type === "string" ? values[info.identifier] : values[info.identifier].toString(),
                    query: undefined
                }))
                await requestSupportedValueAddCommand(supportedValues)
                await requestSendToBubbleCommand()
                //@ts-ignore
                window.open(`https://impactmate.earth/version-test/form_request/?request=${window._env_.request}`, "_blank", 'noopener')
            }
        },
        [informationConcepts.result],
    )

    const globalFormState = useFormWithPartialFields({
        fields: partialFields,
        onSubmit: onSubmit,
        formikConfig: {
            enableReinitialize: true
        }
    })

    const categories = useMemo(() => informationConcepts.result && evidenceTypeMapped ? informationConceptsToCategories(informationConcepts.result.informationConcepts, evidenceTypeMapped, filters) : [], [informationConcepts.result, evidenceTypeMapped, filters])

    if (informationConcepts.status !== "SUCCESS" || informationConcepts.result === undefined) return (
        <MainLoading />
    )
    return (
        <Box sx={{ padding: "10px 20px", paddingTop: "70px", maxWidth: "1500px", margin: "auto" }}>
            <PageFilters evidenceTypeMapped={evidenceTypeMapped} filters={filters} changeFilters={changeFilters} />
            <CertificatFillerAccrodion globalFormState={globalFormState} categories={categories} />
            <LanguageSelector />
            <Box sx={{ display: "flex", justifyContent: "center", paddingBottom: "30px" }}>
                <Button onClick={globalFormState.submitForm} style={{ width: "150px", fontSize: "17px" }}>{t("validate")}</Button>
            </Box>
        </Box>
    )
}

const informationConceptsToCategories = (informationConcepts: InformationConceptDTO[], evidenceTypeMapped: Map<string, EvidenceTypeDTO>, filters: FiltersState): Category[] => {
    const filteredInfoConcepts = filterInfoConcepts(informationConcepts, filters)
    const objCategories: { [key: string]: Category } = {}
    //@ts-ignore
    filteredInfoConcepts.forEach((el: (InformationConceptDTO & { category: { id: string, name: string } })) => {
        el.category = { id: "value-category", name: "Valeur à renseigner" }
        const newField: CcevFormField = {
            key: el.identifier,
            name: el.identifier,
            label: el.name,
            type: enumTypeToEnumFields(el.unit.type),
            textFieldProps: {
                textFieldType: el.unit.type === "number" ? "number" : "text",
            },
            hasEvidence: hasEvidence(el.evidenceTypes, evidenceTypeMapped),
            fieldUnit: el.unit.notation
        }
        const fields = objCategories[el.category.id]?.fields ? [...objCategories[el.category.id].fields, newField] : [newField]
        objCategories[el.category.id] = {
            key: el.category.id,
            name: el.category.name,
            fields: fields
        }
    })
    return objToArray(objCategories)
}

const filterInfoConcepts = (informationConcepts: InformationConceptDTO[], filters: FiltersState) => {
    return informationConcepts.filter((info) => {
        if (filters.evidence) {
            if (!info.evidenceTypeChoices.evidenceTypeLists.some((evidenceTypeIds: any) => !!evidenceTypeIds.find((evidenceTypeId: any) => filters.evidence === evidenceTypeId))) {
                return false
            }
        }
        if (filters.search) {
            const search = filters.search.trim()
            if (search !== '') {
                const startsWith = new RegExp(`^$|${search}`, 'i')
                if (startsWith.test(info.name)) {
                    return true
                }
                if (leven(search, info.name) <= 5) {
                    return true
                }
                const searchSplited = search.split(' ')
                if (searchSplited.length > 2) {
                    const containsMultiple = new RegExp(`^$|${searchSplited.join('+|')}`, 'gi')
                    const matched = info.name.match(containsMultiple)
                    if (matched && matched.length > Math.floor(searchSplited.length/2)) {
                        return true
                    }
                }
                return false
            }
        }
        return true
    })
}

const enumTypeToEnumFields = (type: "string" | "date" | "number" | "boolean"): "textfield" | "select" | "datepicker" | "radioChoices" | "checkbox" => {
    switch (type) {
        case "string":
            return "textfield"
        case "date":
            return "datepicker"
        case "number":
            return "textfield"
        case "boolean":
            return "checkbox"
        default:
            return "textfield"
    }
}

const hasEvidence = (evidenceTypeIds: string[][], evidenceTypeMapped: Map<string, EvidenceTypeDTO>) => {
    let hasEvidence = false
    evidenceTypeIds.forEach((array) => {
        if (array.length > 0) {
            const every = array.every((evidenceTypeId) => !!evidenceTypeMapped.get(evidenceTypeId)?.evidence)
            if (every) hasEvidence = true
        }
    })
    return hasEvidence
}

const getDefaultValue = (type: "string" | "date" | "number" | "boolean", value?: any): Date | string | number | boolean | undefined => {
    if (value === undefined) return undefined
    switch (type) {
        case "date":
            return new Date(Number(value))
        case "number":
            return Number(value)
        case "boolean":
            return value === "true"
        case "string":
            return value
        default:
            return undefined
    }
}
