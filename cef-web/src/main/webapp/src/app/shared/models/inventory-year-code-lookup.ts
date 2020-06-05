import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class InventoryYearCodeLookup implements BaseCodeLookup {
  code: string;
  description: string;
  shortName: string;
  lastInventoryYear: number;
  mapTo: string;
}
