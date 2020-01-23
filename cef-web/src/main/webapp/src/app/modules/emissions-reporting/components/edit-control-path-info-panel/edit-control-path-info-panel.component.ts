import { Component, OnInit, Input } from '@angular/core';
import { ControlPath } from 'src/app/shared/models/control-path';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-control-path-info-panel',
  templateUrl: './edit-control-path-info-panel.component.html',
  styleUrls: ['./edit-control-path-info-panel.component.scss']
})
export class EditControlPathInfoPanelComponent implements OnInit {
  @Input() controlPath: ControlPath;

    controlPathForm = this.fb.group({
    pathId: ['', [Validators.required, Validators.maxLength(20)]],
    description: ['', [Validators.required, Validators.maxLength(200)]],
    });

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
  }

  ngOnChanges() {
    this.controlPathForm.reset(this.controlPath);
  }
}
