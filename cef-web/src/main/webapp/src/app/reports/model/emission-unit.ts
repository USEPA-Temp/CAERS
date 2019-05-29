import { Process } from './process';

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
  unitOfMeasureCode: number;
  unitOfMeasureDescription: number;

  processes: Process[];
}
