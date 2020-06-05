import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {UserContextService} from "../services/user-context.service";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ReviewerAuthGuard implements CanActivateChild {

  constructor(private userContext: UserContextService) {}

  canActivateChild(childRoute: ActivatedRouteSnapshot,
                   state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    return this.checkRole();
  }

  private checkRole(): Observable<boolean> {

    return this.userContext.getUser().pipe(
       map(user => user.isReviewer())
    );
  }
}
