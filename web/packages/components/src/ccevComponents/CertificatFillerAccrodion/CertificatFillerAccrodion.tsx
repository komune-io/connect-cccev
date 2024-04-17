import { ExpandMoreRounded } from '@mui/icons-material'
import { Accordion, AccordionDetails, AccordionSummary, Box, Typography } from '@mui/material'
import { useMemo, useState } from 'react'
import { CcevForm, CcevFormField } from '../CcevForm'
import { FormState } from '@komune-io/g2-forms'

export type Category = {
    key: string
    name: string
    fields: CcevFormField[]
}

interface CertificatFillerAccrodionProps {
    categories: Category[]
    globalFormState: FormState
}

export const CertificatFillerAccrodion = (props: CertificatFillerAccrodionProps) => {
    const { categories, globalFormState } = props

    const [currentPan, setcurrentPan] = useState<string | undefined>(categories[0]?.key ?? undefined)

    const accordions = useMemo(() => categories.map((category) => (
        <Accordion key={category.key} expanded={currentPan === category.key} onChange={() => setcurrentPan(category.key)}>
            <AccordionSummary
                expandIcon={<ExpandMoreRounded />}
                aria-controls="panel1bh-content"
                id="panel1bh-header"
            >
                <Typography sx={{marginLeft: "20px", lineHeight: 1}} variant="h6" textAlign="center" >{category.name}</Typography>
            </AccordionSummary>
            <AccordionDetails>
                <CcevForm fields={category.fields} formState={globalFormState} />
            </AccordionDetails>
        </Accordion>
    )), [categories, currentPan, globalFormState])

    return (
        <Box sx={{padding: "10px"}}>
            {accordions}
        </Box>
    )
}
