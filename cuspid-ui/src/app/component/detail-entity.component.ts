import { Component, Input } from '@angular/core';
import { CuspidEntity } from '../core/constant/constant';

@Component({
  selector: 'app-detail-entity',
  templateUrl: 'detail-entity.component.html',
  styleUrls: ['detail-entity.component.scss']
})
export class DetailEntityComponent {
  @Input() cuspidEntity: CuspidEntity;

  constructor() {

  }
}
