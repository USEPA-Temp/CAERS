import { EmissionFormulaVariableCode } from 'src/app/shared/models/emission-formula-variable-code';

export class EmissionFormulaVariable {
  id: number;
  value: number;
  variableCode: EmissionFormulaVariableCode;

  constructor(value: number, variable: EmissionFormulaVariableCode) {
    this.value = value;
    this.variableCode = variable;
  }
}

