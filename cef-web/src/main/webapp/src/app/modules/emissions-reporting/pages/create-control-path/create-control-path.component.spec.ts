import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateControlPathComponent } from './create-control-path.component';

describe('CreateControlPathComponent', () => {
  let component: CreateControlPathComponent;
  let fixture: ComponentFixture<CreateControlPathComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateControlPathComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateControlPathComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
