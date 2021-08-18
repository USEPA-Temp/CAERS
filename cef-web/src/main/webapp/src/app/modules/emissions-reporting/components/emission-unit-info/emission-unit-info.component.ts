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
  @Input() readOnlyMode: boolean;
  editInfo = false;
  unitId: number;

  @ViewChild(EditEmissionUnitInfoPanelComponent)
  infoComponent: EditEmissionUnitInfoPanelComponent;

  constructor(private route: ActivatedRoute,
              private unitService: EmissionUnitService,
              private sharedService: SharedService) { }

  ngOnInit() {
      this.route.paramMap
      .subscribe(map => {
        this.unitId = parseInt(map.get('unitId'));
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

      this.unitService.update(updatedUnit)
      .subscribe(result => {

        Object.assign(this.emissionsUnit, result);
        this.sharedService.updateReportStatusAndEmit(this.route);
        this.setEditInfo(false);
      });
    }
  }

}
