import { Component, OnInit } from '@angular/core';
import { MasterFacilityRecordService } from 'src/app/core/services/master-facility-record.service';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';

@Component({
  selector: 'app-master-facility-information',
  templateUrl: './master-facility-information.component.html',
  styleUrls: ['./master-facility-information.component.scss']
})
export class MasterFacilityInformationComponent implements OnInit {

  records: MasterFacilityRecord[] = [];
  selectedFacility: MasterFacilityRecord;

  constructor(private mfrService: MasterFacilityRecordService) { }

  ngOnInit(): void {

    this.mfrService.getMyRecords()
    .subscribe(result =>
      this.records = result.sort((a, b) => (a.name > b.name) ? 1 : -1)
      );
  }

  onFacilitySelected(facility: MasterFacilityRecord) {

    this.selectedFacility = facility;
  }

}
