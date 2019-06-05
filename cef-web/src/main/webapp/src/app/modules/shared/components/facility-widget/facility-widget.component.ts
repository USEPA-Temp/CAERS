import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/core/services/user.service';
import { UserToken } from 'src/app/shared/models/user-token';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
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
      .subscribe((data: { facility: CdxFacility }) => {
            console.log(data.facility);
            this.userService.getCurrentUserNaasToken()
            .subscribe(userToken => initFrsWidget(userToken.userRoleId, userToken.token,
              data.facility.programId, userToken.baseServiceUrl));
        });
  }

}
