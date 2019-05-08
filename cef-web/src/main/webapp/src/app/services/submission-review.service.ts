import {Injectable, PipeTransform} from '@angular/core';

import {BehaviorSubject, Observable, of, Subject} from 'rxjs';

import { SubmissionReview } from '../model/submission-review';
import { SUBMISSIONREVIEWS } from '../fake-submission-reviews';

import { DecimalPipe } from '@angular/common';
import { debounceTime, delay, switchMap, tap } from 'rxjs/operators';
import { SortDirection } from '../sortable.directive';


interface SearchResult {
  submissionReviews: SubmissionReview[];
  total: number;
}

interface State {
  page: number;
  pageSize: number;
  searchTerm: string;
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

function matches(submissionReview: SubmissionReview, term: string, pipe: PipeTransform) {
  return submissionReview.facilityName.toLowerCase().includes(term)
    || submissionReview.county.toLowerCase().includes(term)
    || pipe.transform(submissionReview.eisId).includes(term);
}

@Injectable({providedIn: 'root'})
export class SubmissionReviewService {
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _search$ = new Subject<void>();
  private _submissionReviews$ = new BehaviorSubject<SubmissionReview[]>([]);
  private _total$ = new BehaviorSubject<number>(0);

  private _state: State = {
    page: 1,
    pageSize: 4,
    searchTerm: '',
    sortColumn: '',
    sortDirection: ''
  };

  constructor(private pipe: DecimalPipe) {
    this._search$.pipe(
      tap(() => this._loading$.next(true)),
      debounceTime(200),
      switchMap(() => this._search()),
      delay(200),
      tap(() => this._loading$.next(false))
    ).subscribe(result => {
      this._submissionReviews$.next(result.submissionReviews);
      this._total$.next(result.total);
    });

    this._search$.next();
  }

  get submissionReviews$() { return this._submissionReviews$.asObservable(); }
  get total$() { return this._total$.asObservable(); }
  get loading$() { return this._loading$.asObservable(); }
  get page() { return this._state.page; }
  get pageSize() { return this._state.pageSize; }
  get searchTerm() { return this._state.searchTerm; }

  set page(page: number) { this._set({page}); }
  set pageSize(pageSize: number) { this._set({pageSize}); }
  set searchTerm(searchTerm: string) { this._set({searchTerm}); }
  set sortColumn(sortColumn: string) { this._set({sortColumn}); }
  set sortDirection(sortDirection: SortDirection) { this._set({sortDirection}); }

  private _set(patch: Partial<State>) {
    Object.assign(this._state, patch);
    this._search$.next();
  }

  private _search(): Observable<SearchResult> {
    const {sortColumn, sortDirection, pageSize, page, searchTerm} = this._state;

    // 1. sort
    let submissionReviews = sort(SUBMISSIONREVIEWS, sortColumn, sortDirection);

    // 2. filter
    submissionReviews = submissionReviews.filter(review => matches(review, searchTerm, this.pipe));
    const total = submissionReviews.length;

    // 3. paginate
    submissionReviews = submissionReviews.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
    return of({submissionReviews, total});
  }
}
