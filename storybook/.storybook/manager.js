import { addons } from '@storybook/addons';
import {create} from "@storybook/theming";
import logo from "../assets/logo.png";

addons.setConfig({
    theme: create({
        base: 'light',
        brandTitle: 'Connect CCCEV',
        brandUrl: "https://komune-io.github.io/connect-cccev/",
        brandImage: logo,
    }),
    showToolbar: false
});
