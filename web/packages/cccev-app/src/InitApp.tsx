// import { parse } from 'qs'
// import { NoMatchPage } from '@komune-io/g2-providers'
// import { useMemo } from 'react'

interface InitAppProps {
    children: JSX.Element
}

export const InitApp = (props: InitAppProps) => {
    const {
        children
    } = props
    // const canEnter = useMemo(() =>{
    //     const urlParams = parse(window.location.search, { ignoreQueryPrefix: true }) 
    //     if (typeof urlParams.request === "string" && typeof urlParams.framework === "string") {
    //         //@ts-ignore
    //         window._env_.request = urlParams.request
    //         //@ts-ignore
    //         window._env_.framework = urlParams.framework
    //         return true
    //     }
    //     return false
    // },  [])

    // if (!canEnter) {
    //     return (
    //         <NoMatchPage noGoBack/>
    //     )
    // }
    return children
}
