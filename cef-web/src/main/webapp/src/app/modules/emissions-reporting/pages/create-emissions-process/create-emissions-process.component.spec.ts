import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEmissionsProcessComponent } from './create-emissions-process.component';

describe('CreateEmissionsProcessComponent', () => {
  let component: CreateEmissionsProcessComponent;
  let fixture: ComponentFixture<CreateEmissionsProcessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEmissionsProcessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEmissionsProcessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
