import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { SccCode } from 'src/app/shared/models/scc-code';

@Injectable({
  providedIn: 'root'
})
export class ExternalSccService {

  private baseUrl = 'https://ofmpub.epa.gov/sccwebservices/v1/SCC';  // URL to web api

  constructor(private http: HttpClient) { }

  basicSearch(searchTerm: string): Observable<SccCode[]> {

    const criteriaParams = new HttpParams()
        .set('facetName[]', 'Code||SCC Level One||SCC Level Two||SCC Level Three||SCC Level Four||Short Name||Sector')
        .set('facetQualifier[]', 'contains')
        .set('facetMatchType[]', 'all_words')
        .set('facetValue[]', searchTerm);

    return this.http.jsonp<SccCode[]>(this.baseUrl + '?' + criteriaParams.toString(), 'callback');
  }
}
