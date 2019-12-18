import { ValidationDetail } from 'src/app/shared/models/validation-detail';

export interface ValidationResult {

    federalErrors: ValidationItem[];
    federalWarnings: ValidationItem[];
    stateErrors: ValidationItem[];
    stateWarnings: ValidationItem[];
}

export interface ValidationItem {

    errorMsg: string;
    field: string;
    invalidValue: ValidationDetail;
}
