import { User } from 'src/app/shared/models/user';
import { UserService } from 'src/app/core/services/user.service';
import { Injectable } from '@angular/core';
import { Observable, of } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserContextService {
  user: User;

  constructor(private userService: UserService) {
    this.loadUser();
  }

  loadUser(): void {
    this.userService.getCurrentUser()
    .subscribe(currentUser => this.user = currentUser);
  }

  handoffToCdx(whereTo): Observable<any> {
      return this.userService.initHandoffToCdx(whereTo);
  }

  getUser():Observable<User>{
      if(this.user){
          return of(this.user);
      }else{
          return this.userService.getCurrentUser();
      }
  }
}
