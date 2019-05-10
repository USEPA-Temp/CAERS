import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionProcessListComponent } from './emission-process-list.component';

describe('EmissionProcessListComponent', () => {
  let component: EmissionProcessListComponent;
  let fixture: ComponentFixture<EmissionProcessListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionProcessListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionProcessListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
