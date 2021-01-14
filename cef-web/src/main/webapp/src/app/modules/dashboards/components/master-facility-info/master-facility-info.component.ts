import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';
import { UserFacilityAssociationService } from 'src/app/core/services/user-facility-association.service';
import { UserFacilityAssociation } from 'src/app/shared/models/user-facility-association';

@Component({
  selector: 'app-master-facility-info',
  templateUrl: './master-facility-info.component.html',
  styleUrls: ['./master-facility-info.component.scss']
})
export class MasterFacilityInfoComponent implements OnInit, OnChanges {
  @Input() facility: MasterFacilityRecord;
  userFacilityAssociations: UserFacilityAssociation[];

  constructor(private userFacilityAssociationService: UserFacilityAssociationService) { }

  ngOnInit(): void {
  }

  ngOnChanges() {

    this.userFacilityAssociationService.getAssociationDetailsForFacility(this.facility.id)
    .subscribe(result => {
      this.userFacilityAssociations = result;
    });

  }

}
