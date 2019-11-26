import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { FacilitySiteService } from 'src/app/core/services/facility-site.service';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { EditEmissionUnitInfoPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-emission-unit-info-panel/edit-emission-unit-info-panel.component';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-emission-unit-info',
  templateUrl: './emission-unit-info.component.html',
  styleUrls: ['./emission-unit-info.component.scss']
})
export class EmissionUnitInfoComponent implements OnInit {
  @Input() emissionsUnit: EmissionUnit;
  
  editInfo = false;
  facilitySiteId: number;
  eisProgramId: string;
  unitId: number;
  reportId: number;

  @ViewChild(EditEmissionUnitInfoPanelComponent, { static: false })
  private infoComponent: EditEmissionUnitInfoPanelComponent;

  constructor(private route: ActivatedRoute,
              private facilitySiteService: FacilitySiteService,
              private unitService: EmissionUnitService) { }

  ngOnInit() {
      this.route.paramMap
      .subscribe(map => {
        this.eisProgramId = map.get('facilityId');
      });
      this.route.paramMap
      .subscribe(map => {
        this.reportId = parseInt(map.get('reportId'));
      });
      this.route.paramMap
      .subscribe(map => {
        this.unitId = parseInt(map.get('unitId'));
      });

      this.facilitySiteService.retrieveForReport(this.eisProgramId,this.reportId)
      .subscribe(result => {
        this.facilitySiteId = result.id;
      })
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
        this.setEditInfo(false);
      });
    }
  }

}
