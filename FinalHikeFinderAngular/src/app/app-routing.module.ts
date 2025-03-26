import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HikespotlistComponent } from './component/hikespotlist/hikespotlist.component';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { HosthikeComponent } from './component/hosthike/hosthike.component';
import { canLeaveTeleSetUp, canProceedToPage } from './guards/login-guard';
import { HostedhikesComponent } from './component/hostedhikes/hostedhikes.component';
import { PersonalhostedhikesComponent } from './component/personalhostedhikes/personalhostedhikes.component';
import { TelegramConnectComponent } from './component/telegram-connect/telegram-connect.component';

const routes: Routes = [
  {path:"", component: LoginComponent},
  {path:"register", component: RegisterComponent},
  {path:"telegramconnect", component: TelegramConnectComponent, canActivate:[canProceedToPage], canDeactivate: [canLeaveTeleSetUp]},
  {path:"home", component: HikespotlistComponent, canActivate:[canProceedToPage]},
  {path:"hosthike/:hikeSpotId", component: HosthikeComponent, canActivate:[canProceedToPage]},
  {path:"hostedhikes", component:HostedhikesComponent, canActivate:[canProceedToPage]},
  {path:"personalhostedhikes", component:PersonalhostedhikesComponent, canActivate:[canProceedToPage]},
  {path:"**", redirectTo:"/", pathMatch:"full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
