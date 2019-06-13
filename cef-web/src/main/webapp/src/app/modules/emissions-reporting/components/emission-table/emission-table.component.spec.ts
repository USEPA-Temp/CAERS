import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionTableComponent } from './emission-table.component';

describe('EmissionTableComponent', () => {
  let component: EmissionTableComponent;
  let fixture: ComponentFixture<EmissionTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
