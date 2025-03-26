import { Injectable } from "@angular/core";

@Injectable({
    providedIn:"root"
})
export class CheckLogin{
    
    checkLogin(){
        let loggedIn = sessionStorage.getItem("loggedIn")
        if (loggedIn){
            return true
        }
        return false
    }
}