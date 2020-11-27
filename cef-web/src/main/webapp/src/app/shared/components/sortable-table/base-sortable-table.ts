import { SortableHeaderDirective, compare, SortEvent, SortDirection } from 'src/app/shared/directives/sortable.directive';
import { ViewChildren, QueryList, Directive } from '@angular/core';

@Directive()
export abstract class BaseSortableTable {
  tableData: any[];
  @ViewChildren(SortableHeaderDirective) headers: QueryList<SortableHeaderDirective>;

  private sortColumn: string;
  private sortDirection: SortDirection;


  onSort({column, direction}: SortEvent) {

    // resetting other headers
    if (this.headers) {

      this.headers.forEach(header => {
        if (header.sortable !== column) {
          header.direction = '';
        }
      });
    }

    // sorting data
    if (direction !== '') {
      this.tableData = [...this.tableData].sort((a, b) => {
        let aVal = a;
        let bVal = b;
        // flatten out nested references
        for (const colName of column.split('.')) {
          aVal = aVal[colName];
          bVal = bVal[colName];
        }
        const res = compare(aVal, bVal);
        return direction === 'asc' ? res : -res;
      });
    }

    this.sortColumn = column;
    this.sortDirection = direction;
  }

  resortTable() {

    this.onSort({column: this.sortColumn, direction: this.sortDirection});
  }
}
