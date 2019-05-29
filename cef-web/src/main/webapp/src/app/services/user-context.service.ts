import { User } from '../model/user';
import { UserService } from './user.service';
import { Injectable } from '@angular/core';

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
}
