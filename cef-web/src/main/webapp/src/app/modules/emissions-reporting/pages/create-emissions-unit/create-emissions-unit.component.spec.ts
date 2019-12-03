import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEmissionsUnitComponent } from './create-emissions-unit.component';

describe('CreateEmissionsUnitComponent', () => {
  let component: CreateEmissionsUnitComponent;
  let fixture: ComponentFixture<CreateEmissionsUnitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEmissionsUnitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEmissionsUnitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
