/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
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
