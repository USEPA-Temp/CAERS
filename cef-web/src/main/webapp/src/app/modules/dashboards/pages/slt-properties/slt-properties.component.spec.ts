import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SltPropertiesComponent } from './slt-properties.component';

describe('SltPropertiesComponent', () => {
  let component: SltPropertiesComponent;
  let fixture: ComponentFixture<SltPropertiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SltPropertiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SltPropertiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
