import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmissionFactor } from 'src/app/shared/models/emission-factor';

@Injectable({
  providedIn: 'root'
})
export class EmissionFactorService {

  private baseUrl = 'api/emissionFactor';  // URL to web api

  constructor(private http: HttpClient) { }

  search(criteria: EmissionFactor): Observable<EmissionFactor[]> {
    // convert fields used for searching into strings
    const criteriaParams = {
      sccCode: criteria.sccCode.toString(),
      pollutantCode: criteria.pollutantCode,
      controlIndicator: '' + criteria.controlIndicator
    };

    return this.http.get<EmissionFactor[]>(this.baseUrl, {params: criteriaParams});
  }
}
