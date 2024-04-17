import { useCallback, useMemo, useState } from 'react'
import { PopUp, Action } from '@komune-io/g2'
import { Select, Option } from '@komune-io/g2-forms'
import { useTheme } from '@komune-io/g2-themes'
import { Typography } from '@mui/material'
import { EvidenceTypeDTO } from 'datahub'

interface addEvidencePopUpProps {
    open: boolean
    errorMessage?: string
    onClose: () => void
    onValidate: (evidenceTypeId: string) => void
    evidenceTypeMapped?: Map<string, EvidenceTypeDTO>
}

export const AddEvidencePopUp = (props: addEvidencePopUpProps) => {
    const { open, onClose, onValidate, errorMessage, evidenceTypeMapped } = props
    const theme = useTheme()
    const [evidenceTypeId, setEvidenceTypeId] = useState<string | undefined>(undefined)
    
    const onValidateMemoized = useCallback(
        async () => evidenceTypeId && (await onValidate(evidenceTypeId)),
        [onValidate, evidenceTypeId],
    )

    const onCloseMemoized = useCallback(
        () => {
            setEvidenceTypeId(undefined)
            onClose()
        },
        [onClose],
    )


    const actions = useMemo((): Action[] => errorMessage ? [{
        key: "addEvidencePopUp-ok",
        label: "Ok",
        onClick: onCloseMemoized
    }] 
    :
    [{
        key: "addEvidencePopUp-cancel",
        label: "Cancel",
        onClick: onCloseMemoized,
        variant: "text"
    },
    {
        key: "addEvidencePopUp-confirm",
        label: "Confirm",
        onClick: onValidateMemoized,
        disabled: !evidenceTypeId
    }], [onCloseMemoized, onValidateMemoized, evidenceTypeId, errorMessage])

    const selectOptions = useMemo(() => {
        const options: Option[] = []
        if (!evidenceTypeMapped) return []
        evidenceTypeMapped.forEach((evidenceType, evidenceTypeId) => {
            if (!evidenceType.evidence) {
                options.push({
                    key: evidenceTypeId,
                    label: evidenceType.name
                }) 
            }
        })
        return options
    }, [evidenceTypeMapped])

    return (
        <PopUp
            open={open}
            onClose={onCloseMemoized}
            actions={actions}
        >
            {errorMessage ? (
                <Typography color={theme.colors.error}>
                    {errorMessage}
                </Typography>
            ) : (
                <>
                    <Typography>
                        Choose the evidence you are adding:
                    </Typography>
                    <Select
                        options={selectOptions}
                        value={evidenceTypeId}
                        //@ts-ignore
                        onChangeValue={setEvidenceTypeId}
                    />
                </>
            )
            }
        </PopUp>
    )
}
