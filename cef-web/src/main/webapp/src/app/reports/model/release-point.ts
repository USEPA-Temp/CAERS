import { BaseCodeLookup } from '../../model/base-code-lookup';

export class ReleasePoint {
  id: number;
  description: string;
  operatingStatusCode: BaseCodeLookup;
  programSystemCode: BaseCodeLookup;
  releasePointIdentifier: string;
  typeCode: string;
}
