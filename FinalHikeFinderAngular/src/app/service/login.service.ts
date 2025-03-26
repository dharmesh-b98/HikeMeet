import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { User } from '../model/user';


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  httpClient = inject(HttpClient)
  backendUrl = environment.backendUrl


  checkCredentials(user: User): Observable<boolean>{
    return this.httpClient.post<boolean>(this.backendUrl + "/login", user)
  }
}
