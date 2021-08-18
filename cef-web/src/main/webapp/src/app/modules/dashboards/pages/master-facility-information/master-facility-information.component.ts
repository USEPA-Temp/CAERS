/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
import { Component, OnInit, ViewChild } from '@angular/core';
import { MasterFacilityRecordService } from 'src/app/core/services/master-facility-record.service';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';
import { EditMasterFacilityInfoComponent } from 'src/app/modules/dashboards/components/edit-master-facility-info/edit-master-facility-info.component';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

@Component({
  selector: 'app-master-facility-information',
  templateUrl: './master-facility-information.component.html',
  styleUrls: ['./master-facility-information.component.scss']
})
export class MasterFacilityInformationComponent implements OnInit {

  records: MasterFacilityRecord[] = [];
  selectedFacility: MasterFacilityRecord;
  editInfo = false;
  addFacility = false;
  programSystemCode: BaseCodeLookup;

  @ViewChild(EditMasterFacilityInfoComponent)
  private masterFacilityRecordComponent: EditMasterFacilityInfoComponent;

  constructor(private mfrService: MasterFacilityRecordService) { }

  ngOnInit(): void {
      this.refreshFacilityList();
      this.mfrService.getUserProgramSystemCode()
        .subscribe(result =>
                this.programSystemCode = result
            );
  }

  refreshFacilityList() {
    this.mfrService.getMyProgramRecords()
    .subscribe(result =>
      this.records = result.sort((a, b) => (a.name > b.name) ? 1 : -1)
      );
  }

  onFacilitySelected(facility: MasterFacilityRecord) {
    this.setEditInfo(false);
    this.setAddFacility(false);
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

  setAddFacility(value: boolean) {
    this.addFacility = value;
  }

  onCancelEdit() {
    if (this.addFacility) {
        this.selectedFacility = null;
    }
    this.setEditInfo(false); 
    this.setAddFacility(false);
  }

  updateMasterFacilityRecord() {

      if (!this.masterFacilityRecordComponent.facilitySiteForm.valid) {
        this.masterFacilityRecordComponent.facilitySiteForm.markAllAsTouched();
      } else {
        const updatedMasterFacility = new MasterFacilityRecord();
        Object.assign(updatedMasterFacility, this.masterFacilityRecordComponent.facilitySiteForm.value);
        if (!this.addFacility) {
          updatedMasterFacility.id = this.selectedFacility.id;
          updatedMasterFacility.eisProgramId = this.selectedFacility.eisProgramId;
          updatedMasterFacility.programSystemCode = this.selectedFacility.programSystemCode;

          this.mfrService.update(updatedMasterFacility)
            .subscribe(result => {
              Object.assign(this.selectedFacility, result);
              this.setEditInfo(false);
              this.setAddFacility(false);
          });
        } else {
            updatedMasterFacility.programSystemCode = this.programSystemCode;
  
            this.mfrService.add(updatedMasterFacility)
              .subscribe(result => {
                Object.assign(this.selectedFacility, result);
                this.setEditInfo(false);
                this.setAddFacility(false);
                this.refreshFacilityList();
            });
        }
      }
  }

  addMasterFacilityRecord() {
      const emptyMfr: MasterFacilityRecord = new MasterFacilityRecord();
      emptyMfr.agencyFacilityId = '';
      this.selectedFacility = emptyMfr;
      this.setEditInfo(false);
      this.setAddFacility(true);
  }


}
