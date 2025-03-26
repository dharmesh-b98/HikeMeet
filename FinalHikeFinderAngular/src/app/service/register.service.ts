import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { User } from '../model/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  httpClient = inject(HttpClient)
  backendUrl = environment.backendUrl

  registerUser(user: User): Observable<any>{
    return this.httpClient.post<any>(this.backendUrl+"/login/create",user)
  }
}
