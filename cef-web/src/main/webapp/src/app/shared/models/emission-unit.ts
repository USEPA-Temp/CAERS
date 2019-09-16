import { Process } from 'src/app/shared/models/process';
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
  designCapacity: number;
  unitOfMeasureDescription: number;
  comments: string;
  processes: Process[];
}
