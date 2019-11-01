import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DeleteDialogComponent } from 'src/app/shared/components/delete-dialog/delete-dialog.component';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { SharedService } from 'src/app/core/services/shared.service';

@Component({
  selector: 'app-emissions-unit-table',
  templateUrl: './emissions-unit-table.component.html',
  styleUrls: ['./emissions-unit-table.component.scss']
})
export class EmissionsUnitTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: EmissionUnit[];
  baseUrl: string;

  constructor(private modalService: NgbModal,
              private emissionUnitService: EmissionUnitService,
              private route: ActivatedRoute,
              private sharedService: SharedService) {
    super();
  }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}/${BaseReportUrl.EMISSIONS_UNIT}`;
    });
  }

  openDeleteModal(emissionUnitName: string, emissionUnitId: number, facilitySiteId: number) {
        const modalMessage = `Are you sure you want to delete ${emissionUnitName} from this facility? This will also remove 
          any Emission Process, Emissions, Control Assignments, and Release Point Assignments associated with this Emissions Unit.`;
        const modalRef = this.modalService.open(DeleteDialogComponent, { size: 'sm' });
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
    });
  }
}
