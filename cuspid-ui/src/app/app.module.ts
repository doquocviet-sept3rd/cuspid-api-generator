import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppTranslateModule } from './app.translate';
import { SharedModule } from './shared/shared.module';
import { DetailEntityComponent } from './component/detail-entity.component';

/**
 * @author Do Quoc Viet
 */

@NgModule({
  declarations: [
    AppComponent,
    DetailEntityComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AppTranslateModule,
    SharedModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
