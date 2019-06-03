import { User } from 'src/app/shared/models/user';
import { UserService } from 'src/app/core/http/user/user.service';
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
