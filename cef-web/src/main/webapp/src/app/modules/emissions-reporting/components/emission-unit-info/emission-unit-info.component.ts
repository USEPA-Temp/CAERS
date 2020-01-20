import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { FacilitySiteService } from 'src/app/core/services/facility-site.service';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { EditEmissionUnitInfoPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-emission-unit-info-panel/edit-emission-unit-info-panel.component';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { ReportStatus } from 'src/app/shared/enums/report-status';

@Component({
  selector: 'app-emission-unit-info',
  templateUrl: './emission-unit-info.component.html',
  styleUrls: ['./emission-unit-info.component.scss']
})
export class EmissionUnitInfoComponent implements OnInit {
  @Input() emissionsUnit: EmissionUnit;
  @Input() facilitySiteId: number;
  @Input() emissionsReport: ReportStatus;
  editInfo = false;
  readOnlyMode = true;
  unitId: number;

  @ViewChild(EditEmissionUnitInfoPanelComponent, { static: false })
  private infoComponent: EditEmissionUnitInfoPanelComponent;

  constructor(private route: ActivatedRoute,
              private unitService: EmissionUnitService,
              private sharedService: SharedService) { }

  ngOnInit() {
      this.route.paramMap
      .subscribe(map => {
        this.unitId = parseInt(map.get('unitId'));
      });

      this.route.data
      .subscribe(data => {
        this.readOnlyMode = ReportStatus.IN_PROGRESS !== data.facilitySite.emissionsReport.status;
      });
  }

  setEditInfo(value: boolean) {
    this.editInfo = value;
  }

  updateUnit() {
    if (!this.infoComponent.emissionUnitForm.valid) {
      this.infoComponent.emissionUnitForm.markAllAsTouched();
    } else {
      const updatedUnit = new EmissionUnit();

      Object.assign(updatedUnit, this.infoComponent.emissionUnitForm.value);
      updatedUnit.facilitySiteId = this.facilitySiteId;
      updatedUnit.id = this.unitId;
      updatedUnit.programSystemCode = this.emissionsUnit.programSystemCode;

      this.unitService.update(updatedUnit)
      .subscribe(result => {

        Object.assign(this.emissionsUnit, result);
        this.sharedService.updateReportStatusAndEmit(this.route);
        this.setEditInfo(false);
      });
    }
  }

}
