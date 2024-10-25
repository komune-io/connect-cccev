import React from 'react'
import { ReactComponent } from './EvidenceBounded.svg'
import { MergeReactElementProps } from 'utils'
import { useTheme } from '@komune-io/g2-themes'

interface EvidenceBoundedBasicProps {
  color?: string
}

type EvidenceBoundedProps = MergeReactElementProps<'svg', EvidenceBoundedBasicProps>

export const EvidenceBounded = React.forwardRef(
  (props: EvidenceBoundedProps, ref: React.Ref<SVGSVGElement>) => {
    const theme = useTheme()
    const {color = theme.colors.success} = props
    return <ReactComponent fill={color} ref={ref} {...props} />
  }
)
