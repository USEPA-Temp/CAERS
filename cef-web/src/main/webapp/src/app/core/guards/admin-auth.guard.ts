import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdminAuthGuard implements CanActivate {

  constructor(private userContext: UserContextService) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot):
      Observable<boolean> | Promise<boolean> | boolean {

    return this.checkAdmin();
  }

  private checkAdmin(): Observable<boolean> {

    return this.userContext.getUser().pipe(
      map(user => user.isAdmin())
    );
  }
}

