import {Component, OnInit} from '@angular/core';
import {UserContextService} from "src/app/core/services/user-context.service";
import {User} from "src/app/shared/models/user";

@Component({
  selector: 'app-horizontal-nav',
  templateUrl: './horizontal-nav.component.html',
  styleUrls: ['./horizontal-nav.component.scss']
})
export class HorizontalNavComponent implements OnInit {

  isCollapsed = true;

  user: User;

  constructor(private userContext: UserContextService) { }

  ngOnInit(): void {

    this.userContext.getUser()
       .subscribe(result => {
          this.user = result;
       });
  }

}
