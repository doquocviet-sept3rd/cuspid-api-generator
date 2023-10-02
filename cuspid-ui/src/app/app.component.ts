import { Component } from '@angular/core';
import { CUSPID_ENTITIES, CuspidEntity } from './core/constant/constant';
import { Location } from '@angular/common';
import { StringsUtil } from './core/util/strings.util';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  protected readonly CUSPID_ENTITIES: CuspidEntity[] = CUSPID_ENTITIES;
  currentEntity: CuspidEntity;

  constructor(
    private readonly _location: Location
  ) {
    this.currentEntity = this.CUSPID_ENTITIES[0];
  }

  onCuspidEntityClick(cuspidEntity: CuspidEntity): void {
    // Only change the current url without reloading the application
    this._location.replaceState(StringsUtil.getRoute(cuspidEntity.id));
    this.currentEntity = cuspidEntity;
  }

}
