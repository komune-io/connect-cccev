import React from "react";
import reportWebVitals from "reportWebVitals";
import { Languages, languages } from "i18n";
import { store } from "store";
import { ThemeContextProvider, AppProvider, ReduxProvider } from "@komune-io/g2";
import { LoadingComponent } from "components";
import { muiTheme, theme } from "Themes";
import App from "App";
import { InitApp } from "./InitApp";
import { QueryClient } from 'react-query'
import { createRoot } from 'react-dom/client'

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 86400000
    }
  }
})

//@ts-ignore
const container: HTMLElement = document.getElementById("root")

const root = createRoot(container)

root.render(
  <React.StrictMode>
    <ThemeContextProvider theme={theme} customMuiTheme={muiTheme}>
      <ReduxProvider reduxStore={store}>
          <AppProvider<Languages>
            languages={languages}
            queryClient={queryClient}
            loadingComponent={<LoadingComponent />}
          >
            <InitApp>
              <App />
            </InitApp>
          </AppProvider>
      </ReduxProvider>
    </ThemeContextProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
