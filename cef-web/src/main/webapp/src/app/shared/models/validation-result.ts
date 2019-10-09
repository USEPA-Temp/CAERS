
export interface ValidationResult {

    federalErrors: ValidationItem[];
    federalWarnings: ValidationItem[];
    stateErrors: ValidationItem[];
    stateWarnings: ValidationItem[];
}

export interface ValidationItem {

    errorMsg: string;
    field: string;
}
