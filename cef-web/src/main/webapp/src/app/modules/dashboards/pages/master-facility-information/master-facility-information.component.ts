import { Component, OnInit, ViewChild } from '@angular/core';
import { MasterFacilityRecordService } from 'src/app/core/services/master-facility-record.service';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';
import { EditMasterFacilityInfoComponent } from 'src/app/modules/dashboards/components/edit-master-facility-info/edit-master-facility-info.component';

@Component({
  selector: 'app-master-facility-information',
  templateUrl: './master-facility-information.component.html',
  styleUrls: ['./master-facility-information.component.scss']
})
export class MasterFacilityInformationComponent implements OnInit {

  records: MasterFacilityRecord[] = [];
  selectedFacility: MasterFacilityRecord;
  editInfo = false;

  @ViewChild(EditMasterFacilityInfoComponent)
  private masterFacilityRecordComponent: EditMasterFacilityInfoComponent;

  constructor(private mfrService: MasterFacilityRecordService) { }

  ngOnInit(): void {

    this.mfrService.getMyRecords()
    .subscribe(result =>
      this.records = result.sort((a, b) => (a.name > b.name) ? 1 : -1)
      );
  }

  onFacilitySelected(facility: MasterFacilityRecord) {
    this.setEditInfo(false);
    this.selectedFacility = facility;
  }

  onEditClick(facility: MasterFacilityRecord) {
      this.setEditInfo(true);
      this.selectedFacility = facility;
      console.log(`agencyId: ${this.selectedFacility.agencyFacilityId}`)
  }

  setEditInfo(value: boolean) {
    this.editInfo = value;
  }

  updateMasterFacilityRecord() {

      if (!this.masterFacilityRecordComponent.facilitySiteForm.valid) {
        this.masterFacilityRecordComponent.facilitySiteForm.markAllAsTouched();
      } else {
        const updatedMasterFacility = new MasterFacilityRecord();
        Object.assign(updatedMasterFacility, this.masterFacilityRecordComponent.facilitySiteForm.value);
        updatedMasterFacility.id = this.selectedFacility.id;
        updatedMasterFacility.eisProgramId = this.selectedFacility.eisProgramId;
        updatedMasterFacility.programSystemCode = this.selectedFacility.programSystemCode;

        this.mfrService.update(updatedMasterFacility)
            .subscribe(result => {
                Object.assign(this.selectedFacility, result);
                this.setEditInfo(false);
            });
      }
  }


}
