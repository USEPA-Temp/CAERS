import { Injectable } from '@angular/core';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Pollutant } from 'src/app/shared/models/pollutant';
import { CalculationMethodCode } from 'src/app/shared/models/calculation-method-code';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';
import { FipsStateCode } from 'src/app/shared/models/fips-state-code';
import { FacilityNaicsCode } from 'src/app/shared/models/facility-naics-code';
import { AircraftEngineTypeCode } from 'src/app/shared/models/aircraft-engine-type-code';
import { PointSourceSccCode } from 'src/app/shared/models/point-source-scc-code';
import { EisLatLongToleranceLookup } from 'src/app/shared/models/eis-latlong-tolerance-lookup';
import { FacilityCategoryCode } from 'src/app/shared/models/facility-category-code';
import { FipsCounty } from 'src/app/shared/models/fips-county';

@Injectable({
  providedIn: 'root'
})
export class LookupService {

  private baseUrl = 'api/lookup';  // URL to web api

  constructor(private http: HttpClient) { }

  retrieveCalcMaterial(): Observable<BaseCodeLookup[]> {
    const url = `${this.baseUrl}/calculation/material`;
    return this.http.get<BaseCodeLookup[]>(url);
  }

  retrieveCalcMethod(): Observable<CalculationMethodCode[]> {
    const url = `${this.baseUrl}/calculation/method`;
    return this.http.get<CalculationMethodCode[]>(url);
  }

  retrieveCalcParam(): Observable<BaseCodeLookup[]> {
    const url = `${this.baseUrl}/calculation/parameter`;
    return this.http.get<BaseCodeLookup[]>(url);
  }

  retrieveOperatingStatus(): Observable<BaseCodeLookup[]> {
    const url = `${this.baseUrl}/operatingStatus`;
    return this.http.get<BaseCodeLookup[]>(url);
  }

  retrieveEmissionsOperatingType(): Observable<BaseCodeLookup[]> {
    const url = `${this.baseUrl}/emissionsOperatingType`;
    return this.http.get<BaseCodeLookup[]>(url);
  }

  retrievePollutant(): Observable<Pollutant[]> {
    const url = `${this.baseUrl}/pollutant`;
    return this.http.get<Pollutant[]>(url);
  }

  retrieveReportingPeriod(): Observable<BaseCodeLookup[]> {
    const url = `${this.baseUrl}/reportingPeriod`;
    return this.http.get<BaseCodeLookup[]>(url);
  }

  retrieveUom(): Observable<UnitMeasureCode[]> {
    const url = `${this.baseUrl}/uom`;
    return this.http.get<UnitMeasureCode[]>(url);
  }

  retrieveFacilityContactType(): Observable<BaseCodeLookup[]> {
    const url = `${this.baseUrl}/contactType`;
    return this.http.get<BaseCodeLookup[]>(url);
  }

  retrieveFipsCounties(): Observable<FipsCounty[]> {
    const url = `${this.baseUrl}/county`;
    return this.http.get<FipsCounty[]>(url);
  }

  retrieveFipsCountiesForState(stateCode: string): Observable<FipsCounty[]> {
    const url = `${this.baseUrl}/county/state/${stateCode}`;
    return this.http.get<FipsCounty[]>(url);
  }

  retrieveFipsStateCode(): Observable<FipsStateCode[]> {
    const url = `${this.baseUrl}/stateCode`;
    return this.http.get<FipsStateCode[]>(url);
  }

  retrieveUnitType(): Observable<BaseCodeLookup[]> {
    const url = `${this.baseUrl}/unitType`;
    return this.http.get<BaseCodeLookup[]>(url);
  }

  retrieveReleaseTypeCode(): Observable<BaseCodeLookup[]> {
    const url = `${this.baseUrl}/releaseType`;
    return this.http.get<BaseCodeLookup[]>(url);
  }

  retrieveProgramSystemTypeCode(): Observable<BaseCodeLookup[]> {
    const url = `${this.baseUrl}/programSystemType`;
    return this.http.get<BaseCodeLookup[]>(url);
  }

  retrieveControlMeasureCodes(): Observable<BaseCodeLookup[]> {
    const url = `${this.baseUrl}/controlMeasure`;
    return this.http.get<BaseCodeLookup[]>(url);
  }

  retrieveTribalCode(): Observable<BaseCodeLookup[]> {
    const url = `${this.baseUrl}/tribalCode`;
    return this.http.get<BaseCodeLookup[]>(url);
  }

  retrieveNaicsCode(): Observable<FacilityNaicsCode[]> {
    const url = `${this.baseUrl}/naicsCode`;
    return this.http.get<FacilityNaicsCode[]>(url);
  }

  retrieveAircraftEngineCodes(scc: string): Observable<AircraftEngineTypeCode[]> {
    const url = `${this.baseUrl}/aircraftEngineCode/${scc}`;
    return this.http.get<AircraftEngineTypeCode[]>(url);
  }

  retrievePointSourceSccCode(code: string): Observable<PointSourceSccCode> {
    const url = `${this.baseUrl}/pointSourceSccCode/${code}`;
    return this.http.get<PointSourceSccCode>(url);
  }

  retrieveLatLongTolerance(eisProgramId: string): Observable<EisLatLongToleranceLookup> {
    const url = `${this.baseUrl}/coordinateTolerance/${eisProgramId}`;
    return this.http.get<EisLatLongToleranceLookup>(url);
  }

  retrieveFacilityCategory(): Observable<FacilityCategoryCode[]> {
    const url = `${this.baseUrl}/facility/category`;
    return this.http.get<FacilityCategoryCode[]>(url);
  }

  retrieveFacilitySourceType(): Observable<BaseCodeLookup[]> {
    const url = `${this.baseUrl}/facility/sourceType`;
    return this.http.get<BaseCodeLookup[]>(url);
  }

}
