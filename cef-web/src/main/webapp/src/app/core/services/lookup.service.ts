import { Injectable } from '@angular/core';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Pollutant } from 'src/app/shared/models/pollutant';
import { CalculationMethodCode } from 'src/app/shared/models/calculation-method-code';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';

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

}
