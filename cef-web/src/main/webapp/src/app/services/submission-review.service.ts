import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { SubmissionReview } from '../model/submission-review';
import { SUBMISSIONREVIEWS } from '../fake-submission-reviews';
import { DecimalPipe } from '@angular/common';
import { switchMap } from 'rxjs/operators';
import { SortDirection } from '../sortable.directive';
import { HttpClient } from '@angular/common/http';


interface SearchResult {
  submissionReviews: SubmissionReview[];
  total: number;
}

interface State {
  page: number;
  pageSize: number;
  sortColumn: string;
  sortDirection: SortDirection;
}

function compare(v1, v2) {
  return v1 < v2 ? -1 : v1 > v2 ? 1 : 0;
}

function sort(submissionReviews: SubmissionReview[], column: string, direction: string): SubmissionReview[] {
  if (direction === '') {
    return submissionReviews;
  } else {
    return [...submissionReviews].sort((a, b) => {
      const res = compare(a[column], b[column]);
      return direction === 'asc' ? res : -res;
    });
  }
}

@Injectable({providedIn: 'root'})
export class SubmissionReviewService {

  private baseUrl = 'api/facility';

  private _search$ = new Subject<void>();
  private _submissionReviews$ = new BehaviorSubject<SubmissionReview[]>([]);
  private _total$ = new BehaviorSubject<number>(0);

  private _state: State = {
    page: 1,
    pageSize: 4,
    sortColumn: '',
    sortDirection: ''
  };

  constructor(private pipe: DecimalPipe, private http: HttpClient) {
    this._search$.pipe(switchMap(() => this._search())
    ).subscribe(result => {
      this._submissionReviews$.next(result.submissionReviews);
      this._total$.next(result.total);
    });

    this._search$.next();
  }

  get submissionReviews$() { return this._submissionReviews$.asObservable(); }
  get total$() { return this._total$.asObservable(); }
  get page() { return this._state.page; }
  get pageSize() { return this._state.pageSize; }

  set page(page: number) { this._set({page}); }
  set pageSize(pageSize: number) { this._set({pageSize}); }
  set sortColumn(sortColumn: string) { this._set({sortColumn}); }
  set sortDirection(sortDirection: SortDirection) { this._set({sortDirection}); }

  private _set(patch: Partial<State>) {
    Object.assign(this._state, patch);
    this._search$.next();
  }

  private _search(): Observable<SearchResult> {
    const {sortColumn, sortDirection, pageSize, page} = this._state;

    let facilities: SubmissionReview[] = [];
    this.getFacilities('GA').subscribe(function(result) {
      facilities = result;
    });

    //sort
    //let submissionReviews = sort(facilities, sortColumn, sortDirection);
    let submissionReviews = sort(SUBMISSIONREVIEWS, sortColumn, sortDirection);
    const total = submissionReviews.length;

    //paging
    submissionReviews = submissionReviews.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
    return of({submissionReviews, total});
  }
  
  getFacilities (stateCode: string): Observable<SubmissionReview[]> {
    const url = `${this.baseUrl}/state/${stateCode}`;
    return this.http.get<SubmissionReview[]>(url);
  };
}
