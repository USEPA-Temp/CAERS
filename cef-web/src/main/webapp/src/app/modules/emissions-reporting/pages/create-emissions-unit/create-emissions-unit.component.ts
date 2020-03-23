import { Component, OnInit, ViewChild } from '@angular/core';
import { EditEmissionUnitInfoPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-emission-unit-info-panel/edit-emission-unit-info-panel.component';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { FacilitySiteService } from 'src/app/core/services/facility-site.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { SharedService } from 'src/app/core/services/shared.service';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';


@Component({
  selector: 'app-create-emissions-unit',
  templateUrl: './create-emissions-unit.component.html',
  styleUrls: ['./create-emissions-unit.component.scss']
})
export class CreateEmissionsUnitComponent implements OnInit {


  @ViewChild(EditEmissionUnitInfoPanelComponent, { static: true })
  private infoComponent: EditEmissionUnitInfoPanelComponent;

  facilitySiteId: number;
  eisProgramId: string;
  reportId: number;

  constructor(private emissionUnitService: EmissionUnitService,
              private router: Router,
              private route: ActivatedRoute,
              private sharedService: SharedService,
              private facilitySiteService: FacilitySiteService) { }

  ngOnInit() {
      this.route.paramMap
      .subscribe(map => {
        this.eisProgramId = map.get('facilityId');
      });
      this.route.paramMap
      .subscribe(map => {
        this.reportId = parseInt(map.get('reportId'));
      });

      this.facilitySiteService.retrieveForReport(this.eisProgramId, this.reportId)
      .subscribe(result => {
        this.facilitySiteId = result.id;
      });
  }

  isValid() {
    return this.infoComponent.emissionUnitForm.valid;
  }

  onSubmit() {
        if (!this.isValid()) {
          this.infoComponent.emissionUnitForm.markAllAsTouched();
        }
        else {
          const emissionUnit = new EmissionUnit();

          Object.assign(emissionUnit, this.infoComponent.emissionUnitForm.value);
          emissionUnit.facilitySiteId = this.facilitySiteId;
          emissionUnit.unitIdentifier = this.infoComponent.emissionUnitForm.controls.unitIdentifier.value.trim();

          this.emissionUnitService.create(emissionUnit)
          .subscribe(result => {
            this.sharedService.updateReportStatusAndEmit(this.route);
            this.router.navigate(['../..'], { relativeTo: this.route });
          });
        }
  }

}
