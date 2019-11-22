import { Process } from 'src/app/shared/models/process';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class EmissionUnit {
  id: number;
  facilitySiteId: number;
  unitIdentifier: string;
  description: string;
  unitTypeCode: BaseCodeLookup;
  operatingStatusCode: BaseCodeLookup;
  programSystemCode: string;
  typeCodeDescription: string;
  statusYear: number;
  unitOfMeasureCode: BaseCodeLookup;
  designCapacity: number;
  unitOfMeasureDescription: number;
  comments: string;
  processes: Process[];
}
