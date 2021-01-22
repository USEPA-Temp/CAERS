import { Component, OnInit } from '@angular/core';
import { UserFacilityAssociationService } from 'src/app/core/services/user-facility-association.service';
import { UserFacilityAssociation } from 'src/app/shared/models/user-facility-association';

@Component({
  selector: 'app-pending-user-facility-associations',
  templateUrl: './pending-user-facility-associations.component.html',
  styleUrls: ['./pending-user-facility-associations.component.scss']
})
export class PendingUserFacilityAssociationsComponent implements OnInit {
  pendingAssociations: UserFacilityAssociation[] = [];

  constructor(private userFacilityAssociationService: UserFacilityAssociationService) { }

  ngOnInit(): void {
    this.userFacilityAssociationService.getPendingAssociationDetails()
    .subscribe(result => {
      this.pendingAssociations = result;
    });
  }

}
