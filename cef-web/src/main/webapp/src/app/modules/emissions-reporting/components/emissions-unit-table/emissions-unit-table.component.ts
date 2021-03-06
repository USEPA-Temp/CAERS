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
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { SharedService } from 'src/app/core/services/shared.service';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { UtilityService } from 'src/app/core/services/utility.service';

@Component({
  selector: 'app-emissions-unit-table',
  templateUrl: './emissions-unit-table.component.html',
  styleUrls: ['./emissions-unit-table.component.scss']
})
export class EmissionsUnitTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: EmissionUnit[];
  @Input() createUrl = '.';
  readOnlyMode = true;
  baseUrl: string;
  faPlus = faPlus;

  constructor(private modalService: NgbModal,
              private emissionUnitService: EmissionUnitService,
              private userContextService: UserContextService,
              private route: ActivatedRoute,
              private sharedService: SharedService) {
    super();
  }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}/${BaseReportUrl.EMISSIONS_UNIT}`;
    });

    this.route.data
      .subscribe((data: { facilitySite: FacilitySite }) => {
        this.userContextService.getUser().subscribe( user => {
          if (UtilityService.isNotReadOnlyMode(user, data.facilitySite.emissionsReport.status)) {
            this.readOnlyMode = false;
          }
        });
      });
  }

  openDeleteModal(emissionUnitName: string, emissionUnitId: number, facilitySiteId: number) {
    const modalMessage = `Are you sure you want to delete ${emissionUnitName} from this facility? This will also remove 
      any Emission Process, Emissions, Control Assignments, and Release Point Assignments associated with this Emissions Unit.`;
    const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.continue.subscribe(() => {
        this.deleteEmissionUnit(emissionUnitId, facilitySiteId);
    });
  }

  // delete an emission unit from the database
  deleteEmissionUnit(emissionUnitId: number, facilitySiteId: number) {
    this.emissionUnitService.delete(emissionUnitId).subscribe(() => {

      // update the UI table with the current list of emission units
      this.emissionUnitService.retrieveForFacility(facilitySiteId)
        .subscribe(emissionUnitResponse => {
          this.tableData = emissionUnitResponse;
        });

      // emit the facility data back to the sidebar to reflect the updated
      // list of emission units
      this.route.data
        .subscribe((data: { facilitySite: FacilitySite }) => {
            this.sharedService.emitChange(data.facilitySite);
        });
      this.sharedService.updateReportStatusAndEmit(this.route);
    }, error => {
      if (error.error && error.status === 422) {
        const modalRef = this.modalService.open(ConfirmationDialogComponent);
        modalRef.componentInstance.message = error.error.message;
        modalRef.componentInstance.singleButton = true;
      }
    });
  }
}
