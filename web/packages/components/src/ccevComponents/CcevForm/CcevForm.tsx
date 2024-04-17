import { NetworkCell } from '@mui/icons-material'
import { Divider, Stack, styled, Typography } from '@mui/material'
import { Form, FormField, FormProps } from '@komune-io/g2-forms'
import { Tooltip } from '@komune-io/g2-notifications'
import { useMemo } from 'react'
import { EvidenceBounded, EvidenceNotBounded } from '../../icons'
import { useTranslation } from "react-i18next"

const FormStyled = styled(Form)({
    "& .EvidenceIcon": {
        width: 27,
        height: 27
    },
    "& .AruiForm-field": {
        margin: 0
    },
    "& .MuiFormControl-root": {
        width: "300px"
    },
    "& .MuiInputLabel-root": {
        whiteSpace: "normal",
        maxWidth: "600px"
    }
})

export type CcevFormField = Omit<FormField, "customDisplay"> & {
    hasEvidence: boolean
    fieldUnit?: string
}

interface CcevFormProps extends Omit<FormProps, "fields"> {
    fields: CcevFormField[]
}

export const CcevForm = (props: CcevFormProps) => {
    const { fields, ...other } = props
    const {t} = useTranslation()

    const fieldsMapping = useMemo((): FormField[] => fields.map((field): FormField => ({
        ...field,
        textFieldProps: {
            ...field.textFieldProps,
            inputIcon: field.fieldUnit,
            iconPosition: "end"
        },
        customDisplay: (input) => (
            <Stack key={`${field.key}-stackContainer`} direction="row" alignItems="center" spacing={0} justifyContent="space-between">
                {input}
                <Stack sx={{marginLeft: "5px"}} direction="row" alignItems="center" spacing={2}>
                    {field.hasEvidence ?
                        <Tooltip helperText={t("certificateFillerPage.evidenceIsBounded")}>
                            <EvidenceBounded className="EvidenceIcon" />
                        </Tooltip>
                        :
                        <Tooltip helperText={t("certificateFillerPage.noEvidenceIsBounded")}>
                            <EvidenceNotBounded className="EvidenceIcon" />
                        </Tooltip>
                    }
                    <Typography variant="body2">
                        {t("trustability")}:
                    </Typography>
                    <NetworkCell />
                </Stack>
            </Stack>
        )
    })), [fields, t])

    return (
        <FormStyled fields={fieldsMapping} fieldsStackProps={{ divider: <Divider flexItem />, spacing: 3 }} {...other} />
    )
}
