import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarComponent } from './sidebar.component';
import { CollapseNavComponent } from './collapse-nav/collapse-nav.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from "src/app/shared/shared.module";
import { RouterTestingModule } from '@angular/router/testing';


describe('SidebarComponent', () => {
  let component: SidebarComponent;
  let fixture: ComponentFixture<SidebarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SidebarComponent, CollapseNavComponent ],
      imports: [NgbModule, SharedModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
