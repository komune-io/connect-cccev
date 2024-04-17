import { Divider, Stack, Typography } from '@mui/material'
import { Button } from '@komune-io/g2-components'
import { DropFileIcon } from './DropFileIcon'
import { Title } from './Title'
import { EvidenceTypeListDisplayer } from 'components'
import { useCallback, useMemo } from 'react'
import { useTranslation } from "react-i18next"
import { EvidenceTypeListDTO, requestEvidenceRemoveCommand } from 'datahub'

interface EvidenceListProps {
    changeEvidence: (evidence?: string) => void
    addEvidenceType: (evidenceTypeId?: string | undefined) => void
    evidenceTypeLists?: EvidenceTypeListDTO[][]
    fetchEvidenceTypeLists: () => void
    CanAddEvidence: boolean
}

export const EvidenceList = (props: EvidenceListProps) => {
    const { changeEvidence, addEvidenceType, evidenceTypeLists, fetchEvidenceTypeLists, CanAddEvidence } = props

    const { t } = useTranslation()

    const onAddEvidence = useCallback(
        () => {
            addEvidenceType()
        },
        [addEvidenceType],
    )

    const onUploadEvidence = useCallback(
        (evidenceTypeId: string) => {
            addEvidenceType(evidenceTypeId)
        },
        [addEvidenceType],
    )

    const onViewEvidence = useCallback(
        (evidenceTypeId: string) => {
            changeEvidence(evidenceTypeId)
        },
        [changeEvidence],
    )

    const onDeleteEvidence = useCallback(
        async (evidenceTypeId: string) => {
            await requestEvidenceRemoveCommand(evidenceTypeId)
            fetchEvidenceTypeLists()
        },
        [fetchEvidenceTypeLists],
    )

    
    const evidenceTypeListsUi = useMemo(() => evidenceTypeLists?.map((el, index) => (
        <EvidenceTypeListDisplayer key={`evidenceTypeLists-${index}`} onDelete={onDeleteEvidence} onView={onViewEvidence} onUpload={onUploadEvidence} evidenceTypeLists={el} />
    )), [evidenceTypeLists, onUploadEvidence, onViewEvidence, onDeleteEvidence])


    return (
        <>
            <Stack
                sx={{ position: "relative", padding: "0px 10px" }}
                alignItems="center"
                spacing={3}
            >
                <Title />
                <Divider sx={{ borderColor: "#8294A3", borderBottomWidth: "2px" }} style={{ marginTop: "5px" }} flexItem />
                {evidenceTypeListsUi}
                {CanAddEvidence && <Button onClick={onAddEvidence} style={{ width: "180px", zIndex: 1 }}>{t("addAnEvidence")}</Button>}
            </Stack>
            {CanAddEvidence && <Stack
                direction="row"
                spacing={3}
                justifyContent="center"
                alignItems="center"
                sx={{
                    position: "absolute",
                    bottom: "50px",
                    width: "100%"
                }}
            >
                <DropFileIcon style={{ width: "80px" }} />
                <Typography variant="body2" color="#787878">
                    {t("canDropFile")}
                </Typography>
            </Stack>}
        </>
    )
}
