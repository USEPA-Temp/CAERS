import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReleasePointApportionmentModalComponent } from './release-point-apportionment-modal.component';

describe('ReleasePointApportionmentModalComponent', () => {
  let component: ReleasePointApportionmentModalComponent;
  let fixture: ComponentFixture<ReleasePointApportionmentModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReleasePointApportionmentModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReleasePointApportionmentModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
