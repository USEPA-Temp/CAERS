import { UserContextService } from './services/user-context.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(public userContext: UserContextService) {
    userContext.loadUser();
  }
}
