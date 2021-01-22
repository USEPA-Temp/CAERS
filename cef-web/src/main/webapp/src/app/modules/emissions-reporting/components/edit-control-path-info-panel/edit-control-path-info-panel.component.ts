import { Component, OnInit, Input } from '@angular/core';
import { ControlPath } from 'src/app/shared/models/control-path';
import {FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import { ControlPathService } from "../../../../core/services/control-path.service";
import {FacilitySiteService} from "../../../../core/services/facility-site.service";
import {LookupService} from "../../../../core/services/lookup.service";
import {ActivatedRoute} from "@angular/router";
import {FacilitySite} from "../../../../shared/models/facility-site";

@Component({
  selector: 'app-edit-control-path-info-panel',
  templateUrl: './edit-control-path-info-panel.component.html',
  styleUrls: ['./edit-control-path-info-panel.component.scss']
})
export class EditControlPathInfoPanelComponent implements OnInit {
  @Input() controlPath: ControlPath;
  pathIds: string[] = [];

    controlPathForm = this.fb.group({
    pathId: ['', [Validators.required, Validators.maxLength(20)]],
    description: ['', [Validators.required, Validators.maxLength(200)]],
    }, {validators: [
        this.pathIdCheck()
        ]});

  constructor(private fb: FormBuilder, private ctrlPathSvc: ControlPathService, private route: ActivatedRoute) { }

  ngOnInit() {

      this.route.data.subscribe((data: {facilitySite: FacilitySite}) => {
          this.ctrlPathSvc.retrieveForFacilitySite(data.facilitySite.id).subscribe(controlPaths => {
              console.log(controlPaths);
              if (controlPaths) {
                  for (const path of controlPaths) {
                      this.pathIds.push(path.pathId)
                  }
                  console.log(this.pathIds)
              }
          })
      });
      console.log(this.controlPath)

  }

  ngOnChanges() {
    this.controlPathForm.reset(this.controlPath);
  }

  pathIdCheck(): ValidatorFn {
      return (control: FormGroup): ValidationErrors | null => {
          for (const id of this.pathIds) {
              if (control.get('pathId').value === id) {
                  return {duplicatePathId: true};
              }
          }
          return null;
      }
  }


}
