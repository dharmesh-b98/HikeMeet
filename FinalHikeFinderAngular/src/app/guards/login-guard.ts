import { inject } from "@angular/core"
import { ActivatedRouteSnapshot, CanActivateFn, CanDeactivateFn, Router, RouterStateSnapshot } from "@angular/router"
import { CheckLogin } from "./check-login"
import { TelegramConnectComponent } from "../component/telegram-connect/telegram-connect.component"

export const canProceedToPage: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    // boolean Promise<boolea>, Observable<boolean>
    // UrlTree Promise<UrlTree> Observable<UrlTree>
    const checkLogin = inject(CheckLogin)
    const router = inject(Router)
  
    if (!checkLogin.checkLogin())
      return router.parseUrl('/login')
  
    return true
}


export const canLeaveTeleSetUp: CanDeactivateFn<TelegramConnectComponent> = (telegramConnectComponent: TelegramConnectComponent,
  route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {

  // boolean Promise<boolea>, Observable<boolean>
  // UrlTree Promise<UrlTree> Observable<UrlTree>
  if (telegramConnectComponent.teleSetUpDone || telegramConnectComponent.overideDeactivateGuard)
    return true

  else{
    const router = inject(Router)
    return router.parseUrl('/telegramconnect')
  }
}
