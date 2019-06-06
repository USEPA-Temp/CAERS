import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class ReleasePoint {
  id: number;
  description: string;
  operatingStatusCode: BaseCodeLookup;
  programSystemCode: BaseCodeLookup;
  releasePointIdentifier: string;
  typeCode: string;
  stackHeight: number;
  stackHeightUomCode: string;
  stackDiameter: number;
  stackDiameterUomCode: string;
  exitGasVelocity: number;
  exitGasVelocityUomCode: string;
  exitGasTemperature: number;
  exitGasFlowRate: number;
  exitGasFlowUomCode: string;
  statusYear: number;
  fugitiveLine1Latitude: number;
  fugitiveLine1Longitude: number;
  fugitiveLine2Latitude: number;
  fugitiveLine2Longitude: number;
  latitude: number;
  longitude: number;
}
