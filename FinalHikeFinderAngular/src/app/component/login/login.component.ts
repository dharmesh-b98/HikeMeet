import { Component, inject, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../../service/login.service';
import { User } from '../../model/user';
import { Router } from '@angular/router';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

  fb = inject(FormBuilder)
  loginService = inject(LoginService)
  router = inject(Router)
  userService = inject(UserService)

  form!: FormGroup
  loginStatus: boolean = false
  
  ngOnInit(): void {
    this.createForm()
  }
  
  createForm(){
    this.form = this.fb.group({
      username: this.fb.control<string>("", [Validators.required]),
      password: this.fb.control<string>("", [Validators.required]),
    })
  }

  checkFormGroupInvalid(){
    return this.form.invalid
  }
  

  checkCredentials(){
    let user = this.form.value as User
    this.loginService.checkCredentials(user).subscribe(
      (data) => {
        console.log(data)
        this.loginStatus = data
        if (this.loginStatus == true){
          sessionStorage.setItem("loggedIn", user.username);
          this.userService.getUser(user.username).subscribe(
            (freshUser) => {
              if (freshUser.telegramUsername){
                this.router.navigate(["/home"])
              }
              else{
                this.router.navigate(["/telegramconnect"])
              }
            }
          )
          
        }
        else {
          alert("Wrong Credentials")
        }
      }
    )
  }

}
