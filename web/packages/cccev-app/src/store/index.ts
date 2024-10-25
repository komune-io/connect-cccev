import { initRedux } from "@komune-io/g2";
import { filtersReducer } from "./filters/filters.reducer";
import thunk from "redux-thunk"
import { evidenceReducer } from "./evidence/evidence.reducer";

const reducers = {
    filters: filtersReducer,
    evidence: evidenceReducer
}

export const {store, reducer} = initRedux<typeof reducers>(reducers, [thunk])

export type State = ReturnType<typeof reducer>;

