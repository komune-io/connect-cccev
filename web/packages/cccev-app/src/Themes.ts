import { ThemeOptions } from "@mui/material";
import { Theme as AruiTheme } from "@komune-io/g2";


type DeepPartial<T> = {
  [P in keyof T]?: DeepPartial<T[P]>;
};

export const theme: DeepPartial<AruiTheme> = {
  name: "cccev-app",
  colors: {
    primary: "#B1DAFB",
    secondary: "#052947",
  },
  shadows: [
    "0 0px 0px 0 rgba(0,0,0,0)",
    "0px 4px 8px rgba(0, 0, 0, 0.2)",
    "0px 5px 12px rgba(0, 0, 0, 0.21)",
    "0px 6px 16px rgba(0, 0, 0, 0.22)",
    "0px 7px 20px rgba(0, 0, 0, 0.23)",
    "0px 8px 24px rgba(0, 0, 0, 0.24)",
    "0px 9px 28px rgba(0, 0, 0, 0.25)",
    "0px 10px 32px rgba(0, 0, 0, 0.26)",
    "0px 11px 36px rgba(0, 0, 0, 0.27)",
    "0px 12px 40px rgba(0, 0, 0, 0.28)",
    "0px 13px 44px rgba(0, 0, 0, 0.29)",
    "0px 14px 48px rgba(0, 0, 0, 0.3)",
    "0px 15px 52px rgba(0, 0, 0, 0.31)",
  ],
};

export const muiTheme: ThemeOptions = {
  components: {
    MuiTooltip: {
      styleOverrides: {
        tooltip: {
          fontFamily: "roboto",
          fontWeight: 500
        }
      }
    }
  },
  typography: {
    subtitle2: {
      fontFamily: "roboto",
      fontWeight: 700
    },
    subtitle1: {
      fontFamily: "roboto",
      fontWeight: 700
    },
    body1: {
      fontFamily: "roboto",
    },
    body2: {
      fontFamily: "roboto",
    },
    caption: {
      fontFamily: "roboto",
    },
    button: {
      fontFamily: "roboto",
      fontWeight: 500,
    }
  }
};
