import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { LookupService } from 'src/app/core/services/lookup.service';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';

@Component({
  selector: 'app-edit-emission-unit-info-panel',
  templateUrl: './edit-emission-unit-info-panel.component.html',
  styleUrls: ['./edit-emission-unit-info-panel.component.scss']
})
export class EditEmissionUnitInfoPanelComponent implements OnInit, OnChanges {
  @Input() emissionUnit: EmissionUnit;
  emissionUnitForm = this.fb.group({
    unitTypeCode: [null, Validators.required],
    operatingStatusCode: [null, Validators.required],
    unitOfMeasureCode: [null, Validators.required],
    unitIdentifier: ['', [
      Validators.required,
      Validators.maxLength(20)
    ]],
    statusYear: ['', [
      Validators.required,
      Validators.min(1900),
      Validators.max(2050),
      Validators.pattern('[0-9]*')
    ]],
    designCapacity: ['', [
      Validators.required,
      Validators.maxLength(20)
    ]],
    description: ['', [
      Validators.required,
      Validators.maxLength(500)
    ]],
    comments: ['', Validators.maxLength(2000)]
  });

  operatingStatusValues: BaseCodeLookup[];
  unitTypeValues: BaseCodeLookup[];
  uomValues: UnitMeasureCode[];

  constructor(private fb: FormBuilder,
              private lookupService: LookupService,
              public formUtils: FormUtilsService,
              ) { }

  ngOnInit() {
    
    this.lookupService.retrieveUom()
    .subscribe(result => {
      this.uomValues = result;
    });

    this.lookupService.retrieveOperatingStatus()
    .subscribe(result => {
      this.operatingStatusValues = result;
    });

    this.lookupService.retrieveUnitType()
    .subscribe(result => {
        result.sort((a, b) => {
          if (a.description < b.description) {
            return -1;
          }
          if (a.description > b.description) {
            return 1;
          }
            return 0;
        }
        );
      this.unitTypeValues = result;
    });
    
  }

  ngOnChanges() {
    this.emissionUnitForm.reset(this.emissionUnit);
  }
}
