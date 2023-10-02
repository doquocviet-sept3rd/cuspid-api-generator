import { NgModule } from '@angular/core';
import { TableComponent } from './component/table/table.component';
import { NgForOf } from '@angular/common';
import { PaginationComponent } from './component/pagination/pagination.component';

@NgModule({
  declarations: [
    TableComponent,
    PaginationComponent
  ],
  imports: [
    NgForOf
  ],
  exports: [
    TableComponent,
    PaginationComponent
  ]
})
export class SharedModule {
}
