import { Process } from './process';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class EmissionUnit {
  id: number;
  facilityId: number;
  unitIdentifier: string;
  description: string;
  unitTypeCodeDescription: string;
  operatingStatusCodeDescription: string;
  programSystemCode: string;
  typeCodeDescription: string;
  statusYear: number;
  unitOfMeasureCode: BaseCodeLookup;
  unitOfMeasureDescription: number;

  processes: Process[];
}
