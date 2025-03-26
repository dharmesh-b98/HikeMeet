import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { User } from '../model/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  httpClient = inject(HttpClient)
  backendUrl = environment.backendUrl

  getUser(username: string): Observable<User>{
    return this.httpClient.get<User>(this.backendUrl + "/user/" + username)
  }

  getUUID(username: string): Observable<any>{
    return this.httpClient.get<any>(this.backendUrl + "/user/getUUID/" + username)
  }

}
