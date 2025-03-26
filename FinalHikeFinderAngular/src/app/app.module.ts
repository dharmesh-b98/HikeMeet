import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HikespotlistComponent } from './component/hikespotlist/hikespotlist.component';
import { provideHttpClient } from '@angular/common/http';
import { LoginComponent } from './component/login/login.component';
import {ReactiveFormsModule} from '@angular/forms';
import { RegisterComponent } from './component/register/register.component';
import { GoogleMapsModule } from '@angular/google-maps';

import { NgxsModule } from '@ngxs/store';
import { NgxsLoggerPluginModule } from '@ngxs/logger-plugin';
import { NgxsReduxDevtoolsPluginModule } from '@ngxs/devtools-plugin';


import { HikespotsmapComponent } from './component/hikespotsmap/hikespotsmap.component';
import { HosthikeComponent } from './component/hosthike/hosthike.component';
import { HostedhikesComponent } from './component/hostedhikes/hostedhikes.component';
import { PersonalhostedhikesComponent } from './component/personalhostedhikes/personalhostedhikes.component';
import { AppState } from './ngxs-store/hikestore';
import { TelegramConnectComponent } from './component/telegram-connect/telegram-connect.component';
import { MaterialModule } from './material/material/material.module';

@NgModule({
  declarations: [
    AppComponent,
    HikespotlistComponent,
    LoginComponent,
    RegisterComponent,
    HikespotsmapComponent,
    HosthikeComponent,
    HostedhikesComponent,
    PersonalhostedhikesComponent,
    TelegramConnectComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    GoogleMapsModule,
    NgxsModule.forRoot([AppState]),
    NgxsLoggerPluginModule.forRoot(),
    NgxsReduxDevtoolsPluginModule.forRoot(),
    MaterialModule
  ],
  providers: [
    provideHttpClient()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
