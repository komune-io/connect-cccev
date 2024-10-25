import React from 'react'
import { ReactComponent } from './EvidenceNotBounded.svg'
import { MergeReactElementProps } from 'utils'
import { useTheme } from '@komune-io/g2-themes'

interface EvidenceNotBoundedBasicProps {
  color?: string
}

type EvidenceNotBoundedProps = MergeReactElementProps<'svg', EvidenceNotBoundedBasicProps>

export const EvidenceNotBounded = React.forwardRef(
  (props: EvidenceNotBoundedProps, ref: React.Ref<SVGSVGElement>) => {
    const theme = useTheme()
    const {color = theme.colors.error} = props
    return <ReactComponent fill={color} ref={ref} {...props} />
  }
)
