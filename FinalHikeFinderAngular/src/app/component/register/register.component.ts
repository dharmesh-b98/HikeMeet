import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterService } from '../../service/register.service';
import { User } from '../../model/user';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  fb = inject(FormBuilder)
  registerService = inject(RegisterService)
  router = inject(Router)

  form!: FormGroup
  user!: User

  errorMessage: string = ""
  

  
  ngOnInit(): void {
    this.createForm()
  }
  
  createForm(){
    this.form = this.fb.group({
      username: this.fb.control<string>("", [Validators.required, Validators.minLength(5), Validators.maxLength(15)]),
      password: this.fb.control<string>("", [Validators.required, Validators.pattern("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")])
    })
  }

  registerUser(){
    this.user = this.form.value
      this.registerService.registerUser(this.user).subscribe({
        next: (data) => {
          this.errorMessage = ""
          console.log(data.reply)
          this.router.navigate(["/"])
        },
        error: (error) =>{
          this.errorMessage = error.error.reply
          console.log(this.errorMessage)
        }     
      })   
  }
  
  checkFormGroupInvalid(){
    return this.form.invalid
  }

}
