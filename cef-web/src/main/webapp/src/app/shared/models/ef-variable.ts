import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { VariableValidationType } from 'src/app/shared/enums/variable-validation-type';

export class EfVariable implements BaseCodeLookup {
  code: string;
  description: string;
  shortName: string;
  validationType: VariableValidationType;
}
