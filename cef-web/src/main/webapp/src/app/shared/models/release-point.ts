import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class ReleasePoint {
  id: number;
  description: string;
  operatingStatusCode: BaseCodeLookup;
  programSystemCode: BaseCodeLookup;
  facilitySiteId: number;
  releasePointIdentifier: string;
  typeCode: BaseCodeLookup;
  stackHeight: number;
  stackHeightUomCode: BaseCodeLookup;
  stackDiameter: number;
  stackDiameterUomCode: BaseCodeLookup;
  exitGasVelocity: number;
  exitGasVelocityUomCode: BaseCodeLookup;
  exitGasTemperature: number;
  exitGasFlowRate: number;
  exitGasFlowUomCode: BaseCodeLookup;
  statusYear: number;
  fugitiveLine1Latitude: number;
  fugitiveLine1Longitude: number;
  fugitiveLine2Latitude: number;
  fugitiveLine2Longitude: number;
  latitude: number;
  longitude: number;
  comments: string;
}
