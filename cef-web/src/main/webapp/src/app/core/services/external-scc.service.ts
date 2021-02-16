import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { SccCode } from 'src/app/shared/models/scc-code';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ExternalSccService {

  private baseUrl = environment.sccSearchApiUrl;  // URL to web api

  constructor(private http: HttpClient) { }

  basicSearch(searchTerm: string): Observable<SccCode[]> {

    const searchTermParams = new HttpParams()
        .set('facetName[]', 'Code||SCC Level One||SCC Level Two||SCC Level Three||SCC Level Four||Short Name||Sector')
        .set('facetValue[]', searchTerm)
        .set('facetQualifier[]', 'contains')
        .set('facetMatchType[]', 'all_words')

    const pointOnlyParams = new HttpParams()
        .set('facetName[]', 'Data Category')
        .set('facetValue[]', 'Point')
        .set('facetQualifier[]', 'exact')
        .set('facetMatchType[]', 'whole_phrase');

    return this.http.get<SccCode[]>(this.baseUrl + '?' + searchTermParams.toString() + '&' + pointOnlyParams.toString());
  }
}
