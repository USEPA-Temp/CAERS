import { EfVariable } from 'src/app/shared/models/ef-variable';

export class EmissionFormulaVariable {
  id: number;
  value: number;
  emissionFactorVariableCode: EfVariable;

  constructor(value: number, variable: EfVariable) {
    this.value = value;
    this.emissionFactorVariableCode = variable;
  }
}

