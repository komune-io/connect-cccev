import { ErrorOutline, Upload } from '@mui/icons-material'
import { Paper, Stack, styled, Typography } from '@mui/material'
import { Tooltip } from '@komune-io/g2-notifications'
import { useTheme } from '@komune-io/g2-themes'
import { useCallback } from 'react'
import { DeleteIcon, PdfIcon, ViewFileIcon } from '../../icons'
import { useTranslation } from "react-i18next"

const EvidencePaper = styled(Paper)(({ theme }) => ({
    padding: "5px 10px",
    width: "100%",
    borderRadius: "5px",
    boxSizing: "border-box",
    cursor: "pointer",
    marginLeft: "10px",
    zIndex: 1,
    "& .evidence-iconActions": {
        color: "#353945",
        fill: "#353945",
        stroke: "#353945"
    },
    "& .evidence-iconActions:hover": {
        color: theme.palette.primary.main,
        fill: theme.palette.primary.main,
        stroke: theme.palette.primary.main
    },
    "&.evidence-needed": {
        background: "#E6E9EF"
    },
    "& .evidence-pdficon": {
        width: "21px",
        flexShrink: 0
    },
    "&.evidence-needed .evidence-label": {
        color: "#676879"
    },
    "& .evidence-label": {
        flexGrow: 1
    },
    "& .evidence-viewFileIcon": {
        width: "24px",
        height: "24px"
    },
    "& .evidence-deleteIcon": {
        width: "22px",
        height: "22px"
    }
}))

interface EvidenceProps {
    variant?: "needed" | "added"
    label: string
    onUpload?: () => void
    onView?: () => void
    onDelete?: () => void
}

export const Evidence = (props: EvidenceProps) => {
    const { variant, label, onDelete, onUpload, onView } = props
    const theme = useTheme()
    const {t} = useTranslation()
    const onDeleteMemoized = useCallback(
        (event: React.MouseEvent<SVGSVGElement, MouseEvent>) => {
            event.stopPropagation()
            onDelete && onDelete()
        },
        [onDelete],
    )
    if (variant === "needed") {
        return (
            <Tooltip helperText={t("certificateFillerPage.evidenceNotAddedHelper")}>
                <EvidencePaper className="evidence-needed" onClick={onUpload}>
                    <Stack
                        direction="row"
                        alignItems="center"
                        spacing={1}
                    >
                        <PdfIcon className="evidence-pdficon" />
                        <Typography className="evidence-label" variant="body2">
                            {label}
                        </Typography>
                        <ErrorOutline sx={{ color: theme.colors.error }} className="evidence-errorIcon" />
                        <Upload sx={{color: "#353945"}} />
                    </Stack>
                </EvidencePaper>
            </Tooltip>
        )
    }
    return (
        <EvidencePaper onClick={onView}>
            <Stack
                direction="row"
                alignItems="center"
                spacing={1}
            >
                <PdfIcon className="evidence-pdficon" />
                <Typography className="evidence-label" variant="body2">
                    {label}
                </Typography>
                <Tooltip helperText={t("certificateFillerPage.viewEvidenceHelper")}>
                    <ViewFileIcon style={{stroke: "none"}} className="evidence-iconActions evidence-viewFileIcon" />
                </Tooltip>
                <Tooltip helperText={t("certificateFillerPage.deleteEvidenceHelper")}>
                    <DeleteIcon style={{fill: "none"}} onClick={onDeleteMemoized} className="evidence-iconActions evidence-deleteIcon" />
                </Tooltip>
            </Stack>
        </EvidencePaper>
    )
}
