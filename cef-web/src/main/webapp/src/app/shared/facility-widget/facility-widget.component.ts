import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { UserToken } from 'src/app/model/user-token';
import { Facility } from 'src/app/model/facility';
import { ActivatedRoute } from '@angular/router';

declare const initFrsWidget: any;

@Component({
  selector: 'app-facility-widget',
  templateUrl: './facility-widget.component.html',
  styleUrls: ['./facility-widget.component.scss']
})
export class FacilityWidgetComponent implements OnInit {
   userToken: UserToken;

  constructor(private userService: UserService, private route: ActivatedRoute) { }

  ngOnInit() {
      this.route.data
      .subscribe((data: { facility: Facility }) => {
            console.log(data.facility);
            this.userService.getCurrentUserNaasToken()
            .subscribe(userToken => initFrsWidget(userToken.userRoleId, userToken.token,
              data.facility.programId, userToken.baseServiceUrl));
        });
  }

}
