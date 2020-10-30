import { Component, OnInit, OnChanges, Input, Output, EventEmitter } from '@angular/core';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { EmissionService } from 'src/app/core/services/emission.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormControl, Validators, ValidatorFn, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BulkEntryEmissionHolder } from 'src/app/shared/models/bulk-entry-emission-holder';
import { Emission } from 'src/app/shared/models/emission';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { OperatingStatus } from 'src/app/shared/enums/operating-status';

@Component({
  selector: 'app-bulk-entry-emissions-table',
  templateUrl: './bulk-entry-emissions-table.component.html',
  styleUrls: ['./bulk-entry-emissions-table.component.scss']
})
export class BulkEntryEmissionsTableComponent extends BaseSortableTable implements OnInit, OnChanges {
  @Input() tableData: BulkEntryEmissionHolder[];
  @Input() readOnlyMode: boolean;
  @Output() emissionsUpdated = new EventEmitter<BulkEntryEmissionHolder[]>();
  baseUrl: string;
  facilitySite: FacilitySite;

  emissionForm = this.fb.group({});

  operatingStatus = OperatingStatus;

  constructor(
      private emissionService: EmissionService,
      private route: ActivatedRoute,
      private fb: FormBuilder,
      private toastr: ToastrService,
      public formUtils: FormUtilsService) {
    super();
  }

  ngOnInit() {

    this.route.paramMap
    .subscribe(map => {
      this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}`;
    });

    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {

      this.facilitySite = data.facilitySite;
    });
  }

  ngOnChanges() {
    this.tableData.sort((a, b) => {

      if (a.unitIdentifier === b.unitIdentifier) {
        return a.emissionsProcessIdentifier > b.emissionsProcessIdentifier ? 1 : -1;
      }
      return a.unitIdentifier > b.unitIdentifier ? 1 : -1;
    });

    this.tableData.forEach(rp => {
      rp.emissions.sort((a, b) => (a.pollutant.pollutantName > b.pollutant.pollutantName) ? 1 : -1);
    });

    this.tableData.forEach(rp => {
      rp.emissions.forEach(e => {
        if ((e.totalManualEntry || e.emissionsCalcMethodCode.totalDirectEntry)
            && !(rp.operatingStatusCode.code === OperatingStatus.TEMP_SHUTDOWN
              || rp.operatingStatusCode.code === OperatingStatus.PERM_SHUTDOWN)) {
          if (!this.emissionForm.contains('' + e.id)) {
            const fc = new FormControl({
                value: e.totalEmissions,
                disabled: false
              }, {
                  updateOn: 'blur'
              });
            fc.setValidators([
              Validators.required,
              Validators.min(0),
              this.checkSignificantFigures(fc)
            ]);
            this.emissionForm.setControl('' + e.id, fc);
          }
        } else {
          // use setControl to make sure new values are applied
          const fc = new FormControl({
              value: e.totalEmissions,
              disabled: true
            }, {
                updateOn: 'blur'
            });
          fc.setValidators([
            Validators.required,
            Validators.min(0),
            this.checkSignificantFigures(fc)
          ]);
          this.emissionForm.setControl('' + e.id, fc);
        }
      });
    });
  }

  onSubmit() {
    if (!this.emissionForm.valid) {
      this.emissionForm.markAllAsTouched();
    } else {

      const emissionDtos: Emission[] = [];

      for (const [key, value] of Object.entries(this.emissionForm.value)) {

        const e = new Emission();
        e.id = +key;
        e.totalEmissions = +value;
        emissionDtos.push(e);
      }

      this.emissionService.bulkUpdate(this.facilitySite.id, emissionDtos)
      .subscribe(result => {
        this.emissionsUpdated.emit(result);
        this.toastr.success('', 'Changes successfully saved and Total Emissions recalculated.');
        // reset dirty flags
        this.emissionForm.markAsPristine();
      });

    }
  }


  checkSignificantFigures(totalEmissions: FormControl): ValidatorFn {
      return (control: FormGroup): {[key: string]: any} | null => {
          if (totalEmissions.value && this.formUtils.getSignificantFigures(totalEmissions.value) > this.formUtils.emissionsMaxSignificantFigures) {
              return {significantFiguresInvalid: true};
            }
          return null;
    };
  }

}
