import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EditReleasePointPanelComponent } from '../../components/edit-release-point-panel/edit-release-point-panel.component';
import { ReleasePointService } from 'src/app/core/services/release-point.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ReleasePoint } from 'src/app/shared/models/release-point';

@Component({
  selector: 'app-create-release-point',
  templateUrl: './create-release-point.component.html',
  styleUrls: ['./create-release-point.component.scss']
})
export class CreateReleasePointComponent implements OnInit {
  @Input() facilitySite: FacilitySite;

  @ViewChild(EditReleasePointPanelComponent, { static: true })
  private releasePointComponent: EditReleasePointPanelComponent;

  constructor(
    private releasePointService: ReleasePointService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {

    this.route.data
    .subscribe(data => {
      this.facilitySite = data.facilitySite;
    });

  }

  isValid() {
    return this.releasePointComponent.releasePointForm.valid;
  }

  onSubmit() {

    if (!this.isValid()) {
      this.releasePointComponent.releasePointForm.markAllAsTouched();
    } else {
      const saveReleasePoint = new ReleasePoint();

      Object.assign(saveReleasePoint, this.releasePointComponent.releasePointForm.value);
      saveReleasePoint.facilitySiteId = this.facilitySite.id;

      this.releasePointService.create(saveReleasePoint)
      .subscribe(result => {
        console.log(saveReleasePoint.id);
        this.router.navigate(['./..'], { relativeTo: this.route });
      });
    }

  }

}
