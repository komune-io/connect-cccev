import { Help } from '@mui/icons-material'
import { Stack, Typography } from '@mui/material'
import { useTheme } from '@komune-io/g2-themes'
import { Tooltip } from '@komune-io/g2-notifications'
import { useTranslation } from "react-i18next"


export const Title = () => {
    const theme = useTheme()
    const {t} = useTranslation()
    return (
        <Stack
            direction="row"
            justifyContent="center"
            alignItems="center"
            spacing={1}
        >
            <Typography variant="h6">
            {t("evidenceList")}
            </Typography>
            <Tooltip helperText={t("certificateFillerPage.evidenceListInfo")}>
                <Help sx={{ color: theme.colors.info, width: 25, height: 25, zIndex: 2 }} />
            </Tooltip>
        </Stack>
    )
}
