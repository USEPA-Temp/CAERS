import { SortableHeaderDirective, SortEvent } from 'src/app/shared/directives/sortable.directive';
import { ViewChildren, QueryList, Directive, AfterViewInit, DoCheck, AfterContentInit } from '@angular/core';
import { TableController } from 'src/app/shared/utils/table-controller';

@Directive()
export abstract class BaseSortableTable implements DoCheck, AfterContentInit, AfterViewInit {

  controller = new TableController<any>();

  tableData: any[];
  tableItems: any[];
  @ViewChildren(SortableHeaderDirective) headers: QueryList<SortableHeaderDirective>;

  // override to enable filtering
  matchFunction: (item: any, searchTerm: any) => boolean;

  ngDoCheck() {
    // check if tableData has changed to update the controller
    const check = this.controller.tableData === this.tableData;
    if (!check) {
      this.resortTable();
    }
  }

  ngAfterContentInit() {
    // initiate table
    this.initTable();
  }

  ngAfterViewInit() {
    // set headers after they've loaded
    this.controller.headers = this.headers;
  }

  initTable() {
    this.controller.tableData = this.tableData;
    if (this.matchFunction) {
      this.controller.matchFunction = this.matchFunction;
    }
    this.controller.init();
    this.controller.items$.subscribe(items => {
      this.tableItems = items;
    });
  }

  onSort({column, direction}: SortEvent) {

    this.controller.onSort({column, direction});
  }

  resortTable() {

    this.controller.tableData = this.tableData;
    this.controller.refreshTable();
  }

}
