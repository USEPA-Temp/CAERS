import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecalculateEmissionTonsModalComponent } from './recalculate-emission-tons-modal.component';

describe('RecalculateEmissionTonsModalComponent', () => {
  let component: RecalculateEmissionTonsModalComponent;
  let fixture: ComponentFixture<RecalculateEmissionTonsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecalculateEmissionTonsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecalculateEmissionTonsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
