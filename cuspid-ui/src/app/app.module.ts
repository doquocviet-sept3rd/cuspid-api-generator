import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppTranslateModule } from './app.translate';

/**
 * @author Do Quoc Viet
 */

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AppTranslateModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
