import { Component, OnInit } from '@angular/core';
import { AppProperty } from 'src/app/shared/models/app-property';
import { Validators, FormControl, FormBuilder } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AdminPropertyService } from 'src/app/core/services/admin-property.service';

@Component({
  selector: 'app-admin-announcement-properties',
  templateUrl: './admin-announcement-properties.component.html',
  styleUrls: ['./admin-announcement-properties.component.scss']
})
export class AdminAnnouncementPropertiesComponent implements OnInit {

  announcementEnabled: AppProperty;
  announcementText: AppProperty;

  propertyForm = this.fb.group({
    enabled: [false, Validators.required],
    text: ['', Validators.maxLength(2000)]
  });

  constructor(
      private propertyService: AdminPropertyService,
      private fb: FormBuilder,
      private toastr: ToastrService) { }

  ngOnInit() {

    this.propertyService.retrieve('feature.announcement.enabled')
    .subscribe(result => {

      this.propertyForm.get('enabled').reset(result.value === 'true');
      this.announcementEnabled = result;

    });

    this.propertyService.retrieve('feature.announcement.text')
    .subscribe(result => {

      this.propertyForm.get('text').reset(result.value);
      this.announcementText = result;

    });
  }

  onSubmit() {
    if (!this.propertyForm.valid) {
      this.propertyForm.markAllAsTouched();
    } else {

      const updatedProperties: AppProperty[] = [];
      updatedProperties.push(Object.assign(new AppProperty(),
          {name: 'feature.announcement.enabled', value: this.propertyForm.value.enabled}));
      updatedProperties.push(Object.assign(new AppProperty(),
          {name: 'feature.announcement.text', value: this.propertyForm.value.text}));

      this.propertyService.bulkUpdate(updatedProperties)
      .subscribe(result => {
        console.log(result);
        this.toastr.success('', 'Properties updated successfully. Changes will appear after a refresh.');
      });

    }
  }

}
