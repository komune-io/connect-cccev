import { Paper, Divider, Typography, styled, Stack } from '@mui/material'
import { ProgressIndicator } from '@komune-io/g2-notifications'
import { WomanCheckingIllustration } from './WomanCheckingIllustration'
import { useTranslation } from "react-i18next"

const TrustabilityPaper = styled(Paper)({
    display: "flex",
    padding: "10px",
    borderRadius: "2px",
    position: "absolute",
    width: "500px",
    height: "100px",
    left: "10%",
    top: "110px"
})

export const TrustabilityCard = () => {
    const {t} = useTranslation()
    return (
        <TrustabilityPaper>
            <Stack
                sx={{
                    width: "200px",
                    marginRight: "15px",
                }}
            >
                <ProgressIndicator value={30} label={t("percentCompleted", {percent: 30})} style={{ display: "block" }} />
                <Typography sx={{marginTop: "10px"}} variant="subtitle1">
                    {t("trustability")}
                </Typography>
            </Stack>
            <Divider sx={{borderColor: "#8294A3"}} orientation="vertical" flexItem />
            <Stack
                sx={{
                    marginLeft: "20px",
                }}
                justifyContent="space-between"
            >
                <Typography sx={{fontSize: "15px"}} >
                    {t("certificateFillerPage.improveTrustability")}
                </Typography>
                <Stack
                    sx={{
                        paddingLeft: "10px"
                    }}
                    justifyContent="space-between"
                    direction="row"
                >
                    <Typography
                        variant="body2"
                    >
                        {t("certificateFillerPage.fillTheFields")}
                    </Typography>
                    <WomanCheckingIllustration style={{ width: "120px", margin: "0px 10px" }} />
                </Stack>
            </Stack>
        </TrustabilityPaper>
    )
}
