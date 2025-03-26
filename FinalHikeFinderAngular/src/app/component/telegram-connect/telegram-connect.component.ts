import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../model/user';
import { UserService } from '../../service/user.service';
import { interval, map, Observable, take } from 'rxjs';

@Component({
  selector: 'app-telegram-connect',
  standalone: false,
  templateUrl: './telegram-connect.component.html',
  styleUrl: './telegram-connect.component.css'
})
export class TelegramConnectComponent implements OnInit{
  usernameLoggedIn = sessionStorage.getItem("loggedIn") ?? ""
  userService = inject(UserService)
  router = inject(Router)
  user!: User;
  overideDeactivateGuard = false
  uuid!: string  
  startCount = 30;
  countdown$!: Observable<number> 


  ngOnInit(): void {
    this.getUser()
  }

  get teleSetUpDone(){
    this.getUser()
    console.log(this.user)
    if (this.user.telegramUsername && this.user.chatId){
      console.log("setup done")
      return true
    }
    console.log("setup not done")
    return false
  }


  logout(){
    this.overideDeactivateGuard = true
    sessionStorage.clear()
    this.router.navigate([''])
  }


  getUser(){
    const username = this.usernameLoggedIn
    this.userService.getUser(username).subscribe(
      (user) => this.user=user
    )
  }


  getOTP(){
    
    this.userService.getUUID(this.usernameLoggedIn).subscribe(
      (response) => {
        this.startCountdown()
        this.uuid = response.uuid
      }
    )
  }

  startCountdown() {
    this.countdown$ = interval(1000).pipe(
      take(this.startCount + 1),       // Emit (startCount + 1) times, including 0
      map(val => this.startCount - val)  // Map the emitted value to countdown value
    );
  }



}
