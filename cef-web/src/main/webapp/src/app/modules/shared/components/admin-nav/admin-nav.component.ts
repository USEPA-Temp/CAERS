import { Component, OnInit } from '@angular/core';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { User } from 'src/app/shared/models/user';

@Component({
  selector: 'app-admin-nav',
  templateUrl: './admin-nav.component.html',
  styleUrls: ['./admin-nav.component.scss']
})
export class AdminNavComponent implements OnInit {

  user: User;

  constructor(private userContext: UserContextService) { }

  ngOnInit() {
    this.userContext.getUser()
    .subscribe(result => {
      this.user = result;
    });
  }

}
